package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

import java.util.HashSet;

public class IRLoadInst extends IRInst {
  public IRRegister destReg;
  public IREntity srcAddr;
  public IRType type;

  public IRLoadInst(IRBasicBlock block, IRRegister destReg, IREntity srcAddr) {
    super(block);
    this.destReg = destReg;
    this.srcAddr = srcAddr;
    this.type = destReg.type;
  }

  @Override
  public String toString() {
    return destReg + " = load " + type + ", " + srcAddr.toStringWithType();
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HashSet<IREntity> getUse() {
    HashSet<IREntity> ret = new HashSet<>();
    ret.add(srcAddr);
    return ret;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    srcAddr = srcAddr == old ? newOne : srcAddr;
  }
}