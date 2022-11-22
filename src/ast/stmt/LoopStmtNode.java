package ast.stmt;

import ast.expr.*;
import utils.*;
import IR.*;
import java.util.ArrayList;

public abstract class LoopStmtNode extends StmtNode {
  public ExprNode cond;
  public ArrayList<StmtNode> stmts = new ArrayList<StmtNode>();
  public IRBasicBlock condBlock, loopBlock, nextBlock;

  public LoopStmtNode(Position pos) {
    super(pos);
  }
}