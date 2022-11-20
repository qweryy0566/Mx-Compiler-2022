package IR.inst;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;
import IR.entity.IREntity;

public class IRStoreInst extends IRInst {
  public IREntity val, destAddr;

  public String toString() {
    return "";
  }
}

