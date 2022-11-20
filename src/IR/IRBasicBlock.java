package IR;

import IR.inst.*;
import java.util.LinkedList;
import java.util.ArrayList;

public class IRBasicBlock {
  public String name;
  public LinkedList<IRInst> insts = new LinkedList<IRInst>();
  public IRTerminalInst terminalInst = null;
  public IRFunction parentFunction = null;
  public ArrayList<IRBasicBlock> pres = new ArrayList<IRBasicBlock>(), succs = new ArrayList<IRBasicBlock>();
  
  public void addInst(IRInst inst) {
    insts.add(inst);
  }
  public String toString() {
    return "";
  }
}