package IR.type;

import IR.entity.*;

public class IRPtrType extends IRType {
  public IRType baseType;
  public int dim = 1, cnt = 1;

  public IRPtrType(IRType baseType) {
    super(baseType.name + "*", 4);
    this.baseType = baseType;
    if (baseType instanceof IRPtrType) {
      this.baseType = ((IRPtrType) baseType).baseType;
      this.dim = ((IRPtrType) baseType).dim + 1;
    } else {
      this.dim = 1;
    }
  }

  public IRPtrType(IRType baseType, int dim) {
    super(baseType.name + "*".repeat(dim), 4);
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

  @Override
  public String toString() {
    return baseType.toString() + "*".repeat(dim);
  }

  @Override
  public IREntity defaultValue() {
    return irNullConst;
  }
}