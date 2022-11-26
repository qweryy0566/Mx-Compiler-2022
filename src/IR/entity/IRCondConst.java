package IR.entity;

import utils.*;
import IR.type.*;

public class IRCondConst extends IRConst {
  public boolean val;

  public IRCondConst(boolean val) {
    super(irCondType);
    this.val = val;
  }
}