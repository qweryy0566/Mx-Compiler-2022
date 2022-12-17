package assembly.inst;

import assembly.operand.*;

public class ASMLuiInst extends ASMInst {
    public Reg dest;
    public int imm;

    public ASMLuiInst(Reg dest, int imm) {
        this.dest = dest;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "lui " + dest + ", " + imm;
    }
}