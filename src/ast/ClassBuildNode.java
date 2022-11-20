package ast;

import ast.stmt.*;
import utils.*;

public class ClassBuildNode extends Node {
  public String name;
  public SuiteNode suite;

  public ClassBuildNode(Position pos, String name, SuiteNode suite) {
    super(pos);
    this.name = name;
    this.suite = suite;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}