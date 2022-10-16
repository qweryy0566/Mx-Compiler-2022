package ast.stmt;

import ast.*;

public class BreakNode extends StmtNode {
  public BreakNode() {
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}