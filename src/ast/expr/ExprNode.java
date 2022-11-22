package ast.expr;

import ast.*;
import utils.*;
import IR.entity.*;
import IR.type.*;

public abstract class ExprNode extends Node {
  public String str;
  public Type type;
  public FuncDefNode funcDef = null;
  public IRRegister storePtr = null; // for left value in IR
  public IREntity value = null;      // for IR

  public ExprNode(Position pos) {
    super(pos);
  }

  public abstract boolean isLeftValue();

  public IRType getIRType() {
    return value.type;
  }
};