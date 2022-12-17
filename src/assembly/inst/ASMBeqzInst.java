package assembly.inst;

import assembly.operand.*;
import assembly.*;

public class ASMBeqzInst extends ASMInst {
  ASMBlock toBlock;

  public ASMBeqzInst(Reg rs, ASMBlock toBlock) {
    this.rs1 = rs;
    this.toBlock = toBlock;
  }
}