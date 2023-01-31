package backend;

import assembly.*;
import assembly.inst.*;
import assembly.operand.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Stack;

/*
 * Register allocation using the graph coloring algorithm
 * reference: Modern Compiler Implementation in C
 */

public class PremAllocator {
  public ASMModule module;

  public static final int kCnt = 27; // number of physical registers
  PhysicsReg RegSp = PhysicsReg.regMap.get("sp");

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
      if (obj == null || !(obj instanceof Edge))
        return false;
      Edge e = (Edge) obj;
      return (u == e.u && v == e.v) || (u == e.v && v == e.u);
    }

    @Override
    public int hashCode() {
      return u.hashCode() ^ v.hashCode();
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
    module.functions.forEach(func -> workOnFunc(func));
  }

  LinkedList<ASMInst> newInsts;

  void workOnFunc(ASMFunction func) {
    while (true) {
      new LivenessAnalyzer(func).work();
      initAll(func);
      build(func);
      makeWorkList();
      do {
        if (!simplifyWorkList.isEmpty())
          stepSimplify();
        else if (!workListMoves.isEmpty())
          stepCoalesce();
        else if (!freezeWorkList.isEmpty())
          stepFreeze();
        else if (!spillWorkList.isEmpty())
          stepSelectSpill();
      } while (!simplifyWorkList.isEmpty() || !workListMoves.isEmpty() ||
          !freezeWorkList.isEmpty() || !spillWorkList.isEmpty());
      assignColors();
      if (spilledNodes.isEmpty())
        break;
      rewriteProgram(func);
    }

    func.blocks.forEach(block -> {
      newInsts = new LinkedList<>();
      for (ASMInst inst : block.insts) {
        if (inst instanceof ASMLiInst && ((ASMLiInst) inst).pseudoImm instanceof StackImm)
          ((StackImm) ((ASMLiInst) inst).pseudoImm).calc();
        if (inst.rd instanceof VirtualReg)
          inst.rd = PhysicsReg.idReg.get(color.get(inst.rd));
        if (inst.rs1 instanceof VirtualReg)
          inst.rs1 = PhysicsReg.idReg.get(color.get(inst.rs1));
        if (inst.rs2 instanceof VirtualReg)
          inst.rs2 = PhysicsReg.idReg.get(color.get(inst.rs2));
        if (!(inst instanceof ASMMvInst) || inst.rd != inst.rs1)
          newInsts.add(inst);
      }
      block.insts = newInsts;
    });
  }

  void addEdge(Reg u, Reg v) {
    Edge e = new Edge(u, v);
    if (u == v || adjSet.contains(e))
      return;
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

  void initAll(ASMFunction func) {
    preColored.clear();
    initial.clear();
    simplifyWorkList.clear();
    freezeWorkList.clear();
    spillWorkList.clear();
    spilledNodes.clear();
    coalescedNodes.clear();
    coloredNodes.clear();
    selectStack.clear();

    coalescedMoves.clear();
    constrainedMoves.clear();
    frozenMoves.clear();
    workListMoves.clear();
    activeMoves.clear();

    adjSet.clear();
    adjList.clear();
    degree.clear();
    moveList.clear();
    alias.clear();
    color.clear();

    PhysicsReg.regMap.values().forEach(reg -> {
      preColored.add(reg);
      adjList.put(reg, new HashSet<>());
      degree.put(reg, Integer.MAX_VALUE);
      moveList.put(reg, new HashSet<>());
      alias.put(reg, null);
      color.put(reg, reg.id);
      reg.spillWeight = 0;
    });
    func.blocks.forEach(block -> block.insts.forEach(inst -> {
      initial.addAll(inst.getDef());
      initial.addAll(inst.getUse());
    }));
    initial.removeAll(preColored);
    initial.forEach(reg -> {
      adjList.put(reg, new HashSet<>());
      degree.put(reg, 0);
      moveList.put(reg, new HashSet<>());
      alias.put(reg, null);
      color.put(reg, null);
      reg.spillWeight = 0;
    });

    // compute spill weight
    func.blocks.forEach(block -> {
      double weight = Math.pow(10, block.loopDepth);
      block.insts.forEach(inst -> {
        inst.getDef().forEach(reg -> reg.spillWeight += weight);
        inst.getUse().forEach(reg -> reg.spillWeight += weight);
      });
    });
  }

  void build(ASMFunction func) {
    // build interference graph
    func.blocks.forEach(block -> {
      HashSet<Reg> live = new HashSet<>(block.liveOut);
      for (int i = block.insts.size() - 1; i >= 0; i--) {
        ASMInst inst = block.insts.get(i);
        if (inst instanceof ASMMvInst) {
          live.removeAll(inst.getUse());
          inst.getDef().forEach(reg -> moveList.get(reg).add((ASMMvInst) inst));
          inst.getUse().forEach(reg -> moveList.get(reg).add((ASMMvInst) inst));
          // all add to workListMoves initially
          workListMoves.add((ASMMvInst) inst);
        }
        live.addAll(inst.getDef());
        for (Reg def : inst.getDef())
          live.forEach(l -> addEdge(def, l));
        live.removeAll(inst.getDef());
        live.addAll(inst.getUse());
      }
    });
  }

  // the move instructions that are related to reg and can be coalesced currently
  HashSet<ASMMvInst> nodeMoves(Reg reg) {
    HashSet<ASMMvInst> ret = new HashSet<ASMMvInst>(activeMoves);
    ret.addAll(workListMoves);
    ret.retainAll(moveList.get(reg));
    return ret;
  }

  boolean moveRelated(Reg reg) {
    return nodeMoves(reg).size() > 0;
  }

  void makeWorkList() {
    initial.forEach(reg -> {
      if (degree.get(reg) >= kCnt)
        spillWorkList.add(reg);
      else if (moveRelated(reg))
        freezeWorkList.add(reg);
      else
        simplifyWorkList.add(reg);
    });
    initial.clear();
  }

  // the registers that are adjacent to reg CURRENTLY
  HashSet<Reg> adjacent(Reg reg) {
    HashSet<Reg> ret = new HashSet<>(adjList.get(reg));
    ret.removeAll(selectStack);
    ret.removeAll(coalescedNodes);
    return ret;
  }

  void decrementDegree(Reg reg) {
    int d = degree.get(reg);
    degree.put(reg, d - 1);
    if (d == kCnt) {
      HashSet<Reg> nodes = adjacent(reg);
      nodes.add(reg);
      enableMoves(nodes);
      spillWorkList.remove(reg);
      if (moveRelated(reg))
        freezeWorkList.add(reg);
      else
        simplifyWorkList.add(reg);
    }
  }

  void enableMoves(HashSet<Reg> nodes) {
    nodes.forEach(reg -> nodeMoves(reg).forEach(mv -> {
      if (activeMoves.contains(mv)) {
        activeMoves.remove(mv);
        workListMoves.add(mv);
      }
    }));
  }

  void stepSimplify() {
    Reg reg = simplifyWorkList.getFirst();
    simplifyWorkList.remove(reg);
    selectStack.push(reg);
    adjacent(reg).forEach(adj -> decrementDegree(adj));
  }

  Reg getAlias(Reg reg) {
    // TODO : compress path
    if (coalescedNodes.contains(reg)) {
      Reg regAlias = getAlias(alias.get(reg));
      alias.put(reg, regAlias);
      return regAlias;
    } else
      return reg;
  }

  // add reg to simplifyWorkList if it is not preColored and not move related
  void addWorkList(Reg reg) {
    if (!preColored.contains(reg) && !moveRelated(reg) && degree.get(reg) < kCnt) {
      freezeWorkList.remove(reg);
      simplifyWorkList.add(reg); // low degree and no move related
    }
  }

  boolean George(Reg t, Reg r) {
    return degree.get(t) < kCnt || preColored.contains(t) || adjSet.contains(new Edge(t, r));
  }

  boolean Briggs(HashSet<Reg> uv) {
    int k = 0;
    for (Reg reg : uv)
      if (degree.get(reg) >= kCnt)
        k++;
    return k < kCnt;
  }

  void combine(Reg u, Reg v) {
    if (freezeWorkList.contains(v))
      freezeWorkList.remove(v);
    else
      spillWorkList.remove(v);
    coalescedNodes.add(v);
    alias.put(v, u); // fa[v] = u
    moveList.get(u).addAll(moveList.get(v));
    enableMoves(new HashSet<Reg>() {{ add(v); }});
    adjacent(v).forEach(t -> {
      addEdge(t, u);
      decrementDegree(t);
    });
    if (degree.get(u) >= kCnt && freezeWorkList.contains(u)) {
      freezeWorkList.remove(u);
      spillWorkList.add(u);
    }
  }

  void stepCoalesce() {
    ASMMvInst mv = workListMoves.iterator().next();
    Reg x = getAlias(mv.rd), y = getAlias(mv.rs1);
    Edge e = preColored.contains(y) ? new Edge(y, x) : new Edge(x, y);
    workListMoves.remove(mv);
    if (e.u == e.v) {
      // delete the move instruction directly
      coalescedMoves.add(mv);
      addWorkList(e.u);
    } else if (preColored.contains(e.v) || adjSet.contains(e)) {
      // physical registers must conflict with each other
      // or two virtual registers are already adjacent
      constrainedMoves.add(mv);
      addWorkList(e.u);
      addWorkList(e.v);
    } else {
      // e.v can't be preColored
      boolean flag = true;
      for (Reg reg : adjacent(e.v))
        flag &= George(reg, e.u);
      HashSet<Reg> uv = new HashSet<>(adjacent(e.u));
      uv.addAll(adjacent(e.v));
      if (preColored.contains(e.u) && flag || !preColored.contains(e.u) && Briggs(uv)) {
        coalescedMoves.add(mv);
        combine(e.u, e.v); // combine e.v to e.u
        addWorkList(e.u);
      } else {
        // can't combine temporarily
        activeMoves.add(mv);
      }
    }
  }

  void freezeMoves(Reg reg) {
    nodeMoves(reg).forEach(mv -> {
      Reg x = mv.rd, y = mv.rs1, v;
      v = getAlias(y) == getAlias(reg) ? getAlias(x) : getAlias(y);
      activeMoves.remove(mv);
      frozenMoves.add(mv);
      if (nodeMoves(v).size() == 0 && degree.get(v) < kCnt) {
        // then v is not move related anymore
        freezeWorkList.remove(v);
        simplifyWorkList.add(v);
      }
    });
  }

  void stepFreeze() {
    Reg reg = freezeWorkList.getFirst();
    freezeWorkList.remove(reg);
    simplifyWorkList.add(reg);
    freezeMoves(reg);
  }

  void stepSelectSpill() {
    Reg m = null;
    for (Reg reg : spillWorkList)
      if (m == null || reg.spillWeight / degree.get(reg) < m.spillWeight / degree.get(m))
        m = reg;
    spillWorkList.remove(m);
    simplifyWorkList.add(m); // 看看是否是实际溢出
    freezeMoves(m);
  }

  void assignColors() {
    while (!selectStack.isEmpty()) {
      Reg reg = selectStack.pop();
      HashSet<Integer> okColors = new HashSet<>();
      for (int i = 5; i < 32; i++)
        okColors.add(i);
      adjacent(reg).forEach(adj -> {
        Reg adjAlias = getAlias(adj);
        if (coloredNodes.contains(adjAlias) || preColored.contains(adjAlias))
          okColors.remove(color.get(adjAlias));
      });
      if (okColors.isEmpty())
        spilledNodes.add(reg);
      else {
        coloredNodes.add(reg);
        color.put(reg, okColors.iterator().next());
      }
    }
    coalescedNodes.forEach(reg -> color.put(reg, color.get(getAlias(reg))));
  }

  void rewriteProgram(ASMFunction func) {
    // allocate stack space for spilled variables
    spilledNodes.forEach(reg -> {
      ((VirtualReg) reg).stackOffset = func.paramUsed + func.allocaUsed + func.spillUsed;
      func.spillUsed += 4;
    });

    // create a new VirtualReg for each def and use in spilledNodes
    func.blocks.forEach(block -> {
      newInsts = new LinkedList<>();
      for (ASMInst inst : block.insts) {
        VirtualReg same = null;
        if (inst.rs1 != null && inst.rs1.stackOffset != null) {
          VirtualReg newReg = new VirtualReg(4);
          allocateUse(newReg, (VirtualReg) inst.rs1);
          if (inst.rs1 == inst.rs2)
            inst.rs2 = newReg;
          if (inst.rs1 == inst.rd)
            same = newReg;
          inst.rs1 = newReg;
        }
        if (inst.rs2 != null && inst.rs2.stackOffset != null) {
          VirtualReg newReg = new VirtualReg(4);
          allocateUse(newReg, (VirtualReg) inst.rs2);
          if (inst.rs2 == inst.rd)
            same = newReg;
          inst.rs2 = newReg;
        }
        newInsts.add(inst);
        if (inst.rd != null && inst.rd.stackOffset != null) {
          VirtualReg newReg = same == null ? new VirtualReg(4) : same;
          allocateDef(newReg, (VirtualReg) inst.rd);
          inst.rd = newReg;
        }
      }
      block.insts = newInsts;
    });
  }

  void allocateUse(VirtualReg newReg, VirtualReg reg) {
    if (reg.stackOffset < 1 << 11)
      newInsts.add(new ASMLoadInst(reg.size, newReg, RegSp, new Imm(reg.stackOffset)));
    else {
      newInsts.add(new ASMLiInst(newReg, new VirtualImm(reg.stackOffset)));
      newInsts.add(new ASMBinaryInst("add", newReg, newReg, RegSp));
      newInsts.add(new ASMLoadInst(reg.size, newReg, newReg));
    }
  }

  void allocateDef(VirtualReg newReg, VirtualReg reg) {
    if (reg.stackOffset < 1 << 11)
      newInsts.add(new ASMStoreInst(reg.size, RegSp, newReg, new Imm(reg.stackOffset)));
    else {
      VirtualReg addr = new VirtualReg(4);
      newInsts.add(new ASMLiInst(addr, new VirtualImm(reg.stackOffset)));
      newInsts.add(new ASMBinaryInst("add", addr, addr, RegSp));
      newInsts.add(new ASMStoreInst(reg.size, addr, newReg));
    }
  }
}
