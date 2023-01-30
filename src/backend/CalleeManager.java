package backend;

import assembly.*;
import assembly.inst.*;
import assembly.operand.*;

public class CalleeManager {
  ASMModule module;

  public CalleeManager(ASMModule module) {
    this.module = module;
  }

  public void work() {
    for (var curFunc : module.functions) {
      int totalStack = curFunc.paramUsed + curFunc.allocaUsed + curFunc.spillUsed;
      if (!curFunc.name.equals("main"))
        for (int i = 11; i >= 0; --i) // 应该不至于一个函数有 499 个参数吧
          curFunc.entryBlock.insts.addFirst(new ASMStoreInst(4, PhysicsReg.get("sp"), PhysicsReg.get("s" + i),
              new Imm(curFunc.paramUsed + (i + 1 << 2))));
      if (totalStack < 1 << 11)
        curFunc.entryBlock.insts.addFirst(new ASMUnaryInst("addi", PhysicsReg.get("sp"), PhysicsReg.get("sp"),
            new Imm(-totalStack)));
      else {
        curFunc.entryBlock.insts.addFirst(new ASMBinaryInst("add", PhysicsReg.regMap.get("sp"), PhysicsReg.get("sp"),
            PhysicsReg.get("t0")));
        curFunc.entryBlock.insts.addFirst(new ASMLiInst(PhysicsReg.get("t0"), new VirtualImm(-totalStack)));
      }

      if (!curFunc.name.equals("main"))
        for (int i = 0; i <= 11; ++i)
          curFunc.exitBlock.insts.add(new ASMLoadInst(4, PhysicsReg.get("s" + i), PhysicsReg.get("sp"),
              new Imm(curFunc.paramUsed + (i + 1 << 2))));
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