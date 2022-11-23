package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

public class IRTruncInst extends IRInst {
  public IREntity val;
  public IRType targetType;
  public IRRegister dest;

  public IRTruncInst(IRBasicBlock block, IRRegister dest, IREntity val, IRType type) {
    super(block);
    this.dest = dest;
    this.val = val;
    this.targetType = type;
  }
  public String toString() {
    return "";
  }
} 