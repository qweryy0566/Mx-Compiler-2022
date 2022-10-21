package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;

public class MemberExprNode extends ExprNode implements BuiltinElements {
  public ExprNode obj;
  public String member;

  public MemberExprNode(Position pos, ExprNode obj, String member) {
    super(pos);
    this.obj = obj;
    this.member = member;
  }

  @Override
  public boolean isLeftValue() {
    return obj.isLeftValue() || obj.type == ThisType;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}