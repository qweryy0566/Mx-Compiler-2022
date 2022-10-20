package frontend;

import grammar.*;
import utils.*;

import java.util.HashMap;
import ast.*;
import ast.stmt.*;
import ast.expr.*;

public class SemanticChecker implements ASTVisitor, BuiltinElements {
  private GlobalScope globalScope;
  private Scope currentScope;

  public SemanticChecker() {
    globalScope = new GlobalScope();
    currentScope = globalScope;
  }

  public void visit(ProgramNode node) {
    for (var def : node.defList) {
      def.accept(this);
    }
  }

  public void visit(FuncDefNode node) {
    // TODO
    node.suite.accept(this);
  }

  public void visit(ClassDefNode node) {

  }

  public void visit(VarDefNode node) {
    node.units.forEach(unit -> unit.accept(this));
  }

  public void visit(VarDefUnitNode node) {
    node.type.accept(this);
    // TODO : check type is valid
    if (node.initVal != null)
      node.initVal.accept(this);
    if (currentScope.hasVar(node.varName))
      throw new BaseError(node.pos, "redefinition of variable " + node.varName);
    currentScope.addVar(node.varName, node.type.type);
  }

  public void visit(ParameterListNode node) {

  }

  public void visit(TypeNode node) {

  }

  public void visit(ClassBuildNode node) {

  }

  public void visit(SuiteNode node) {
    // TODO
    node.stmts.forEach(stmt -> stmt.accept(this));
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
    node.expr.accept(this);
  }

  public void visit(ExprNode node) {

  }

  public void visit(AtomExprNode node) {
    if (node.str.equals("null")) {
      node.type = new Type(NullType);
    } else if (node.str.equals("true") || node.str.equals("false")) {
      node.type = new Type(BoolType);
    } else if (node.str.matches("\".*\"")) {
      node.type = new Type(StringType);
    } else if (node.str.equals("this")) {
      node.type = new Type(ThisType);
    } else {
      node.type = new Type(IntType);
    }
    // System.out.println(node.type.typeName);
  }

  public void visit(VarExprNode node) {
    node.type = currentScope.getVarType(node.str);
    if (node.type == null)
      throw new BaseError(node.pos, "Undefined variable " + node.str);
    // System.out.println(node.type.typeName);
  }

  public void visit(BinaryExprNode node) {
    node.lhs.accept(this);
    node.rhs.accept(this);
    if (!node.lhs.type.equals(node.rhs.type))
      throw new BaseError(node.pos, "Type mismatch");
    node.type = new Type(node.lhs.type);
    switch (node.op) {
      case "+":
      case "<=":
      case ">=":
      case "<":
      case ">":
        if (!node.type.equals(IntType) && !node.type.equals(StringType))
          throw new BaseError(node.pos, "Type mismatch");
        break;
      case "*":
      case "/":
      case "%":
      case "-":
      case ">>":
      case "<<":
      case "&":
      case "^":
      case "|":
        if (!node.type.equals(IntType))
          throw new BaseError(node.pos, "Type mismatch");
        break;
      case "&&":
      case "||":
        if (!node.type.equals(BoolType))
          throw new BaseError(node.pos, "Type mismatch");
        break;
    }
  }

  public void visit(UnaryExprNode node) {
    node.expr.accept(this);
    if (node.op.equals("++") || node.op.equals("--")) {
      if (!node.expr.isLeftValue() || !node.expr.type.equals(IntType))
        throw new BaseError(node.pos, "Left value required");
      node.type = new Type(IntType);
    } else if (node.op.equals("!")) {
      if (!node.expr.type.equals(BoolType))
        throw new BaseError(node.pos, "Type is not bool");
      node.type = new Type(BoolType);
    } else {
      if (!node.expr.type.equals(IntType))
        throw new BaseError(node.pos, "Type is not int");
      node.type = new Type(IntType);
    }
  }

  public void visit(PreAddExprNode node) {
    node.expr.accept(this);
    if (!node.expr.isLeftValue() || !node.expr.type.equals(IntType))
      throw new BaseError(node.pos, "Left value required");
    node.type = new Type(IntType);
  }

  public void visit(AssignExprNode node) {
    node.lhs.accept(this);
    node.rhs.accept(this);
    if (!node.lhs.type.equals(node.rhs.type))
      throw new BaseError(node.pos, "Type mismatch");
    node.type = new Type(node.lhs.type);
    if (!node.lhs.isLeftValue())
      throw new BaseError(node.pos, "Left value required");
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
    node.exprs.forEach(expr -> expr.accept(this));
  }

}