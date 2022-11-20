package IR.entity;

public class IRStringConst extends IRConst {
  public String val;
  
  public IRStringConst(String val) {
    super(null); // TODO: type
    this.val = val;
  }
}