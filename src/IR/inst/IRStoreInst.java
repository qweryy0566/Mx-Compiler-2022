package IR.inst;

import IR.entity.*;
import IR.*;

public class IRStoreInst extends IRInst {
  public IREntity val, destAddr;

  public IRStoreInst(IRBasicBlock block, IREntity val, IREntity destAddr) {
    super(block);
    this.val = val;
    this.destAddr = destAddr;
  }

  @Override
  public String toString() {
    return "store " + val.toString() + ", " + destAddr.toString();
  }
}

