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
  public HashSet<Reg> spillTemp = new HashSet<>();
  // 虎书：注意：要避免选择那种由读取前面已溢出的寄存器产生的、活跃范围很小的寄存器

  public PremAllocator(ASMModule module) {
    this.module = module;
  }

  public void work() {
    for (var func : module.functions)
      workOnFunc(func);
  }

  LinkedList<ASMInst> newInsts;
  ASMFunction curFunc;

  void workOnFunc(ASMFunction func) {
    curFunc = func;
    spillTemp.clear();
    while (true) {
      new LivenessAnalyzer(func).work();
      initAll(func);
      build(func);
      makeWorkList();
      // int cnt = 0;
      do {
        // cnt++;
        // System.out.println("simplifyWorkListSize = " + simplifyWorkList.size());
        // System.out.println("workListMovesSize = " + workListMoves.size());
        // System.out.println("freezeWorkListSize = " + freezeWorkList.size());
        // System.out.println("spillWorkListSize = " + spillWorkList.size());
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
      // System.out.println("cnt = " + cnt);
      assignColors();
      if (spilledNodes.isEmpty())
        break;
      rewriteProgram(func);
    }

    for (var block : func.blocks) {
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
    }
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

    for (var reg : PhysicsReg.regMap.values()) {
      preColored.add(reg);
      adjList.put(reg, new HashSet<>());
      degree.put(reg, Integer.MAX_VALUE);
      moveList.put(reg, new HashSet<>());
      alias.put(reg, null);
      color.put(reg, reg.id);
      reg.spillWeight = 0;
    }
    for (var block : func.blocks)
      for (var inst : block.insts) {
        initial.addAll(inst.getDef());
        initial.addAll(inst.getUse());
      }
    initial.removeAll(preColored);
    for (var reg : initial) {
      adjList.put(reg, new HashSet<>());
      degree.put(reg, 0);
      moveList.put(reg, new HashSet<>());
      alias.put(reg, null);
      color.put(reg, null);
      reg.spillWeight = 0;
    }

    // compute spill weight
    for (var block : func.blocks) {
      double weight = Math.pow(10, block.loopDepth);
      for (var inst : block.insts) {
        for (var reg : inst.getDef())
          reg.spillWeight += weight;
        for (var reg : inst.getUse())
          reg.spillWeight += weight;
      }
    }
  }

  void build(ASMFunction func) {
    // build interference graph
    for (var block : func.blocks) {
      LinkedHashSet<Reg> live = new LinkedHashSet<>(block.liveOut);
      for (int i = block.insts.size() - 1; i >= 0; i--) {
        ASMInst inst = block.insts.get(i);
        if (inst instanceof ASMMvInst) {
          live.removeAll(inst.getUse());
          for (var reg : inst.getDef())
            moveList.get(reg).add((ASMMvInst) inst);
          for (var reg : inst.getUse())
            moveList.get(reg).add((ASMMvInst) inst);
          // all add to workListMoves initially
          workListMoves.add((ASMMvInst) inst);
        }
        live.addAll(inst.getDef());
        // debug
        for (Reg def : inst.getDef())
          for (var l : live)
            addEdge(def, l);
        live.removeAll(inst.getDef());
        live.addAll(inst.getUse());
      }
    }
  }

  // the move instructions that are related to reg and can be coalesced currently
  LinkedHashSet<ASMMvInst> nodeMoves(Reg reg) {
    LinkedHashSet<ASMMvInst> ret = new LinkedHashSet<ASMMvInst>(activeMoves);
    ret.addAll(workListMoves);
    ret.retainAll(moveList.get(reg));
    return ret;
  }

  boolean moveRelated(Reg reg) {
    return nodeMoves(reg).size() > 0;
  }

  void makeWorkList() {
    for (var reg : initial) {
      if (degree.get(reg) >= kCnt)
        spillWorkList.add(reg);
      else if (moveRelated(reg))
        freezeWorkList.add(reg);
      else
        simplifyWorkList.add(reg);
    }
    initial.clear();
  }

  // the registers that are adjacent to reg CURRENTLY
  LinkedHashSet<Reg> adjacent(Reg reg) {
    LinkedHashSet<Reg> ret = new LinkedHashSet<>(adjList.get(reg));
    ret.removeAll(selectStack);
    ret.removeAll(coalescedNodes);
    return ret;
  }

  void decrementDegree(Reg reg) {
    int d = degree.get(reg);
    degree.put(reg, d - 1);
    if (d == kCnt) {
      LinkedHashSet<Reg> nodes = adjacent(reg);
      nodes.add(reg);
      enableMoves(nodes);
      spillWorkList.remove(reg);
      if (moveRelated(reg))
        freezeWorkList.add(reg);
      else
        simplifyWorkList.add(reg);
    }
  }

  void enableMoves(LinkedHashSet<Reg> nodes) {
    for (var reg : nodes) {
      var nodeMoves = nodeMoves(reg);
      for (var mv : nodeMoves)
        if (activeMoves.contains(mv)) {
          activeMoves.remove(mv);
          workListMoves.add(mv);
        }
    }
  }

  void stepSimplify() {
    while (!simplifyWorkList.isEmpty()) {
      Reg reg = simplifyWorkList.removeFirst();
      selectStack.push(reg);
      for (var adj : adjacent(reg))
        decrementDegree(adj);
    }
  }

  Reg getAlias(Reg reg) {
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

  boolean Briggs(LinkedHashSet<Reg> uv) {
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
    enableMoves(new LinkedHashSet<Reg>() {{ add(v); }});
    for (var t : adjacent(v)) {
      addEdge(t, u);
      decrementDegree(t);
    }
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
    } else if (preColored.contains(e.v) || adjSet.contains(e)
        || e.u == PhysicsReg.get("zero") || e.v == PhysicsReg.get("zero")) {
      // NOTICE : zero register cna't be coalesced (t61.mx: mv %8, zero + mv %19 %8)

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
      LinkedHashSet<Reg> uv = new LinkedHashSet<>(adjacent(e.u));
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
    for (var mv : nodeMoves(reg)) {
      Reg x = mv.rd, y = mv.rs1, v;
      v = getAlias(y) == getAlias(reg) ? getAlias(x) : getAlias(y);
      activeMoves.remove(mv);
      frozenMoves.add(mv);
      if (nodeMoves(v).size() == 0 && degree.get(v) < kCnt) {
        // then v is not move related anymore
        freezeWorkList.remove(v);
        simplifyWorkList.add(v);
      }
    }
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
      if (m == null || reg.spillWeight / degree.get(reg) < m.spillWeight / degree.get(m) && !spillTemp.contains(reg))
        m = reg;
    spillWorkList.remove(m);
    simplifyWorkList.add(m); // 看看是否是实际溢出
    freezeMoves(m);
  }

  void assignColors() {
    while (!selectStack.isEmpty()) {
      Reg reg = selectStack.pop();
      LinkedHashSet<Integer> okColors = new LinkedHashSet<>();
      for (int i = 5; i < 32; i++)
        okColors.add(i);
      for (var adj : adjList.get(reg)) {
        Reg adjAlias = getAlias(adj);
        if (coloredNodes.contains(adjAlias) || preColored.contains(adjAlias))
          okColors.remove(color.get(adjAlias));
      }
      if (okColors.isEmpty())
        spilledNodes.add(reg);
      else {
        coloredNodes.add(reg);
        color.put(reg, okColors.iterator().next());
      }
    }
    for (Reg reg : coalescedNodes)
      color.put(reg, color.get(getAlias(reg)));
  }

  void rewriteProgram(ASMFunction func) {
    // allocate stack space for spilled variables
    for (Reg reg : spilledNodes) {
      ((VirtualReg) reg).stackOffset = func.paramUsed + func.allocaUsed + func.spillUsed;
      func.spillUsed += 4;
    }

    // create a new VirtualReg for each def and use in spilledNodes
    for (var block : func.blocks) {
      newInsts = new LinkedList<>();
      for (ASMInst inst : block.insts) {
        VirtualReg same = null;
        if (inst.rs1 != null && inst.rs1.stackOffset != null) {
          VirtualReg newReg = new VirtualReg(4);
          spillTemp.add(newReg);
          allocateUse(newReg, (VirtualReg) inst.rs1);
          if (inst.rs1 == inst.rs2)
            inst.rs2 = newReg;
          if (inst.rs1 == inst.rd)
            same = newReg;
          inst.rs1 = newReg;
        }
        if (inst.rs2 != null && inst.rs2.stackOffset != null) {
          VirtualReg newReg = new VirtualReg(4);
          spillTemp.add(newReg);
          allocateUse(newReg, (VirtualReg) inst.rs2);
          if (inst.rs2 == inst.rd)
            same = newReg;
          inst.rs2 = newReg;
        }
        newInsts.add(inst);
        if (inst.rd != null && inst.rd.stackOffset != null) {
          VirtualReg newReg = same == null ? new VirtualReg(4) : same;
          spillTemp.add(newReg);
          allocateDef(newReg, (VirtualReg) inst.rd);
          inst.rd = newReg;
        }
      }
      block.insts = newInsts;
    }
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
      spillTemp.add(addr);
      newInsts.add(new ASMLiInst(addr, new VirtualImm(reg.stackOffset)));
      newInsts.add(new ASMBinaryInst("add", addr, addr, RegSp));
      newInsts.add(new ASMStoreInst(reg.size, addr, newReg));
    }
  }
}
