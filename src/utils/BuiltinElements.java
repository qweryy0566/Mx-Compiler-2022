package utils;

public interface BuiltinElements {
  Type VoidType = new Type(TypeCategory.VOID, "void");
  Type IntType = new Type(TypeCategory.INT, "int");
  Type BoolType = new Type(TypeCategory.BOOL, "bool");
  Type StringType = new Type(TypeCategory.STRING, "string");  
  Type NullType = new Type(TypeCategory.NULL, "null");
  Type ThisType = new Type(TypeCategory.THIS, "this");
}