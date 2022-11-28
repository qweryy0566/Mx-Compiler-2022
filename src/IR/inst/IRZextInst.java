package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

public class IRZextInst extends IRInst {
  public IREntity val;
  public IRType targetType;
  public IRRegister dest;

  public IRZextInst(IRBasicBlock block, IRRegister dest, IREntity val, IRType type) {
    super(block);
    this.dest = dest;
    this.val = val;
    this.targetType = type;
  }

  @Override
  public String toString() {
    return "%" + String.valueOf(dest.index) + " = zext " + val.toString() + " to " + targetType.toString();
  }
} 