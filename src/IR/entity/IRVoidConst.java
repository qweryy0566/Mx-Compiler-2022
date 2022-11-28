package IR.entity;

public class IRVoidConst extends IRConst {
  public IRVoidConst() {
    super(irVoidType);
  }

  @Override
  public String toString() {
    return "void";
  }
}