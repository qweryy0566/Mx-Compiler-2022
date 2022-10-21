package ast.stmt;

import ast.*;
import ast.expr.*;
import utils.*;
import java.util.ArrayList;

public class WhileStmtNode extends StmtNode {
  public ExprNode cond;
  public ArrayList<StmtNode> stmts = new ArrayList<StmtNode>();

  public WhileStmtNode(Position pos, ExprNode cond) {
    super(pos);
    this.cond = cond;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}