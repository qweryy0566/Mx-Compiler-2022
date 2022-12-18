package assembly.inst;

import assembly.operand.*;

public class ASMStoreInst extends ASMInst {
  int size;
  // size : 1 -> sb, 4 -> sw

  public ASMStoreInst(int size, Reg rs1, Reg rs2, Imm imm) {
    this.size = size;
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.imm = imm;
  }
  public ASMStoreInst(int size, Reg rs1, Reg rs2) {
    this(size, rs1, rs2, new Imm(0));
  }

  @Override
  public String toString() {
    return "s" + (size == 1 ? "b" : "w") + " " + rs2 + ", " + imm + "(" + rs1 + ")";
  }
}