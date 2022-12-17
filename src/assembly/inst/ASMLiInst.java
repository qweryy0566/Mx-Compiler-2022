package assembly.inst;

import assembly.operand.*;

public class ASMLiInst extends ASMInst {
    public VirtualImm imm;

    public ASMLiInst(Reg rd, VirtualImm imm) {
      this.rd = rd;
      this.imm = imm;
    }
}