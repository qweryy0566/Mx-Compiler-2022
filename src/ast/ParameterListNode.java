package ast;

import utils.*;
import ast.stmt.*;
import ast.expr.*;
import java.util.ArrayList;

public class ParameterListNode extends Node {
  public ArrayList<VarDefUnitNode> units;

  public ParameterListNode(Position pos) {
    super(pos);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
