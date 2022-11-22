package ast.stmt;

import ast.expr.*;
import utils.*;
import java.util.ArrayList;

public abstract class LoopStmtNode extends StmtNode {
  public ExprNode cond;
  public ArrayList<StmtNode> stmts = new ArrayList<StmtNode>();

  public LoopStmtNode(Position pos) {
    super(pos);
  }
}