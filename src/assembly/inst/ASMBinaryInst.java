package assembly.inst;

import assembly.operand.*;

public class ASMBinaryInst extends ASMInst {
  String op;

  public ASMBinaryInst(String op, Reg rd, Reg rs1, Reg rs2) {
    switch (op) {
      case "sdiv": this.op = "div"; break;
      case "srem": this.op = "rem"; break;
      case "shl": this.op = "sll"; break;
      case "ashr": this.op = "sra"; break;
      default: this.op = op;
    }
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
  } 
  // public ASMBinaryInst(String op, Reg rd, Reg rs1, Imm imm) {
  //   this.op = op;
  //   this.rd = rd;
  //   this.rs1 = rs1;
  //   this.imm = imm;
  // }
}