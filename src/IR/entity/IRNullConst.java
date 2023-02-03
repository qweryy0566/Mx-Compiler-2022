package IR.entity;

import IR.type.*;

public class IRNullConst extends IRConst {
  public IRNullConst() {
    super(irNullType);
  }

  public IRNullConst(IRType type) {
    super(type);
  }

  @Override
  public String toString() {
    return "null";
  }

  @Override
  public String toStringWithType() {
    return type == irNullType ? toString()  : type + " " + toString();
  }

  @Override
  public boolean isZero() {
    return true;
  }

  @Override
  public boolean equals(IRConst other) {
    return other instanceof IRNullConst;
  }
}