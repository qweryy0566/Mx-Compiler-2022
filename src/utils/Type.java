package utils;

public class Type {
  public String typeName;
  public int dim = 0;

  public Type(String typeName) {
    this.typeName = typeName;
  }

  public Type(String typeName, int dim) {
    this.typeName = typeName;
    this.dim = dim;
  }
};