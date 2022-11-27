package ast;

import ast.stmt.*;
import utils.*;

public class ClassBuildNode extends Node {
  public String name;
  public SuiteNode suite;
  public FuncDefNode info;

  public ClassBuildNode(Position pos, String name, SuiteNode suite) {
    super(pos);
    this.name = name;
    this.suite = suite;
  }

  public FuncDefNode transToFuncDef() {
    FuncDefNode funcDef = new FuncDefNode(pos, name);
    funcDef.returnType = new TypeNode(pos, "void");
    funcDef.stmts = suite.stmts;
    return info = funcDef;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}