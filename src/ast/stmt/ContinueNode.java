package ast.stmt;

import ast.*;

public class ContinueNode extends StmtNode {
  public ContinueNode() {
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}