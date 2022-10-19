package ast;

import ast.expr.*;
import ast.stmt.*;

public interface ASTVisitor {
  void visit(Node node);
  void visit(ProgramNode node);

  void visit(FuncDefNode node);
  void visit(ClassDefNode node);
  void visit(VarDefNode node);
  void visit(VarDefUnitNode node);
  void visit(ParameterListNode node);
  void visit(TypeNode node);
  void visit(ClassBuildNode node);


  void visit(StmtNode node);
  void visit(SuiteNode node);
  void visit(IfStmtNode node);
  void visit(WhileStmtNode node);
  void visit(ForStmtNode node);
  void visit(ContinueNode node);
  void visit(BreakNode node);
  void visit(ReturnStmtNode node);
  void visit(ExprStmtNode node);

  void visit(ExprNode node);
  void visit(BinaryExprNode node);
  void visit(UnaryExprNode node);
  void visit(AssignExprNode node);
  void visit(FuncExprNode node);
  void visit(ArrayExprNode node);
  void visit(MemberExprNode node);  
  void visit(NewExprNode node);
  void visit(LambdaExprNode node);
  void visit(ExprListNode node);
}
