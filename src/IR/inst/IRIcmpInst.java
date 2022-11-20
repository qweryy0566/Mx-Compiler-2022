package IR.inst;

import IR.entity.*;
import IR.type.*;

// eq, ne, sgt, sge, slt, sle, ugt, uge, ult, ule

public class IRIcmpInst extends IRInst {
  public IRType type;
  public IRRegister cmpReg;
  public IREntity lhs, rhs;
  public String op;

  public String toString() {
    return "";
  }
}