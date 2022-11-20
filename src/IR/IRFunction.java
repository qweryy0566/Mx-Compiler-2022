package IR;

import utils.*;
import ast.*;
import ast.stmt.*;
import ast.expr.*;
import IR.entity.IRRegister;
import IR.inst.*;
import IR.type.IRType;

import java.util.LinkedList;
import java.util.ArrayList;

public class IRFunction {
  public String name;
  public IRType returntype;
  public ArrayList<IRRegister> parameters = new ArrayList<IRRegister>();
  public IRBasicBlock entryBlock;
  public LinkedList<IRBasicBlock> blocks = new LinkedList<IRBasicBlock>();
  public ArrayList<IRAllocaInst> allocaInsts = new ArrayList<IRAllocaInst>();

}