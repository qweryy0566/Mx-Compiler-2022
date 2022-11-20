package ast.expr;

import ast.*;
import utils.*;

public class FuncExprNode extends ExprNode {
  public ExprNode funcName;
  public ExprListNode args;

  public FuncExprNode(Position pos, ExprNode func) {
    super(pos);
    this.funcName = func;
  }

  @Override
  public boolean isLeftValue() {
    return false;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}