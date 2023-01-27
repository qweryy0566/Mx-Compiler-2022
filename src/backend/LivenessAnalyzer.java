package backend;

import assembly.*;
import assembly.inst.*;
import assembly.operand.*;
import java.util.HashSet;
import java.util.LinkedList;

public class LivenessAnalyzer {
  ASMModule module;

  LinkedList<ASMBlock> workList = new LinkedList<>();
  HashSet<ASMBlock> inWorkList = new HashSet<>();

  public LivenessAnalyzer(ASMModule module) {
    this.module = module;
    // init
    for (ASMFunction func : module.functions)
      for (ASMBlock block : func.blocks) {
        block.liveIn.clear();
        block.liveOut.clear();
      }
    // compute the use and def of each block
    // chapter 17.4.2
    for (ASMFunction func : module.functions)
      for (ASMBlock block : func.blocks) {
        block.use.clear();
        block.def.clear();
        for (ASMInst inst : block.insts) {
          inst.getUse().forEach(reg -> {
            if (!block.def.contains(reg))
              block.use.add(reg);
          });
          block.def.addAll(inst.getDef());
        }
      }
  }

  // chapter 17.4.5
  public void work() {
    for (ASMFunction func : module.functions) {
      livenessOfBlock(func);
      func.blocks.forEach(block -> livenessOfInst(block));
    }
  }

  void livenessOfBlock(ASMFunction func) {
    workList.clear();
    inWorkList.clear();
    workList.add(func.exitBlock);
    inWorkList.add(func.exitBlock);
    while (!workList.isEmpty()) {
      ASMBlock block = workList.removeFirst();
      inWorkList.remove(block);
      HashSet<Reg> newLiveOut = new HashSet<>();
      block.succ.forEach(succ -> newLiveOut.addAll(succ.liveIn));
      HashSet<Reg> newLiveIn = new HashSet<>(block.use);
      newLiveIn.addAll(newLiveOut);
      newLiveIn.removeAll(block.def);
      if (!newLiveIn.equals(newLiveIn)) {
        block.liveIn = newLiveIn;
        block.liveOut = newLiveOut;
        block.pred.forEach(pred -> {
          if (!inWorkList.contains(pred)) {
            workList.add(pred);
            inWorkList.add(pred);
          }
        });
      }
    }
  }

  void livenessOfInst(ASMBlock block) {
    HashSet<Reg> live = new HashSet<>(block.liveOut);
    for (int i = block.insts.size() - 1; i >= 0; --i) {
      ASMInst inst = block.insts.get(i);
      inst.liveOut = new HashSet<>(live);
      inst.getDef().forEach(reg -> live.remove(reg));
      inst.getUse().forEach(reg -> live.add(reg));
      inst.liveIn = new HashSet<>(live);
    }
  }
}
