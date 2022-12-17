package assembly.inst;

import assembly.operand.*;

public class ASMStoreInst extends ASMInst {
  int size, offset;
  // size : 1 -> sb, 4 -> sw

  public ASMStoreInst(int size, Reg rs1, Reg rs2, int offset) {
    this.size = size;
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.offset = offset;
  }
  public ASMStoreInst(int size, Reg rs1, Reg rs2) {
    this(size, rs1, rs2, 0);
  }
}