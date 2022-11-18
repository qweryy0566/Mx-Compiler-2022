package IR.inst;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.*;

public class IRBranchInst extends IRTerminalInst {
  public IRBasicBlock thenBlock;
  public IRBasicBlock elseBlock;

  public IRBranchInst() {
    super();
  }
}