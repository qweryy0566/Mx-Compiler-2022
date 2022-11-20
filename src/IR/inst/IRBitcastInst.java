package IR.inst;

import IR.entity.*;
import IR.type.*;

public class IRBitcastInst extends IRInst {
  public IREntity val;
  public IRType type;
  public IRRegister dest;
  public String toString() {
    return "";
  }
}