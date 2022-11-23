package IR.type;

public class IRIntType extends IRType {
  public int bitWidth;

  public IRIntType(int bitWidth) {
    super("i" + String.valueOf(bitWidth), bitWidth);
    this.bitWidth = bitWidth;
  }
}