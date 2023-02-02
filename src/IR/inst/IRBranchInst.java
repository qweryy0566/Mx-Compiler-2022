package IR.inst;

import IR.*;
import IR.entity.*;
import java.util.HashSet;

public class IRBranchInst extends IRTerminalInst {
  public IREntity cond;
  public IRBasicBlock thenBlock;
  public IRBasicBlock elseBlock;

  public IRBranchInst(IRBasicBlock block, IREntity cond, IRBasicBlock thenBlock, IRBasicBlock elseBlock) {
    super(block);
    this.cond = cond;
    this.thenBlock = thenBlock;
    this.elseBlock = elseBlock;
  }
  @Override
  public String toString() {
    return "br " + cond.toStringWithType() + ", label %" + thenBlock.name + ", label %" + elseBlock.name;
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HashSet<IREntity> getUse() {
    HashSet<IREntity> ret = new HashSet<>();
    ret.add(cond);
    return ret;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    cond = cond == old ? newOne : cond;
  }
}