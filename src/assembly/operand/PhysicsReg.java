package assembly.operand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class PhysicsReg extends Reg {
  public String name;
  public int id;
  public static HashMap<String, PhysicsReg> regMap = new HashMap<>() {
    {
      put("zero", new PhysicsReg("zero", 0));
      put("ra", new PhysicsReg("ra", 1));
      put("sp", new PhysicsReg("sp", 2));
      put("gp", new PhysicsReg("gp", 3));
      put("tp", new PhysicsReg("tp", 4));
      put("t0", new PhysicsReg("t0", 5));
      put("t1", new PhysicsReg("t1", 6));
      put("t2", new PhysicsReg("t2", 7));
      put("s0", new PhysicsReg("s0", 8));
      put("s1", new PhysicsReg("s1", 9));
      put("a0", new PhysicsReg("a0", 10));
      put("a1", new PhysicsReg("a1", 11));
      put("a2", new PhysicsReg("a2", 12));
      put("a3", new PhysicsReg("a3", 13));
      put("a4", new PhysicsReg("a4", 14));
      put("a5", new PhysicsReg("a5", 15));
      put("a6", new PhysicsReg("a6", 16));
      put("a7", new PhysicsReg("a7", 17));
      put("s2", new PhysicsReg("s2", 18));
      put("s3", new PhysicsReg("s3", 19));
      put("s4", new PhysicsReg("s4", 20));
      put("s5", new PhysicsReg("s5", 21));
      put("s6", new PhysicsReg("s6", 22));
      put("s7", new PhysicsReg("s7", 23));
      put("s8", new PhysicsReg("s8", 24));
      put("s9", new PhysicsReg("s9", 25));
      put("s10", new PhysicsReg("s10", 26));
      put("s11", new PhysicsReg("s11", 27));
      put("t3", new PhysicsReg("t3", 28));
      put("t4", new PhysicsReg("t4", 29));
      put("t5", new PhysicsReg("t5", 30));
      put("t6", new PhysicsReg("t6", 31));
    }
  };

  public static HashSet<Reg> callerSave = new HashSet<>() {
    {
      add(regMap.get("ra"));
      for (int i = 0; i < 7; i++) add(regMap.get("t" + i));
      for (int i = 0; i < 8; i++) add(regMap.get("a" + i));
    }
  };

  public static HashSet<Reg> calleeSave = new HashSet<>() {
    {
      for (int i = 0; i < 12; i++) add(regMap.get("s" + i));
    }
  };

  public static ArrayList<Reg> idReg = new ArrayList<>() {
    {
      add(regMap.get("zero"));
      add(regMap.get("ra"));
      add(regMap.get("sp"));
      add(regMap.get("gp"));
      add(regMap.get("tp"));  
      for (int i = 0; i < 3; i++) add(regMap.get("t" + i));
      for (int i = 0; i < 2; i++) add(regMap.get("s" + i));
      for (int i = 0; i < 8; i++) add(regMap.get("a" + i));
      for (int i = 2; i < 12; i++) add(regMap.get("s" + i));
      for (int i = 3; i < 7; i++) add(regMap.get("t" + i));
    }
  };

  public static LinkedHashSet<Integer> okInit = new LinkedHashSet<>() {
    {
      for (int i = 5; i < 32; ++i) add(i);
    }
  };

  public static PhysicsReg get(String name) {
    return regMap.get(name);
  }

  public PhysicsReg(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public String toString() {
    return name;
  }
}