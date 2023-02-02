package middleend;

import IR.*;
import IR.inst.*;

import java.util.LinkedList;

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
    
    LinkedList<IRBasicBlock> newBlocks = new LinkedList<>();
    for (var block : func.blocks)
      if (!block.preds.isEmpty() || block == func.entryBlock)
        newBlocks.add(block);
      else
        for (var succ : block.succs)
          succ.preds.remove(block);
    func.blocks = newBlocks;
  }
}
