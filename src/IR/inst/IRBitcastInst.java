package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

public class IRBitcastInst extends IRCastInst {
  public IRBitcastInst(IRBasicBlock block, IREntity val, IRType type, IRRegister dest) {
    super(block, val, type, dest);
  }

  @Override
  public String toString() {
    return dest + " = bitcast " + val.toStringWithType() + " to " + targetType;
  }
}