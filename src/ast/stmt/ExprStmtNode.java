package ast.stmt;

import ast.expr.*;
import ast.*;

public class ExprStmtNode extends StmtNode {
  ExprNode expr;

  public ExprStmtNode(ExprNode expr) {
    this.expr = expr;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}