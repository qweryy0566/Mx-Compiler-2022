package IR.inst;

import IR.entity.*;
import IR.*;

public class IRRetInst extends IRInst {
  public IREntity val;

  public IRRetInst(IRBasicBlock block, IREntity val) {
    super(block);
    this.val = val;
  }
  public String toString() {
    return "";
  }
}