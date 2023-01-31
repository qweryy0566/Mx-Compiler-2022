package middleend;

import IR.*;
import IR.inst.*;

public class CFGBuilder {
  IRProgram program;

  public CFGBuilder(IRProgram program) {
    this.program = program;
  }

  public void work() {
    program.funcList.forEach(func -> workOnFunc(func)); 
  }

  public void workOnFunc(IRFunction func) {
    func.blocks.forEach(block -> {
      if (block.terminalInst instanceof IRJumpInst) {
        IRJumpInst jumpInst = (IRJumpInst) block.terminalInst;
        block.succs.add(jumpInst.toBlock);
        jumpInst.toBlock.preds.add(block);
      } else if (block.terminalInst instanceof IRBranchInst) {
        IRBranchInst branchInst = (IRBranchInst) block.terminalInst;
        block.succs.add(branchInst.thenBlock);
        block.succs.add(branchInst.elseBlock);
        branchInst.thenBlock.preds.add(block);
        branchInst.elseBlock.preds.add(block);
      }
    });
  }
}
