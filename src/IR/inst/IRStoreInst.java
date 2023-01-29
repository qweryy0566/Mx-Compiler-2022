package IR.inst;

import IR.entity.*;
import IR.*;

public class IRStoreInst extends IRInst {
  public IREntity val, destAddr;
  public int param_idx = -1;
  // if param_idx != -1, then it stores a parameter

  public IRStoreInst(IRBasicBlock block, IREntity val, IREntity destAddr) {
    super(block);
    this.val = val;
    this.destAddr = destAddr;
  }

  public IRStoreInst(IRBasicBlock block, IREntity val, IREntity destAddr, int param_idx) {
    super(block);
    this.val = val;
    this.destAddr = destAddr;
    this.param_idx = param_idx;
  }

  @Override
  public String toString() {
    return "store " + val.toStringWithType() + ", " + destAddr.toStringWithType();
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }
}

