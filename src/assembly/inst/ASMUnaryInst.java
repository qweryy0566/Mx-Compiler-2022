package assembly.inst;

import assembly.operand.*;

public class ASMUnaryInst extends ASMInst {
  String op;
  // seqz, snez, slli, xori, addi

  public ASMUnaryInst(String op, Reg rd, Reg rs1) {
    this.op = op;
    this.rd = rd;
    this.rs1 = rs1;
  }

  public ASMUnaryInst(String op, Reg rd, Reg rs1, Imm imm) {
    switch (op) {
      case "shli": this.op = "slli"; break;
      case "ashri": this.op = "srai"; break;
      default: this.op = op;
    }
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }

  @Override
  public String toString() {
    if (imm == null)
      return op + " " + rd + ", " + rs1;
    else
      return op + " " + rd + ", " + rs1 + ", " + imm;
  }
}