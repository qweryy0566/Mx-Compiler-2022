package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;

public class ArrayExprNode extends ExprNode {
  public ExprNode varName;
  public ExprNode index;

  public ArrayExprNode(Position pos, ExprNode varName, ExprNode index) {
    super(pos);
    this.varName = varName;
    this.index = index;
  }

  @Override
  public boolean isLeftValue() {
    return varName.isLeftValue();
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}