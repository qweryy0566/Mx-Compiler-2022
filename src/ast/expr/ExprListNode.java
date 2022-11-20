package ast.expr;

import ast.*;
import java.util.ArrayList;
import utils.*;

public class ExprListNode extends Node {
  public ArrayList<ExprNode> exprs = new ArrayList<ExprNode>();

  public ExprListNode(Position pos) {
    super(pos);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}