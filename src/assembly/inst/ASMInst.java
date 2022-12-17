package assembly.inst;

import assembly.operand.*;

public abstract class ASMInst {
  public Reg rd, rs1, rs2;
  public Imm imm;
}