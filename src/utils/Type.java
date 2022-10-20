package utils;

import ast.*;

public class Type {
  public String typeName;
  public int dim = 0;

  public Type(String typeName) {
    this.typeName = typeName;
  }
  public Type(String typeName, int dim) {
    this(typeName);
    this.dim = dim;
  }
  
  public Type(Type type) {
    this.typeName = type.typeName;
    this.dim = type.dim;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (obj == this) return true;
    if (!(obj instanceof Type)) return false;
    Type type = (Type) obj;
    if (this.dim != type.dim) return false;
    if (!this.typeName.equals(type.typeName)) return false;
    return true;
  }
};