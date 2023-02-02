// only insert in IR Optimizer
package IR.inst;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import IR.*;
import IR.entity.*;

public class IRPhiInst extends IRInst {
  public IRRegister dest, src;
  public ArrayList<IREntity> values = new ArrayList<>();
  public ArrayList<IRBasicBlock> blocks = new ArrayList<>();

  public IRPhiInst(IRBasicBlock block, IRRegister src, IRRegister dest) {
    super(block);
    this.src = src;
    this.dest = dest;
  }

  public void add(IREntity value, IRBasicBlock block) {
    values.add(value == null ? dest.type.defaultValue() : value);
    blocks.add(block);
  }

  @Override
  public String toString() {
    String ret = dest + " = phi " + dest.type + " ";
    for (int i = 0; i < values.size(); ++i) {
      ret += "[ " + values.get(i) + ", %" + blocks.get(i).name + " ]";
      if (i != values.size() - 1)
        ret += ", ";
    }
    return ret;
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public LinkedHashSet<IREntity> getUse() {
    LinkedHashSet<IREntity> ret = new LinkedHashSet<>();
    for (IREntity value : values)
      ret.add(value);
    return ret;
  }

  @Override
  public IRRegister getDef() {
    return dest;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    // for (int i = 0; i < values.size(); ++i)
    //   values.set(i, values.get(i) == old ? newOne : values.get(i));
  }
}
