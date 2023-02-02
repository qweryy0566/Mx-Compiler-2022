package assembly.inst;

import assembly.*;

public class ASMJumpInst extends ASMInst {
  public ASMBlock toBlock;

  public ASMJumpInst(ASMBlock toBlock) {
    this.toBlock = toBlock;
  }

  @Override
  public String toString() {
    return "j " + toBlock.name;
  }
}