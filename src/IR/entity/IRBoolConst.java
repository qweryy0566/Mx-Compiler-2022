package IR.entity;

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
    return "i8 " + toString();
  }

  @Override
  public boolean isZero() {
    return !val;
  }

  @Override
  public boolean equals(IRConst other) {
    return other instanceof IRBoolConst && ((IRBoolConst) other).val == val;
  }
}