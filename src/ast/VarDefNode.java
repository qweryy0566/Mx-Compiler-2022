package ast;

import ast.expr.*;
import ast.stmt.*;
import java.util.ArrayList;
import utils.*;

public class VarDefNode extends StmtNode {
  public ArrayList<VarDefUnitNode> units = new ArrayList<VarDefUnitNode>();

  public VarDefNode(Position pos) {
    super(pos);
  }
  
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}