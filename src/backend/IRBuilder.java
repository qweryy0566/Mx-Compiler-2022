package backend;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;
import IR.inst.*;
import IR.entity.*;
import IR.type.*;

public class IRBuilder implements ASTVisitor, BuiltinElements {
  private IRFunction currentFunction;
  private IRBasicBlock currentBlock;
  private GlobalScope globalScope;
  private Scope currentScope;
  private IRProgram root;

  public IRBuilder(IRProgram root, GlobalScope globalScope) {
    this.root = root;
    this.globalScope = globalScope;
    currentScope = globalScope;
  }

  private IREntity getVal(ExprNode node) {
    if (node.value != null)
      return node.value;
    else {
      assert node.storePtr != null;
      IRRegister val = new IRRegister(node.str, ((IRPtrType) node.storePtr.type).baseType);
      currentBlock.addInst(new IRLoadInst(currentBlock, val, node.storePtr));
      return node.value = val;
    }
  }

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
    node.units.forEach(unit -> unit.accept(this));
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
    node.cond.accept(this);
    IRBasicBlock thenBlock = new IRBasicBlock(currentFunction, "then");
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
    node.expr.accept(this);
  }

  @Override
  public void visit(AtomExprNode node) {
    if (node.type == IntType) {
      node.value = new IRIntConst(Integer.parseInt(node.str));
    } else if (node.type == BoolType) {
      node.value = new IRBoolConst(node.str.equals("true"));
    } else if (node.type == StringType) {
      root.addStringConst(node.str);  // contain quotes
      // TODO: add string constant
    } else if (node.type == NullType) {
      node.value = new IRNullConst();
    } else {
      
    }
  }

  @Override
  public void visit(VarExprNode node) {
    node.storePtr = currentScope.getIRVarPtr(node.str);
    // if (node.type != null && !node.type.isArrayType()) {
    //   IRRegister ptr = currentScope.getIRVarPtr(node.str);
    //   IRRegister val = new IRRegister(node.str, ((IRPtrType) ptr.type).baseType);
    //   currentBlock.addInst(new IRLoadInst(currentBlock, val, ptr));
    //   node.value = val;
    // }
  }

  @Override
  public void visit(BinaryExprNode node) {
    node.lhs.accept(this);
    if (!node.op.equals("&&") && !node.op.equals("||"))
      node.rhs.accept(this);
    else {
      // TODO: short circuit
    }
    if (node.lhs.type == StringType || node.rhs.type == StringType) {
      // TODO : special for string

    } else {
      IRRegister dest = null;
      IRType operandType = null;
      String op = null;
      switch (node.op) {
        case "+": op = "add"; break;
        case "-": op = "sub"; break;
        case "*": op = "mul"; break;
        case "/": op = "sdiv"; break;
        case "%": op = "srem"; break;
        case "<<": op = "shl"; break;
        case ">>": op = "ashr"; break;
        case "&": op = "and"; break;
        case "|": op = "or"; break; 
        case "^": op = "xor"; break;
        case "<": op = "slt"; break; 
        case "<=": op = "sle"; break;
        case ">": op = "sgt"; break;
        case ">=": op = "sge"; break;
        case "==": op = "eq"; break;
        case "!=": op = "ne"; break;
      }
      switch (node.op) {
        case "+":
        case "-": 
        case "*": 
        case "/": 
        case "%": 
        case "<<":
        case ">>":
        case "&": 
        case "|": 
        case "^": 
          operandType = irIntType;
          dest = new IRRegister("tmp", irIntType);
          currentBlock.addInst(new IRCalcInst(currentBlock, operandType, dest, getVal(node.lhs), getVal(node.rhs), op));
          break;
        case "<": 
        case "<=":
        case ">": 
        case ">=":
          operandType = irIntType;
          dest = new IRRegister("tmp", irCondType);
          currentBlock.addInst(new IRIcmpInst(currentBlock, operandType, dest, getVal(node.lhs), getVal(node.rhs), op));
          break;
        case "==":
        case "!=":
          operandType = node.lhs.type == NullType ? node.rhs.getIRType() : node.lhs.getIRType();
          dest = new IRRegister("tmp", irCondType);
          currentBlock.addInst(new IRIcmpInst(currentBlock, operandType, dest, getVal(node.lhs), getVal(node.rhs), op));
          break;
      }
      node.value = dest;
    }
  }

  @Override
  public void visit(UnaryExprNode node) {
    node.expr.accept(this);
    IRRegister dest = null;
    IRType operandType = null;
    String op = null;
    switch (node.op) {
      case "++":
        operandType = irIntType;

      case "--":
      case "+":
      case "-":
      case "~":
      case "!":
    }
  }

  @Override
  public void visit(PreAddExprNode node) {

  }

  @Override
  public void visit(AssignExprNode node) {
    node.rhs.accept(this);
    node.lhs.accept(this);
    node.storePtr = node.lhs.storePtr;
    node.value = getVal(node.rhs);
    currentBlock.addInst(new IRStoreInst(currentBlock, node.value, node.storePtr));
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
  public void visit(LambdaExprNode node) {
  }

  @Override
  public void visit(ExprListNode node) {

  }
}