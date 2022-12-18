package backend;

import java.util.HashMap;

import IR.*;
import IR.inst.*;
import IR.entity.*;
import IR.type.*;
import assembly.*;
import assembly.operand.*;
import utils.*;
import assembly.inst.*;

public class InstSelector implements IRVisitor, BuiltinElements {
  ASMModule module;
  ASMFunction curFunc;
  ASMBlock curBlock;

  HashMap<IRBasicBlock, ASMBlock> blockMap = new HashMap<>();

  public InstSelector(ASMModule module) {
    this.module = module;
  }

  Reg getReg(IREntity entity) {
    if (entity.asmReg == null) {
      assert !(entity instanceof IRGlobalVar);
      assert !(entity instanceof IRStringConst);
      if (entity instanceof IRRegister) {
        entity.asmReg = new VirtualReg(entity.type.size);
      } else if (entity instanceof IRConst) {
        entity.asmReg = new VirtualImm((IRConst) entity);
      }
    }
    return entity.asmReg;
  }
  void storeReg(int size, Reg value, Reg dest, int offset) {
    if (offset < 1 << 11)
      curBlock.addInst(new ASMStoreInst(size, dest, value, new Imm(offset)));
    else {
      VirtualReg tmp = new VirtualReg(4);
      curBlock.addInst(new ASMBinaryInst("add", tmp, dest, new VirtualImm(offset)));
      curBlock.addInst(new ASMStoreInst(size, tmp, value));
    }
  }
  void loadReg(int size, Reg dest, Reg src, int offset) {
    if (offset < 1 << 11)
      curBlock.addInst(new ASMLoadInst(size, dest, src, new Imm(offset)));
    else {
      VirtualReg tmp = new VirtualReg(4);
      curBlock.addInst(new ASMBinaryInst("add", tmp, src, new VirtualImm(offset)));
      curBlock.addInst(new ASMLoadInst(size, dest, tmp));
    }
  }

  public void visit(IRProgram node) {
    // add global vars
    node.globalVarList.forEach(globalVar -> {
      globalVar.asmReg = new GlobalValue(globalVar);
      module.globalValues.add((GlobalValue) globalVar.asmReg);
    });
    node.stringConst.values().forEach(str -> {
      GlobalString globalStr = new GlobalString(".str." + String.valueOf(str.id), str.val);
      module.globalStrings.add(globalStr);
      str.asmReg = globalStr;
    });
    node.funcList.forEach(func -> {
      curFunc = new ASMFunction(func.name);
      module.functions.add(curFunc);
      func.accept(this);
    });
  }

  public void visit(IRFunction node) {
    // add params
    blockMap.clear();
    VirtualReg.cnt = 0;
    // find max argument cnt
    int maxArgCnt = 0;
    for (IRBasicBlock blk : node.blocks) {
      blockMap.put(blk, new ASMBlock("." + blk.name));
      for (IRInst inst : blk.insts)
        if (inst instanceof IRCallInst)
          maxArgCnt = Math.max(maxArgCnt, ((IRCallInst) inst).args.size());
    }
    curFunc.paramUsed = (maxArgCnt > 8 ? maxArgCnt - 8 : 0) << 2;

    for (int i = 0; i < node.blocks.size(); ++i) {
      curBlock = blockMap.get(node.blocks.get(i));
      if (i == 0)
        storeReg(4, PhysicsReg.regMap.get("ra"), PhysicsReg.regMap.get("sp"), curFunc.paramUsed);
      node.blocks.get(i).accept(this);
      curFunc.addBlock(curBlock);
    }
    curFunc.virtualRegCnt = VirtualReg.cnt;
    // set stack frame
    curFunc.totalStack = curFunc.paramUsed + curFunc.allocaUsed + curFunc.virtualRegCnt * 4;
    // if (curFunc.totalStack % 16 != 0)
    //   curFunc.totalStack += 16 - curFunc.totalStack % 16;
    ASMBlock entryBlock = curFunc.blocks.get(0), exitBlock = curFunc.blocks.get(curFunc.blocks.size() - 1);
    entryBlock.insts.addFirst(new ASMBinaryInst("add", PhysicsReg.regMap.get("sp"), PhysicsReg.regMap.get("sp"),
        new VirtualImm(-curFunc.totalStack)));
    exitBlock.insts.add(new ASMBinaryInst("add", PhysicsReg.regMap.get("sp"), PhysicsReg.regMap.get("sp"),
        new VirtualImm(curFunc.totalStack)));
    exitBlock.insts.add(new ASMRetInst());
    // set params
    for (int i = 0; i < node.params.size(); ++i)
      if (i < 8)
        node.params.get(i).asmReg = PhysicsReg.regMap.get("a" + i);
      else
        node.params.get(i).asmReg = new VirtualReg(i);
  }

  public void visit(IRBasicBlock node) {
    node.insts.forEach(inst -> {
      inst.accept(this);
    });
    node.terminalInst.accept(this);
  }

  public void visit(IRAllocaInst node) {
    curBlock.addInst(new ASMBinaryInst("add", getReg(node.allocaReg), PhysicsReg.regMap.get("sp"),
        new VirtualImm(curFunc.paramUsed + curFunc.allocaUsed)));
    curFunc.allocaUsed += 4;
  }

  public void visit(IRBranchInst node) {
    curBlock.addInst(new ASMBeqzInst(getReg(node.cond), blockMap.get(node.elseBlock)));
    curBlock.addInst(new ASMJumpInst(blockMap.get(node.thenBlock)));
  }

  public void visit(IRCalcInst node) {
    curBlock.addInst(new ASMBinaryInst(node.op, getReg(node.res), getReg(node.lhs), getReg(node.rhs)));
  }

  public void visit(IRCallInst node) {
    for (int i = 0; i < node.args.size(); ++i) {
      IREntity arg = node.args.get(i);
      if (i < 8)
        curBlock.addInst(new ASMMvInst(PhysicsReg.regMap.get("a" + i), getReg(arg)));
      else
        storeReg(arg.type.size, getReg(arg), PhysicsReg.regMap.get("sp"), i - 8 << 2);
    }
    curBlock.addInst(new ASMCallInst(node.funcName));
    if (node.callReg != null)
      curBlock.addInst(new ASMMvInst(getReg(node.callReg), PhysicsReg.regMap.get("a0")));
  }

  public void visit(IRCastInst node) {
    curBlock.addInst(new ASMMvInst(getReg(node.dest), getReg(node.val)));
  }

  public void visit(IRGetElementPtrInst node) {
    if (node.pToType == irBoolType) {
      curBlock.addInst(new ASMBinaryInst("add", getReg(node.res), getReg(node.ptr), getReg(node.indexList.get(0))));
    } else {
      Reg idx = node.pToType instanceof IRStructType ? getReg(node.indexList.get(1)) : getReg(node.indexList.get(0));
      VirtualReg tmp = new VirtualReg(4);
      curBlock.addInst(new ASMUnaryInst("slli", tmp, idx, new Imm(2)));
      curBlock.addInst(new ASMBinaryInst("add", getReg(node.res), getReg(node.ptr), tmp));
    }
  }

  public void visit(IRIcmpInst node) {
    // LLVM_IR: eq, ne, sgt, sge, slt, sle
    // RISCV32_ASM: seqz, snez, slt
    VirtualReg tmp = new VirtualReg(4);
    switch (node.op) {
      case "eq":
        curBlock.addInst(new ASMBinaryInst("sub", tmp, getReg(node.lhs), getReg(node.rhs)));
        curBlock.addInst(new ASMUnaryInst("seqz", getReg(node.cmpReg), tmp));
        break;
      case "ne":
        curBlock.addInst(new ASMBinaryInst("sub", tmp, getReg(node.lhs), getReg(node.rhs)));
        curBlock.addInst(new ASMUnaryInst("snez", getReg(node.cmpReg), tmp));
        break;
      case "sgt":
        curBlock.addInst(new ASMBinaryInst("slt", getReg(node.cmpReg), getReg(node.rhs), getReg(node.lhs)));
        break;
      case "sge":
        curBlock.addInst(new ASMBinaryInst("slt", tmp, getReg(node.lhs), getReg(node.rhs)));
        curBlock.addInst(new ASMUnaryInst("xori", getReg(node.cmpReg), tmp, new Imm(1)));
        break;
      case "slt":
        curBlock.addInst(new ASMBinaryInst("slt", getReg(node.cmpReg), getReg(node.lhs), getReg(node.rhs)));
        break;
      case "sle":
        curBlock.addInst(new ASMBinaryInst("slt", tmp, getReg(node.rhs), getReg(node.lhs)));
        curBlock.addInst(new ASMUnaryInst("xori", getReg(node.cmpReg), tmp, new Imm(1)));
        break;
    }
  }

  public void visit(IRJumpInst node) {
    curBlock.addInst(new ASMJumpInst(blockMap.get(node.toBlock)));
  }

  public void visit(IRLoadInst node) {
    loadReg(node.type.size, getReg(node.destReg), getReg(node.srcAddr), 0);
  }

  public void visit(IRRetInst node) {
    // ret val -> load val to a0 and return
    if (node.val != null)
      curBlock.addInst(new ASMMvInst(PhysicsReg.regMap.get("a0"), getReg(node.val)));
    loadReg(4, PhysicsReg.regMap.get("ra"), PhysicsReg.regMap.get("sp"), curFunc.paramUsed);
    // 寄存器分配完再加 ret
  }

  public void visit(IRStoreInst node) {
    // store : rs2 -> (rs1) address
    storeReg(node.val.type.size, getReg(node.val), getReg(node.destAddr), 0);
  }
}