package IR.type;

import java.util.ArrayList;

public class IRStructType extends IRType {
  public ArrayList<IRType> membersType = new ArrayList<IRType>();

  public IRStructType(String name) {
    super(name, 0); // TODO: size
  }
}