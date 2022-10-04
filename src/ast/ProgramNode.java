package ast;

import java.util.ArrayList;

public class ProgramNode extends Node {
  public ArrayList<Node> defList = new ArrayList<Node>();

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
