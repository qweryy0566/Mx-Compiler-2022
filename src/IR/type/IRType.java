package IR.type;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;

public abstract class IRType {
  public String name;
  public int size;

  public IRType(String name) {
    this.name = name;
  }
}

