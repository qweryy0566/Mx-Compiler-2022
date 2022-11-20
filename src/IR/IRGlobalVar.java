package IR;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.entity.IREntity;
import IR.inst.*;
import IR.type.IRType;

public class IRGlobalVar {
  public String name;
  public IRType type;
  public IREntity initVal;
  // TODO : design
}