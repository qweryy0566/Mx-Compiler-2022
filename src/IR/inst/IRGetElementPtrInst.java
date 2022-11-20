package IR.inst;

import IR.entity.*;
import IR.type.*;
import IR.*;

import java.util.ArrayList;

public class IRGetElementPtrInst extends IRInst {
  public IRRegister res;
  public IRType ptrType;
  public ArrayList<IREntity> indexList = new ArrayList<IREntity>();

  public IRGetElementPtrInst(IRBasicBlock block, IRType ptrType, IRRegister res) {
    super(block);
    this.ptrType = ptrType;
    this.res = res;
  }
  public String toString() {
    return "";
  }
}