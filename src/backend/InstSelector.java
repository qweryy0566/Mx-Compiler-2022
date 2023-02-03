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
  int blockCnt = 0;

  HashMap<IRBasicBlock, ASMBlock> blockMap = new HashMap<>();

  static HashMap<Integer, Integer> log2 = new HashMap<>() {
    {
      for (int i = 0; i < 31; ++i) put(1 << i, i);
    }
  };

  public InstSelector(ASMModule module) {
    this.module = module;
  }

  Reg immToReg(VirtualImm imm) {
    VirtualReg reg = new VirtualReg(4);
    curBlock.addInst(new ASMLiInst(reg, imm));
    return reg;
  }

  Reg getReg(IREntity entity) {
    if (entity.asmReg == null) {
      if (entity instanceof IRRegister)
        entity.asmReg = new VirtualReg(entity.type.size);
      else if (entity instanceof IRConst)
        return ((IRConst) entity).isZero() ? PhysicsReg.get("zero") : immToReg(new VirtualImm((IRConst) entity));
    } else if (entity.asmReg instanceof Global) {
      VirtualReg reg = new VirtualReg(4);
      String name = ((Global) entity.asmReg).name;
      curBlock.addInst(new ASMLuiInst(reg, new RelocationFunc(RelocationFunc.Type.hi, name)));
      curBlock.addInst(new ASMUnaryInst("addi", reg, reg, new RelocationFunc(RelocationFunc.Type.lo, name)));
      return reg;
    }
    return entity.asmReg;
  }

  void storeReg(int size, Reg value, Reg dest, int offset) {
    if (offset < 1 << 11)
      curBlock.addInst(new ASMStoreInst(size, dest, value, new Imm(offset)));
    else {
      VirtualReg tmp = new VirtualReg(4);
      curBlock.addInst(new ASMBinaryInst("add", tmp, dest, immToReg(new VirtualImm(offset))));
      curBlock.addInst(new ASMStoreInst(size, tmp, value));
    }
  }

  void loadReg(int size, Reg dest, Reg src, int offset) {
    if (offset < 1 << 11)
      curBlock.addInst(new ASMLoadInst(size, dest, src, new Imm(offset)));
    else {
      VirtualReg tmp = new VirtualReg(4);
      curBlock.addInst(new ASMBinaryInst("add", tmp, src, immToReg(new VirtualImm(offset))));
      curBlock.addInst(new ASMLoadInst(size, dest, tmp));
    }
  }

  public void visit(IRProgram node) {
    // add global vars
    for (var globalVar : node.globalVarList) {
      globalVar.asmReg = new GlobalValue(globalVar);
      module.globalValues.add((GlobalValue) globalVar.asmReg);
    }
    // add global strings
    for (var str : node.stringConst.values()) {
      GlobalString globalStr = new GlobalString(".str." + String.valueOf(str.id), str.val);
      module.globalStrings.add(globalStr);
      str.asmReg = globalStr;
    }
    if (node.initFunc != null) {
      curFunc = new ASMFunction(node.initFunc.name);
      module.functions.add(curFunc);
      node.initFunc.accept(this);
    }
    for (var func : node.funcList) {
      curFunc = new ASMFunction(func.name);
      module.functions.add(curFunc);
      func.accept(this);
    }
  }

  public void visit(IRFunction node) {
    // add params
    VirtualReg.cnt = 0;
    // find max argument cnt
    int maxArgCnt = 0;
    for (IRBasicBlock blk : node.blocks) {
      blockMap.put(blk, new ASMBlock(".L" + blockCnt++, blk.loopDepth));
      for (IRInst inst : blk.insts)
        if (inst instanceof IRCallInst)
          maxArgCnt = Math.max(maxArgCnt, ((IRCallInst) inst).args.size());
    }
    curFunc.paramUsed = (maxArgCnt > 8 ? maxArgCnt - 8 : 0) << 2;
    // set params
    for (int i = 0; i < node.params.size() && i < 8; ++i)
      node.params.get(i).asmReg = new VirtualReg(node.params.get(i).type.size);

    for (int i = 0; i < node.blocks.size(); ++i) {
      curBlock = blockMap.get(node.blocks.get(i));
      if (i == 0)
        storeReg(4, PhysicsReg.get("ra"), PhysicsReg.get("sp"), curFunc.paramUsed);
      node.blocks.get(i).accept(this);
      curFunc.addBlock(curBlock);
    }
    curFunc.entryBlock = curFunc.blocks.get(0);
    curFunc.exitBlock = curFunc.blocks.get(curFunc.blocks.size() - 1);
    for (int i = 0; i < node.params.size() && i < 8; ++i)
      curFunc.entryBlock.insts.addFirst(new ASMMvInst(node.params.get(i).asmReg, PhysicsReg.get("a" + i)));
    // add callee save
    if (!node.name.equals("main"))
      for (var reg : PhysicsReg.calleeSave) {
        VirtualReg storeReg = new VirtualReg(4);
        curFunc.entryBlock.insts.addFirst(new ASMMvInst(storeReg, reg));
        curFunc.exitBlock.insts.addLast(new ASMMvInst(reg, storeReg));
      }
    curFunc.virtualRegCnt = VirtualReg.cnt;
    for (var block : curFunc.blocks) {
      block.insts.addAll(block.phiConvert);
      block.insts.addAll(block.jumpOrBr);
    }
  }

  public void visit(IRBasicBlock node) {
    for (var inst : node.insts)
      if (inst != node.insts.getLast())
        inst.accept(this);
    if (node.terminalInst instanceof IRBranchInst brInst && node.insts.getLast() instanceof IRIcmpInst cmpInst
        && brInst.getUse().contains(cmpInst.cmpReg)) {
      combineCmpAndBranch(cmpInst, brInst);
    } else {
      if (!node.insts.isEmpty())
        node.insts.getLast().accept(this);
      node.terminalInst.accept(this);
    }
  }

  void combineCmpAndBranch(IRIcmpInst cmpInst, IRBranchInst brInst) {
    String op = "";
    switch (cmpInst.op) {
      case "eq": op = "bne"; break;
      case "ne": op = "beq"; break;
      case "sgt": op = "ble"; break;
      case "sge": op = "blt"; break;
      case "slt": op = "bge"; break;
      case "sle": op = "bgt"; break;
    }
    curBlock.addInst(new ASMBrCmpInst(op, getReg(cmpInst.lhs), getReg(cmpInst.rhs), blockMap.get(brInst.elseBlock)));
    curBlock.succ.add(blockMap.get(brInst.elseBlock));
    blockMap.get(brInst.elseBlock).pred.add(curBlock);
    curBlock.addInst(new ASMJumpInst(blockMap.get(brInst.thenBlock)));
    curBlock.succ.add(blockMap.get(brInst.thenBlock));
    blockMap.get(brInst.thenBlock).pred.add(curBlock);
  }

  /*
   * 将带有 param_idx 且 >= 8 的 allocaInst 替换为
   * li reg, ParaImm(param_idx - 8 << 2)
   * add allocaReg, sp, reg
   * 且不占用栈的 alloca 区
   */
  public void visit(IRAllocaInst node) {
    if (node.param_idx < 8) {
      int offset = curFunc.paramUsed + curFunc.allocaUsed;
      if (offset < 1 << 11)
        curBlock.addInst(new ASMUnaryInst("addi", getReg(node.allocaReg), PhysicsReg.get("sp"), new Imm(offset)));
      else
        curBlock.addInst(new ASMBinaryInst("add", getReg(node.allocaReg), PhysicsReg.get("sp"),
            immToReg(new VirtualImm(offset))));
      curFunc.allocaUsed += 4;
    } else {
      VirtualReg reg = new VirtualReg(4);
      curBlock.addInst(new ASMLiInst(reg, new StackImm(curFunc, node.param_idx - 8 << 2)));
      curBlock.addInst(new ASMBinaryInst("add", getReg(node.allocaReg), PhysicsReg.get("sp"), reg));
    }
  }

  public void visit(IRBranchInst node) {
    curBlock.addInst(new ASMBeqzInst(getReg(node.cond), blockMap.get(node.elseBlock)));
    curBlock.succ.add(blockMap.get(node.elseBlock));
    blockMap.get(node.elseBlock).pred.add(curBlock);
    curBlock.addInst(new ASMJumpInst(blockMap.get(node.thenBlock)));
    curBlock.succ.add(blockMap.get(node.thenBlock));
    blockMap.get(node.thenBlock).pred.add(curBlock);
  }

  public void visit(IRCalcInst node) {
    switch (node.op) {
      case "add":
      case "and":
      case "or":
      case "xor":
        if (node.lhs instanceof IRIntConst) {
          IREntity tmp = node.lhs;
          node.lhs = node.rhs;
          node.rhs = tmp;
        }
      case "shl":
      case "ashr":
        if (node.rhs instanceof IRIntConst intConst && intConst.val < 1 << 11 && intConst.val >= -(1 << 11))
          curBlock.addInst(new ASMUnaryInst(node.op + "i", getReg(node.res), getReg(node.lhs), new Imm(intConst.val)));
        else
          curBlock.addInst(new ASMBinaryInst(node.op, getReg(node.res), getReg(node.lhs), getReg(node.rhs)));
        break;
      case "sub":
        if (node.rhs instanceof IRIntConst intConst && intConst.val <= 1 << 11 && intConst.val > -(1 << 11))
          curBlock.addInst(new ASMUnaryInst("addi", getReg(node.res), getReg(node.lhs), new Imm(-intConst.val)));
        else
          curBlock.addInst(new ASMBinaryInst(node.op, getReg(node.res), getReg(node.lhs), getReg(node.rhs)));
        break;
      case "mul":
        if (node.lhs instanceof IRIntConst intConst && log2.containsKey(intConst.val)) {
          IREntity tmp = node.lhs;
          node.lhs = node.rhs;
          node.rhs = tmp;
        }
        if (node.rhs instanceof IRIntConst intConst && log2.containsKey(intConst.val))
          curBlock.addInst(new ASMUnaryInst("slli", getReg(node.res), getReg(node.lhs), new Imm(log2.get(intConst.val))));
        else
          curBlock.addInst(new ASMBinaryInst(node.op, getReg(node.res), getReg(node.lhs), getReg(node.rhs)));
        break;
      case "sdiv":
        if (node.rhs instanceof IRIntConst intConst && log2.containsKey(intConst.val))
          curBlock.addInst(new ASMUnaryInst("srai", getReg(node.res), getReg(node.lhs), new Imm(log2.get(intConst.val))));
        else
          curBlock.addInst(new ASMBinaryInst(node.op, getReg(node.res), getReg(node.lhs), getReg(node.rhs)));
        break;
      default:
        curBlock.addInst(new ASMBinaryInst(node.op, getReg(node.res), getReg(node.lhs), getReg(node.rhs)));
    }
  }

  public void visit(IRCallInst node) {
    ASMCallInst callInst = new ASMCallInst(node.funcName);
    for (int i = 0; i < node.args.size(); ++i) {
      IREntity arg = node.args.get(i);
      if (i < 8) {
        curBlock.addInst(new ASMMvInst(PhysicsReg.get("a" + i), getReg(arg)));
        callInst.addUse(PhysicsReg.get("a" + i));
      } else
        storeReg(arg.type.size, getReg(arg), PhysicsReg.get("sp"), i - 8 << 2);
    }
    curBlock.addInst(callInst);
    if (node.callReg != null)
      curBlock.addInst(new ASMMvInst(getReg(node.callReg), PhysicsReg.get("a0")));
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
      if (idx == PhysicsReg.get("zero"))
        curBlock.addInst(new ASMMvInst(getReg(node.res), getReg(node.ptr)));
      else {
        curBlock.addInst(new ASMUnaryInst("slli", tmp, idx, new Imm(2)));
        curBlock.addInst(new ASMBinaryInst("add", getReg(node.res), getReg(node.ptr), tmp));
      }
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
    curBlock.succ.add(blockMap.get(node.toBlock));
    blockMap.get(node.toBlock).pred.add(curBlock);
  }

  public void visit(IRLoadInst node) {
    if (node.srcAddr.asmReg instanceof Global global) {
      String name = global.name;
      Reg reg = getReg(node.destReg);
      curBlock.addInst(new ASMLuiInst(reg, new RelocationFunc(RelocationFunc.Type.hi, name)));
      curBlock.addInst(new ASMLoadInst(node.type.size, reg, reg, new RelocationFunc(RelocationFunc.Type.lo, name)));
    } else
      loadReg(node.type.size, getReg(node.destReg), getReg(node.srcAddr), 0);
  }

  public void visit(IRRetInst node) {
    // ret val -> load val to a0 and return
    if (node.val != irVoidConst)
      curBlock.addInst(new ASMMvInst(PhysicsReg.get("a0"), getReg(node.val)));
    loadReg(4, PhysicsReg.get("ra"), PhysicsReg.get("sp"), curFunc.paramUsed);
    // 寄存器分配完再加 ret
  }

  // 忽略 param_idx 且 >= 8 的 storeInst
  public void visit(IRStoreInst node) {
    // store : rs2 -> (rs1) address
    if (node.param_idx >= 8)
      return;
    if (node.destAddr.asmReg instanceof Global global) {
      String name = global.name;
      VirtualReg reg = new VirtualReg(4);
      curBlock.addInst(new ASMLuiInst(reg, new RelocationFunc(RelocationFunc.Type.hi, name)));
      curBlock.addInst(new ASMStoreInst(node.val.type.size, reg, getReg(node.val),
          new RelocationFunc(RelocationFunc.Type.lo, name)));
    } else
      storeReg(node.val.type.size, getReg(node.val), getReg(node.destAddr), 0);
  }

  public void visit(IRPhiInst node) {
    VirtualReg tmp = new VirtualReg(node.dest.type.size);
    curBlock.addInst(new ASMMvInst(getReg(node.dest), tmp));
    for (int i = 0; i < node.values.size(); ++i) {
      IREntity val = node.values.get(i);
      if (val instanceof IRConst constVal)
        blockMap.get(node.blocks.get(i)).phiConvert.add(new ASMLiInst(tmp, new VirtualImm(constVal)));
      else
        blockMap.get(node.blocks.get(i)).phiConvert.add(new ASMMvInst(tmp, getReg(node.values.get(i))));
    }
  }
}