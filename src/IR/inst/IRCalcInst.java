package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

// add, sub, mul, udiv, sdiv, urem, srem, shl, lshr, ashr, and, or, xor

public class IRCalcInst extends IRInst {
  public IRType resultType;
  public String op;
  public IRRegister res;
  public IREntity lhs, rhs;

  public IRCalcInst(IRBasicBlock block, IRType resultType, IRRegister res, IREntity lhs, IREntity rhs, String op) {
    super(block);
    this.resultType = resultType;
    this.res = res;
    this.lhs = lhs;
    this.rhs = rhs;
    this.op = op;
  }
  
  public String toString() {
    return "";
  }
}