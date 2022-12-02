package ast.expr;

import ast.*;
import utils.*;
import java.util.ArrayList;
import IR.entity.*;

public class NewExprNode extends ExprNode {
  public String typeName;
  public int dim = 0;
  public ArrayList<ExprNode> sizeList = new ArrayList<ExprNode>();

  public IREntity arraySize;

  public NewExprNode(Position pos, String typeName) {
    super(pos);
    this.typeName = typeName;
  }

  @Override
  public boolean isLeftValue() {
    return false;
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}