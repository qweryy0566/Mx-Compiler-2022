package assembly.inst;

import assembly.operand.*;
import java.util.HashSet;

public abstract class ASMInst {
  public Reg rd, rs1, rs2;
  public Imm imm;

  public HashSet<Reg> liveIn = new HashSet<Reg>(), liveOut = new HashSet<Reg>();

  public abstract String toString();

  public HashSet<Reg> getUse() {
    HashSet<Reg> ret = new HashSet<Reg>();
    if (rs1 != null) ret.add(rs1);
    if (rs2 != null) ret.add(rs2);
    return ret;
  }

  public HashSet<Reg> getDef() {
    HashSet<Reg> ret = new HashSet<Reg>();
    if (rd != null) ret.add(rd);
    return ret;
  }
}