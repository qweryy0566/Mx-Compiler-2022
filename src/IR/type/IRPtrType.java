package IR.type;

public class IRPtrType extends IRType {
  public IRType baseType;
  public int dim = 1;

  public IRPtrType(IRType baseType) {
    super(baseType.name + "*", 32);
    this.baseType = baseType;
  }
}