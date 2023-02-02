package IR.inst;

import IR.*;
import IR.entity.*;
import java.util.HashSet;

public abstract class IRInst {
  
  public IRBasicBlock parentBlock = null;
  public abstract String toString();

  public IRInst(IRBasicBlock block) {
    this.parentBlock = block;
  }

  public abstract void accept(IRVisitor visitor);

  public abstract HashSet<IREntity> getUse();

  public abstract void replaceUse(IREntity old, IREntity newOne);
}

