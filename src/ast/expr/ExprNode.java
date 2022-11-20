package ast.expr;

import ast.*;
import utils.*;
import IR.entity.*;

public abstract class ExprNode extends Node {
  public String str;
  public Type type;
  public FuncDefNode funcDef = null;
  public IREntity value = null; // for IR

  public ExprNode(Position pos) {
    super(pos);
  }

  public abstract boolean isLeftValue();
};