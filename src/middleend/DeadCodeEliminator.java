package middleend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import IR.*;
import IR.inst.*;
import IR.entity.*;

public class DeadCodeEliminator {
  IRProgram program;

  HashMap<IRRegister, HashSet<IRInst>> useList = new HashMap<>();
  HashMap<IRRegister, IRInst> defList = new HashMap<>();
  LinkedList<IRRegister> workList = new LinkedList<>();
  HashSet<IRRegister> inWorkList = new HashSet<>();

  public DeadCodeEliminator(IRProgram program) {
    this.program = program;
  }

  public void work() {
    program.funcList.forEach(func -> workOnFunc(func));
  }

  void workOnFunc(IRFunction func) {
    useList.clear();
    for (var block : func.blocks) {
      for (var inst : block.insts) {
        if (inst.getDef() != null) {
          defList.put(inst.getDef(), inst);
          workList.add(inst.getDef());
          inWorkList.add(inst.getDef());
        }
        for (var use : inst.getUse())
          if (use instanceof IRRegister reg)
            useList.computeIfAbsent(reg, k -> new HashSet<>()).add(inst);
      }
      var inst = block.terminalInst;
      for (var use : inst.getUse())
        if (use instanceof IRRegister reg)
          useList.computeIfAbsent(reg, k -> new HashSet<>()).add(inst);
    }
    while (!workList.isEmpty()) {
      IRRegister reg = workList.removeFirst();
      inWorkList.remove(reg);
      if (useList.get(reg) == null || useList.get(reg).isEmpty()) {
        IRInst inst = defList.get(reg);
        if (inst instanceof IRCallInst)
          continue; // call inst has side effect
        inst.isDeleted = true;
        for (var use : inst.getUse())
          if (use instanceof IRRegister useReg) {
            useList.get(useReg).remove(inst);
            if (!inWorkList.contains(useReg)) {
              workList.add(useReg);
              inWorkList.add(useReg);
            }
          }
      }
    }

    for (var block : func.blocks)
      block.insts.removeIf(inst -> inst.isDeleted);
  }
}
