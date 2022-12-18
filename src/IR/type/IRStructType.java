package IR.type;

import java.util.ArrayList;
import java.util.HashMap;
import IR.entity.*;

public class IRStructType extends IRType {
  public ArrayList<IRType> memberType = new ArrayList<IRType>();
  public HashMap<String, Integer> memberOffset = new HashMap<>();
  public boolean hasBuild = false;

  public IRStructType(String name, int size) {
    super(name, size);
  }

  public void addMember(String name, IRType type) {
    memberType.add(type);
    memberOffset.put(name, memberType.size() - 1);
  }

  public boolean hasMember(String name) {
    return memberOffset.containsKey(name);
  }

  public IRType getMemberType(String name) {
    return !memberOffset.containsKey(name) ? null : memberType.get(memberOffset.get(name));
  }

  public void calcSize() {
    size = memberType.size() << 2; // 速度大点没事。
  }

  @Override
  public String toString() {
    return "%struct." + name;
  }

  @Override
  public IREntity defaultValue() {
    return new IRNullConst(this);
  }
}