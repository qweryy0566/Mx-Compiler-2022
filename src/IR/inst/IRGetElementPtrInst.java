package IR.inst;

import IR.entity.*;
import IR.type.*;

import java.util.ArrayList;

public class IRGetElementPtrInst extends IRInst {
  public IRRegister res;
  public IRType ptrType;
  public ArrayList<IREntity> indexList = new ArrayList<IREntity>();

  public String toString() {
    return "";
  }
}