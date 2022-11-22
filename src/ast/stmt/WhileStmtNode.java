package ast.stmt;

import ast.*;
import ast.expr.*;
import utils.*;

public class WhileStmtNode extends LoopStmtNode {
  public WhileStmtNode(Position pos, ExprNode cond) {
    super(pos);
    this.cond = cond;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}