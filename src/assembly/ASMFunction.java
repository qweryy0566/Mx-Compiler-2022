package assembly;

import assembly.operand.*;
import java.util.ArrayList;

public class ASMFunction {
  public String name;
  public ArrayList<ASMBlock> blocks = new ArrayList<ASMBlock>();
  public ArrayList<Reg> params = new ArrayList<Reg>();
  public ASMBlock entryBlock, exitBlock;

  // for stack frame
  public int virtualRegCnt = 0, spillUsed = 0;
  public int allocaUsed = 1 + 12 << 2; // 1 for return address and 12 for callee-save registers
  public int paramUsed = 0;

  public ASMFunction(String name) {
    this.name = name;
  }

  public void addBlock(ASMBlock block) {
    blocks.add(block);
  }

  public String toString() {
    String ret = "  .text\n" + "  .globl " + name + "\n";
    ret += name + ":\n";
    for (ASMBlock block : blocks)
      ret += block;
    return ret;
  }  
}