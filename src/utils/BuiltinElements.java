package utils;

import ast.FuncDefNode;

public interface BuiltinElements {
  Type VoidType = new Type("void");
  Type IntType = new Type("int");
  Type BoolType = new Type("bool");
  Type StringType = new Type("string");  
  Type NullType = new Type("null");
  Type ThisType = new Type("this");
  Type AutoType = new Type("auto");

  FuncDefNode PrintFunc = new FuncDefNode(null, VoidType, "print", StringType);
  FuncDefNode PrintlnFunc = new FuncDefNode(null, VoidType, "println", StringType);
  FuncDefNode PrintIntFunc = new FuncDefNode(null, VoidType, "printInt", IntType);
  FuncDefNode PrintlnIntFunc = new FuncDefNode(null, VoidType, "printlnInt", IntType);
  FuncDefNode GetStringFunc = new FuncDefNode(null, StringType, "getString", null);
  FuncDefNode GetIntFunc = new FuncDefNode(null, IntType, "getInt", null);
  FuncDefNode ToStringFunc = new FuncDefNode(null, StringType, "toString", IntType);
}