package IR.entity;

import IR.type.*;

public abstract class IRConst extends IREntity {
  public IRConst(IRType type) {
    super(type);
  }

  public abstract boolean isZero();

  public abstract boolean equals(IRConst other);
}