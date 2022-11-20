package IR.entity;

import utils.*;
import IR.type.*;

public class IRIntConst extends IRConst {
  public int val;

  public IRIntConst(int val) {
    super(irIntType);
    this.val = val;
  }
}