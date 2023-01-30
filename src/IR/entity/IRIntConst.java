package IR.entity;

public class IRIntConst extends IRConst {
  public int val;

  public IRIntConst(int val) {
    super(irIntType);
    this.val = val;
  }

  @Override
  public String toString() {
    return String.valueOf(val);
  }

  @Override
  public String toStringWithType() {
    return "i32 " + toString();
  }

  @Override
  public boolean isZero() {
    return val == 0;
  }
}