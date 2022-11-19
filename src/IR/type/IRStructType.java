package IR.type;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import java.util.ArrayList;

public class IRStructType extends IRType {
  public ArrayList<IRType> membersType = new ArrayList<IRType>();

  public IRStructType(String name) {
    super(name);
  }
}