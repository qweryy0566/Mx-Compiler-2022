package assembly.inst;

import assembly.operand.*;

public class ASMMvInst extends ASMInst {
  public ASMMvInst(Reg rd, Reg rs) {
    this.rd = rd;
    this.rs1 = rs;
  }

  @Override
  public String toString() {
    return "mv " + rd + ", " + rs1;
  }
}