package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

public class IRZextInst extends IRCastInst {
  public IRZextInst(IRBasicBlock block, IRRegister dest, IREntity val, IRType type) {
    super(block, val, type, dest);
  }

  @Override
  public String toString() {
    return dest + " = zext " + val.toStringWithType() + " to " + targetType;
  }
} 