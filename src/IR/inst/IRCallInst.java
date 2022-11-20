package IR.inst;

import IR.*;
import IR.entity.*;
import IR.type.*;

import java.util.ArrayList;

public class IRCallInst extends IRInst {
  public IRType returnType;
  public ArrayList<IREntity> args = new ArrayList<IREntity>();
  public IRRegister callReg;
  public IRFunction callfunc;

  public String toString() {
    return "";
  }
}