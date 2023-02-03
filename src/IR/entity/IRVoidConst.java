package IR.entity;

public class IRVoidConst extends IRConst {
  public IRVoidConst() {
    super(irVoidType);
  }

  @Override
  public String toString() {
    return "void";
  }

  @Override
  public String toStringWithType() {
    return toString();
  }

  @Override
  public boolean isZero() {
    return false;
  }

  @Override
  public boolean equals(IRConst other) {
    return other instanceof IRVoidConst;
  }
}