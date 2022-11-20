package IR;

import IR.entity.*;
import IR.type.*;
import java.util.ArrayList;

public class IRProgram {
  public ArrayList<IRFunction> funcList = new ArrayList<IRFunction>();
  public ArrayList<IRGlobalVar> globalVarList = new ArrayList<IRGlobalVar>();
  public ArrayList<IRStringConst> stringConstList = new ArrayList<IRStringConst>();
  public ArrayList<IRStructType> structTypeList = new ArrayList<IRStructType>();
}