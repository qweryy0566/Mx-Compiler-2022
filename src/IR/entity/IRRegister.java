package IR.entity;

import IR.type.*;

public class IRRegister extends IREntity {
  public String name;
  public int index = -1;
  public static int regCnt = 0;

  public IRRegister(String name, IRType type) {
    super(type);
    this.name = name;
  }

  @Override
  public String toString() {
    if (index == -1 && (name == null || !name.equals("retval")))
      index = regCnt++;
    return "%" + (name != null && name.equals("retval") ? name : "." + String.valueOf(index));
  }

  @Override
  public String toStringWithType() {
    return type + " " + toString();
  }
}