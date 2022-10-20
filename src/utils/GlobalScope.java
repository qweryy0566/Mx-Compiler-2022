package utils;

import java.util.HashMap;

import ast.ClassDefNode;
import ast.FuncDefNode;

public class GlobalScope extends Scope implements BuiltinElements {
  public HashMap<String, FuncDefNode> funcMember = new HashMap<>();
  public HashMap<String, ClassDefNode> classMember = new HashMap<>();

  public GlobalScope() {
    super(null);
    funcMember.put("print", PrintFunc);
    funcMember.put("println", PrintlnFunc);
    funcMember.put("printInt", PrintIntFunc);
    funcMember.put("printlnInt", PrintlnIntFunc);
    funcMember.put("getString", GetStringFunc);
    funcMember.put("getInt", GetIntFunc);
    funcMember.put("toString", ToStringFunc);
  }

  public void addFunc(String name, FuncDefNode funcDef) {
    funcMember.put(name, funcDef);
  }
  public FuncDefNode getFuncDef(String name) {
    return funcMember.get(name);
  }
}
