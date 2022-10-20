package utils;

import java.util.HashMap;

import ast.ClassDefNode;
import ast.FuncDefNode;

public class GlobalScope extends Scope {
  public HashMap<String, FuncDefNode> funcMember = new HashMap<>();
  public HashMap<String, ClassDefNode> classMember = new HashMap<>();

  public GlobalScope() {
    super(null);
  }

}
