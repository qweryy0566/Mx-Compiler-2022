package assembly.operand;

public class GlobalString extends Global {
  public String str;
  public GlobalString(String name, String str) {
    super(name);
    this.str = str;
  }
  public String toString() {
    return name;
  }
}