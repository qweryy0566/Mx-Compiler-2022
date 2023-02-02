package IR.inst;

import utils.*;
import IR.entity.*;
import IR.*;

import java.util.HashSet;

public class IRRetInst extends IRTerminalInst implements BuiltinElements {
  public IREntity val;

  public IRRetInst(IRBasicBlock block, IREntity val) {
    super(block);
    this.val = val;
  }
  
  @Override
  public String toString() {
    return "ret " + val.toStringWithType();
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HashSet<IREntity> getUse() {
    HashSet<IREntity> ret = new HashSet<>();
    ret.add(val);
    return ret;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    val = val == old ? (newOne == null ? irIntConst0 : newOne) : val;
  }
}