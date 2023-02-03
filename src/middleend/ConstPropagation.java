package middleend;

import IR.*;
import IR.inst.*;
import utils.*;
import IR.entity.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class ConstPropagation implements BuiltinElements {
  IRProgram program;

  HashMap<IRRegister, HashSet<IRInst>> useList = new HashMap<>();
  LinkedList<IRInst> workList = new LinkedList<>();
  HashSet<IRInst> inWorkList = new HashSet<>();
  IRFunction curFunc;

  public ConstPropagation(IRProgram program) {
    this.program = program;
  }

  public void work() {
    program.funcList.forEach(func -> workOnFunc(func));
  }

  void workOnFunc(IRFunction func) {
    useList = func.useList;
    workList.clear();
    curFunc = func;
    for (var block : func.blocks) {
      for (var inst : block.insts)
        if (canBeReplaced(inst) != null) {
          workList.add(inst);
          inWorkList.add(inst);
        }
      workList.add(block.terminalInst);
      inWorkList.add(block.terminalInst);
    }

    while (!workList.isEmpty()) {
      IRInst inst = workList.removeFirst();
      inWorkList.remove(inst);
      if (inst.isDeleted)
        continue;
      IREntity c = canBeReplaced(inst), def = inst.getDef();
      if (c != null) {
        inst.isDeleted = true;
        for (var use : useList.get(def)) {
          use.replaceUse(def, c);
          if (c instanceof IRRegister reg)
            useList.get(reg).add(use);
          if (!inWorkList.contains(use)) {
            workList.add(use);
            inWorkList.add(use);
          }
        }
      } else if (inst instanceof IRBranchInst brInst && brInst.cond instanceof IRCondConst cond) {
        IRBasicBlock atBlock = brInst.parentBlock;
        IRBasicBlock toBlock = cond.val ? brInst.thenBlock : brInst.elseBlock;
        IRBasicBlock deleteBlock = cond.val ? brInst.elseBlock : brInst.thenBlock;
        atBlock.terminalInst = new IRJumpInst(atBlock, toBlock);
        atBlock.succs.remove(deleteBlock);
        deleteBlock.preds.remove(atBlock);
        for (var phiInst : deleteBlock.insts) {
          if (!(phiInst instanceof IRPhiInst phi))
            break;
          boolean found = false;
          for (int i = 0; i < phi.values.size(); ++i)
            if (phi.blocks.get(i) == atBlock) {
              phi.blocks.remove(i);
              phi.values.remove(i);
              found = true;
              break;
            }
          if (found && !inWorkList.contains(phiInst)) {
            workList.add(phiInst);
            inWorkList.add(phiInst);
          }
        }
        if (deleteBlock.preds.size() == 0)
          deleteBlock(deleteBlock);
      }
    }

    for (var block : func.blocks)
      block.insts.removeIf(inst -> inst.isDeleted);
  }

  void deleteBlock(IRBasicBlock block) {
    curFunc.blocks.remove(block);
    for (var inst : block.insts) {
      inst.isDeleted = true;
      for (var use : inst.getUse())
        if (useList.get(use) != null)
          useList.get(use).remove(inst);
    }
    block.terminalInst.isDeleted = true;
    for (var use : block.terminalInst.getUse())
      useList.get(use).remove(block.terminalInst);
    for (var succ : block.succs) {
      succ.preds.remove(block);
      for (var phiInst : succ.insts) {
        if (!(phiInst instanceof IRPhiInst phi))
          break;
        boolean found = false;
        for (int i = 0; i < phi.values.size(); ++i)
          if (phi.blocks.get(i) == block) {
            phi.blocks.remove(i);
            phi.values.remove(i);
            found = true;
            break;
          }
        if (found && !inWorkList.contains(phiInst)) {
          workList.add(phiInst);
          inWorkList.add(phiInst);
        }
      }
      if (succ.preds.size() == 0)
        deleteBlock(succ);
    }
  }

  IREntity canBeReplaced(IRInst inst) {
    if (inst instanceof IRPhiInst phiInst) {
      if (phiInst.values.size() == 1)
        return phiInst.values.get(0);
      else if (phiInst.values.get(0) instanceof IRConst constVal) {
        for (int i = 1; i < phiInst.values.size(); ++i)
          if (!(phiInst.values.get(i) instanceof IRConst other) || !constVal.equals(other))
            return null;
        return constVal;
      }
      return null;
    }
    if (inst instanceof IRZextInst castInst) {
      if (castInst.val instanceof IRCondConst condConst)
        return condConst.val ? irBoolTrueConst : irBoolFalseConst;
      return null;
    }
    if (inst instanceof IRTruncInst castInst) {
      if (castInst.val instanceof IRBoolConst condConst)
        return condConst.val ? irTrueConst : irFalseConst;
      return null;
    }
    if (inst instanceof IRCalcInst calcInst)
      return calcInst.calcConst();
    if (inst instanceof IRIcmpInst icmpInst)
      return icmpInst.calcConst();
    return null;
  }
}
