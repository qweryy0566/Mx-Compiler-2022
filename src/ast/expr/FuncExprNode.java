package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;
import java.util.ArrayList;

public class FuncExprNode extends ExprNode {
  public ExprNode func;
  public ExprListNode args;

  public FuncExprNode(Position pos, ExprNode func) {
    super(pos);
    this.func = func;
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