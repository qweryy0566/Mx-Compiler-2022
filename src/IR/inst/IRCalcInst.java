package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;
import java.util.LinkedHashSet;

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
  
  @Override
  public String toString() {
    return res + " = " + op + " " + lhs.toStringWithType() + ", " + rhs;
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public LinkedHashSet<IREntity> getUse() {
    LinkedHashSet<IREntity> ret = new LinkedHashSet<>();
    ret.add(lhs);
    ret.add(rhs);
    return ret;
  }

  @Override
  public IRRegister getDef() {
    return res;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    lhs = lhs == old ? newOne : lhs;
    rhs = rhs == old ? newOne : rhs;
  }

  public IRIntConst calcConst() {
    if (lhs instanceof IRIntConst && rhs instanceof IRIntConst) {
      int lhsVal = ((IRIntConst) lhs).val;
      int rhsVal = ((IRIntConst) rhs).val;
      int resVal = 0;
      switch (op) {
        case "add":
          resVal = lhsVal + rhsVal;
          break;
        case "sub":
          resVal = lhsVal - rhsVal;
          break;
        case "mul":
          resVal = lhsVal * rhsVal;
          break;
        case "sdiv":
          if (rhsVal == 0) return null;
          resVal = lhsVal / rhsVal;
          break;
        case "srem":
          resVal = lhsVal % rhsVal;
          break;
        case "shl":
          resVal = lhsVal << rhsVal;
          break;
        case "ashr":
          resVal = lhsVal >> rhsVal;
          break;
        case "and":
          resVal = lhsVal & rhsVal;
          break;
        case "or":
          resVal = lhsVal | rhsVal;
          break;
        case "xor":
          resVal = lhsVal ^ rhsVal;
          break;
      }
      return new IRIntConst(resVal);
    }
    return null;
  }
}