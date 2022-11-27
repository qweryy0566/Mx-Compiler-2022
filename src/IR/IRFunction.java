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
}