package middleend;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;
import IR.inst.*;
import IR.entity.*;
import IR.type.*;
import java.util.ArrayList;
import java.util.HashMap;

public class IRBuilder implements ASTVisitor, BuiltinElements {
  IRFunction currentFunction = null;
  IRStructType currentClass = null;
  IRBasicBlock currentBlock = null;
  GlobalScope globalScope;
  Scope currentScope;
  IRProgram root;
  
  HashMap<String, IRStructType> structTypeMap = new HashMap<>();
  HashMap<IRRegister, Integer> arraySizeMap = new HashMap<>();

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
      IRRegister val = new IRRegister("", ((IRPtrType) node.storePtr.type).pointToType());
      currentBlock.addInst(new IRLoadInst(currentBlock, val, node.storePtr));
      return node.value = val;
    }
  }
  private IREntity getCond(ExprNode node) {
    IREntity cond = getVal(node);
    if (cond.type == irBoolType) {
      if (cond instanceof IRBoolConst boolConst)
        return boolConst.val ? irTrueConst : irFalseConst;
      IRRegister tmp = new IRRegister("", irCondType);
      currentBlock.addInst(new IRTruncInst(currentBlock, tmp, cond, irCondType));
      return tmp;
    }
    return cond;
  }
 
  private IRType typeTrans(Type type, boolean isReturnType) {
    IRType irType;
    switch (type.typeName) {
      case "int":
        irType = irIntType; break;
      case "bool":
        irType = isReturnType ? irCondType : irBoolType; break;
      case "string":
        irType = irStringType; break;
      case "void":
        irType = irVoidType; break;
      default:
        irType = new IRPtrType(structTypeMap.get(type.typeName), 1);  // all class is pointer
    }
    if (type.dim > 0)
      irType = new IRPtrType(irType, type.dim);
    return irType;
  }

  private void addStore(IRRegister ptr, ExprNode rhs) {
    if (getVal(rhs).type == irCondType) {
      if (rhs.value instanceof IRCondConst condConst)
        currentBlock.addInst(new IRStoreInst(currentBlock, condConst.val ? irBoolTrueConst : irBoolFalseConst, ptr));
      else {
        IRRegister tmp = new IRRegister("", irBoolType);
        currentBlock.addInst(new IRZextInst(currentBlock, tmp, rhs.value, irBoolType));
        currentBlock.addInst(new IRStoreInst(currentBlock, tmp, ptr));
      }
    } else {
      if (rhs.value instanceof IRNullConst)
        rhs.value = new IRNullConst(((IRPtrType) ptr.type).pointToType());
      currentBlock.addInst(new IRStoreInst(currentBlock, rhs.value, ptr));
    }
  }

  private IRRegister stringCmp(String cmpName, IREntity lhs, IREntity rhs) {
    IRRegister tmp = new IRRegister("", irBoolType), res = new IRRegister("", irCondType);
    currentBlock.addInst(new IRCallInst(currentBlock, tmp, irBoolType, cmpName, lhs, rhs));
    currentBlock.addInst(new IRTruncInst(currentBlock, res, tmp, irCondType));
    return res;
  }

  @Override
  public void visit(ProgramNode node) {
    node.defList.forEach(def -> {
      if (def instanceof ClassDefNode)
        structTypeMap.put(((ClassDefNode) def).name,
            new IRStructType(((ClassDefNode) def).name, ((ClassDefNode) def).varMember.size() << 2));
            // 4 bytes for each member
    });  // first pass to get all struct type
    node.defList.forEach(def -> {
      if (def instanceof ClassDefNode) def.accept(this);
    });
    node.defList.forEach(def -> {
      if (def instanceof VarDefNode) def.accept(this);
    });
    node.defList.forEach(def -> {
      if (def instanceof FuncDefNode) def.accept(this);
    });

    // add global var init function
    if (root.initBlock.insts.size() == 0) {
      root.initFunc = null;
    } else {
      root.initFunc.finish();
      IRBasicBlock mainEntry = root.mainFunc.blocks.get(0);
      mainEntry.insts.addFirst(new IRCallInst(mainEntry, irVoidType, "__mx_global_var_init"));
    }
    root.mainFunc.finish();
  }

  @Override
  public void visit(FuncDefNode node) {
    IRBasicBlock.blockCnt = 0;
    node.returnType.irType = typeTrans(node.returnType.type, true);
    String funcName = currentClass != null ? currentClass.name + "." + node.name : node.name;
    currentFunction = new IRFunction(funcName, node.returnType.irType);
    root.funcList.add(currentFunction);
  
    currentScope = new Scope(currentScope, node.returnType.type);
    currentBlock = currentFunction.appendBlock(new IRBasicBlock(currentFunction, "entry_", 0));
    if (currentClass != null) {  // is a method
      IRPtrType classPtrType = new IRPtrType(currentClass);
      IRRegister thisVal = new IRRegister("this", classPtrType);
      currentFunction.params.add(thisVal);
      // store this pointer
      IRRegister thisAddr = new IRRegister("this.addr", new IRPtrType(classPtrType));
      currentBlock.addInst(new IRAllocaInst(currentBlock, classPtrType, thisAddr, 0));
      currentBlock.addInst(new IRStoreInst(currentBlock, thisVal, thisAddr));
      currentScope.addIRVar("this", thisAddr);
    }
    if (node.params != null)
      node.params.accept(this);
  
    // exit block
    currentFunction.exitBlock = new IRBasicBlock(currentFunction, "return_", 0);
    currentBlock.terminalInst = new IRJumpInst(currentBlock, currentFunction.exitBlock);
    if (node.returnType.type.equals(VoidType)) {
      currentFunction.exitBlock.terminalInst = new IRRetInst(currentFunction.exitBlock, irVoidConst);
    } else {
      IRType retValType = node.returnType.irType == irCondType ? irBoolType : node.returnType.irType;
      currentFunction.retAddr = new IRRegister("retval", new IRPtrType(retValType));
      currentFunction.exitBlock.addInst(new IRAllocaInst(currentBlock, retValType, currentFunction.retAddr));
      IRRegister tmp, retVal = new IRRegister("ret", retValType);
      currentFunction.exitBlock.addInst(new IRLoadInst(currentBlock, retVal, currentFunction.retAddr));
      if (node.returnType.irType == irCondType) {
        tmp = new IRRegister("", irCondType);
        currentFunction.exitBlock.addInst(new IRTruncInst(currentBlock, tmp, retVal, irCondType));
        currentFunction.exitBlock.terminalInst = new IRRetInst(currentFunction.exitBlock, tmp);
      } else {
        currentFunction.exitBlock.terminalInst = new IRRetInst(currentFunction.exitBlock, retVal);
      }
    }
    if (funcName.equals("main"))
      root.mainFunc = currentFunction;

    node.stmts.forEach(stmt -> stmt.accept(this));
    node.irFunc = currentFunction;  // store the ir function
    currentScope = currentScope.parentScope;
    if (!funcName.equals("main")) currentFunction.finish();
    currentFunction = null;
    currentBlock = null;
  }

  @Override
  public void visit(ClassDefNode node) {
    currentScope = new Scope(currentScope, node);
    currentClass = structTypeMap.get(node.name);  // set current class 
    root.structTypeList.add(currentClass);
  
    node.varDefList.forEach(varDef -> varDef.accept(this)); // first add all the fields
    if (node.classBuild != null) {
      currentClass.hasBuild = true;
      node.classBuild.accept(this);
    }
    node.funcDefList.forEach(funcDef -> funcDef.className = node.name);
    node.funcDefList.forEach(funcDef -> funcDef.accept(this)); // ?
    currentScope = currentScope.parentScope;
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
      IRRegister definingPtr = new IRRegister(node.varName + ".addr", new IRPtrType(node.type.irType));
      currentScope.addIRVar(node.varName, definingPtr);  // use the varName as the key
      currentBlock.addInst(new IRAllocaInst(currentBlock, node.type.irType, definingPtr,
          param_idx == -1 ? -1 : param_idx + (currentClass == null ? 0 : 1))); // record the index of the parameter
      if (node.initVal != null) {
        node.initVal.accept(this);
        addStore(definingPtr, node.initVal);
      } else if (node.type.type.isReferenceType() && param_idx == -1) {
        currentBlock.addInst(new IRStoreInst(currentBlock, new IRNullConst(node.type.irType), definingPtr));
      }
    } else if (currentClass != null) {
      currentClass.addMember(node.varName, node.type.irType);
      // do not add to currentScope
    } else {
      IRGlobalVar gVar = new IRGlobalVar(node.varName, node.type.irType);
      if (node.initVal != null && node.initVal instanceof AtomExprNode
          && !node.initVal.type.equals(StringType) && !node.initVal.str.equals("this")) {
        node.initVal.accept(this);
        gVar.initVal = getVal(node.initVal) instanceof IRCondConst
            ? new IRBoolConst(((IRCondConst) node.initVal.value).val)
            : node.initVal.value;
        globalScope.addIRVar(node.varName, gVar);
      } else {
        gVar.initVal = node.type.irType.defaultValue();
        globalScope.addIRVar(node.varName, gVar);
        if (node.initVal != null) {
          IRFunction tmpFunc = currentFunction;
          IRBasicBlock tmpBlock = currentBlock;
          currentFunction = root.initFunc;
          currentBlock = root.initBlock;
          node.initVal.accept(this);
          addStore(gVar, node.initVal);
          root.initBlock = currentBlock;
          currentFunction = tmpFunc;
          currentBlock = tmpBlock;
        }
      }
      root.globalVarList.add(gVar);
    }
  }

  int param_idx = -1;
  @Override
  public void visit(ParameterListNode node) {
    for (param_idx = 0; param_idx < node.units.size(); ++param_idx) {
      VarDefUnitNode unit = node.units.get(param_idx);
      assert unit.initVal == null;
      unit.accept(this);
      IRRegister input = new IRRegister("", unit.type.irType);
      currentFunction.params.add(input);
      currentBlock.addInst(new IRStoreInst(currentBlock, input, currentScope.getIRVarPtr(unit.varName),
          param_idx + (currentClass == null ? 0 : 1)));
    }
    param_idx = -1;
  }

  @Override
  public void visit(TypeNode node) {
    node.irType = typeTrans(node.type, false);
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
    IREntity cond = getCond(node.cond);
    IRBasicBlock lastBlock = currentBlock;
    IRBasicBlock nextBlock = new IRBasicBlock(currentFunction, "if.end_", currentBlock.loopDepth);
    nextBlock.terminalInst = currentBlock.terminalInst;
    IRBasicBlock thenBlock = new IRBasicBlock(currentFunction, "if.then_", nextBlock, currentBlock.loopDepth);
    currentScope = new Scope(currentScope);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(thenBlock);
    node.thenStmts.forEach(stmt -> stmt.accept(this));
    currentScope = currentScope.parentScope;
    if (node.elseStmts != null && !node.elseStmts.isEmpty()) {
      IRBasicBlock elseBlock = new IRBasicBlock(currentFunction, "if.else_", nextBlock, currentBlock.loopDepth);
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
    node.condBlock = new IRBasicBlock(currentFunction, "while.cond_", currentBlock.loopDepth + 1);
    node.loopBlock = new IRBasicBlock(currentFunction, "while.loop_", currentBlock.loopDepth + 1);
    node.nextBlock = new IRBasicBlock(currentFunction, "while.end_", currentBlock.loopDepth);
    node.nextBlock.terminalInst = currentBlock.terminalInst;
    currentBlock.terminalInst = new IRJumpInst(currentBlock, node.condBlock);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.condBlock);
    node.cond.accept(this);
    currentBlock.terminalInst = new IRBranchInst(currentBlock, getCond(node.cond), node.loopBlock, node.nextBlock);
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
    node.condBlock = new IRBasicBlock(currentFunction, "for.cond_", currentBlock.loopDepth + 1);
    node.loopBlock = new IRBasicBlock(currentFunction, "for.loop_", currentBlock.loopDepth + 1);
    node.stepBlock = new IRBasicBlock(currentFunction, "for.step_", currentBlock.loopDepth + 1);
    node.nextBlock = new IRBasicBlock(currentFunction, "for.end_", currentBlock.loopDepth);
    node.nextBlock.terminalInst = currentBlock.terminalInst;
    currentBlock.terminalInst = new IRJumpInst(currentBlock, node.condBlock);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.condBlock);
    if (node.cond != null) {
      node.cond.accept(this);
      currentBlock.terminalInst = new IRBranchInst(currentBlock, getCond(node.cond), node.loopBlock, node.nextBlock);
    } else {
      currentBlock.terminalInst = new IRJumpInst(currentBlock, node.loopBlock);
    }
    currentBlock.isFinished = true;
    currentScope = new Scope(currentScope);
    currentBlock = currentFunction.appendBlock(node.loopBlock);
    node.stmts.forEach(stmt -> stmt.accept(this));
    currentBlock.terminalInst = new IRJumpInst(currentBlock, node.stepBlock);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.stepBlock);
    // Notice: step is not in the scope of for
    currentScope = currentScope.parentScope;
    if (node.step != null) node.step.accept(this);
    currentBlock.terminalInst = new IRJumpInst(currentBlock, node.condBlock);
    currentBlock.isFinished = true;
    currentBlock = currentFunction.appendBlock(node.nextBlock);
    currentScope = currentScope.parentScope;
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
    if (node.expr != null) {
      node.expr.accept(this);
      addStore(currentFunction.retAddr, node.expr);
    }
    currentBlock.terminalInst = new IRJumpInst(currentBlock, currentFunction.exitBlock);
    currentBlock.isFinished = true;
  }

  @Override
  public void visit(ExprStmtNode node) {
    if (node.expr != null) node.expr.accept(this);
  }

  @Override
  public void visit(AtomExprNode node) {
    if (node.type.equals(IntType)) {
      node.value = new IRIntConst(Integer.parseInt(node.str));
    } else if (node.type.equals(BoolType)) {
      node.value = new IRCondConst(node.str.equals("true"));
    } else if (node.type.equals(StringType)) {
      IRStringConst strPtr = root.addStringConst(node.str.substring(1, node.str.length() - 1));  // not contain quotes
      node.value = new IRRegister("", new IRPtrType(irCharType));
      currentBlock
        .addInst(new IRGetElementPtrInst(currentBlock, strPtr, (IRRegister) node.value, irIntConst0, irIntConst0));
    } else if (node.type.equals(NullType)) {
      node.value = new IRNullConst();
    } else { // this
      node.storePtr = currentScope.getIRVarPtr("this");
    }
  }

  @Override
  public void visit(VarExprNode node) {
    // maybe get a null pointer
    node.storePtr = currentScope.getIRVarPtr(node.str);
    if (node.storePtr == null) {  // is a member or a function
      IRRegister thisAddr = (IRRegister) currentScope.getIRVarPtr("this");
      if (thisAddr != null) {  // is a member
        IRType objPtrType =  ((IRPtrType) thisAddr.type).pointToType();
        IRType objRealType = ((IRPtrType) objPtrType).pointToType();
        IRRegister thisVal = new IRRegister("this", objPtrType);
        if (((IRStructType) objRealType).hasMember(node.str)) {
          currentBlock.addInst(new IRLoadInst(currentBlock, thisVal, thisAddr));
          node.storePtr = new IRRegister("this." + node.str,
              new IRPtrType(((IRStructType) objRealType).getMemberType(node.str)));
          currentBlock.addInst(new IRGetElementPtrInst(currentBlock, thisVal, node.storePtr, irIntConst0,
              new IRIntConst(((IRStructType) objRealType).memberOffset.get(node.str))));
        }
      }
    }
  }

  @Override
  public void visit(BinaryExprNode node) {
    node.lhs.accept(this);
    if (!node.op.equals("&&") && !node.op.equals("||"))
      node.rhs.accept(this);
    else {
      IRRegister temp = new IRRegister(".shortCirTemp", new IRPtrType(irBoolType));
      currentBlock.addInst(new IRAllocaInst(currentBlock, irBoolType, temp));
      IRBasicBlock rhsBlock = new IRBasicBlock(currentFunction, "rhsBlock_", currentBlock.loopDepth);
      IRBasicBlock trueBlock = new IRBasicBlock(currentFunction, "trueBlock_", currentBlock.loopDepth);
      IRBasicBlock falseBlock = new IRBasicBlock(currentFunction, "falseBlock_", currentBlock.loopDepth);
      IRBasicBlock nextBlock = new IRBasicBlock(currentFunction, "shortCir.end_", currentBlock.loopDepth);
      nextBlock.terminalInst = currentBlock.terminalInst;
      currentBlock.terminalInst = node.op.equals("&&")
          ? new IRBranchInst(currentBlock, getCond(node.lhs), rhsBlock, falseBlock)
          : new IRBranchInst(currentBlock, getCond(node.lhs), trueBlock, rhsBlock);
      currentBlock.isFinished = true;
      currentBlock = currentFunction.appendBlock(rhsBlock);
      node.rhs.accept(this);
      currentBlock.terminalInst = new IRBranchInst(currentBlock, getCond(node.rhs), trueBlock, falseBlock);
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
      node.value = new IRRegister("", irCondType);
      currentBlock.addInst(new IRTruncInst(currentBlock, (IRRegister) node.value, loadTemp, irCondType));
      return;
    }
    IRRegister dest = null;
    IRType operandType = null;
    String op = null;
    if (node.lhs.type.equals(StringType) || node.rhs.type.equals(StringType)) {
      switch (node.op) {
        case "+":
          node.value = new IRRegister("", irStringType);
          currentBlock
              .addInst(new IRCallInst(currentBlock, (IRRegister) node.value, irStringType, "__mx_stradd",
                  getVal(node.lhs), getVal(node.rhs)));
          break;
        case "<":
          node.value = stringCmp("__mx_strlt", getVal(node.lhs), getVal(node.rhs));
          break;
        case "<=":
          node.value = stringCmp("__mx_strle", getVal(node.lhs), getVal(node.rhs));
          break;
        case ">":
          node.value = stringCmp("__mx_strgt", getVal(node.lhs), getVal(node.rhs));
          break;
        case ">=":
          node.value = stringCmp("__mx_strge", getVal(node.lhs), getVal(node.rhs));
          break;
        case "==":
          node.value = stringCmp("__mx_streq", getVal(node.lhs), getVal(node.rhs));
          break;
        case "!=":
          node.value = stringCmp("__mx_strne", getVal(node.lhs), getVal(node.rhs));
          break;
      }
    } else {
      IREntity lhs = getVal(node.lhs), rhs = getVal(node.rhs);
      switch (node.op) {
        case "+":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRIntConst(((IRIntConst) lhs).val + ((IRIntConst) rhs).val);
          op = "add"; break;
        case "-":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRIntConst(((IRIntConst) lhs).val - ((IRIntConst) rhs).val);
          op = "sub"; break;
        case "*":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRIntConst(((IRIntConst) lhs).val * ((IRIntConst) rhs).val);
          op = "mul"; break;
        case "/":
          if (lhs instanceof IRConst && rhs instanceof IRConst && ((IRIntConst) rhs).val != 0)
            node.value = new IRIntConst(((IRIntConst) lhs).val / ((IRIntConst) rhs).val);
          op = "sdiv"; break;
        case "%":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRIntConst(((IRIntConst) lhs).val % ((IRIntConst) rhs).val);
          op = "srem"; break;
        case "<<":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRIntConst(((IRIntConst) lhs).val << ((IRIntConst) rhs).val);
          op = "shl"; break;
        case ">>":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRIntConst(((IRIntConst) lhs).val >> ((IRIntConst) rhs).val);
          op = "ashr"; break;
        case "&":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRIntConst(((IRIntConst) lhs).val & ((IRIntConst) rhs).val);
          op = "and"; break;
        case "|":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRIntConst(((IRIntConst) lhs).val | ((IRIntConst) rhs).val);
          op = "or"; break; 
        case "^":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRIntConst(((IRIntConst) lhs).val ^ ((IRIntConst) rhs).val);
          op = "xor"; break;
        case "<":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRCondConst(((IRIntConst) lhs).val < ((IRIntConst) rhs).val);
          op = "slt"; break; 
        case "<=":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRCondConst(((IRIntConst) lhs).val <= ((IRIntConst) rhs).val);
          op = "sle"; break;
        case ">":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRCondConst(((IRIntConst) lhs).val > ((IRIntConst) rhs).val);
          op = "sgt"; break;
        case ">=":
          if (lhs instanceof IRConst && rhs instanceof IRConst)
            node.value = new IRCondConst(((IRIntConst) lhs).val >= ((IRIntConst) rhs).val);
          op = "sge"; break;
        case "==":
          if (lhs instanceof IRIntConst && rhs instanceof IRIntConst)
            node.value = new IRCondConst(((IRIntConst) lhs).val == ((IRIntConst) rhs).val);
          op = "eq"; break;
        case "!=":
          if (lhs instanceof IRIntConst && rhs instanceof IRIntConst)
            node.value = new IRCondConst(((IRIntConst) lhs).val != ((IRIntConst) rhs).val);
          op = "ne"; break;
      }
      if (node.value != null) return;
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
          dest = new IRRegister("", irIntType);
          currentBlock.addInst(new IRCalcInst(currentBlock, operandType, dest, lhs, rhs, op));
          break;
        case "<": 
        case "<=":
        case ">": 
        case ">=":
          operandType = irIntType;
          dest = new IRRegister("", irCondType);
          currentBlock.addInst(new IRIcmpInst(currentBlock, operandType, dest, lhs, rhs, op));
          break;
        case "==":
        case "!=":
          if (lhs.type instanceof IRIntType && lhs.type != irIntType) {
            IRRegister tmp = new IRRegister("", irIntType);
            currentBlock.addInst(new IRZextInst(currentBlock, tmp, lhs, irIntType));
            lhs = tmp;
          }
          if (rhs.type instanceof IRIntType && rhs.type != irIntType) {
            IRRegister tmp = new IRRegister("", irIntType);
            currentBlock.addInst(new IRZextInst(currentBlock, tmp, rhs, irIntType));
            rhs = tmp;
          }
          operandType = node.lhs.type.equals(NullType) ? node.rhs.getIRType() : node.lhs.getIRType();
          dest = new IRRegister("tmp", irCondType);
          currentBlock.addInst(new IRIcmpInst(currentBlock, operandType, dest, lhs, rhs, op));
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
        dest = new IRRegister("", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, node.value, irIntConst1, op));
        currentBlock.addInst(new IRStoreInst(currentBlock, dest, node.expr.storePtr));
        break;
      case "--":
        op = "sub";
        node.value = getVal(node.expr);
        dest = new IRRegister("", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, node.value, irIntConst1, op));
        currentBlock.addInst(new IRStoreInst(currentBlock, dest, node.expr.storePtr));
        break;
      case "+":
        node.value = getVal(node.expr);
        break;
      case "-":
        op = "sub";
        dest = new IRRegister("", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, irIntConst0, getVal(node.expr), op));
        node.value = dest;
        break;
      case "~":  // x ^ -1
        op = "xor";
        dest = new IRRegister("", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, getVal(node.expr), irIntConstn1, op));
        node.value = dest;
        break;
      case "!":
        assert node.expr.type.equals(BoolType);
        op = "xor";
        dest = new IRRegister("", irCondType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irCondType, dest, getCond(node.expr), irTrueConst, op));
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
        dest = new IRRegister("", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, getVal(node.expr), irIntConst1, op));
        currentBlock.addInst(new IRStoreInst(currentBlock, dest, node.expr.storePtr));
        node.value = dest;
        node.storePtr = node.expr.storePtr;
        break;
      case "--":
        op = "sub";
        dest = new IRRegister("", irIntType);
        currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, dest, getVal(node.expr), irIntConst1, op));
        currentBlock.addInst(new IRStoreInst(currentBlock, dest, node.expr.storePtr));
        node.value = dest;
        node.storePtr = node.expr.storePtr;
        break;
    }
  }

  @Override
  public void visit(AssignExprNode node) {
    if (node.lhs.str != null && node.lhs.str.equals(node.rhs.str))
      return;
    node.rhs.accept(this);
    node.lhs.accept(this);
    node.storePtr = node.lhs.storePtr;
    node.value = getVal(node.rhs);
    addStore(node.storePtr, node.rhs);
  }

  @Override
  public void visit(FuncExprNode node) {
    node.funcName.accept(this);
    FuncDefNode funcDef = node.funcName.funcDef;
    String funcRealName = funcDef.className == null ? funcDef.name : funcDef.className + "." + funcDef.name;
    funcDef.returnType.irType = typeTrans(funcDef.returnType.type, true);
    IRCallInst call = new IRCallInst(currentBlock, funcDef.returnType.irType, funcRealName);

    // check builtin function
    if (funcDef == ArraySizeFunc) {
      IRRegister array = ((MemberExprNode) node.funcName).objAddr;
      IRRegister tmp1, tmp2 = new IRRegister("", irIntPtrType);
      if (array.type.toString().equals("i32*"))
        tmp1 = array;
      else {
        tmp1 = new IRRegister("", irIntPtrType);
        currentBlock.addInst(new IRBitcastInst(currentBlock, array, irIntPtrType, tmp1));
      }
      currentBlock.addInst(new IRGetElementPtrInst(currentBlock, tmp1, tmp2, irIntConstn1));
      node.value = new IRRegister("", irIntType);
      currentBlock.addInst(new IRLoadInst(currentBlock, (IRRegister) node.value, tmp2)); 
    } else {
      if (funcDef == StringLengthFunc) call.funcName = "strlen";
      else if (funcDef == StringSubStringFunc) call.funcName = "__mx_substring";
      else if (funcDef == StringParseIntFunc) call.funcName = "__mx_parseInt";
      else if (funcDef == StringOrdFunc) call.funcName = "__mx_ord";

      if (funcDef.className != null) {  // method
        if (node.funcName instanceof MemberExprNode)
          call.args.add(((MemberExprNode) node.funcName).objAddr);
        else {  // 需要传入 this 指针
          IRRegister thisPtr = currentScope.getIRVarPtr("this");
          IRRegister thisVal = new IRRegister("", ((IRPtrType) thisPtr.type).pointToType());
          currentBlock.addInst(new IRLoadInst(currentBlock, thisVal, thisPtr));
          call.args.add(thisVal);
        }
      }
      if (node.args != null) {
        node.args.accept(this);
        node.args.exprs.forEach(arg -> call.args.add(getVal(arg)));
      }
      if (funcDef.returnType.irType != irVoidType)
        call.callReg = new IRRegister("", funcDef.returnType.irType);
      currentBlock.addInst(call);
      node.value = call.callReg;
    }
  }

  @Override
  public void visit(ArrayExprNode node) {
    node.array.accept(this);
    node.index.accept(this);
    IRRegister dest = new IRRegister("", getVal(node.array).type);
    currentBlock.addInst(new IRGetElementPtrInst(currentBlock, getVal(node.array), dest, getVal(node.index)));
    node.storePtr = dest;
  }

  @Override
  public void visit(MemberExprNode node) {
    // all class is pointer
    node.obj.accept(this);
    IRType objRealType = getVal(node.obj).type;
    node.objAddr = (IRRegister) node.obj.value;  // store obj addr for member function call
    assert objRealType instanceof IRPtrType;
    objRealType = ((IRPtrType) objRealType).pointToType();
    if (objRealType instanceof IRStructType) {
      IRType memberType = ((IRStructType) objRealType).getMemberType(node.member);
      if (memberType != null) {
        node.storePtr = new IRRegister("", new IRPtrType(memberType));
        currentBlock.addInst(new IRGetElementPtrInst(currentBlock, getVal(node.obj), node.storePtr, irIntConst0,
            new IRIntConst(((IRStructType) objRealType).memberOffset.get(node.member))));
      }
    }
  }

  private IREntity newArray(IRType type, int at, ArrayList<ExprNode> sizeList) {
    IRRegister callReg = new IRRegister("", new IRPtrType(irCharType));
    sizeList.get(at).accept(this);
    IREntity cnt = getVal(sizeList.get(at)), size;
    int sizeOfType = ((IRPtrType) type).pointToType().size;
    if (cnt instanceof IRIntConst) {
      size = new IRIntConst(((IRIntConst) cnt).val * sizeOfType + 4);
    } else {
      IRIntConst typeSize = new IRIntConst(sizeOfType);
      IRRegister tmpSize = new IRRegister("", irIntType);
      currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, tmpSize, cnt, typeSize, "mul"));
      size = new IRRegister("", irIntType);
      currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, (IRRegister) size, tmpSize, irIntConst4, "add"));
    }
    currentBlock.addInst(new IRCallInst(currentBlock, callReg, new IRPtrType(irCharType), "malloc", size));
  
    // store the size of array
    IRRegister ptr, tmp1 = new IRRegister("", irIntPtrType), tmp2 = new IRRegister("", irIntPtrType);
    currentBlock.addInst(new IRBitcastInst(currentBlock, callReg, irIntPtrType, tmp1));
    currentBlock.addInst(new IRStoreInst(currentBlock, cnt, tmp1));
    currentBlock.addInst(new IRGetElementPtrInst(currentBlock, tmp1, tmp2, irIntConst1));
    if (type.toString().equals("i32*")) ptr = tmp2;
    else {
      ptr = new IRRegister("", type);
      currentBlock.addInst(new IRBitcastInst(currentBlock, tmp2, type, ptr));
    }
  
    if (at + 1 < sizeList.size()) {
      IRRegister idx = new IRRegister("", irIntPtrType);
      currentBlock.addInst(new IRAllocaInst(currentBlock, irIntType, idx));
      currentBlock.addInst(new IRStoreInst(currentBlock, irIntConst0, idx));
      IRBasicBlock condBlock = new IRBasicBlock(currentFunction, "for.cond_", currentBlock.loopDepth + 1);
      IRBasicBlock loopBlock = new IRBasicBlock(currentFunction, "for.loop_", currentBlock.loopDepth + 1);
      IRBasicBlock stepBlock = new IRBasicBlock(currentFunction, "for.step_", currentBlock.loopDepth + 1);
      IRBasicBlock nextBlock = new IRBasicBlock(currentFunction, "for.end_", currentBlock.loopDepth);
      nextBlock.terminalInst = currentBlock.terminalInst;
      currentBlock.terminalInst = new IRJumpInst(currentBlock, condBlock);
      currentBlock.isFinished = true;

      currentBlock = currentFunction.appendBlock(condBlock);
      IRRegister cond = new IRRegister("", irCondType);
      IRRegister iVal = new IRRegister("", irIntType);
      currentBlock.addInst(new IRLoadInst(currentBlock, iVal, idx));
      currentBlock.addInst(new IRIcmpInst(currentBlock, irIntType, cond, iVal, cnt, "slt"));
      currentBlock.terminalInst = new IRBranchInst(currentBlock, cond, loopBlock, nextBlock);
      currentBlock.isFinished = true;

      currentBlock = currentFunction.appendBlock(loopBlock);
      IREntity iPtrVal = newArray(((IRPtrType) type).pointToType(), at + 1, sizeList);
      IRRegister iPtr = new IRRegister("", type);
      // should load in every block!
      IRRegister iVal2 = new IRRegister("", irIntType);
      currentBlock.addInst(new IRLoadInst(currentBlock, iVal2, idx));
      currentBlock.addInst(new IRGetElementPtrInst(currentBlock, ptr, iPtr, iVal2));
      currentBlock.addInst(new IRStoreInst(currentBlock, iPtrVal, iPtr));
      currentBlock.terminalInst = new IRJumpInst(currentBlock, stepBlock);
      currentBlock.isFinished = true;

      currentBlock = currentFunction.appendBlock(stepBlock);
      IRRegister iRes = new IRRegister("", irIntType);
      IRRegister iVal3 = new IRRegister("", irIntType);
      currentBlock.addInst(new IRLoadInst(currentBlock, iVal3, idx));
      currentBlock.addInst(new IRCalcInst(currentBlock, irIntType, iRes, iVal3, irIntConst1, "add"));
      currentBlock.addInst(new IRStoreInst(currentBlock, iRes, idx));
      currentBlock.terminalInst = new IRJumpInst(currentBlock, condBlock);
      currentBlock.isFinished = true;

      currentBlock = currentFunction.appendBlock(nextBlock);
    }
    return ptr;
  }
  @Override
  public void visit(NewExprNode node) {
    IRType type = typeTrans(node.type, false);
    if (node.dim > 0) {
      node.value = node.sizeList.size() > 0 ? newArray(type, 0, node.sizeList) : new IRNullConst(type);
    } else {
      IRStructType classType = (IRStructType) ((IRPtrType) type).pointToType();
      IRRegister callReg = new IRRegister("", irStringType);
      currentBlock.
          addInst(new IRCallInst(currentBlock, callReg, irStringType, "malloc", new IRIntConst(classType.size)));
      node.value = new IRRegister("", type);
      currentBlock.addInst(new IRBitcastInst(currentBlock, callReg, type, (IRRegister) node.value));
      if (classType.hasBuild)
        currentBlock.
            addInst(new IRCallInst(currentBlock, null, irVoidType, classType.name + "." + classType.name, node.value));
    }
  }

  @Override
  public void visit(LambdaExprNode node) {} // No need to implement

  @Override
  public void visit(ExprListNode node) {
    node.exprs.forEach(expr -> expr.accept(this));
  }
}