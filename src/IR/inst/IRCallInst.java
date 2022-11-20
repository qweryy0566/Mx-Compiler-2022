package IR.inst;

import IR.*;
import IR.entity.*;
import IR.type.*;

import java.util.ArrayList;

public class IRCallInst extends IRInst {
  public IRType returnType;
  public ArrayList<IREntity> args = new ArrayList<IREntity>();
  public IRRegister callReg;
  public IRFunction callfunc;

  public IRCallInst(IRBasicBlock block, IRType returnType, IRFunction callfunc, IRRegister callReg) {
    super(block);
    this.returnType = returnType;
    this.callfunc = callfunc;
    this.callReg = callReg;
  }

  public String toString() {
    return "";
  }
}