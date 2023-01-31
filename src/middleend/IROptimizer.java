package middleend;

import IR.*;

public class IROptimizer {
  public IROptimizer(IRProgram program) {
    new CFGBuilder(program).work();
    new Mem2Reg(program).work();
  }
}