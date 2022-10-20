package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;

public class BinaryExprNode extends ExprNode {
  public String op;
  public ExprNode lhs, rhs;

  public BinaryExprNode(Position pos, ExprNode lhs, String op, ExprNode rhs) {
    super(pos);
    this.lhs = lhs;
    this.op = op;
    this.rhs = rhs;
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