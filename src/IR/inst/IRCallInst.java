package IR.inst;

import IR.*;
import IR.entity.*;
import IR.type.*;
import java.util.LinkedHashSet;

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
  public IRCallInst(IRBasicBlock block, IRRegister callReg, IRType returnType, String funcName, IREntity... args) {
    super(block);
    this.returnType = returnType;
    this.callReg = callReg;
    this.funcName = funcName;
    for (IREntity arg : args)
      this.args.add(arg);
  }

  @Override
  public String toString() {
    String ret = (callReg != null ? callReg + " = call " : "call ") + returnType + " @" + funcName + "(";
    for (int i = 0; i < args.size(); ++i) {
      ret += args.get(i).toStringWithType();
      if (i != args.size() - 1) ret += ", ";
    }
    ret += ")";
    return ret;
  }

  @Override
  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public LinkedHashSet<IREntity> getUse() {
    LinkedHashSet<IREntity> ret = new LinkedHashSet<>();
    for (IREntity arg : args)
      ret.add(arg);
    return ret;
  }

  @Override
  public IRRegister getDef() {
    return callReg;
  }

  @Override
  public void replaceUse(IREntity old, IREntity newOne) {
    for (int i = 0; i < args.size(); ++i)
      args.set(i, args.get(i) == old ? newOne : args.get(i));
  }
}