package IR.type;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;

public class IRPtrType extends IRType {
  public IRType baseType;
  public int dim = 1;

  public IRPtrType(IRType baseType) {
    super(baseType.name + "*");
    this.baseType = baseType;
    this.size = 4;
  }
}