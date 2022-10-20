package ast;

import utils.*;

public class TypeNode extends Node {
  
  public String typeName;
  public int dim = 0;

  public TypeNode(Position pos) {
    super(pos);
  }
  public TypeNode(Position pos, String name) {
    super(pos);
    this.typeName = name;
  }
  public TypeNode(Position pos, String name, int dim) {
    super(pos);
    this.typeName = name;
    this.dim = dim;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}