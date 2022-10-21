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

  public SemanticChecker(GlobalScope globalScope) {
    this.globalScope = globalScope;
    currentScope = globalScope;
  }

  public void visit(ProgramNode node) {
    FuncDefNode mainFunc = globalScope.getFuncDef("main");
    if (mainFunc == null || !mainFunc.returnType.type.equals(IntType))
      throw new BaseError(node.pos, "do not have correct main function");
    for (var def : node.defList) {
      def.accept(this);
    }
  }

  public void visit(FuncDefNode node) {
    // Already add the funcName to globalScope in SymbolCollector
    node.returnType.accept(this);
    currentScope = new Scope(currentScope, node.returnType.type);
    if (node.params != null)
      node.params.accept(this);
    node.suite.accept(this);
    if ((!VoidType.equals(node.returnType.type) && !node.name.equals("main")) && !currentScope.isReturned)
      throw new BaseError(node.pos, "Function " + node.name + " should have return statement");
    currentScope = currentScope.parentScope;
  }

  public void visit(ClassDefNode node) {
    // Already add the className to globalScope in SymbolCollector
    currentScope = new Scope(currentScope, node);
    node.varDefList.forEach(varDef -> varDef.accept(this)); // add varName to currentScope
    if (node.name.equals(node.classBuild.name))
      node.classBuild.accept(this);
    else
      throw new BaseError(node.classBuild.pos, "class name not match");
    node.funcDefList.forEach(funcDef -> funcDef.accept(this)); // add funcName to currentScope
    currentScope = currentScope.parentScope;
  }

  public void visit(VarDefNode node) {
    node.units.forEach(unit -> unit.accept(this));
  }

  public void visit(VarDefUnitNode node) {
    node.type.accept(this);
    if (node.initVal != null)
      node.initVal.accept(this);
    // should check the initVal first
    if (currentScope.hasVarInThisScope(node.varName))
      throw new BaseError(node.pos, "redefinition of variable " + node.varName);
    currentScope.addVar(node.varName, node.type.type);
  }

  public void visit(ParameterListNode node) {
    node.units.forEach(param -> param.accept(this));
  }

  public void visit(TypeNode node) {
    switch (node.type.typeName) {
      case "int":
      case "bool":
      case "string":
      case "void":
      case "null":
      case "this":
        break;
      default:
        if (globalScope.getClassDef(node.type.typeName) == null)
          throw new BaseError(node.pos, "undefined type " + node.type.typeName);
    }
  }

  public void visit(ClassBuildNode node) {
    currentScope = new Scope(currentScope);
    node.suite.accept(this);
    currentScope = currentScope.parentScope;
  }

  public void visit(SuiteNode node) {
    node.stmts.forEach(stmt -> stmt.accept(this));
  }

  public void visit(IfStmtNode node) {
    node.cond.accept(this);
    if (!BoolType.equals(node.cond.type))
      throw new BaseError(node.pos, "invalid condition expression");
    currentScope = new Scope(currentScope);
    node.thenStmt.accept(this);
    currentScope = currentScope.parentScope;
    if (node.elseStmt != null) {
      currentScope = new Scope(currentScope);
      node.elseStmt.accept(this);
      currentScope = currentScope.parentScope;
    }
  }

  public void visit(WhileStmtNode node) {
    node.cond.accept(this);
    if (!BoolType.equals(node.cond.type))
      throw new BaseError(node.pos, "invalid condition expression");
    currentScope = new Scope(currentScope, true);
    node.loop.accept(this);
    currentScope = currentScope.parentScope;
  }

  public void visit(ForStmtNode node) {
    currentScope = new Scope(currentScope, true);
    if (node.varDef != null)
      node.varDef.accept(this);
    if (node.init != null)
      node.init.accept(this);
    if (node.cond != null) {
      node.cond.accept(this);
      if (!BoolType.equals(node.cond.type))
        throw new BaseError(node.pos, "invalid condition expression");
    }
    if (node.step != null)
      node.step.accept(this);
    node.loop.accept(this);
    currentScope = currentScope.parentScope;
  }

  public void visit(ContinueNode node) {
    if (!currentScope.inLoop)
      throw new BaseError(node.pos, "continue statement not in loop");
  }

  public void visit(BreakNode node) {
    if (!currentScope.inLoop)
      throw new BaseError(node.pos, "break statement not in loop");
  }

  public void visit(ReturnStmtNode node) {
    if (currentScope.returnType == null)
      throw new BaseError(node.pos, "return statement outside function");
    if (node.expr == null) {
      if (!currentScope.returnType.equals(VoidType))
        throw new BaseError(node.pos, "return type mismatch");
    } else {
      node.expr.accept(this);
      // AutoType : for LambdaExprNode
      if (AutoType.equals(currentScope.returnType)) {
        currentScope.returnType = node.expr.type;
      } else if (!currentScope.returnType.equals(node.expr.type)) {
        throw new BaseError(node.pos, "return type mismatch");
      }
    }
    currentScope.isReturned = true;
  }

  public void visit(ExprStmtNode node) {
    node.expr.accept(this);
  }

  public void visit(AtomExprNode node) {
    if (node.str.equals("null")) {
      node.type = NullType;
    } else if (node.str.equals("true") || node.str.equals("false")) {
      node.type = BoolType;
    } else if (node.str.matches("\".*\"")) {
      node.type = StringType;
    } else if (node.str.equals("this")) {
      node.type = ThisType;
    } else {
      node.type = IntType;
    }
    // System.out.println(node.type.typeName);
  }

  public void visit(VarExprNode node) {
    node.type = currentScope.getVarType(node.str);
    if (currentScope.inWhichClass != null && currentScope.inWhichClass.getFuncDef(node.str) != null)
      node.funcDef = currentScope.inWhichClass.getFuncDef(node.str);
    else
      node.funcDef = globalScope.getFuncDef(node.str);
    // System.out.println(node.type.typeName);
  }

  public void visit(BinaryExprNode node) {
    node.lhs.accept(this);
    node.rhs.accept(this);
    if (node.lhs.type == null || node.rhs.type == null) {
      // maybe an object compare with null
      if ((node.op.equals("==") || node.op.equals("!=")) && (node.lhs.type.isClass || node.rhs.type.isClass)) {
        node.type = BoolType;
        return;
      } else
        throw new BaseError(node.pos, "invalid expression");
    }
    if (VoidType.equals(node.lhs.type) || VoidType.equals(node.rhs.type))
      throw new BaseError(node.pos, "invalid expression");
    if (!node.lhs.type.equals(node.rhs.type))
      throw new BaseError(node.pos, "Type mismatch");
    switch (node.op) {
      case "+":
      case "<=":
      case ">=":
      case "<":
      case ">":
        if (!node.lhs.type.equals(IntType) && !node.lhs.type.equals(StringType))
          throw new BaseError(node.pos, "Type mismatch");
        node.type = node.op.equals("+") ? new Type(node.lhs.type) : BoolType;
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
        if (!node.lhs.type.equals(IntType))
          throw new BaseError(node.pos, "Type mismatch");
        node.type = IntType;
        break;
      case "&&":
      case "||":
        if (!node.lhs.type.equals(BoolType))
          throw new BaseError(node.pos, "Type mismatch");
        node.type = BoolType;
        break;
    }
  }

  public void visit(UnaryExprNode node) {
    node.expr.accept(this);
    if (node.expr.type == null)
      throw new BaseError(node.pos, "invalid expression");
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
    if (node.expr.type == null)
      throw new BaseError(node.pos, "invalid expression");
    if (!node.expr.isLeftValue() || !node.expr.type.equals(IntType))
      throw new BaseError(node.pos, "Left value required");
    node.type = new Type(IntType);
  }

  public void visit(AssignExprNode node) {
    node.lhs.accept(this);
    node.rhs.accept(this);
    if (node.lhs.type == null || node.rhs.type == null) {
      // maybe object = null
      if (node.rhs.type == null && node.lhs.type.isClass) {
        node.type = new Type(node.lhs.type);
        return;
      } else
        throw new BaseError(node.pos, "invalid expression");
    }
    if (VoidType.equals(node.lhs.type) || VoidType.equals(node.rhs.type))
      throw new BaseError(node.pos, "invalid expression");
    if (!node.lhs.type.equals(node.rhs.type))
      throw new BaseError(node.pos, "Type mismatch");
    node.type = new Type(node.lhs.type);
    if (!node.lhs.isLeftValue())
      throw new BaseError(node.pos, "Left value required");
  }

  public void visit(FuncExprNode node) {
    node.funcName.accept(this);
    if (node.funcName.funcDef == null)
      throw new BaseError(node.pos, "Function " + node.funcName.str + " is not defined");
    var funcDef = node.funcName.funcDef;
    if (node.args != null) {
      node.args.accept(this);
      if (funcDef.params == null || funcDef.params.units.size() != node.args.exprs.size())
        throw new BaseError(node.pos, "Parameter number mismatch");
      for (int i = 0; i < funcDef.params.units.size(); i++) {
        var param = funcDef.params.units.get(i);
        var arg = node.args.exprs.get(i);
        if (!param.type.type.equals(arg.type))
          throw new BaseError(node.pos, "Parameter type mismatch");
      }
    } else {
      if (funcDef.params != null)
        throw new BaseError(node.pos, "Parameter number mismatch");
    }
    node.type = new Type(funcDef.returnType.type);
  }

  public void visit(ArrayExprNode node) {
    node.array.accept(this);
    node.index.accept(this);
    if (node.array.type == null || node.index.type == null)
      throw new BaseError(node.pos, "invalid expression");
    node.type = new Type(node.array.type);
    --node.type.dim;
    if (node.type.dim < 0)
      throw new BaseError(node.pos, "Type mismatch");
  }

  public void visit(MemberExprNode node) {
    node.obj.accept(this);
    if (node.obj.type == null)
      throw new BaseError(node.pos, "invalid expression");
    if (!node.obj.type.isClass && !ThisType.equals(node.obj.type))
      throw new BaseError(node.pos, "Type mismatch");
    var classDef = ThisType.equals(node.obj.type)
        ? currentScope.inWhichClass
        : globalScope.getClassDef(node.obj.type.typeName);
    if (classDef == null)
      throw new BaseError(node.pos, "Class " + node.obj.type.typeName + " is not defined");
    node.type = classDef.getVarType(node.member);
    node.funcDef = classDef.getFuncDef(node.member);
  }

  public void visit(NewExprNode node) {
    for (var size : node.sizeList) {
      size.accept(this);
      if (size.type == null || !size.type.equals(IntType))
        throw new BaseError(node.pos, "invalid expression");
    }
    new TypeNode(node.pos, node.typeName).accept(this);
    node.type = new Type(node.typeName, node.dim);
  }

  public void visit(LambdaExprNode node) {
    Scope tempScope = currentScope;
    currentScope = new Scope(node.isCapture ? currentScope : globalScope, AutoType);
    if (node.params != null)
      node.params.accept(this);
    node.suite.accept(this);
    if (!currentScope.isReturned)
      throw new BaseError(node.pos, "LambdaExpr must return a value");
    node.type = new Type(currentScope.returnType);
    currentScope = tempScope;

    if (node.args != null) {
      node.args.accept(this);
      if (node.params == null || node.params.units.size() != node.args.exprs.size())
        throw new BaseError(node.pos, "Parameter number mismatch");
      for (int i = 0; i < node.params.units.size(); i++) {
        var param = node.params.units.get(i);
        var arg = node.args.exprs.get(i);
        if (!param.type.type.equals(arg.type))
          throw new BaseError(node.pos, "Parameter type mismatch");
      }
    } else {
      if (node.params != null)
        throw new BaseError(node.pos, "Parameter number mismatch");
    }
  }

  public void visit(ExprListNode node) {
    node.exprs.forEach(expr -> expr.accept(this));
  }

}