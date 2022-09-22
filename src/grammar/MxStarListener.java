// Generated from /home/qweryy/Mx-Compiler/src/grammar/MxStar.g4 by ANTLR 4.9.2
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
	 * Enter a parse tree produced by {@link MxStarParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(MxStarParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(MxStarParser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MxStarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MxStarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#plus_minus_expr}.
	 * @param ctx the parse tree
	 */
	void enterPlus_minus_expr(MxStarParser.Plus_minus_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#plus_minus_expr}.
	 * @param ctx the parse tree
	 */
	void exitPlus_minus_expr(MxStarParser.Plus_minus_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxStarParser#plus_minus_op}.
	 * @param ctx the parse tree
	 */
	void enterPlus_minus_op(MxStarParser.Plus_minus_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxStarParser#plus_minus_op}.
	 * @param ctx the parse tree
	 */
	void exitPlus_minus_op(MxStarParser.Plus_minus_opContext ctx);
}