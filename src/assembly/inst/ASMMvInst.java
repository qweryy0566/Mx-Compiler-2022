package assembly.inst;

import assembly.operand.*;

public class ASMMvInst extends ASMInst {
    public Reg rd;
    public Reg rs;

    public ASMMvInst(Reg rd, Reg rs) {
        this.rd = rd;
        this.rs = rs;
    }
}