package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

public class IRAllocaInst extends IRInst {
  public IRType type;
  public IRRegister allocaReg;
  public IRAllocaInst(IRBasicBlock block, IRType type, IRRegister allocaReg) {
    super(block);
    this.type = type;
    this.allocaReg = allocaReg;
  }

  @Override
  public String toString() {
    return allocaReg + " = alloca " + type;
  }
}

