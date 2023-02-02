package IR;

import IR.inst.*;
import java.util.LinkedList;

public class IRBasicBlock {
  public String name;
  public LinkedList<IRInst> insts = new LinkedList<IRInst>();
  public IRTerminalInst terminalInst = null;
  public IRFunction parentFunction = null;
  public int loopDepth = 0;
  public boolean isFinished = false;

  public LinkedList<IRBasicBlock> preds = new LinkedList<>(), succs = new LinkedList<>();
  public IRBasicBlock idom = null;
  public LinkedList<IRBasicBlock> domChildren = new LinkedList<>();
  public LinkedList<IRBasicBlock> domFrontier = new LinkedList<>();

  public LinkedList<IRPhiInst> phiInsts = new LinkedList<>();

  public static int blockCnt = 0;

  public IRBasicBlock(IRFunction function, String name, int loopDepth) {
    this.parentFunction = function;
    this.name = name + String.valueOf(blockCnt++);
    this.loopDepth = loopDepth;
  }
  public IRBasicBlock(IRFunction function, String name, IRBasicBlock toBlock, int loopDepth) {
    this(function, name, loopDepth);
    this.terminalInst = new IRJumpInst(this, toBlock);
  }
  
  public void addInst(IRInst inst) {
    if (inst instanceof IRPhiInst phiInst) {
      for (IRPhiInst enumInst : phiInsts)
        if (phiInst.src == enumInst.src)
          return;
      phiInsts.add((IRPhiInst) inst);
    } else {
      if (isFinished) return;
      if (inst instanceof IRAllocaInst)
        parentFunction.allocaInsts.add((IRAllocaInst) inst);
      else if (inst instanceof IRTerminalInst)
        terminalInst = (IRTerminalInst) inst;
      else
        insts.add(inst);
    }
  }

  public String toString() {
    String ret = name + ":\n";
    for (IRInst inst : insts)
      ret += "  " + inst + "\n";
    if (terminalInst != null)
      ret += "  " + terminalInst + "\n";
    return ret;
  }

  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }
}