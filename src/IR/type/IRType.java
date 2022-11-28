package IR.type;

public abstract class IRType {
  public String name;
  public int size;  // cnt of bit

  public IRType(String name, int size) {
    this.name = name;
    this.size = size;
  }

  public abstract String toString();
}

