package IR.entity;

import utils.*;
import IR.type.*;

public abstract class IREntity implements BuiltinElements {
  public IRType type;
  
  IREntity(IRType type) {
    this.type = type;
  }

  public abstract String toString();
}