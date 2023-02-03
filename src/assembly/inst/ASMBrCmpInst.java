package assembly.inst;

import assembly.operand.*;
import assembly.*;

public class ASMBrCmpInst extends ASMInst {
  String op;
  ASMBlock toBlock;

  public ASMBrCmpInst(String op, Reg rs1, Reg rs2, ASMBlock toBlock) {
    this.op = op;
    this.toBlock = toBlock;
    this.rs1 = rs1;
    this.rs2 = rs2;
  }

  @Override
  public String toString() {
    return op + " " + rs1 + ", " + rs2 + ", " + toBlock.name;
  }
}
