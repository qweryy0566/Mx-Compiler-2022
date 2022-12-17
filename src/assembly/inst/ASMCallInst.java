package assembly.inst;

public class ASMCallInst extends ASMInst {
  String funcName;

  public ASMCallInst(String funcName) {
    this.funcName = funcName;
  }
}