package IR;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;

public class IRType {
  public String name;
  public int dim = 0, size = 0, ptrLevel = 0;

  public IRType(String name) {
    this.name = name;
  }
}

