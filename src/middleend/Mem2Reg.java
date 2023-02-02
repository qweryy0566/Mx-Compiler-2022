package middleend;

import IR.*;
import IR.inst.*;
import IR.type.*;
import IR.entity.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Mem2Reg {
  IRProgram program;
  IRFunction curFunc;
  LinkedHashSet<IRRegister> promoteAllocas = new LinkedHashSet<>();
  HashMap<IRRegister, HashSet<IRBasicBlock>> allocaDefs = new HashMap<>();
  HashMap<IRRegister, IREntity> reachingDef = new HashMap<>();

  public Mem2Reg(IRProgram program) {
    this.program = program;
  }

  public void work() {
    new DomTreeBuilder(program).work();
    program.funcList.forEach(func -> workOnFunc(func));
  }

  void workOnFunc(IRFunction func) {
    curFunc = func;
    promoteCollect();
    for (var alloca : promoteAllocas)
      promoteMem2Reg(alloca);
    reachingDef.clear();
    renameVar(func.entryBlock);
    simplifyPhi(func.entryBlock);
  }

  void promoteCollect() {
    promoteAllocas.clear();
    for (var inst : curFunc.entryBlock.insts) {
      if (!(inst instanceof IRAllocaInst))
        break;
      IRRegister reg = ((IRAllocaInst) inst).allocaReg;
      allocaDefs.put(reg, new HashSet<>());
      if (isAllocaPromoteable((IRAllocaInst) inst))
        promoteAllocas.add(reg);
    }
  }

  boolean isAllocaPromoteable(IRAllocaInst inst) {
    if (inst.param_idx >= 8)
      return false;
    IRRegister reg = inst.allocaReg;
    for (var block : curFunc.blocks)
      for (var user : block.insts) {
        if (!(user instanceof IRLoadInst) && !(user instanceof IRStoreInst) && user.getUse().contains(reg))
          return false;
        if (user instanceof IRStoreInst storeInst && storeInst.destAddr == reg)
          allocaDefs.get(reg).add(block);
      }
    return true;
  }

  // reference : SSA Book
  void promoteMem2Reg(IRRegister reg) {
    HashSet<IRBasicBlock> hasPhi = new HashSet<>();
    HashSet<IRBasicBlock> inWorkList = new HashSet<>(allocaDefs.get(reg));
    LinkedList<IRBasicBlock> workList = new LinkedList<>(inWorkList);
    while (!workList.isEmpty()) {
      IRBasicBlock block = workList.removeFirst();
      inWorkList.remove(block);
      for (IRBasicBlock df : block.domFrontier)
        if (!hasPhi.contains(df)) {
          df.addInst(new IRPhiInst(df, reg, new IRRegister("", ((IRPtrType) reg.type).pointToType())));
          hasPhi.add(df);
          if (!inWorkList.contains(df)) {
            workList.add(df);
            inWorkList.add(df);
          }
        }
    }
  }

  void renameVar(IRBasicBlock block) {
    var oldReachingDef = new HashMap<>(reachingDef);
    LinkedList<IRInst> newInsts = new LinkedList<>();
    for (var inst : block.phiInsts) {
      reachingDef.put(inst.src, inst.dest);
      // System.out.println("reachingDef of " + inst.src + " changed to " + inst.dest);
    }
    for (int i = 0; i < block.insts.size(); ++i) {
      var inst = block.insts.get(i);
      if (inst instanceof IRAllocaInst alloca && promoteAllocas.contains(alloca.allocaReg))
        continue;
      if (inst instanceof IRLoadInst ld && promoteAllocas.contains(ld.srcAddr)) {
        // System.out.println(ld + ", " + reachingDef.get(ld.srcAddr));
        for (int j = i + 1; j < block.insts.size(); ++j)
          block.insts.get(j).replaceUse(ld.destReg, reachingDef.get(ld.srcAddr));
        if (block.terminalInst != null)
          block.terminalInst.replaceUse(ld.destReg, reachingDef.get(ld.srcAddr));
      } else if (inst instanceof IRStoreInst st && promoteAllocas.contains(st.destAddr)) {
        // System.out.println(st.destAddr + " " + st.val);
        reachingDef.put(st.destAddr, st.val);
      } else {
        newInsts.add(inst);
      }
    }
    block.insts = newInsts;
    // add edge
    block.succs.forEach(succ -> {
      succ.phiInsts.forEach(phi -> phi.add(reachingDef.get(phi.src), block));
    });

    block.domChildren.forEach(child -> renameVar(child));
    reachingDef = oldReachingDef;
  }

  void simplifyPhi(IRBasicBlock block) {
    block.phiInsts.forEach(phi -> {
      IREntity val = phi.values.get(0);
      boolean flag = true;
      for (int j = 1; j < phi.values.size(); ++j)
        if (phi.values.get(j) != val) {
          flag = false;
          break;
        }
      if (flag) {
        block.insts.forEach(inst -> inst.replaceUse(phi.dest, val));
        phi.isDeleted = true;
      }
    });
    for (int i = block.phiInsts.size() - 1; i >= 0; --i) {
      IRPhiInst phi = block.phiInsts.get(i);
      if (!phi.isDeleted)
        block.insts.addFirst(phi);
    }
    block.domChildren.forEach(child -> simplifyPhi(child));
  }
}
