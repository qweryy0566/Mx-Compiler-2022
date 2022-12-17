package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

public class IRTruncInst extends IRCastInst {
  public IRTruncInst(IRBasicBlock block, IRRegister dest, IREntity val, IRType type) {
    super(block, val, type, dest);
  }

  @Override
  public String toString() {
    return dest + " = trunc " + val.toStringWithType() + " to " + targetType;
  }
} 