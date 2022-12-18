package assembly.operand;

import IR.entity.*;

public class GlobalValue extends Global {
  public int word, size;
  public GlobalValue(IRGlobalVar var) {
    super(var.name);
    if (var.initVal instanceof IRIntConst) {
      word = ((IRIntConst) var.initVal).val;
      size = 4;
    } else if (var.initVal instanceof IRBoolConst) {
      word = ((IRBoolConst) var.initVal).val ? 1 : 0;
      size = 1;
    } else if (var.initVal instanceof IRNullConst) {
      word = 0;
      size = 4;
    } else {
      throw new Error("GlobalValue: " + var.initVal);
    }
  }

  public String toString() {
    String ret = name + ":\n";
    ret += (size == 4 ? "  .word " : "  .byte ") + word + "\n";
    return ret;
  }
}