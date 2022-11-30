package IR.entity;

import IR.type.*;

public class IRRegister extends IREntity {
  public String name;
  public int index;
  public static int regCnt = 0;

  public IRRegister(String name, IRType type) {
    super(type);
    this.name = name;
    index = regCnt++;
  }

  @Override
  public String toString() {
    return "%" + String.valueOf(index);
  }

  @Override
  public String toStringWithType() {
    return type + " " + toString();
  }
}