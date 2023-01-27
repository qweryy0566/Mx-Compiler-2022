package backend;

import assembly.*;
import assembly.inst.*;
import assembly.operand.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Stack;


public class PremAllocator {
  public ASMModule module;

  public LinkedHashSet<Reg> preColored = new LinkedHashSet<Reg>(PhysicsReg.regMap.values());
  public LinkedHashSet<Reg> initial = new LinkedHashSet<Reg>();
  public LinkedList<Reg> simplifyWorkList = new LinkedList<Reg>();
  public LinkedList<Reg> freezeWorkList = new LinkedList<Reg>();
  public LinkedList<Reg> spillWorkList = new LinkedList<Reg>();
  public LinkedHashSet<Reg> spilledNodes = new LinkedHashSet<Reg>();
  public LinkedHashSet<Reg> coalescedNodes = new LinkedHashSet<Reg>();
  public LinkedHashSet<Reg> coloredNodes = new LinkedHashSet<Reg>();
  public Stack<Reg> selectStack = new Stack<Reg>();
  
  public LinkedHashSet<ASMMvInst> coalescedMoves = new LinkedHashSet<>();
  public LinkedHashSet<ASMMvInst> constrainedMoves = new LinkedHashSet<>();
  public LinkedHashSet<ASMMvInst> frozenMoves = new LinkedHashSet<>();
  public LinkedHashSet<ASMMvInst> workListMoves = new LinkedHashSet<>();
  public LinkedHashSet<ASMMvInst> activeMoves = new LinkedHashSet<>();

  // interference graph

  public static class Edge {
    public Reg u, v;
    public Edge(Reg u, Reg v) {
      this.u = u;
      this.v = v;
    }
    @Override
    public boolean equals(Object obj) {
      if (obj == null || !(obj instanceof Edge)) return false;
      Edge e = (Edge) obj;
      return (u == e.u && v == e.v) || (u == e.v && v == e.u);
    }
    @Override
    public int hashCode() {
      return u.hashCode() + v.hashCode();
    }
  }

  public HashSet<Edge> adjSet = new HashSet<>();
  public HashMap<Reg, HashSet<Reg>> adjList = new HashMap<>();
  public HashMap<Reg, Integer> degree = new HashMap<>();
  public HashMap<Reg, HashSet<ASMMvInst>> moveList = new HashMap<>();
  public HashMap<Reg, Reg> alias = new HashMap<>();
  public HashMap<Reg, Integer> color = new HashMap<>();

  public PremAllocator(ASMModule module) {
    this.module = module;
  }

  public void work() {
    new LivenessAnalyzer(module).work();
    build();
    makeWorkList();
  }

  void addEdge(Reg u, Reg v) {
    if (u == v) return;
    Edge e = new Edge(u, v);
    if (adjSet.contains(e)) return;
    adjSet.add(e);
    if (!preColored.contains(u)) {
      adjList.get(u).add(v);
      degree.put(u, degree.get(u) + 1);
    }
    if (!preColored.contains(v)) {
      adjList.get(v).add(u);
      degree.put(v, degree.get(v) + 1);
    }
  }
  void build() {
    // init
    // TODO
    // build interference graph
    for (ASMFunction func : module.functions)
      for (ASMBlock block : func.blocks) {
        HashSet<Reg> live = new HashSet<>(block.liveOut);
        for (int i = block.insts.size() - 1; i >= 0; i--) {
          ASMInst inst = block.insts.get(i);
          if (inst instanceof ASMMvInst) {
            live.removeAll(inst.getUse());
            inst.getDef().forEach(reg -> moveList.get(reg).add((ASMMvInst) inst));
            inst.getUse().forEach(reg -> moveList.get(reg).add((ASMMvInst) inst));
            workListMoves.add((ASMMvInst) inst); 
          }
          live.addAll(inst.getDef());
          for (Reg def : inst.getDef())
            live.forEach(l -> addEdge(def, l));
          live.removeAll(inst.getDef());
          live.addAll(inst.getUse());
        }
      }
  }

  void makeWorkList() {

  }
}
