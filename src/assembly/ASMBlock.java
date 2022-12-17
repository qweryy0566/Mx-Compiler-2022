package assembly;

import assembly.inst.*;
import java.util.LinkedList;

public class ASMBlock {
  String name; // do not print when null
  LinkedList<ASMInst> insts = new LinkedList<ASMInst>();

  public ASMBlock(String name) {
    this.name = name;
  }

  public void addInst(ASMInst inst) {
    insts.add(inst);
  }
}