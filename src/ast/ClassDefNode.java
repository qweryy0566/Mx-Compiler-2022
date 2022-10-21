package ast;

import utils.*;
import ast.expr.*;
import ast.stmt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassDefNode extends Node {
  public String name;
  public ArrayList<VarDefNode> varDefList = new ArrayList<VarDefNode>();
  public ArrayList<FuncDefNode> funcDefList = new ArrayList<FuncDefNode>();
  public HashMap<String, FuncDefNode> funcMember = new HashMap<String, FuncDefNode>();
  public HashMap<String, VarDefUnitNode> varMember = new HashMap<String, VarDefUnitNode>();
  public ClassBuildNode classBuild;

  public ClassDefNode(Position pos, String name) {
    super(pos);
    this.name = name;
  }

  public FuncDefNode getFuncDef(String name) {
    return funcMember.get(name);
  }
  public Type getVarType(String name) {
    VarDefUnitNode unit = varMember.get(name);
    if (unit == null) return null;
    return unit.type.type;
  }
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}