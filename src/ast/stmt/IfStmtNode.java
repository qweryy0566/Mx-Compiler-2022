package ast.stmt;

import ast.*;
import ast.expr.*;
import utils.*;
import java.util.ArrayList;

public class IfStmtNode extends StmtNode {
  public ExprNode cond;
  public ArrayList<StmtNode> thenStmts = new ArrayList<StmtNode>();
  public ArrayList<StmtNode> elseStmts = new ArrayList<StmtNode>();

  public IfStmtNode(Position pos, ExprNode cond) {
    super(pos);
    this.cond = cond;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
};