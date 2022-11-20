package utils;

import ast.*;

public class Type {
  public String typeName;
  public int dim = 0;
  public boolean isClass = false;

  public Type(String typeName) {
    this.typeName = typeName;
    if (!typeName.equals("void")
        && !typeName.equals("int")
        && !typeName.equals("bool")
        && !typeName.equals("string")
        && !typeName.equals("null")
        && !typeName.equals("this"))
      isClass = true;
  }

  public Type(String typeName, int dim) {
    this(typeName);
    this.dim = dim;
  }

  public Type(Type type) {
    this.typeName = type.typeName;
    this.dim = type.dim;
    this.isClass = type.isClass;
  }

  public boolean isReferenceType() {
    return dim > 0 || isClass;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if (obj == this)
      return true;
    if (!(obj instanceof Type))
      return false;
    Type type = (Type) obj;
    if (this.dim != type.dim)
      return false;
    if (!this.typeName.equals(type.typeName))
      return false;
    return true;
  }

  public boolean isArrayType() {
    return dim > 0;
  }
};