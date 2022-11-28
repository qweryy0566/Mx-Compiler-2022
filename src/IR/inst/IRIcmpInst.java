package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

// eq, ne, sgt, sge, slt, sle, ugt, uge, ult, ule

public class IRIcmpInst extends IRInst {
  public IRType type;
  public IRRegister cmpReg;
  public IREntity lhs, rhs;
  public String op;

  public IRIcmpInst(IRBasicBlock block, IRType type, IRRegister cmpReg, IREntity lhs, IREntity rhs, String op) {
    super(block);
    this.type = type;
    this.cmpReg = cmpReg;
    this.lhs = lhs;
    this.rhs = rhs;
    this.op = op;
  }

  @Override
  public String toString() {
    return "%" + String.valueOf(cmpReg.index) + " = icmp " + op + " " + lhs.toString() + ", " + rhs.toString();
  }
}