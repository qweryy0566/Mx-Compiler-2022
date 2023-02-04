package middleend;

import IR.*;

public class IROptimizer {
  public IROptimizer(IRProgram program) {
    new Global2Local(program).work();
    new CFGBuilder(program).work();
    new Mem2Reg(program).work();
    new DeadCodeEliminator(program).work();
    new ConstPropagation(program).work();
  }
}