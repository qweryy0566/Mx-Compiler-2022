package ast.stmt;

import ast.*;
import ast.expr.*;

public class WhileStmtNode extends StmtNode {
  ExprNode cond;
  StmtNode loop;

  public WhileStmtNode(ExprNode cond, StmtNode loop) {
    this.cond = cond;
    this.loop = loop;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}