package ast;

public class TypeNode extends Node {
  String type;
  int dim;

  TypeNode() {}
  TypeNode(String type) {
    this.type = type;
  }
  TypeNode(String type, int dim) {
    this.type = type;
    this.dim = dim;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}