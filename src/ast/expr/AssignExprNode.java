package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;

public class AssignExprNode extends BinaryExprNode {

  public AssignExprNode(Position pos, ExprNode lhs, ExprNode rhs) {
    super(pos, lhs, "=", rhs);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}