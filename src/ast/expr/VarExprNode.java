package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;

public class VarExprNode extends AtomExprNode {
  public VarExprNode (Position pos, String str) {
    super(pos, str);
  }


  @Override
  public boolean isLeftValue() {
    return true;
  }
  
  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}
