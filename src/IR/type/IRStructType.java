package IR.type;

import java.util.ArrayList;
import java.util.HashMap;

public class IRStructType extends IRType {
  public ArrayList<IRType> memberType = new ArrayList<IRType>();
  public HashMap<String, Integer> memberOffset = new HashMap<>();

  public IRStructType(String name) {
    super(name, 0); // TODO: size
    
  }
}