package assembly.inst;

import assembly.operand.*;

public class ASMMvInst extends ASMInst {
    public Reg rd;
    public Reg rs1;

    public ASMMvInst(Reg rd, Reg rs) {
        this.rd = rd;
        this.rs1 = rs;
    }
}