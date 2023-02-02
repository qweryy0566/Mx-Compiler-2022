package middleend;

import IR.*;
import IR.inst.*;
import IR.entity.*;

public class FakeBranchEliminator {
  IRProgram program;

  public FakeBranchEliminator(IRProgram program) {
    this.program = program;
  }

  public void work() {
    program.funcList.forEach(func -> workOnFunc(func));
  }

  void workOnFunc(IRFunction func) {
    for (var block : func.blocks)
      if (block.terminalInst instanceof IRBranchInst branchInst)
        if (branchInst.cond instanceof IRCondConst constBool)
          if (constBool.val)
            block.terminalInst = new IRJumpInst(branchInst.parentBlock, branchInst.thenBlock);
          else
            block.terminalInst = new IRJumpInst(branchInst.parentBlock, branchInst.thenBlock);
  }
}
