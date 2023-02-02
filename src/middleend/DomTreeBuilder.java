package middleend;

import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;

import IR.*;

/*
 * references:
 *  https://blog.csdn.net/dashuniuniu/article/details/52224882
 *  https://www.cs.rice.edu/~keith/EMBED/dom.pdf (A Simple, Fast Dominance Algorithm)
 */

public class DomTreeBuilder {
  IRProgram program;

  public DomTreeBuilder(IRProgram program) {
    this.program = program;
  }

  public void work() {
    program.funcList.forEach(func -> workOnFunc(func));
  }

  // reverse post order
  HashSet<IRBasicBlock> visited = new HashSet<>();
  HashMap<IRBasicBlock, Integer> order = new HashMap<>();
  LinkedList<IRBasicBlock> blockSeq = new LinkedList<>();

  void calcReversePostOrder(IRBasicBlock block) {
    visited.add(block);
    for (var succ : block.succs)
      if (!visited.contains(succ))
        calcReversePostOrder(succ);
    order.put(block, blockSeq.size()); // negative for reverse order
    blockSeq.addFirst(block);
  }

  void workOnFunc(IRFunction func) {
    blockSeq.clear();
    order.clear();
    visited.clear();
    calcReversePostOrder(func.entryBlock);
    func.entryBlock.idom = func.entryBlock;
    blockSeq.removeFirst();
    boolean changed = true;
    while (changed) {
      changed = false;
      for (IRBasicBlock block : blockSeq) {
        IRBasicBlock newIdom = null;
        for (IRBasicBlock pred : block.preds)
          if (newIdom == null)
            newIdom = pred;
          else if (pred.idom != null)
            newIdom = intersect(pred, newIdom);
        if (newIdom != block.idom) {
          block.idom = newIdom;
          changed = true;
        }
      }
    }

    // calculate dominance tree
    blockSeq.forEach(block -> block.idom.domChildren.add(block));

    // calculate dominance frontiers
    blockSeq.addFirst(func.entryBlock);
    for (IRBasicBlock block : blockSeq) {
      if (block.preds.size() < 2)
        continue;
      for (IRBasicBlock pred : block.preds) {
        IRBasicBlock runner = pred;
        // DF(n) = {x | n dominates a predecessor of x and n does not strictly dominate x}
        while (runner != block.idom) {
          runner.domFrontier.add(block);
          runner = runner.idom;
        }
      }
    }
  }
  IRBasicBlock intersect(IRBasicBlock x, IRBasicBlock y) {
    while (x != y) {
      while (order.get(x) < order.get(y))
        x = x.idom;
      while (order.get(y) < order.get(x))
        y = y.idom;
    }
    return x;
  }
}
