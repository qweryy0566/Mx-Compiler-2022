import ast.*;
import grammar.*;
import grammar.MxParser.*;

public class ASTBuilder extends MxParserBaseVisitor<Node> {
  @Override
  public Node visitProgram(MxParser.ProgramContext ctx) {
    ProgramNode program = new ProgramNode();
    for (var def : ctx.children)
      if (def instanceof ClassDefContext) {
        program.defList.add((ClassDefNode) visit(def));
        // program.regi
      } else if (def instanceof FuncDefContext) {
        program.defList.add((FuncDefNode) visit(def));
      } else if (def instanceof VarDefContext) {
        program.defList.add((VarDefNode) visit(def));
      }
    return program;
  }
 
}