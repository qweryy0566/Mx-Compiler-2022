package IR.entity;

import utils.*;
import IR.type.*;

public class IRNullConst extends IRConst {
  public IRNullConst() {
    super(irNullType);
  }

  @Override
  public String toString() {
    return "null";
  }
}