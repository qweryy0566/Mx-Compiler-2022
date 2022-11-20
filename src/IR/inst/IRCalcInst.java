package IR.inst;

import IR.entity.*;
import IR.type.*;

// add, sub, nul, udiv, sdiv, urem, srem, shl, lshr, ashr, and, or, xor

public class IRCalcInst extends IRInst {
  public IRType resultType;
  public String op;
  public IRRegister res;
  public IREntity lhs, rhs;
  
  public String toString() {
    return "";
  }
}