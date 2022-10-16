package ast.stmt;

import ast.*;
import ast.expr.*;

public class IfStmtNode extends StmtNode {
  ExprNode cond;
  StmtNode thenStmt, elseStmt;

  public IfStmtNode(ExprNode cond, StmtNode thenStmt, StmtNode elseStmt) {
    this.cond = cond;
    this.thenStmt = thenStmt;
    this.elseStmt = elseStmt;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
};