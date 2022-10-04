package ast;

public class ClassDefNode extends Node {
  
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}