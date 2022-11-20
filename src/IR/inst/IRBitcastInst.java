package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

public class IRBitcastInst extends IRInst {
  public IREntity val;
  public IRType type;
  public IRRegister dest;

  public IRBitcastInst(IRBasicBlock block, IREntity val, IRType type, IRRegister dest) {
    super(block);
    this.val = val;
    this.type = type;
    this.dest = dest;
  }

  public String toString() {
    return "";
  }
}