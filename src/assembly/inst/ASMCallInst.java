package assembly.inst;

import assembly.operand.*;
import java.util.HashSet;

public class ASMCallInst extends ASMInst {
  String funcName;
  HashSet<Reg> use = new HashSet<>();
  static HashSet<Reg> def = new HashSet<>(PhysicsReg.callerSave);

  public ASMCallInst(String funcName) {
    this.funcName = funcName;
  }

  public void addUse(Reg reg) {
    use.add(reg);
  }

  @Override
  public HashSet<Reg> getUse() {
    return use;
  }
  @Override
  public HashSet<Reg> getDef() {
    return def;
  }

  @Override
  public String toString() {
    return "call " + funcName;
  }
}