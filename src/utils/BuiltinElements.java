package utils;

import ast.*;
import IR.type.*;

public interface BuiltinElements {
  Type VoidType = new Type("void");
  Type IntType = new Type("int");
  Type BoolType = new Type("bool");
  Type StringType = new Type("string");  
  Type NullType = new Type("null");
  Type ThisType = new Type("this");
  Type AutoType = new Type("auto");

  FuncDefNode PrintFunc = new FuncDefNode(null, VoidType, "print", StringType, 1);
  FuncDefNode PrintlnFunc = new FuncDefNode(null, VoidType, "println", StringType, 1);
  FuncDefNode PrintIntFunc = new FuncDefNode(null, VoidType, "printInt", IntType, 1);
  FuncDefNode PrintlnIntFunc = new FuncDefNode(null, VoidType, "printlnInt", IntType, 1);
  FuncDefNode GetStringFunc = new FuncDefNode(null, StringType, "getString", null, 0);
  FuncDefNode GetIntFunc = new FuncDefNode(null, IntType, "getInt", null, 0);
  FuncDefNode ToStringFunc = new FuncDefNode(null, StringType, "toString", IntType, 1);

  FuncDefNode StringLengthFunc = new FuncDefNode(null, IntType, "length", null, 0);
  FuncDefNode StringSubStringFunc = new FuncDefNode(null, StringType, "substring", IntType, 2);
  FuncDefNode StringParseIntFunc = new FuncDefNode(null, IntType, "parseInt", null, 0);
  FuncDefNode StringOrdFunc = new FuncDefNode(null, IntType, "ord", IntType, 1);
  FuncDefNode ArraySizeFunc = new FuncDefNode(null, IntType, "size", null, 0);

  // ------------------ IR Builtin Types ------------------

  IRType irVoidType = new IRVoidType();
  IRType irIntType = new IRIntType(32);
  IRType irNullType = new IRPtrType(irVoidType);
  IRType irBoolType = new IRIntType(8);
  IRType irCondType = new IRIntType(1);
}