package IR.inst;

import IR.entity.*;
import IR.*;

public class IRLoadInst extends IRInst {
  public IRRegister destReg;
  public IREntity srcAddr;

  public IRLoadInst(IRBasicBlock block, IRRegister destReg, IREntity srcAddr) {
    super(block);
    this.destReg = destReg;
    this.srcAddr = srcAddr;
  }

  @Override
  public String toString() {
    return "%" + String.valueOf(destReg.index) + " = load " + srcAddr.toString();
  }
}