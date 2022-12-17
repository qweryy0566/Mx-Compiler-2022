package assembly;

import assembly.operand.*;
import java.util.ArrayList;

public class ASMFunction {
  String name;
  ArrayList<ASMBlock> blocks = new ArrayList<ASMBlock>();
  ArrayList<Reg> params = new ArrayList<Reg>();

  public ASMFunction(String name) {
    this.name = name;
  }

  public void addBlock(ASMBlock block) {
    blocks.add(block);
  }
}