package ast.stmt;

import ast.*;
import ast.expr.*;

public class ForStmtNode extends StmtNode {
  ExprNode init, cond, step;
  StmtNode loop;

  public ForStmtNode(ExprNode init, ExprNode cond, ExprNode step, StmtNode loop) {
    this.init = init;
    this.cond = cond;
    this.step = step;
    this.loop = loop;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}