package ast.stmt;

import ast.*;
import ast.expr.*;

public class ReturnStmtNode extends StmtNode {
  ExprNode expr;

  public ReturnStmtNode(ExprNode expr) {
    this.expr = expr;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}