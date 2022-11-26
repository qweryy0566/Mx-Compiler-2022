package IR.type;

public class IRPtrType extends IRType {
  public IRType baseType;
  public int dim = 1;

  public IRPtrType(IRType baseType) {
    super(baseType.name + "*", 32);
    this.baseType = baseType;
  }

  public IRPtrType(IRType baseType, int dim) {
    super(baseType.name + "*".repeat(dim), 32);
    if (baseType instanceof IRPtrType) {
      this.baseType = ((IRPtrType) baseType).baseType;
      this.dim = ((IRPtrType) baseType).dim + dim;
    } else {
      this.baseType = baseType;
      this.dim = dim;
    }
  }

  public IRType pointToType() {
    return dim == 1 ? baseType : new IRPtrType(baseType, dim - 1);
  }

  public boolean equals(Object obj) {
    if (obj instanceof IRPtrType) {
      IRPtrType ptrType = (IRPtrType) obj;
      return baseType.equals(ptrType.baseType) && dim == ptrType.dim;
    }
    return false;
  }
}