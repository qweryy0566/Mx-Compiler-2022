package IR.type;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;

public class IRArrayType extends IRType {
  public IRType baseType;
  public int cnt;

  public IRArrayType(IRType baseType, int cnt) {
    super("[" + String.valueOf(cnt) + " x " + baseType.name + "]");
    this.baseType = baseType;
    this.cnt = cnt;
    this.size = baseType.size * cnt;
  }
}