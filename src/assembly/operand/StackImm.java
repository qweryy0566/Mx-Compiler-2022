package assembly.operand;

import assembly.*;

public class StackImm extends VirtualImm {
  public ASMFunction func;
  int offset;
  boolean isNegative = false;

  public StackImm(ASMFunction func, int offset) {
    super(0);
    this.func = func;
    this.offset = offset;
  }

  public StackImm(ASMFunction func, int offset, boolean isNegative) {
    super(0);
    this.func = func;
    this.offset = offset;
    this.isNegative = isNegative;
  }

  public void calc() {
    value = func.paramUsed + func.allocaUsed + func.spillUsed + offset;
    if (isNegative) value = -value;
  }
}
