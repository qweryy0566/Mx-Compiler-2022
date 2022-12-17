package assembly.operand;

public class RelocationFunc extends Imm {
  public enum Type { hi, lo };
  public Type type;
  public String symbol;

  public RelocationFunc(Type type, String symbol) {
    this.type = type;
    this.symbol = symbol;
  }
}