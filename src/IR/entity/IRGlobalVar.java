package IR.entity;

import IR.type.*;

public class IRGlobalVar extends IRRegister {  // 写成屎山了
  public IREntity initVal;
  public boolean isCallInit = false;
  
  public IRGlobalVar(String name, IRType type) {
    super(name, new IRPtrType(type));
    --regCnt;
  }

  @Override
  public String toString() {
    return "@" + name;
  }

  @Override
  public String toStringWithType() {
    return type + " " + toString();
  }
}