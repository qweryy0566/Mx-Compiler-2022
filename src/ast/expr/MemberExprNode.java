package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;

public class MemberExprNode extends ExprNode {
  public ExprNode object;
  public String member;

  public MemberExprNode(Position pos, ExprNode object, String member) {
    super(pos);
    this.object = object;
    this.member = member;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}