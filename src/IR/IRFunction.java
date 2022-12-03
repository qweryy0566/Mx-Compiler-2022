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
  public LinkedList<IRBasicBlock> blocks = new LinkedList<IRBasicBlock>();
  public ArrayList<IRAllocaInst> allocaInsts = new ArrayList<IRAllocaInst>();

  public IRBasicBlock exitBlock;
  public IRRegister retAddr;

  public IRFunction(String name, IRType returnType) {
    this.name = name;
    this.returnType = returnType;
  }

  public IRBasicBlock appendBlock(IRBasicBlock block) {
    blocks.add(block);
    return block;
  }

  public void finish() {
    IRBasicBlock entryBlock = blocks.getFirst();
    for (IRAllocaInst inst : allocaInsts)
      entryBlock.insts.addFirst(inst);
    blocks.add(exitBlock);
    // LinkedList<IRBasicBlock> old = blocks;
    // blocks = new LinkedList<IRBasicBlock>();
    // for (IRBasicBlock block : old)
    //   if (blocks.size() > 0 && blocks.getLast().terminalInst instanceof IRJumpInst
    //       && ((IRJumpInst) blocks.getLast().terminalInst).toBlock == block)
    //     blocks.getLast().mergeBlock(block);
    //   else
    //     blocks.add(block);
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
}