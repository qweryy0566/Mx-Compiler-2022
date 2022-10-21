package ast.stmt;

import ast.*;
import ast.expr.*;
import utils.*;

public class IfStmtNode extends StmtNode {
  public ExprNode cond;
  public StmtNode thenStmt, elseStmt;

  public IfStmtNode(Position pos, ExprNode cond, StmtNode thenStmt, StmtNode elseStmt) {
    super(pos);
    this.cond = cond;
    this.thenStmt = thenStmt;
    this.elseStmt = elseStmt;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
};