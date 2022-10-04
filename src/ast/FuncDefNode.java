package ast;

import grammar.MxParser.*;

public class FuncDefNode extends Node {
  public TypeNode returnType;
  public String name;
  public ParameterListContext parameterList;
  public SuiteContext suite;

  public FuncDefNode() {}
  public FuncDefNode(TypeNode returnType, String name, ParameterListContext parameterList, SuiteContext suite) {
    this.returnType = returnType;
    this.name = name;
    this.parameterList = parameterList;
    this.suite = suite;
  }
  
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}