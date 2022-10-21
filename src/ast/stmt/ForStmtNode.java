package ast.stmt;

import ast.*;
import ast.expr.*;
import utils.*;
import java.util.ArrayList;

public class ForStmtNode extends StmtNode {
  public VarDefNode varDef;
  public ExprNode init, cond, step;
  public ArrayList<StmtNode> stmts = new ArrayList<StmtNode>();

  public ForStmtNode(Position pos) {
    super(pos);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}