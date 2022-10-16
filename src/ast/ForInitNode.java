package ast;

import ast.expr.*;

public class ForInitNode extends Node {
  ExprNode expr;
  VarDefNode varDef;

  public ForInitNode(ExprNode expr, VarDefNode varDef) {
    this.expr = expr;
    this.varDef = varDef;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}