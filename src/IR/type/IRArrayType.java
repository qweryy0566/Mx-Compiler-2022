package IR.type;

public class IRArrayType extends IRType {
  public IRType baseType;
  public int cnt;

  public IRArrayType(IRType baseType, int cnt) {
    super("[" + String.valueOf(cnt) + " x " + baseType.name + "]", baseType.size * cnt);
    this.baseType = baseType;
    this.cnt = cnt;
  }
}