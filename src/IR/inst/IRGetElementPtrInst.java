package IR.inst;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;
import IR.entity.IREntity;
import IR.entity.IRRegister;
import IR.type.IRType;

import java.util.ArrayList;

public class IRGetElementPtrInst extends IRInst {
  public IRRegister res;
  public IRType ptrType;
  public ArrayList<IREntity> indexList = new ArrayList<IREntity>();

  public String toString() {
    return "";
  }
}