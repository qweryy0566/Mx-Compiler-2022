package IR.inst;

import IR.entity.*;
import IR.*;

import java.util.LinkedHashSet;

public class IRStoreInst extends IRInst {
  public IREntity val;
  public IRRegister destAddr;
  public int param_idx = -1;
  // if param_idx != -1, then it stores a parameter

  public IRStoreInst(IRBasicBlock block, IREntity val, IRRegister destAddr) {
    super(block);
    this.val = val;
    this.destAddr = destAddr;
  }

  public IRStoreInst(IRBasicBlock block, IREntity val, IRRegister destAddr, int param_idx) {
    super(block);
    this.val = val;
    this.destAddr = destAddr;
    this.param_idx = param_idx;
  }

  @Override
  public String toString() {
    return "store " + val.toStringWithType() + ", " + destAddr.toStringWithType();
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public LinkedHashSet<IREntity> getUse() {
    LinkedHashSet<IREntity> ret = new LinkedHashSet<>();
    ret.add(val);
    ret.add(destAddr);
    return ret;
  }

  @Override
  public IRRegister getDef() {
    return null;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    if (val == old) {
      // System.out.println("replace " + old.toStringWithType() + " with " + newOne.toStringWithType() + " in " + this.toString() + " in " + parentBlock.name);
      val = newOne;
    }
    if (destAddr == old)
      destAddr = (IRRegister) newOne;
  }
}