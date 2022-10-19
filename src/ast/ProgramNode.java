package ast;

import java.util.ArrayList;

import utils.*;

public class ProgramNode extends Node {
  public ArrayList<Node> defList = new ArrayList<Node>();

  public ProgramNode(Position pos) {
    super(pos);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
