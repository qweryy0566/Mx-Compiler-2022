package utils;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class Position {
  private int line, column;

  public Position(int line, int col) {
    this.line = line;
    this.column = col;
  }

  public Position(Token token) {
    this.line = token.getLine();
    this.column = token.getCharPositionInLine();
  }

  public Position(TerminalNode terminal) {
    this(terminal.getSymbol());
  }

  public Position(ParserRuleContext ctx) {
    this(ctx.getStart());
  }

  public int line() {
    return line;
  }

  public int col() {
    return column;
  }

  public String toString() {
    return line + "," + column;
  }
}
