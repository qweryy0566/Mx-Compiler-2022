package assembly.inst;

import assembly.operand.*;

public class ASMLoadInst extends ASMInst {
  int size;
  // size : 1 -> lb, 4 -> lw

  public ASMLoadInst(int size, Reg rd, Reg rs1, Imm imm) {
    this.size = size;
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }
  public ASMLoadInst(int size, Reg rd, Reg rs1) {
    this(size, rd, rs1, new Imm(0));
  }

  @Override
  public String toString() {
    return "l" + (size == 1 ? "b" : "w") + " " + rd + ", " + imm + "(" + rs1 + ")";
  }
}