package IR.entity;

import utils.*;
import IR.type.*;

public class IRBoolConst extends IRConst {
  public boolean val;

  public IRBoolConst(boolean val) {
    super(irCondType);
    this.val = val;
  }
}