package backend;

import assembly.*;
import assembly.inst.*;
import assembly.operand.*;

public class StackManager {
  ASMModule module;

  public StackManager(ASMModule module) {
    this.module = module;
  }

  public void work() {
    for (var curFunc : module.functions) {
      int totalStack = curFunc.paramUsed + curFunc.allocaUsed + curFunc.spillUsed;
     
      if (totalStack < 1 << 11)
        curFunc.entryBlock.insts.addFirst(new ASMUnaryInst("addi", PhysicsReg.get("sp"), PhysicsReg.get("sp"),
            new Imm(-totalStack)));
      else {
        curFunc.entryBlock.insts.addFirst(new ASMBinaryInst("add", PhysicsReg.regMap.get("sp"), PhysicsReg.get("sp"),
            PhysicsReg.get("t0")));
        curFunc.entryBlock.insts.addFirst(new ASMLiInst(PhysicsReg.get("t0"), new VirtualImm(-totalStack)));
      }

      if (totalStack < 1 << 11)
        curFunc.exitBlock.insts.add(new ASMUnaryInst("addi", PhysicsReg.get("sp"), PhysicsReg.get("sp"),
            new Imm(totalStack)));
      else {
        curFunc.exitBlock.insts.add(new ASMLiInst(PhysicsReg.get("t0"), new VirtualImm(totalStack)));
        curFunc.exitBlock.insts.add(new ASMBinaryInst("add", PhysicsReg.get("sp"), PhysicsReg.get("sp"),
            PhysicsReg.get("t0")));
      }
      curFunc.exitBlock.insts.add(new ASMRetInst());
    }
  }
}