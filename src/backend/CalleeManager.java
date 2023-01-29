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
          curFunc.entryBlock.insts.addFirst(new ASMStoreInst(4, PhysicsReg.regMap.get("sp"),
              PhysicsReg.regMap.get("s" + i), new Imm(curFunc.paramUsed + (i + 1 << 2))));
      curFunc.entryBlock.insts.addFirst(new ASMBinaryInst("add", PhysicsReg.regMap.get("sp"),
          PhysicsReg.regMap.get("sp"), PhysicsReg.regMap.get("t0")));
      curFunc.entryBlock.insts.addFirst(new ASMLiInst(PhysicsReg.regMap.get("t0"),
          new VirtualImm(-totalStack)));

      if (!curFunc.name.equals("main"))
        for (int i = 0; i <= 11; ++i)
          curFunc.exitBlock.insts.add(new ASMLoadInst(4, PhysicsReg.regMap.get("s" + i),
              PhysicsReg.regMap.get("sp"), new Imm(curFunc.paramUsed + (i + 1 << 2))));
      curFunc.exitBlock.insts.add(new ASMLiInst(PhysicsReg.regMap.get("t0"),
          new VirtualImm(totalStack)));
      curFunc.exitBlock.insts.add(new ASMBinaryInst("add", PhysicsReg.regMap.get("sp"),
          PhysicsReg.regMap.get("sp"), PhysicsReg.regMap.get("t0")));
      curFunc.exitBlock.insts.add(new ASMRetInst());
    }
  }
}
