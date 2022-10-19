package ast.stmt;

import ast.*;
import ast.expr.*;
import utils.*;

public class WhileStmtNode extends StmtNode {
  ExprNode cond;
  StmtNode loop;

  public WhileStmtNode(Position pos, ExprNode cond, StmtNode loop) {
    super(pos);
    this.cond = cond;
    this.loop = loop;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}