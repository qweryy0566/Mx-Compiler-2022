// only insert in IR Optimizer
package IR.inst;

import java.util.ArrayList;

import IR.*;
import IR.entity.*;

public class IRPhiInst {
  public IRRegister dest;
  public ArrayList<IREntity> values = new ArrayList<>();
  public ArrayList<IRBasicBlock> blocks = new ArrayList<>();


  public IRPhiInst(IRRegister dest) {
    this.dest = dest;
  }

  public void add(IREntity value, IRBasicBlock block) {
    values.add(value);
    blocks.add(block);
  }

  @Override
  public String toString() {
    String ret = dest + " = phi " + dest.type + " ";
    for (int i = 0; i < values.size(); ++i) {
      ret += "[ " + values.get(i) + ", " + blocks.get(i) + " ]";
      if (i != values.size() - 1)
        ret += ", ";
    }
    return ret;
  }
}
