package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

import java.util.LinkedHashSet;

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
  public LinkedHashSet<IREntity> getUse() {
    LinkedHashSet<IREntity> ret = new LinkedHashSet<>();
    ret.add(srcAddr);
    return ret;
  }

  @Override
  public IRRegister getDef() {
    return destReg;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    srcAddr = srcAddr == old ? newOne : srcAddr;
  }
}