package ast;

import utils.*;

public class TypeNode extends Node {
  public Type type;
  public TypeNode(Position pos) {
    super(pos);
  }
  public TypeNode(Position pos, String name) {
    super(pos);
    this.type = new Type(name);
  }
  public TypeNode(Position pos, String name, int dim) {
    super(pos);
    this.type = new Type(name, dim);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}