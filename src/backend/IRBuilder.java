package backend; 

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;

public class IRBuilder implements ASTVisitor {
  private IRFunction currentFunction;
  @Override
  public void visit(ProgramNode node) {

  }

  @Override
  public void visit(FuncDefNode node) {

  }
  
  @Override
  public void visit(ClassDefNode node) {

  }
  
  @Override
  public void visit(VarDefNode node) {

  }
  
  @Override
  public void visit(VarDefUnitNode node) {

  }
  
  @Override
  public void visit(ParameterListNode node) {

  }
  
  @Override
  public void visit(TypeNode node) {

  }
  
  @Override
  public void visit(ClassBuildNode node) {

  }

  @Override
  public void visit(SuiteNode node) {

  }
  
  @Override
  public void visit(IfStmtNode node) {

  }
  
  @Override
  public void visit(WhileStmtNode node) {

  }
  
  @Override
  public void visit(ForStmtNode node) {

  }
  
  @Override
  public void visit(ContinueNode node) {

  }
  
  @Override
  public void visit(BreakNode node) {

  }
  
  @Override
  public void visit(ReturnStmtNode node) {

  }
  
  @Override
  public void visit(ExprStmtNode node) {

  }

  @Override
  public void visit(AtomExprNode node) {

  }
  
  @Override
  public void visit(VarExprNode node) {

  }
  
  @Override
  public void visit(BinaryExprNode node) {

  }
  
  @Override
  public void visit(UnaryExprNode node) {

  }
  
  @Override
  public void visit(PreAddExprNode node) {

  }
  
  @Override
  public void visit(AssignExprNode node) {

  }
  
  @Override
  public void visit(FuncExprNode node) {

  }
  
  @Override
  public void visit(ArrayExprNode node) {

  }
  
  @Override
  public void visit(MemberExprNode node) {

  }
  
  @Override
  public void visit(NewExprNode node) {

  }
  
  @Override
  public void visit(LambdaExprNode node) {}
  
  @Override
  public void visit(ExprListNode node) {

  }
}