package IR.inst;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;

public class IRLoadInst extends IRInst {
  public IRRegister destReg;
  public IREntity srcAddr;

  public String toString() {
    return "";
  }
}