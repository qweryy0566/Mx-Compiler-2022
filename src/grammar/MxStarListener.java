// Generated from MxStar.g4 by ANTLR 4.9.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxStarParser}.
 */
public interface MxStarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MxStarParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MxStarParser.ExprContext ctx);
}