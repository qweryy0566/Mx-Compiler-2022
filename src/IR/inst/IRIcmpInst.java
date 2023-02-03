package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;
import java.util.LinkedHashSet;

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
    return cmpReg + " = icmp " + op +" " + type +  " " + lhs + ", " + rhs;
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
    return cmpReg;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    lhs = lhs == old ? newOne : lhs;
    rhs = rhs == old ? newOne : rhs;
  }

  public IRCondConst calcConst() {
    if (lhs instanceof IRIntConst && rhs instanceof IRIntConst) {
      int lhsVal = ((IRIntConst) lhs).val;
      int rhsVal = ((IRIntConst) rhs).val;
      boolean resVal = false;
      switch (op) {
        case "eq":
          resVal = lhsVal == rhsVal;
          break;
        case "ne":
          resVal = lhsVal != rhsVal;
          break;
        case "sgt":
          resVal = lhsVal > rhsVal;
          break;
        case "sge":
          resVal = lhsVal >= rhsVal;
          break;
        case "slt":
          resVal = lhsVal < rhsVal;
          break;
        case "sle":
          resVal = lhsVal <= rhsVal;
          break;
      }
      return new IRCondConst(resVal);
    }
    return null;
  }
}