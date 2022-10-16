package ast.expr;

import utils.*;
import ast.*;

public class ExprNode extends Node {
  public Type type;

  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
};