import grammar.*;
import ast.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import frontend.*;

public class Compiler {
  public static void main(String[] args) throws Exception {
    CharStream input = CharStreams.fromStream(System.in);
    MxLexer lexer = new MxLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    MxParser parser = new MxParser(tokens);
    ParseTree tree = parser.program();
    ASTBuilder astBuilder = new ASTBuilder();
    ProgramNode ast = (ProgramNode) astBuilder.visit(tree);
    SemanticChecker checker = new SemanticChecker();
    checker.visit(ast);
  }
}