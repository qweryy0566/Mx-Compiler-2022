package ast.stmt;

import ast.*;
import ast.expr.*;
import utils.*;
import IR.*;

public class ForStmtNode extends LoopStmtNode {
  public VarDefNode varDef;
  public ExprNode init, step;
  public IRBasicBlock stepBlock;

  public ForStmtNode(Position pos) {
    super(pos);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}