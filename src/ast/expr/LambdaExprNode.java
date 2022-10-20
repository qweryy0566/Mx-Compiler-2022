package ast.expr;

import ast.*;
import ast.stmt.*;
import utils.*;
import java.util.ArrayList;

public class LambdaExprNode extends ExprNode {
  public boolean isCapture;
  public ParameterListNode params;
  public SuiteNode suite;
  public ExprListNode args;

  public LambdaExprNode(Position pos) {
    super(pos);
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