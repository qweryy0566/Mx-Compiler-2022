package IR.inst;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;
import IR.entity.IREntity;
import IR.entity.IRRegister;

public class IRLoadInst extends IRInst {
  public IRRegister destReg;
  public IREntity srcAddr;

  public String toString() {
    return "";
  }
}