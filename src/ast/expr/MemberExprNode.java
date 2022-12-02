package ast.expr;

import ast.*;
import utils.*;
import IR.entity.*;

public class MemberExprNode extends ExprNode {
  public ExprNode obj;
  public String member;

  public IRRegister objAddr;

  public MemberExprNode(Position pos, ExprNode obj, String member) {
    super(pos);
    this.obj = obj;
    this.member = member;
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