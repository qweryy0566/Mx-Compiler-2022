package IR;

import IR.entity.*;
import IR.type.*;
import java.util.ArrayList;
import java.util.HashMap;

public class IRProgram {
  public ArrayList<IRFunction> funcList = new ArrayList<IRFunction>();
  public ArrayList<IRGlobalVar> globalVarList = new ArrayList<IRGlobalVar>();
  public ArrayList<IRStructType> structTypeList = new ArrayList<IRStructType>();

  public HashMap<String, IRStringConst> stringConst = new HashMap<>();

  public IRStringConst addStringConst(String str) {
    if (!stringConst.containsKey(str))
      stringConst.put(str, new IRStringConst(str));
    return stringConst.get(str);
  }
}