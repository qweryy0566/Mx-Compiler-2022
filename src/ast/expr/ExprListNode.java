package ast.expr;

import ast.*;
import ast.stmt.*;
import java.util.ArrayList;
import utils.*;

public class ExprListNode extends Node {
  public ArrayList<ExprNode> exprs;

  public ExprListNode(Position pos) {
    super(pos);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}