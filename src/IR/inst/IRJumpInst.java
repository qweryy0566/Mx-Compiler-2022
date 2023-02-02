package IR.inst;

import IR.*;
import IR.entity.*;

import java.util.LinkedHashSet;

public class IRJumpInst extends IRTerminalInst {
  public IRBasicBlock toBlock;

  public IRJumpInst(IRBasicBlock block, IRBasicBlock toBlock) {
    super(block);
    this.toBlock = toBlock;
  }

  @Override
  public String toString() {
    return "br label %" + toBlock.name;
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
    return null;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
  }
}