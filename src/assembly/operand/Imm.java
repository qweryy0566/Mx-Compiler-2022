package assembly.operand;

public class Imm extends Operand {
  int value;

  public Imm(int value) {
    this.value = value;
  }

  public String toString() {
    return Integer.toString(value);
  }
}