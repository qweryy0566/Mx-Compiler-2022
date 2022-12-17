package assembly.inst;

import assembly.operand.*;

public class ASMLiInst extends ASMInst {
    public Reg reg;
    public Imm imm;

    public ASMLiInst(Reg reg, Imm imm) {
        this.reg = reg;
        this.imm = imm;
    }
}