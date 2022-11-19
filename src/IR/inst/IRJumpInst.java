package IR.inst;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;

public class IRJumpInst extends IRTerminalInst {
  public IRBasicBlock toBlock;

  public IRJumpInst() {
    super();
  }

  public String toString() {
    return "";
  }
}