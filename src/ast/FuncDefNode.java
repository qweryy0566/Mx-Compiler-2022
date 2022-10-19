package ast;

import ast.stmt.*;
import utils.*;
import java.util.ArrayList;

public class FuncDefNode extends Node {
  public TypeNode returnType;
  public String name;
  public ParameterListNode params;
  public SuiteNode suite;

  public FuncDefNode(Position pos, String name) {
    super(pos);
    this.name = name;
  }
  
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}