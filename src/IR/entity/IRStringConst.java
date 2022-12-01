package IR.entity;

import IR.type.IRArrayType;
import IR.type.IRPtrType;

public class IRStringConst extends IRConst {
  public String val;
  public int id;
  public static int cnt = 0;
  
  public IRStringConst(String val) {
    super(new IRPtrType(new IRArrayType(irBoolType, val.length() + 1)));
    this.val = val;
    this.id = cnt++;
  }

  @Override
  public String toString() {
    return "@str." + String.valueOf(id);
  }

  @Override
  public String toStringWithType() {
    return "[" + String.valueOf(val.length() + 1) + " x i8]* " + toString();
  }
}