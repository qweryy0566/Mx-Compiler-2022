package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

import java.util.ArrayList;

public class IRGetElementPtrInst extends IRInst {
  public IRRegister res;
  public IREntity ptr;
  public IRType ptrType;
  public ArrayList<IREntity> indexList = new ArrayList<IREntity>();

  public IRGetElementPtrInst(IRBasicBlock block, IREntity ptr, IRRegister res, IREntity... indexList) {
    super(block);
    this.ptr = ptr;
    this.ptrType = ptr.type;
    this.res = res;
    for (IREntity index : indexList)
      this.indexList.add(index);
  }
  public String toString() {
    return "";
  }
}