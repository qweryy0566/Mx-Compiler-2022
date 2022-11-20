package IR.inst;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;
import IR.entity.IREntity;
import IR.entity.IRRegister;
import IR.type.IRType;

public class IRBitcastInst extends IRInst {
  public IREntity val;
  public IRType type;
  public IRRegister dest;
  public String toString() {
    return "";
  }
}