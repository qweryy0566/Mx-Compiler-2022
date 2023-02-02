package IR.inst;

import IR.*;
import IR.entity.*;
import java.util.LinkedHashSet;

public abstract class IRInst {
  public boolean isDeleted = false;
  public IRBasicBlock parentBlock = null;
  public abstract String toString();

  public IRInst(IRBasicBlock block) {
    this.parentBlock = block;
  }

  public abstract void accept(IRVisitor visitor);

  public abstract LinkedHashSet<IREntity> getUse();
  public abstract IRRegister getDef();

  public abstract void replaceUse(IREntity old, IREntity newOne);
}

