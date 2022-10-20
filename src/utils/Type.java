package utils;

import ast.*;

public class Type {
  public TypeCategory category;
  public String typeName;
  public int dim = 0;
  public FuncDefNode funcDef = null;

  public Type(String typeName) {
    this.typeName = typeName;
    switch (typeName) {
      case "int":
        this.category = TypeCategory.INT; break;
      case "bool":
        this.category = TypeCategory.BOOL; break;
      case "string":
        this.category = TypeCategory.STRING; break;
      case "void":
        this.category = TypeCategory.VOID; break;
      case "null":
        this.category = TypeCategory.NULL; break;
      case "this":
        this.category = TypeCategory.THIS; break;
      default:
        this.category = TypeCategory.CLASS;
    }
  }
  public Type(String typeName, int dim) {
    this(typeName);
    this.dim = dim;
  }
  public Type(TypeCategory category, String typeName) {
    this.category = category;
    this.typeName = typeName;
  }
  public Type(Type type) {
    this.category = type.category;
    this.typeName = type.typeName;
    this.dim = type.dim;
    this.funcDef = type.funcDef;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;
    if (!(obj instanceof Type)) return false;
    Type type = (Type) obj;
    if (this.category != type.category) return false;
    if (this.dim != type.dim) return false;
    if (this.category == TypeCategory.CLASS) {
      if (!this.typeName.equals(type.typeName)) return false;
    }
    return true;
  }
};