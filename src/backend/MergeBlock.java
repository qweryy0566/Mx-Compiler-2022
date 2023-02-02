package backend;

import java.util.ArrayList;

import assembly.*;
import assembly.inst.*;

public class MergeBlock {
  ASMModule module;

  public MergeBlock(ASMModule module) {
    this.module = module;
  }

  public void work() {
    module.functions.forEach(func -> workOnFunc(func));
  }

  public void workOnFunc(ASMFunction func) {
    ArrayList<ASMBlock> newBlocks = new ArrayList<>();
    newBlocks.add(func.blocks.get(0));
    for (int i = 1; i < func.blocks.size(); ++i) {
      ASMBlock block = func.blocks.get(i);
      ASMBlock lastBlock = newBlocks.get(newBlocks.size() - 1);
      if (block.pred.size() == 0) {
        for (ASMBlock succ : block.succ)
          succ.pred.remove(block);
        continue;
      }
      if (block.pred.size() == 1 && block.pred.get(0) == lastBlock
          && lastBlock.insts.get(lastBlock.insts.size() - 1) instanceof ASMJumpInst jumpInst
          && jumpInst.toBlock == block) {
        lastBlock.insts.removeLast(); // remove jump
        lastBlock.insts.addAll(block.insts);
        lastBlock.succ.remove(block);
        lastBlock.succ.addAll(block.succ);
        for (ASMBlock succ : block.succ) {
          succ.pred.remove(block);
          succ.pred.add(lastBlock);
        }
      } else
        newBlocks.add(block);
    }
    func.blocks = newBlocks;
  }
}
