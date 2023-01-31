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
  public LinkedList<IRBasicBlock> domFrontier = new LinkedList<>();

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
    if (isFinished) return;
    if (inst instanceof IRAllocaInst)
      parentFunction.allocaInsts.add((IRAllocaInst) inst);
    else if (inst instanceof IRTerminalInst)
      terminalInst = (IRTerminalInst) inst;
    else
      insts.add(inst);
  }

  public void mergeBlock(IRBasicBlock block) {
    if (block == this) return;
    isFinished = false;
    for (IRInst inst : block.insts)
      addInst(inst);
    terminalInst = block.terminalInst;
    isFinished = true;
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