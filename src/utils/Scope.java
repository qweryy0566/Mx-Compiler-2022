package utils;

import java.util.HashMap;

public class Scope {
  public HashMap<String, Type> varMember = new HashMap<>();
  public Scope parentScope;

  public Scope(Scope parentScope) {
    this.parentScope = parentScope;
  }

  
}
