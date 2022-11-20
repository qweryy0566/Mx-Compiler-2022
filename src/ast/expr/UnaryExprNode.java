package ast.expr;

import ast.*;
import utils.*;

public class UnaryExprNode extends ExprNode {
  public String op;
  public ExprNode expr;

  public UnaryExprNode(Position pos, String op, ExprNode expr) {
    super(pos);
    this.op = op;
    this.expr = expr;
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