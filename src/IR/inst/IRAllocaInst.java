package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;
import java.util.LinkedHashSet;

public class IRAllocaInst extends IRInst {
  public IRType type;
  public IRRegister allocaReg;
  public int param_idx = -1;
  // if param_idx != -1, then this is allocated for a parameter
  public IRAllocaInst(IRBasicBlock block, IRType type, IRRegister allocaReg) {
    super(block);
    this.type = type;
    this.allocaReg = allocaReg;
  }
  public IRAllocaInst(IRBasicBlock block, IRType type, IRRegister allocaReg, int param_idx) {
    super(block);
    this.type = type;
    this.allocaReg = allocaReg;
    this.param_idx = param_idx;
  }

  @Override
  public String toString() {
    return allocaReg + " = alloca " + type;
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public LinkedHashSet<IREntity> getUse() {
    return new LinkedHashSet<>();
  }

  @Override
  public IRRegister getDef() {
    return allocaReg;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    // do nothing
  }
}

