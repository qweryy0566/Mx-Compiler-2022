package ast;

import utils.*;

public abstract class Node implements BuiltinElements {
  public Position pos;

  public Node(Position pos) {
    this.pos = pos;
  }

  public abstract void accept(ASTVisitor visitor);
}