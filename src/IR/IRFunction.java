package IR;

import IR.entity.*;
import IR.type.*;
import IR.inst.*;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IRFunction {
  public String name;
  public IRType returnType;
  public ArrayList<IRRegister> params = new ArrayList<IRRegister>();
  public LinkedList<IRBasicBlock> blocks = new LinkedList<IRBasicBlock>();
  public ArrayList<IRAllocaInst> allocaInsts = new ArrayList<IRAllocaInst>();

  public IRBasicBlock entryBlock, exitBlock;
  public IRRegister retAddr;

  public HashMap<IRRegister, HashSet<IRInst>> useList = new HashMap<>();

  public IRFunction(String name, IRType returnType) {
    this.name = name;
    this.returnType = returnType;
  }

  public IRBasicBlock appendBlock(IRBasicBlock block) {
    blocks.add(block);
    return block;
  }

  public void finish() {
    entryBlock = blocks.getFirst();
    for (int i = allocaInsts.size() - 1; i >= 0; --i)
      entryBlock.insts.addFirst(allocaInsts.get(i));
    blocks.add(exitBlock);
  }

  public String toString() {
    String ret = "define " + returnType.toString() + " @" + name + "(";
    IRRegister.regCnt = 0;
    for (int i = 0; i < params.size(); ++i) {
      ret += params.get(i).toStringWithType();
      if (i != params.size() - 1) ret += ", ";
    }
    ret += ") {\n";
    for (IRBasicBlock block : blocks)
      ret += block.toString();
    ret += "}\n";
    return ret;
  }

  public void accept(IRVisitor visitor) {
    visitor.visit(this);
  }
}