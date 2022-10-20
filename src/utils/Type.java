package utils;

import ast.*;

public class Type {
  public TypeCategory category;
  public String typeName;
  public int dim = 0;
  public FuncDefNode funcDef = null;

  public Type(String typeName) {
    this.typeName = typeName;
  }

  public Type(String typeName, int dim) {
    this.typeName = typeName;
    this.dim = dim;
  }
};