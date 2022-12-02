import grammar.*;
import utils.*;
import ast.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.FileInputStream; 

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import IR.*;
import frontend.*;
import backend.*;

public class Compiler {
  public static void main(String[] args) throws Exception {
    CharStream input = CharStreams.fromStream(new FileInputStream("input.mx"));
    PrintStream output = new PrintStream("output.ll"); 
    System.setOut(output);
    //CharStream input = CharStreams.fromStream(System.in);
    MxLexer lexer = new MxLexer(input);
    lexer.removeErrorListeners();
    lexer.addErrorListener(new MxErrorListener());
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    MxParser parser = new MxParser(tokens);
    parser.removeErrorListeners();
    parser.addErrorListener(new MxErrorListener());
    ParseTree tree = parser.program();
    ASTBuilder astBuilder = new ASTBuilder();
    ProgramNode ast = (ProgramNode) astBuilder.visit(tree);
    GlobalScope globalScope = new GlobalScope();
    new SymbolCollector(globalScope).visit(ast);
    new SemanticChecker(globalScope).visit(ast);
    IRProgram irProgram = new IRProgram();
    new IRBuilder(irProgram, globalScope).visit(ast);
    System.out.println(irProgram);
  }
}