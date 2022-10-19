package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;
import java.util.ArrayList;

public class NewExprNode extends ExprNode {
  public String typeName;
  public int dim = 0;
  public ArrayList<ExprNode> sizeList;
  public NewExprNode(Position pos, String typeName) {
    super(pos);
    this.typeName = typeName;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}