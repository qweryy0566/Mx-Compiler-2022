package IR.entity;

import IR.type.IRArrayType;
import IR.type.IRPtrType;

public class IRStringConst extends IRConst {
  public String val;
  
  public IRStringConst(String val) {
    super(new IRPtrType(new IRArrayType(irBoolType, val.length())));
    this.val = val;
  }
}