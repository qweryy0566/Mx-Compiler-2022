package IR.inst;

import IR.*;

public class IRJumpInst extends IRTerminalInst {
  public IRBasicBlock toBlock;

  public IRJumpInst(IRBasicBlock block, IRBasicBlock toBlock) {
    super(block);
    this.toBlock = toBlock;
  }

  @Override
  public String toString() {
    return "br label %" + toBlock.name;
  }
}