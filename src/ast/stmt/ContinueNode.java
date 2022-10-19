package ast.stmt;

import ast.*;
import utils.*;

public class ContinueNode extends StmtNode {
  public ContinueNode(Position pos) {
    super(pos);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}