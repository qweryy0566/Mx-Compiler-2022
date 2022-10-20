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
  public FuncDefNode(Position pos, Type type, String name, Type paramType) {
    super(pos);
    this.returnType = new TypeNode(pos, type.typeName, type.dim);
    this.name = name;
    if (paramType != null)
      this.params = new ParameterListNode(pos, paramType);
  }
  
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}