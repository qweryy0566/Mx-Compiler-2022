package ast;

import IR.type.*;
import utils.*;

public class TypeNode extends Node {
  public Type type;
  public IRType irType;

  public TypeNode(Position pos) {
    super(pos);
  }
  public TypeNode(Position pos, String name) {
    super(pos);
    switch (name) {
      case "int":
        this.type = IntType; break;
      case "bool":
        this.type = BoolType; break;
      case "string":
        this.type = StringType; break;
      case "void":
        this.type = VoidType; break;
      default:
        this.type = new Type(name);
    }
    
  }
  public TypeNode(Position pos, String name, int dim) {
    super(pos);
    this.type = new Type(name, dim);
  }

  @Override
  public void accept(ASTVisitor visitor) {
    visitor.visit(this);
  }
}