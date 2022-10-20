package utils;

import java.util.HashMap;

public class Scope {
  public HashMap<String, Type> varMember = new HashMap<>();
  public Scope parentScope;
  public Type returnType;

  public Scope(Scope parentScope) {
    this.parentScope = parentScope;
  }

  public void addVar(String name, Type type) {
    varMember.put(name, type);
  }
  public boolean hasVarInThisScope(String name) {
    return varMember.containsKey(name) || (parentScope != null && parentScope.hasVarInThisScope(name));
  }
  public Type getVarType(String name) {
    if (varMember.containsKey(name))
      return varMember.get(name);
    else
      return parentScope != null ? parentScope.getVarType(name) : null;
  }
}
