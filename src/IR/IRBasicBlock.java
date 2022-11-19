package IR;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.inst.*;
import java.util.LinkedList;

public class IRBasicBlock {
  public String name;
  public LinkedList<IRInst> insts = new LinkedList<IRInst>();
  public IRFunction function;
  public IRBasicBlock lstBlock;
  public IRBasicBlock nxtBlock;
  
  public void addInst(IRInst inst) {
    insts.add(inst);
  }
  public String toString() {
    return "";
  }
}