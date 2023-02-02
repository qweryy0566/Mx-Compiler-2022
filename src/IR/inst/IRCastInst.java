package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;
import java.util.LinkedHashSet;

public abstract class IRCastInst extends IRInst {
  public IREntity val;
  public IRType targetType;
  public IRRegister dest;

  public IRCastInst(IRBasicBlock block, IREntity val, IRType type, IRRegister dest) {
    super(block);
    this.val = val;
    this.targetType = type;
    this.dest = dest;
  }

  @Override
  public final void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public final LinkedHashSet<IREntity> getUse() {
    LinkedHashSet<IREntity> ret = new LinkedHashSet<>();
    ret.add(val);
    return ret;
  }

  @Override
  public final IRRegister getDef() {
    return dest;
  }

  @Override
  public final void replaceUse(IREntity old, IREntity newOne) {
    val = val == old ? newOne : val;
  }
}