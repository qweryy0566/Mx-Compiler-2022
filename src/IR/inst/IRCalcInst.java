package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;
import java.util.HashSet;

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
  public HashSet<IREntity> getUse() {
    HashSet<IREntity> ret = new HashSet<>();
    ret.add(lhs);
    ret.add(rhs);
    return ret;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    lhs = lhs == old ? newOne : lhs;
    rhs = rhs == old ? newOne : rhs;
  }
}