package ast.stmt;

import ast.*;
import ast.expr.*;
import utils.*;

public class ForStmtNode extends StmtNode {
  public VarDefNode varDef;
  public ExprNode init, cond, step;
  public StmtNode loop;

  public ForStmtNode(Position pos, ExprNode cond, ExprNode step, StmtNode loop) {
    super(pos);
    this.cond = cond;
    this.step = step;
    this.loop = loop;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}