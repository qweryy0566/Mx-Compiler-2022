// Generated from /home/qweryy/Mx-Compiler/src/grammar/MxStar.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxStarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxStarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(MxStarParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(MxStarParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(MxStarParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(MxStarParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MxStarParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classBuild}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBuild(MxStarParser.ClassBuildContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(MxStarParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#varDefUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefUnit(MxStarParser.VarDefUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MxStarParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(MxStarParser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseType(MxStarParser.BaseTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MxStarParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(MxStarParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(MxStarParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(MxStarParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(MxStarParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#breakStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(MxStarParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#continueStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStmt(MxStarParser.ContinueStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(MxStarParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#exprStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(MxStarParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MxStarParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(MxStarParser.ExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#preAddSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreAddSub(MxStarParser.PreAddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#postAddSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostAddSub(MxStarParser.PostAddSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#opLevel2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpLevel2(MxStarParser.OpLevel2Context ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#opLevel3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpLevel3(MxStarParser.OpLevel3Context ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#opLevel4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpLevel4(MxStarParser.OpLevel4Context ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#opLevel5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpLevel5(MxStarParser.OpLevel5Context ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#opLevel6}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpLevel6(MxStarParser.OpLevel6Context ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#opLevel7}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpLevel7(MxStarParser.OpLevel7Context ctx);
}