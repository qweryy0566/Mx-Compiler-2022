package IR.inst;

import IR.entity.*;
import IR.*;

public class IRRetInst extends IRTerminalInst {
  public IREntity val;

  public IRRetInst(IRBasicBlock block, IREntity val) {
    super(block);
    this.val = val;
  }
  
  @Override
  public String toString() {
    return "ret " + val.toStringWithType();
  }
}