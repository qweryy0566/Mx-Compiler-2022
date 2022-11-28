package backend;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;
import IR.inst.*;
import IR.entity.*;
import IR.type.*;
import java.util.HashMap;

public class IRBuilder implements ASTVisitor, BuiltinElements {
  IRFunction currentFunction = null;
  IRStructType currentClass = null;
  IRBasicBlock currentBlock = null;
  GlobalScope globalScope;
  Scope currentScope;
  IRProgram root;
  
  HashMap<String, IRStructType> structTypeMap = new HashMap<>();

  public IRBuilder(IRProgram root, GlobalScope globalScope) {
    this.root = root;
    this.globalScope = globalScope;
    currentScope = globalScope;
  }

  // to get the value and add the instruction to the current block
  private IREntity getVal(ExprNode node) {
    if (node.value != null)
      return node.value;
    else {
      assert node.storePtr != null;
      IRRegister val = new IRRegister(node.str, ((IRPtrType) node.storePtr.type).pointToType());
      currentBlock.addInst(new IRLoadInst(currentBlock, val, node.storePtr));
      return node.value = val;
    }
  }
  private IREntity getStringPtr(IREntity str) {
    IRRegister ret = new IRRegister("str", irStringType);
    currentBlock.addInst(new IRGetElementPtrInst(currentBlock, str, ret, irIntConst0, irIntConst0));
    return ret;
  }


  @Override
  public void visit(ProgramNode node) {
    node.defList.forEach(def -> def.accept(this));
  }

  @Override
  public void visit(FuncDefNode node) {
    node.returnType.accept(this);
    String funcName = currentClass != null ? currentClass.name + "." + node.name : node.name;
    currentFunction = new IRFunction(funcName, node.returnType.irType);
    root.funcList.add(currentFunction);
  
    currentScope = new Scope(currentScope, node.returnType.type);
    currentBlock = currentFunction.appendBlock(new IRBasicBlock(currentFunction, "entry"));
    if (currentClass != null) {  // is a method
      IRPtrType classPtrType = new IRPtrType(currentClass);
      IRRegister thisVal = new IRRegister("this", classPtrType);
      currentFunction.params.add(thisVal);
      // store this pointer
      IRRegister thisAddr = new IRRegister("this.addr", new IRPtrType(classPtrType));
      currentBlock.addInst(new IRAllocaInst(currentBlock, classPtrType, thisAddr));
      currentBlock.addInst(new IRStoreInst(currentBlock, thisVal, thisAddr));
      currentScope.addIRVar("this", thisAddr);
    }
    if (node.params != null)
      node.params.accept(this);
    node.stmts.forEach(stmt -> stmt.accept(this));
    if (currentBlock.terminalInst == null) {
      if (node.returnType.type == VoidType)
        currentBlock.addInst(new IRRetInst(currentBlock, irVoidConst));
      else
        currentBlock.addInst(new IRRetInst(currentBlock, irIntConst0));
    }
    node.irFunc = currentFunction;  // store the ir function
    currentScope = currentScope.parentScope;
    currentFunction.addAllocasToEntryBlock();
    currentFunction = null;
    currentBlock = null;
  }

  @Override
  public void visit(ClassDefNode node) {
    currentScope = new Scope(currentScope, node);
    currentClass = new IRStructType(node.name);
    root.structTypeList.add(currentClass);
  
    node.varDefList.forEach(varDef -> varDef.accept(this)); // first add all the fields
    if (node.classBuild != null)
      node.classBuild.accept(this);
    node.funcDefList.forEach(funcDef -> funcDef.className = node.name);
    node.funcDefList.forEach(funcDef -> funcDef.accept(this)); // ?
    currentScope = currentScope.parentScope;
    structTypeMap.put(node.name, currentClass);
    currentClass = null;
  }

  @Override
  public void visit(VarDefNode node) {
    node.units.forEach(unit -> unit.accept(this));
  }

  @Override
  public void visit(VarDefUnitNode node) {
    node.type.accept(this);
    if (currentFunction != null) {  // check if it's in a function first
      IRRegister storePtr = new IRRegister(node.varName + ".addr", new IRPtrType(node.type.irType));
      currentScope.addIRVar(node.varName, storePtr);  // use the varName as the key
      currentBlock.addInst(new IRAllocaInst(currentBlock, node.type.irType, storePtr));
      if (node.initVal != null) {
        node.initVal.accept(this);
        currentBlock.addInst(new IRStoreInst(currentBlock, getVal(node.initVal), storePtr));
      }
    } else if (currentClass != null) {
      currentClass.addMember(node.varName, node.type.irType);
      // do not add to currentScope
    } else {
      // TODO: global variable
    }
  }

  @Override
  public void visit(ParameterListNode node) {
    node.units.forEach(unit -> {
      assert unit.initVal == null;
      unit.accept(this);
      IRRegister input = new IRRegister(unit.varName, unit.type.irType);
      currentFunction.params.add(input);
      currentBlock.addInst(new IRStoreInst(currentBlock, input, currentScope.getIRVarPtr(unit.varName)));
    });
  }

  @Override
  public void visit(TypeNode node) {
    switch (node.type.typeName) {
      case "int":
        node.irType = irIntType; break;
      case "bool":
        node.irType = irBoolType; break;
      case "string":
        // TODO : string
        node.irType = irStringType; break;
      case "void":
        node.irType = irVoidType; break;
      default:
        node.irType = new IRPtrType(structTypeMap.get(node.type.typeName), 1);  // all class is pointer
    }
    if (node.type.dim > 0)
      node.irType = new IRPtrType(node.irType, node.type.dim);
  }

  @Override
  public void visit(ClassBuildNode node) {
    node.transToFuncDef().accept(this);
  }

  @Override
  public void visit(SuiteNode node) {
    currentScope = new Scope(currentScope);
    node.stmts.forEach(stmt -> stmt.accept(this));
    currentScope = currentScope.parentScope;
  }

  @Override
  public void visit(IfStmtNode node) {
    node.cond.accept(this);
    IREntity cond = getVal(node.cond);
    IRBasicBlock lastBlock = currentBlock;
    IRBasicBlock nextBlock = new IRBasicBlock(currentFunction, "");
    nextBlock.terminalInst = currentBlock.terminalInst;
    IRBasicBlock thenBlock = new IRBasicBlock(currentFunction, "if_then_", nextBlock);
    currentScope = new Scope(currentScope);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(thenBlock);
    node.thenStmts.forEach(stmt -> stmt.accept(this));
    currentScope = currentScope.parentScope;
    if (node.elseStmts != null && !node.elseStmts.isEmpty()) {
      IRBasicBlock elseBlock = new IRBasicBlock(currentFunction, "if_else_", nextBlock);
      currentScope = new Scope(currentScope);
      currentBlock.isFinished = true;
      currentBlock = currentFunction.appendBlock(elseBlock);
      node.elseStmts.forEach(stmt -> stmt.accept(this));
      currentScope = currentScope.parentScope;
      lastBlock.terminalInst = new IRBranchInst(lastBlock, cond, thenBlock, elseBlock);
    } else {
      lastBlock.terminalInst = new IRBranchInst(lastBlock, cond, thenBlock, nextBlock);
    }
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(nextBlock);
  }

  @Override
  public void visit(WhileStmtNode node) {
    node.condBlock = new IRBasicBlock(currentFunction, "while_cond_");
    node.loopBlock = new IRBasicBlock(currentFunction, "while_loop_");
    node.nextBlock = new IRBasicBlock(currentFunction, "");
    node.nextBlock.terminalInst = currentBlock.terminalInst;
    currentBlock.terminalInst = new IRJumpInst(currentBlock, node.condBlock);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.condBlock);
    node.cond.accept(this);
    currentBlock.terminalInst = new IRBranchInst(currentBlock, getVal(node.cond), node.loopBlock, node.nextBlock);
    currentBlock = currentFunction.appendBlock(node.loopBlock);
    currentScope = new Scope(currentScope, node);
    node.stmts.forEach(stmt -> stmt.accept(this));
    currentScope = currentScope.parentScope;
    currentBlock.terminalInst = new IRJumpInst(currentBlock, node.condBlock);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.nextBlock);
  }

  @Override
  public void visit(ForStmtNode node) {
    currentScope = new Scope(currentScope, node);
    if (node.varDef != null) node.varDef.accept(this);
    if (node.init != null) node.init.accept(this);
    node.condBlock = new IRBasicBlock(currentFunction, "for_cond_");
    node.loopBlock = new IRBasicBlock(currentFunction, "for_loop_");
    node.stepBlock = new IRBasicBlock(currentFunction, "for_step_");
    node.nextBlock = new IRBasicBlock(currentFunction, "");
    node.nextBlock.terminalInst = currentBlock.terminalInst;
    currentBlock.terminalInst = new IRJumpInst(currentBlock, node.condBlock);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.condBlock);
    if (node.cond != null) {
      node.cond.accept(this);
      currentBlock.terminalInst = new IRBranchInst(currentBlock, getVal(node.cond), node.loopBlock, node.nextBlock);
    } else {
      currentBlock.terminalInst = new IRJumpInst(currentBlock, node.loopBlock);
    }
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.loopBlock);
    node.stmts.forEach(stmt -> stmt.accept(this));
    currentBlock.terminalInst = new IRJumpInst(currentBlock, node.stepBlock);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.stepBlock);
    if (node.step != null) node.step.accept(this);
    currentBlock.terminalInst = new IRJumpInst(currentBlock, node.condBlock);
    currentScope = currentScope.parentScope;
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.nextBlock);
  }

  @Override
  public void visit(ContinueNode node) {
    if (currentScope.inWhichLoop instanceof WhileStmtNode)
      currentBlock.terminalInst = new IRJumpInst(currentBlock, currentScope.inWhichLoop.condBlock);
    else
      currentBlock.terminalInst = new IRJumpInst(currentBlock, ((ForStmtNode) currentScope.inWhichLoop).stepBlock);
    currentBlock.isFinished = true;
  }

  @Override
  public void visit(BreakNode node) {
    currentBlock.terminalInst = new IRJumpInst(currentBlock, currentScope.inWhichLoop.nextBlock);
    currentBlock.isFinished = true;
  }

  @Override
  public void visit(ReturnStmtNode node) {
    if (node.expr == null) {
      currentBlock.terminalInst = new IRRetInst(currentBlock, irVoidConst);
    } else {
      node.expr.accept(this);
      currentBlock.terminalInst = new IRRetInst(currentBlock, getVal(node.expr));
    }
    currentBlock.isFinished = true;
  }

  @Override
  public void visit(ExprStmtNode node) {
    if (node.expr != null) node.expr.accept(this);
  }

  @Override
  public void visit(AtomExprNode node) {
    if (node.type == IntType) {
      node.value = new IRIntConst(Integer.parseInt(node.str));
    } else if (node.type == BoolType) {
      node.value = new IRCondConst(node.str.equals("true"));
    } else if (node.type == StringType) {
      node.value = root.addStringConst(node.str.substring(1, node.str.length() - 1));  // not contain quotes
    } else if (node.type == NullType) {
      node.value = new IRNullConst();
    } else {
    }
  }

  @Override
  public void visit(VarExprNode node) {
    // maybe get a null pointer
    node.storePtr = currentScope.getIRVarPtr(node.str);
    if (node.storePtr == null) {  // is a member
      IRRegister thisAddr = currentScope.getIRVarPtr("this");
      assert thisAddr != null && thisAddr.type instanceof IRPtrType;
      IRType objRealType =  ((IRPtrType) thisAddr.type).pointToType();
      IRRegister thisVal = new IRRegister("this", objRealType);
      currentBlock.addInst(new IRLoadInst(currentBlock, thisVal, thisAddr));
      node.storePtr = new IRRegister("this." + node.str, new IRPtrType(((IRStructType) objRealType).getMemberType(node.str)));
      currentBlock.addInst(new IRGetElementPtrInst(currentBlock, thisVal, node.storePtr, irIntConst0,
          new IRIntConst(((IRStructType) objRealType).memberOffset.get(node.str))));
    }
  }

  @Override
  public void visit(BinaryExprNode node) {
    node.lhs.accept(this);
    if (!node.op.equals("&&") && !node.op.equals("||"))
      node.rhs.accept(this);
    else {
      IRRegister temp = new IRRegister(".shortCirTemp", new IRPtrType(irBoolType));
      currentScope.addIRVar(String.valueOf(IRRegister.regCnt - 1), temp);  // 变量名不可能以数字开头
      IRBasicBlock rhsBlock = new IRBasicBlock(currentFunction, "rhsBlock_");
      IRBasicBlock trueBlock = new IRBasicBlock(currentFunction, "trueBlock_");
      IRBasicBlock falseBlock = new IRBasicBlock(currentFunction, "falseBlock_");
      IRBasicBlock nextBlock = new IRBasicBlock(currentFunction, "");
      nextBlock.terminalInst = currentBlock.terminalInst;
      currentBlock.terminalInst = node.op.equals("&&")
          ? new IRBranchInst(currentBlock, getVal(node.lhs), rhsBlock, falseBlock)
          : new IRBranchInst(currentBlock, getVal(node.lhs), trueBlock, rhsBlock);
      currentBlock.isFinished = true;
      currentBlock = currentFunction.appendBlock(rhsBlock);
      node.rhs.accept(this);
      currentBlock.terminalInst = new IRBranchInst(currentBlock, getVal(node.rhs), trueBlock, falseBlock);
      currentBlock.isFinished = true;
      currentBlock = currentFunction.appendBlock(trueBlock);
      currentBlock.addInst(new IRStoreInst(currentBlock, irBoolTrueConst, temp));
      currentBlock.terminalInst = new IRJumpInst(currentBlock, nextBlock);
      currentBlock.isFinished = true;
      currentBlock = currentFunction.appendBlock(falseBlock);
      currentBlock.addInst(new IRStoreInst(currentBlock, irBoolFalseConst, temp));
      currentBlock.terminalInst = new IRJumpInst(currentBlock, nextBlock);
      currentBlock.isFinished = true;
      currentBlock = currentFunction.appendBlock(nextBlock);
      IRRegister loadTemp = new IRRegister(".loadTemp", irBoolType);
      currentBlock.addInst(new IRLoadInst(currentBlock, loadTemp, temp));
      node.value = new IRRegister("tmp", irCondType);
      currentBlock.addInst(new IRTruncInst(currentBlock, (IRRegister) node.value, loadTemp, irCondType));
      return;
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
          getVal(node.lhs);
          getVal(node.rhs);
          operandType = node.lhs.type == NullType ? node.rhs.getIRType() : node.lhs.getIRType();
          dest = new IRRegister("tmp", irCondType);
          currentBlock.addInst(new IRIcmpInst(currentBlock, operandType, dest, node.lhs.value, node.rhs.value, op));
          break;
      }
      node.value = dest;
    }
  }

  @Override
  public void visit(UnaryExprNode node) {
    node.expr.accept(this);
    IRRegister dest = null;
    String op = null;
    switch (node.op) {
      case "++":
        op = "add";
        node.value = getVal(node.expr);
        dest = new IRRegister("tmp", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, node.value, irIntConst1, op));
        currentBlock.addInst(new IRStoreInst(currentBlock, dest, node.expr.storePtr));
        break;
      case "--":
        op = "sub";
        node.value = getVal(node.expr);
        dest = new IRRegister("tmp", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, node.value, irIntConst1, op));
        currentBlock.addInst(new IRStoreInst(currentBlock, dest, node.expr.storePtr));
        break;
      case "+":
        node.value = getVal(node.expr);
        break;
      case "-":
        op = "sub";
        dest = new IRRegister("tmp", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, irIntConst0, getVal(node.expr), op));
        node.value = dest;
        break;
      case "~":  // x ^ -1
        op = "xor";
        dest = new IRRegister("tmp", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, getVal(node.expr), irIntConstn1, op));
        node.value = dest;
        break;
      case "!":
        assert node.expr.type == BoolType;
        op = "xor";
        dest = new IRRegister("tmp", irCondType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irCondType, dest, getVal(node.expr), irTrueConst, op));
        node.value = dest;
    }
  }

  @Override
  public void visit(PreAddExprNode node) {
    node.expr.accept(this);
    IRRegister dest = null;
    String op = null;
    switch (node.op) {
      case "++":
        op = "add";
        dest = new IRRegister("tmp", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, getVal(node.expr), irIntConst1, op));
        currentBlock.addInst(new IRStoreInst(currentBlock, dest, node.expr.storePtr));
        node.value = dest;
        node.storePtr = node.expr.storePtr;
        break;
      case "--":
        op = "sub";
        dest = new IRRegister("tmp", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, getVal(node.expr), irIntConst1, op));
        currentBlock.addInst(new IRStoreInst(currentBlock, dest, node.expr.storePtr));
        node.value = dest;
        node.storePtr = node.expr.storePtr;
        break;
    }
  }

  @Override
  public void visit(AssignExprNode node) {
    node.rhs.accept(this);
    node.lhs.accept(this);
    node.storePtr = node.lhs.storePtr;
    if (node.rhs.type == StringType) {
      node.value = getStringPtr(node.rhs.value);
    } else {
      node.value = getVal(node.rhs);
      if (node.value.type == irCondType) {
        node.value = new IRRegister("tmp", irBoolType);
        currentBlock.addInst(new IRZextInst(currentBlock, (IRRegister) node.value, getVal(node.rhs), irBoolType));
      }
    }
    currentBlock.addInst(new IRStoreInst(currentBlock, node.value, node.storePtr));
  }

  @Override
  public void visit(FuncExprNode node) {
    // TODO: call a function
    node.funcName.accept(this);

  }

  @Override
  public void visit(ArrayExprNode node) {
    node.array.accept(this);
    node.index.accept(this);
    IRRegister dest = new IRRegister("", node.array.value.type);
    currentBlock.addInst(new IRGetElementPtrInst(currentBlock, getVal(node.array), dest, getVal(node.index)));
    node.storePtr = dest;
  }

  @Override
  public void visit(MemberExprNode node) {
    // all class is pointer
    node.obj.accept(this);
    IRType objRealType = getVal(node.obj).type;
    assert objRealType instanceof IRPtrType;
    objRealType = ((IRPtrType) objRealType).pointToType();
    assert objRealType instanceof IRStructType;
    node.storePtr = new IRRegister(node.member, new IRPtrType(((IRStructType) objRealType).getMemberType(node.member)));
    currentBlock.addInst(new IRGetElementPtrInst(currentBlock, getVal(node.obj), node.storePtr, irIntConst0,
        new IRIntConst(((IRStructType) objRealType).memberOffset.get(node.member))));
  }

  @Override
  public void visit(NewExprNode node) {
    // TODO : new array and new class
  }

  @Override
  public void visit(LambdaExprNode node) {} // No need to implement

  @Override
  public void visit(ExprListNode node) {
    node.exprs.forEach(expr -> expr.accept(this));
  }
}