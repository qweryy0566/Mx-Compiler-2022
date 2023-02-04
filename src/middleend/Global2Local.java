package middleend;

import IR.*;
import IR.inst.*;
import IR.type.IRPtrType;
import IR.entity.*;

import java.util.ArrayList;

public class Global2Local {
  IRProgram program;

  public Global2Local(IRProgram program) {
    this.program = program;
  }

  public void work() {
    var newList = new ArrayList<IRGlobalVar>();
    if (program.initFunc != null)
      program.funcList.addFirst(program.initFunc);
    for (var global : program.globalVarList) {
      if (global.isCallInit) {
        newList.add(global);
        continue;
      }
      IRFunction inFunc = null;
      boolean inOneFunc = true;
      for (var func : program.funcList)
        for (var block : func.blocks)
          for (var inst : block.insts)
            if (inst.getUse().contains(global)) {
              if (inFunc == null)
                inFunc = func;
              else if (inFunc != func) {
                inOneFunc = false;
                break;
              }
            }
      if (inOneFunc && inFunc != null && (inFunc == program.mainFunc || inFunc == program.initFunc)) {
        IRRegister reg = new IRRegister("global", global.type);
        inFunc.allocaInsts.add(new IRAllocaInst(inFunc.entryBlock, ((IRPtrType) global.type).pointToType(), reg));
        inFunc.entryBlock.insts.addFirst(new IRStoreInst(inFunc.entryBlock, global.initVal, reg));
        for (var block : inFunc.blocks)
          for (var inst : block.insts)
            inst.replaceUse(global, reg);
      } else if (inFunc != null) {
        newList.add(global);
      }
    }
    program.globalVarList = newList;
    for (var func : program.funcList)
      func.finish();
  }
}
