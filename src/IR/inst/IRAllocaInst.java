package IR.inst;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;
import IR.type.IRType;

public class IRAllocaInst extends IRInst {
  public IRType type;
  public IRRegister allocaReg;
  public IRAllocaInst(IRType type, IRRegister allocaReg) {
    super();
    this.type = type;
    this.allocaReg = allocaReg;
  }
  public String toString() {
    return "";
  }
}

