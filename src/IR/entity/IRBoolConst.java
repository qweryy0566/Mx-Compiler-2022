package IR.entity;

import utils.*;
import IR.type.*;

public class IRBoolConst extends IRConst {
  public boolean val;

  public IRBoolConst(boolean val) {
    super(irBoolType);
    this.val = val;
  }

  @Override
  public String toString() {
    return val ? "1" : "0";
  }

  @Override
  public String toStringWithType() {
    return toString();
  }
}