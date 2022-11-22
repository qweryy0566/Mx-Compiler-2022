package utils;

import java.util.HashMap;

import ast.*;
import IR.entity.*;

public class GlobalScope extends Scope implements BuiltinElements {
  public HashMap<String, FuncDefNode> funcMember = new HashMap<>();
  public HashMap<String, ClassDefNode> classMember = new HashMap<>();

  public GlobalScope() {
    // super(null);
    funcMember.put("print", PrintFunc);
    funcMember.put("println", PrintlnFunc);
    funcMember.put("printInt", PrintIntFunc);
    funcMember.put("printlnInt", PrintlnIntFunc);
    funcMember.put("getString", GetStringFunc);
    funcMember.put("getInt", GetIntFunc);
    funcMember.put("toString", ToStringFunc);

    ClassDefNode stringDef = new ClassDefNode(null, "string");
    stringDef.funcMember.put("length", StringLengthFunc);
    stringDef.funcMember.put("substring", StringSubStringFunc);
    stringDef.funcMember.put("parseInt", StringParseIntFunc);
    stringDef.funcMember.put("ord", StringOrdFunc);
    classMember.put("string", stringDef);
    classMember.put("int", new ClassDefNode(null, "int"));
    classMember.put("bool", new ClassDefNode(null, "bool"));
  }

  public void addFunc(String name, FuncDefNode funcDef) {
    funcMember.put(name, funcDef);
  }
  public FuncDefNode getFuncDef(String name) {
    return funcMember.get(name);
  }

  public void addClass(String name, ClassDefNode classDef) {
    classMember.put(name, classDef);
  }
  public ClassDefNode getClassDef(String name) {
    return classMember.get(name);
  }
}
