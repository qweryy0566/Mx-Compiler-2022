package assembly.inst;

import assembly.*;

public class ASMInst {
  public ASMBlock parentBlock;

  public ASMInst(ASMBlock parentBlock) {
    this.parentBlock = parentBlock;
  }
}