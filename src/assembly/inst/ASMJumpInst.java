package assembly.inst;

import assembly.*;

public class ASMJumpInst extends ASMInst {
  ASMBlock toBlock;

  public ASMJumpInst(ASMBlock toBlock) {
    this.toBlock = toBlock;
  }
}