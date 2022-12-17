package assembly.inst;

import assembly.operand.*;

public class ASMLoadInst extends ASMInst {
  Reg rd, rs1;
  int size, offset;
  // size : 1 -> lb, 4 -> lw

  public ASMLoadInst(int size, Reg rd, Reg rs1, int offset) {
    this.size = size;
    this.rd = rd;
    this.rs1 = rs1;
    this.offset = offset;
  }
  public ASMLoadInst(int size, Reg rd, Reg rs1) {
    this(size, rd, rs1, 0);
  }
}