package ast;

import utils.*;
import ast.expr.*;
import ast.stmt.*;
import java.util.ArrayList;

public class ClassDefNode extends Node {
  public String name;
  public ArrayList<VarDefNode> varDefList;
  public ArrayList<FuncDefNode> funcDefList;
  public ClassBuildNode classBuild;

  public ClassDefNode(Position pos, String name) {
    super(pos);
    this.name = name;
  }
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}