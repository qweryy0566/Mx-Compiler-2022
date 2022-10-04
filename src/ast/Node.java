package ast;

public abstract class Node {

  public abstract void accept(ASTVisitor visitor);
}