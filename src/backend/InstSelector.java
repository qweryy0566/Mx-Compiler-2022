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

  Reg getReg(IREntity entity) {
    if (entity.asmReg == null) {
      assert entity instanceof IRGlobalVar;
      assert entity instanceof IRStringConst;
      if (entity instanceof IRRegister) {
        entity.asmReg = new VirtualReg();
      } else if (entity instanceof IRConst) {
        entity.asmReg = new VirtualImm((IRConst) entity);
      }
    }
    return entity.asmReg;
  }

  public void visit(IRProgram node) {

    // add global vars
    node.globalVarList.forEach(globalVar -> {
      globalVar.asmReg = new GlobalValue(globalVar);
      module.globalValues.add((GlobalValue) globalVar.asmReg);
    });
    node.stringConst.values().forEach(str -> {
      GlobalString globalStr = new GlobalString(".str" + String.valueOf(str.id), str.val);
      module.globalStrings.add(globalStr);
      str.asmReg = globalStr;
    });
  }
  public void visit(IRFunction node) {
    // TODO: add params
    blockMap.clear();
    for (int i = 0; i < node.blocks.size(); ++i) {
      curBlock = new ASMBlock("." + node.blocks.get(i).name);
      node.blocks.get(i).accept(this);
      curFunc.addBlock(curBlock);
    }
  }
  public void visit(IRBasicBlock node) {
    blockMap.put(node, curBlock);
    node.insts.forEach(inst -> {
      inst.accept(this);
    });
  }

  public void visit(IRAllocaInst node) {
    
  }
  public void visit(IRBranchInst node) {
    curBlock.addInst(new ASMBeqzInst(getReg(node.cond), blockMap.get(node.elseBlock)));
    curBlock.addInst(new ASMJumpInst(blockMap.get(node.thenBlock)));
  }
  public void visit(IRCalcInst node) {
    curBlock.addInst(new ASMBinaryInst(node.op, getReg(node.res), getReg(node.lhs), getReg(node.rhs)));
  }
  public void visit(IRCallInst node) {

  }
  public void visit(IRCastInst node) {
    curBlock.addInst(new ASMMvInst(getReg(node.dest), getReg(node.val)));
  }
  public void visit(IRGetElementPtrInst node) {
    if (node.pToType == irBoolType) {
      curBlock.addInst(new ASMBinaryInst("add", getReg(node.res), getReg(node.ptr), getReg(node.indexList.get(0))));
    } else {
      Reg idx = node.pToType instanceof IRStructType ? getReg(node.indexList.get(1)) : getReg(node.indexList.get(0));
      VirtualReg tmp = new VirtualReg();
      curBlock.addInst(new ASMUnaryInst("slli", tmp, idx, new Imm(2)));
      curBlock.addInst(new ASMBinaryInst("add", getReg(node.res), getReg(node.ptr), tmp));
    }
  }
  public void visit(IRIcmpInst node) {
    // LLVM_IR: eq, ne, sgt, sge, slt, sle
    // RISCV32_ASM: seqz, snez, slt
    VirtualReg tmp = new VirtualReg();
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
    curBlock.addInst(new ASMLoadInst(node.type.size, getReg(node.destReg), getReg(node.srcAddr)));
  }
  public void visit(IRRetInst node) {
    // ret val -> load val to a0 and return
    if (node.val != null)
      curBlock.addInst(new ASMLoadInst(node.val.type.size, PhysicsReg.regMap.get("a0"), getReg(node.val)));
    curBlock.addInst(new ASMRetInst());
  }
  public void visit(IRStoreInst node) {
    // store : rs2 -> (rs1) address
    curBlock.addInst(new ASMStoreInst(node.val.type.size, getReg(node.destAddr), getReg(node.val)));
  }
}