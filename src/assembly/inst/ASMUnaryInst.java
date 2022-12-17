package assembly.inst;

import assembly.operand.*;

public class ASMUnaryInst extends ASMInst {
  String op;
  // seqz, snez

  public ASMUnaryInst(String op, Reg rd, Reg rs1) {
    this.op = op;
    this.rd = rd;
    this.rs1 = rs1;
  }

  public ASMUnaryInst(String op, Reg rd, Reg rs1, Imm imm) {
    this.op = op;
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }
}