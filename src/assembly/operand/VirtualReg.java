package assembly.operand;

public class VirtualReg extends Reg {
  public int id = -1, param_idx = -1; // offset in stack frame
  public int size;
  public static int cnt = 0;
  public VirtualReg(int size) {
    this.size = size;
    id = cnt++;
  }
  public VirtualReg(int size, int param_idx) {
    this.size = size;
    this.param_idx = param_idx - 8;
  }

  @Override
  public String toString() {
    return "%" + Integer.toString(id);
  }
}