package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;

public class PreAddExprNode extends UnaryExprNode {
  public PreAddExprNode(Position pos, String op, ExprNode expr) {
    super(pos, op, expr);
  }

  @Override
  public boolean isLeftValue() {
    return true;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
