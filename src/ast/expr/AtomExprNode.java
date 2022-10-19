package ast.expr;

import ast.*;
import utils.*;

public class AtomExprNode extends ExprNode {
  public String str;

  public AtomExprNode(Position pos, String str) {
    super(pos);
    this.str = str;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
