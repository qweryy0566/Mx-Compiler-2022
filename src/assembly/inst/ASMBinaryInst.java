package assembly.inst;

import assembly.operand.*;

public class ASMBinaryInst extends ASMInst {
  String op;
  Reg rd, rs1, rs2;
  Imm imm;

  public ASMBinaryInst(String op, Reg rd, Reg rs1, Reg rs2) {
    this.op = op;
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
  } 
  public ASMBinaryInst(String op, Reg rd, Reg rs1, Imm imm) {
    this.op = op;
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }
}