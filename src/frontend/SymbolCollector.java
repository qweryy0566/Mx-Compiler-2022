package frontend;

import ast.*;
import ast.expr.*;
import ast.stmt.*;
import utils.*;

public class SymbolCollector implements ASTVisitor {
  private GlobalScope globalScope;
  public SymbolCollector(GlobalScope globalScope) {
    this.globalScope = globalScope;
  }
  public void visit(ProgramNode node) {
    node.defList.forEach(def -> def.accept(this));
  }

  public void visit(FuncDefNode node) {
    if (globalScope.getFuncDef(node.name) != null)
      throw new BaseError(node.pos, "Function " + node.name + " is already defined");
    globalScope.addFunc(node.name, node);
  }
  public void visit(ClassDefNode node) {
    if (globalScope.getClassDef(node.name) != null)
      throw new BaseError(node.pos, "Class " + node.name + " is already defined");
    globalScope.addClass(node.name, node);
    for (var func : node.funcDefList) {
      if (node.funcMember.containsKey(func.name))
        throw new BaseError(func.pos, "Function " + func.name + " is already defined");
      node.funcMember.put(func.name, func);
    }
    for (var var : node.varDefList)
      for (var unit : var.units) {
        if (node.varMember.containsKey(unit.varName))
          throw new BaseError(unit.pos, "Variable " + unit.varName + " is already defined");
        node.varMember.put(unit.varName, unit);
      }
  }
  public void visit(VarDefNode node) {

  }
  public void visit(VarDefUnitNode node) {

  }
  public void visit(ParameterListNode node) {

  }
  public void visit(TypeNode node) {

  }
  public void visit(ClassBuildNode node) {

  }

  public void visit(StmtNode node) {

  }
  public void visit(SuiteNode node) {

  }
  public void visit(IfStmtNode node) {

  }
  public void visit(WhileStmtNode node) {

  }
  public void visit(ForStmtNode node) {

  }
  public void visit(ContinueNode node) {

  }
  public void visit(BreakNode node) {

  }
  public void visit(ReturnStmtNode node) {

  }
  public void visit(ExprStmtNode node) {

  }

  public void visit(ExprNode node) {

  }
  public void visit(AtomExprNode node) {

  }
  public void visit(VarExprNode node) {
    
  }
  public void visit(BinaryExprNode node) {

  }
  public void visit(UnaryExprNode node) {

  }
  public void visit(PreAddExprNode node) {
    
  }
  public void visit(AssignExprNode node) {

  }
  public void visit(FuncExprNode node) {

  }
  public void visit(ArrayExprNode node) {

  }
  public void visit(MemberExprNode node) {

  }
  public void visit(NewExprNode node) {


  }
  public void visit(LambdaExprNode node) {

  }
  public void visit(ExprListNode node) {
    
  }

}