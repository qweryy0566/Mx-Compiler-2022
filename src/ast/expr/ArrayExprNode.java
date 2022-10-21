package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;

public class ArrayExprNode extends ExprNode {
  public ExprNode array;
  public ExprNode index;

  public ArrayExprNode(Position pos, ExprNode array, ExprNode index) {
    super(pos);
    this.array = array;
    this.index = index;
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