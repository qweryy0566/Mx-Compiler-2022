package assembly.inst;

import assembly.operand.*;

public class ASMLuiInst extends ASMInst {
  public ASMLuiInst(Reg dest, Imm imm) {
    this.rd = dest;
    this.imm = imm;
  }

  @Override
  public String toString() {
    return "lui " + rd + ", " + imm;
  }
}