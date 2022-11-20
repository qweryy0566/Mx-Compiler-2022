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

  public String toString() {
    return "";
  }
}