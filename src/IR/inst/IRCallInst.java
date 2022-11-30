package IR.inst;

import IR.*;
import IR.entity.*;
import IR.type.*;

import java.util.ArrayList;

public class IRCallInst extends IRInst {
  public IRType returnType;
  public ArrayList<IREntity> args = new ArrayList<IREntity>();
  public IRRegister callReg;
  public String funcName;

  public IRCallInst(IRBasicBlock block, IRType returnType, String funcName) {
    super(block);
    this.returnType = returnType;
    this.funcName = funcName;
  }

  public String toString() {
    String ret = callReg + " = call " + returnType + " @" + funcName + "(";
    for (int i = 0; i < args.size(); ++i) {
      ret += args.get(i).toStringWithType();
      if (i != args.size() - 1) ret += ", ";
    }
    ret += ")";
    return ret;
  }
}