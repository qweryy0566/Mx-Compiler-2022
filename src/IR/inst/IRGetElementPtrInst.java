package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

import java.util.LinkedHashSet;
import java.util.ArrayList;

public class IRGetElementPtrInst extends IRInst {
  public IRRegister res;
  public IREntity ptr;
  public IRType pToType;
  public ArrayList<IREntity> indexList = new ArrayList<IREntity>();

  public IRGetElementPtrInst(IRBasicBlock block, IREntity ptr, IRRegister res, IREntity... indexList) {
    super(block);
    this.ptr = ptr;
    this.pToType = ((IRPtrType) ptr.type).pointToType();
    this.res = res;
    for (IREntity index : indexList)
      this.indexList.add(index);
  }

  @Override
  public String toString() {
    String ret = res + " = getelementptr " + pToType + ", " + ptr.toStringWithType();
    for (IREntity index : indexList)
      ret += ", " + index.toStringWithType();
    return ret;
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public LinkedHashSet<IREntity> getUse() {
    LinkedHashSet<IREntity> ret = new LinkedHashSet<>();
    ret.add(ptr);
    for (IREntity index : indexList)
      ret.add(index);
    return ret;
  }

  @Override
  public IRRegister getDef() {
    return res;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    ptr = ptr == old ? newOne : ptr;
    for (int i = 0; i < indexList.size(); ++i)
      if (indexList.get(i) == old)
        indexList.set(i, newOne);
  }
}