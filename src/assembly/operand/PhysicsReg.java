package assembly.operand;

import java.util.HashMap;

public class PhysicsReg extends Reg {
  public String name;
  public static HashMap<String, PhysicsReg> regMap = new HashMap<>() {
    {
      put("zero", new PhysicsReg("zero"));
      put("ra", new PhysicsReg("ra"));
      put("sp", new PhysicsReg("sp"));
      put("gp", new PhysicsReg("gp"));
      put("tp", new PhysicsReg("tp"));
      put("t0", new PhysicsReg("t0"));
      put("t1", new PhysicsReg("t1"));
      put("t2", new PhysicsReg("t2"));
      put("s0", new PhysicsReg("s0"));
      put("s1", new PhysicsReg("s1"));
      put("a0", new PhysicsReg("a0"));
      put("a1", new PhysicsReg("a1"));
      put("a2", new PhysicsReg("a2"));
      put("a3", new PhysicsReg("a3"));
      put("a4", new PhysicsReg("a4"));
      put("a5", new PhysicsReg("a5"));
      put("a6", new PhysicsReg("a6"));
      put("a7", new PhysicsReg("a7"));
      put("s2", new PhysicsReg("s2"));
      put("s3", new PhysicsReg("s3"));
      put("s4", new PhysicsReg("s4"));
      put("s5", new PhysicsReg("s5"));
      put("s6", new PhysicsReg("s6"));
      put("s7", new PhysicsReg("s7"));
      put("s8", new PhysicsReg("s8"));
      put("s9", new PhysicsReg("s9"));
      put("s10", new PhysicsReg("s10"));
      put("s11", new PhysicsReg("s11"));
      put("t3", new PhysicsReg("t3"));
      put("t4", new PhysicsReg("t4"));
      put("t5", new PhysicsReg("t5"));
      put("t6", new PhysicsReg("t6"));
    }
  };

  static {
    
  }

  public static PhysicsReg get(String name) {
    return regMap.get(name);
  }

  public PhysicsReg(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}