package IR.type;

import java.util.ArrayList;
import java.util.HashMap;

public class IRStructType extends IRType {
  public ArrayList<IRType> memberType = new ArrayList<IRType>();
  public HashMap<String, Integer> memberOffset = new HashMap<>();

  public IRStructType(String name) {
    super("class." + name, 0); // TODO: size

  }

  public void addMember(String name, IRType type) {
    memberType.add(type);
    memberOffset.put(name, memberType.size() - 1);
  }

  public IRType getMemberType(String name) {
    return memberType.get(memberOffset.get(name));
  }
}