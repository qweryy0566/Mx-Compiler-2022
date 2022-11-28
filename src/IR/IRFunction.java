package IR;

import IR.entity.*;
import IR.type.*;
import IR.inst.*;

import java.util.LinkedList;
import java.util.ArrayList;

public class IRFunction {
  public String name;
  public IRType returnType;
  public ArrayList<IRRegister> params = new ArrayList<IRRegister>();
  // public IRBasicBlock entryBlock;
  public LinkedList<IRBasicBlock> blocks = new LinkedList<IRBasicBlock>();
  public ArrayList<IRAllocaInst> allocaInsts = new ArrayList<IRAllocaInst>();

  public IRFunction(String name, IRType returnType) {
    this.name = name;
    this.returnType = returnType;
  }

  public IRBasicBlock appendBlock(IRBasicBlock block) {
    blocks.add(block);
    return block;
  }

  public String toString() {
    String ret = "define " + returnType.toString() + " @" + name + "(";
    for (int i = 0; i < params.size(); ++i) {
      ret += params.get(i).toString();
      if (i != params.size() - 1) ret += ", ";
    }
    ret += ") {\n";
    for (IRBasicBlock block : blocks)
      ret += block.toString();
    ret += "}\n";
    return ret;
  }
}