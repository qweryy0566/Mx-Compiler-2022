package assembly.operand;

import java.util.HashMap;

public class PhysicsReg extends Reg {
  public String name;
  public static HashMap<String, PhysicsReg> regMap = new HashMap<>() {
    {
      put("zero", new PhysicsReg("zero"));
      put("ra", new PhysicsReg("ra"));
      put("sp", new PhysicsReg("sp"));
      put("t0", new PhysicsReg("t0"));
      put("t1", new PhysicsReg("t1"));
      put("t2", new PhysicsReg("t2"));
      put("a0", new PhysicsReg("a0"));
      put("a1", new PhysicsReg("a1"));
      put("a2", new PhysicsReg("a2"));
      put("a3", new PhysicsReg("a3"));
      put("a4", new PhysicsReg("a4"));
      put("a5", new PhysicsReg("a5"));
      put("a6", new PhysicsReg("a6"));
      put("a7", new PhysicsReg("a7"));
    }
  };

  public PhysicsReg(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}