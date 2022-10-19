package ast.stmt;

import ast.*;
import utils.*;

public class BreakNode extends StmtNode {
  public BreakNode(Position pos) {
    super(pos);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}