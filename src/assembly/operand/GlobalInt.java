package assembly.operand;

public class GlobalInt extends Global {
  public int word;
  public GlobalInt(String name, int word) {
    super(name);
    this.word = word;
  }
}