package assembly;

import assembly.operand.*;
import java.util.ArrayList;

public class ASMModule {
  public ArrayList<GlobalValue> globalValues = new ArrayList<GlobalValue>();
  public ArrayList<GlobalString> globalStrings = new ArrayList<GlobalString>();
  public ArrayList<ASMFunction> functions = new ArrayList<ASMFunction>();
  
}