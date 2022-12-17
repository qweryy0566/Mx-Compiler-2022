package IR.entity;

import utils.*;
import IR.type.*;
import assembly.operand.*;

public abstract class IREntity implements BuiltinElements {
  public IRType type;

  public Reg asmReg;
  
  IREntity(IRType type) {
    this.type = type;
  }

  public abstract String toString();

  public abstract String toStringWithType();
}