import grammar.*;
import utils.GlobalScope;
import ast.*;

import java.io.InputStream;
import java.io.FileInputStream; 

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import frontend.*;

public class Compiler {
  public static void main(String[] args) throws Exception {
    // CharStream input = CharStreams.fromStream(new FileInputStream("data/sema/array-package/array-4.mx"));
    CharStream input = CharStreams.fromStream(System.in);
    MxLexer lexer = new MxLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    MxParser parser = new MxParser(tokens);
    ParseTree tree = parser.program();
    ASTBuilder astBuilder = new ASTBuilder();
    ProgramNode ast = (ProgramNode) astBuilder.visit(tree);
    GlobalScope globalScope = new GlobalScope();
    new SymbolCollector(globalScope).visit(ast);
    new SemanticChecker(globalScope).visit(ast);
  }

  private static InputStream FileInputStream(String string) {
    return null;
  }
}