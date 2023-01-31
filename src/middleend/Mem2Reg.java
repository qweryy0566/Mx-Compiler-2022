package middleend;

import IR.*;
import IR.inst.*;
import IR.entity.*;

public class Mem2Reg {
  IRProgram program;

  public Mem2Reg(IRProgram program) {
    this.program = program;
  }

  public void work() {
    new DomTreeBuilder(program).work();
    // TODO
  }
}
