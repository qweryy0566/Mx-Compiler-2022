package ast;

import utils.*;
import java.util.ArrayList;

public class ParameterListNode extends Node {
  public ArrayList<VarDefUnitNode> units = new ArrayList<VarDefUnitNode>();

  public ParameterListNode(Position pos) {
    super(pos);
  }
  public ParameterListNode(Position pos, Type type, int cnt) {
    super(pos);
    for (int i = 0; i < cnt; ++i)
      units.add(new VarDefUnitNode(pos, new TypeNode(pos, type.typeName, type.dim), "p" + i));
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
