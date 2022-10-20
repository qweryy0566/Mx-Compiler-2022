package ast.expr;

import ast.*;
import utils.*;

public abstract class ExprNode extends Node {
  public String str;
  public Type type;

  public ExprNode(Position pos) {
    super(pos);
  }

  public abstract boolean isLeftValue();
};