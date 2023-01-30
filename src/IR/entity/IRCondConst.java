package IR.entity;

public class IRCondConst extends IRConst {
  public boolean val;

  public IRCondConst(boolean val) {
    super(irCondType);
    this.val = val;
  }

  @Override
  public String toString() {
    return val ? "true" : "false";
  }

  @Override
  public String toStringWithType() {
    return "i1 " + toString();
  }

  @Override
  public boolean isZero() {
    return !val;
  }
}