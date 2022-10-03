// Generated from /home/qweryy/Mx-Compiler/src/grammar/MxStar.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxStarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Add=1, Sub=2, Mul=3, Div=4, Mod=5, GThan=6, LThan=7, GEqual=8, LEqual=9, 
		NEqual=10, EEqual=11, LAnd=12, LOr=13, LNot=14, RShift=15, LShift=16, 
		BAnd=17, BOr=18, BXor=19, BNot=20, Assign=21, SelfAdd=22, SelfSub=23, 
		Member=24, LBracket=25, RBracket=26, LParen=27, RParen=28, Semi=29, Comma=30, 
		LBrace=31, RBrace=32, Quote=33, IntConst=34, PChar=35, StringConst=36, 
		Comment=37, Void=38, Bool=39, Int=40, String=41, New=42, Class=43, Null=44, 
		Ture=45, False=46, This=47, If=48, Else=49, For=50, While=51, Break=52, 
		Continue=53, Return=54, Identifier=55, WhiteSpace=56;
	public static final int
		RULE_program = 0, RULE_funcDef = 1, RULE_returnType = 2, RULE_parameterList = 3, 
		RULE_suite = 4, RULE_classDef = 5, RULE_classBuild = 6, RULE_varDef = 7, 
		RULE_varDefUnit = 8, RULE_type = 9, RULE_typeName = 10, RULE_baseType = 11, 
		RULE_statement = 12, RULE_ifStmt = 13, RULE_whileStmt = 14, RULE_forStmt = 15, 
		RULE_forInit = 16, RULE_breakStmt = 17, RULE_continueStmt = 18, RULE_returnStmt = 19, 
		RULE_exprStmt = 20, RULE_expr = 21, RULE_exprList = 22, RULE_preAddSub = 23, 
		RULE_postAddSub = 24, RULE_opLevel2 = 25, RULE_opLevel3 = 26, RULE_opLevel4 = 27, 
		RULE_opLevel5 = 28, RULE_opLevel6 = 29, RULE_opLevel7 = 30;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "funcDef", "returnType", "parameterList", "suite", "classDef", 
			"classBuild", "varDef", "varDefUnit", "type", "typeName", "baseType", 
			"statement", "ifStmt", "whileStmt", "forStmt", "forInit", "breakStmt", 
			"continueStmt", "returnStmt", "exprStmt", "expr", "exprList", "preAddSub", 
			"postAddSub", "opLevel2", "opLevel3", "opLevel4", "opLevel5", "opLevel6", 
			"opLevel7"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'<'", "'>='", "'<='", 
			"'!='", "'=='", "'&&'", "'||'", "'!'", "'>>'", "'<<'", "'&'", "'|'", 
			"'^'", "'~'", "'='", "'++'", "'--'", "'.'", "'['", "']'", "'('", "')'", 
			"';'", "','", "'{'", "'}'", "'\"'", null, null, null, null, "'void'", 
			"'bool'", "'int'", "'string'", "'new'", "'class'", "'null'", "'true'", 
			"'false'", "'this'", "'if'", "'else'", "'for'", "'while'", "'break'", 
			"'continue'", "'return'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Add", "Sub", "Mul", "Div", "Mod", "GThan", "LThan", "GEqual", 
			"LEqual", "NEqual", "EEqual", "LAnd", "LOr", "LNot", "RShift", "LShift", 
			"BAnd", "BOr", "BXor", "BNot", "Assign", "SelfAdd", "SelfSub", "Member", 
			"LBracket", "RBracket", "LParen", "RParen", "Semi", "Comma", "LBrace", 
			"RBrace", "Quote", "IntConst", "PChar", "StringConst", "Comment", "Void", 
			"Bool", "Int", "String", "New", "Class", "Null", "Ture", "False", "This", 
			"If", "Else", "For", "While", "Break", "Continue", "Return", "Identifier", 
			"WhiteSpace"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MxStar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxStarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MxStarParser.EOF, 0); }
		public List<FuncDefContext> funcDef() {
			return getRuleContexts(FuncDefContext.class);
		}
		public FuncDefContext funcDef(int i) {
			return getRuleContext(FuncDefContext.class,i);
		}
		public List<ClassDefContext> classDef() {
			return getRuleContexts(ClassDefContext.class);
		}
		public ClassDefContext classDef(int i) {
			return getRuleContext(ClassDefContext.class,i);
		}
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << Class) | (1L << Identifier))) != 0)) {
				{
				setState(65);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(62);
					funcDef();
					}
					break;
				case 2:
					{
					setState(63);
					classDef();
					}
					break;
				case 3:
					{
					setState(64);
					varDef();
					}
					break;
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(70);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncDefContext extends ParserRuleContext {
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode LParen() { return getToken(MxStarParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxStarParser.RParen, 0); }
		public TerminalNode LBrace() { return getToken(MxStarParser.LBrace, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public TerminalNode RBrace() { return getToken(MxStarParser.RBrace, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public FuncDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcDef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncDefContext funcDef() throws RecognitionException {
		FuncDefContext _localctx = new FuncDefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_funcDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			returnType();
			setState(73);
			match(Identifier);
			setState(74);
			match(LParen);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Identifier))) != 0)) {
				{
				setState(75);
				parameterList();
				}
			}

			setState(78);
			match(RParen);
			setState(79);
			match(LBrace);
			setState(80);
			suite();
			setState(81);
			match(RBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnTypeContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Void() { return getToken(MxStarParser.Void, 0); }
		public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitReturnType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnTypeContext returnType() throws RecognitionException {
		ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_returnType);
		try {
			setState(85);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				type(0);
				}
				break;
			case Void:
				enterOuterAlt(_localctx, 2);
				{
				setState(84);
				match(Void);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> Identifier() { return getTokens(MxStarParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MxStarParser.Identifier, i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxStarParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxStarParser.Comma, i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(87);
			type(0);
			setState(88);
			match(Identifier);
			}
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(90);
				match(Comma);
				setState(91);
				type(0);
				setState(92);
				match(Identifier);
				}
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SuiteContext extends ParserRuleContext {
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suite; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuiteContext suite() throws RecognitionException {
		SuiteContext _localctx = new SuiteContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_suite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LNot) | (1L << BNot) | (1L << SelfAdd) | (1L << SelfSub) | (1L << LParen) | (1L << Semi) | (1L << LBrace) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << If) | (1L << For) | (1L << While) | (1L << Break) | (1L << Continue) | (1L << Return) | (1L << Identifier))) != 0)) {
				{
				setState(101);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Bool:
				case Int:
				case String:
				case Identifier:
					{
					setState(99);
					varDef();
					}
					break;
				case Add:
				case Sub:
				case LNot:
				case BNot:
				case SelfAdd:
				case SelfSub:
				case LParen:
				case Semi:
				case LBrace:
				case If:
				case For:
				case While:
				case Break:
				case Continue:
				case Return:
					{
					setState(100);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDefContext extends ParserRuleContext {
		public TerminalNode Class() { return getToken(MxStarParser.Class, 0); }
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode LBrace() { return getToken(MxStarParser.LBrace, 0); }
		public TerminalNode RBrace() { return getToken(MxStarParser.RBrace, 0); }
		public TerminalNode Semi() { return getToken(MxStarParser.Semi, 0); }
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public List<ClassBuildContext> classBuild() {
			return getRuleContexts(ClassBuildContext.class);
		}
		public ClassBuildContext classBuild(int i) {
			return getRuleContext(ClassBuildContext.class,i);
		}
		public List<FuncDefContext> funcDef() {
			return getRuleContexts(FuncDefContext.class);
		}
		public FuncDefContext funcDef(int i) {
			return getRuleContext(FuncDefContext.class,i);
		}
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(Class);
			setState(107);
			match(Identifier);
			setState(108);
			match(LBrace);
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << Identifier))) != 0)) {
				{
				setState(112);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
				case 1:
					{
					setState(109);
					varDef();
					}
					break;
				case 2:
					{
					setState(110);
					classBuild();
					}
					break;
				case 3:
					{
					setState(111);
					funcDef();
					}
					break;
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(117);
			match(RBrace);
			setState(118);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBuildContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode LParen() { return getToken(MxStarParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxStarParser.RParen, 0); }
		public TerminalNode LBrace() { return getToken(MxStarParser.LBrace, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public TerminalNode RBrace() { return getToken(MxStarParser.RBrace, 0); }
		public ClassBuildContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBuild; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitClassBuild(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBuildContext classBuild() throws RecognitionException {
		ClassBuildContext _localctx = new ClassBuildContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_classBuild);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			match(Identifier);
			setState(121);
			match(LParen);
			setState(122);
			match(RParen);
			setState(123);
			match(LBrace);
			setState(124);
			suite();
			setState(125);
			match(RBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDefContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<VarDefUnitContext> varDefUnit() {
			return getRuleContexts(VarDefUnitContext.class);
		}
		public VarDefUnitContext varDefUnit(int i) {
			return getRuleContext(VarDefUnitContext.class,i);
		}
		public TerminalNode Semi() { return getToken(MxStarParser.Semi, 0); }
		public List<TerminalNode> Comma() { return getTokens(MxStarParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxStarParser.Comma, i);
		}
		public VarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVarDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefContext varDef() throws RecognitionException {
		VarDefContext _localctx = new VarDefContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_varDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			type(0);
			setState(128);
			varDefUnit();
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(129);
				match(Comma);
				setState(130);
				varDefUnit();
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(136);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDefUnitContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TerminalNode Assign() { return getToken(MxStarParser.Assign, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDefUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDefUnit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitVarDefUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDefUnitContext varDefUnit() throws RecognitionException {
		VarDefUnitContext _localctx = new VarDefUnitContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_varDefUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(Identifier);
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(139);
				match(Assign);
				setState(140);
				expr(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LBracket() { return getToken(MxStarParser.LBracket, 0); }
		public TerminalNode RBracket() { return getToken(MxStarParser.RBracket, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(144);
			typeName();
			}
			_ctx.stop = _input.LT(-1);
			setState(151);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TypeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(146);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(147);
					match(LBracket);
					setState(148);
					match(RBracket);
					}
					} 
				}
				setState(153);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TypeNameContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitTypeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_typeName);
		try {
			setState(156);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				baseType();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(155);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BaseTypeContext extends ParserRuleContext {
		public TerminalNode Int() { return getToken(MxStarParser.Int, 0); }
		public TerminalNode Bool() { return getToken(MxStarParser.Bool, 0); }
		public TerminalNode String() { return getToken(MxStarParser.String, 0); }
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitBaseType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_baseType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public TerminalNode LBrace() { return getToken(MxStarParser.LBrace, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public TerminalNode RBrace() { return getToken(MxStarParser.RBrace, 0); }
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public WhileStmtContext whileStmt() {
			return getRuleContext(WhileStmtContext.class,0);
		}
		public ForStmtContext forStmt() {
			return getRuleContext(ForStmtContext.class,0);
		}
		public BreakStmtContext breakStmt() {
			return getRuleContext(BreakStmtContext.class,0);
		}
		public ContinueStmtContext continueStmt() {
			return getRuleContext(ContinueStmtContext.class,0);
		}
		public ReturnStmtContext returnStmt() {
			return getRuleContext(ReturnStmtContext.class,0);
		}
		public ExprStmtContext exprStmt() {
			return getRuleContext(ExprStmtContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_statement);
		try {
			setState(171);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBrace:
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				match(LBrace);
				setState(161);
				suite();
				setState(162);
				match(RBrace);
				}
				break;
			case If:
				enterOuterAlt(_localctx, 2);
				{
				setState(164);
				ifStmt();
				}
				break;
			case While:
				enterOuterAlt(_localctx, 3);
				{
				setState(165);
				whileStmt();
				}
				break;
			case For:
				enterOuterAlt(_localctx, 4);
				{
				setState(166);
				forStmt();
				}
				break;
			case Break:
				enterOuterAlt(_localctx, 5);
				{
				setState(167);
				breakStmt();
				}
				break;
			case Continue:
				enterOuterAlt(_localctx, 6);
				{
				setState(168);
				continueStmt();
				}
				break;
			case Return:
				enterOuterAlt(_localctx, 7);
				{
				setState(169);
				returnStmt();
				}
				break;
			case Add:
			case Sub:
			case LNot:
			case BNot:
			case SelfAdd:
			case SelfSub:
			case LParen:
			case Semi:
				enterOuterAlt(_localctx, 8);
				{
				setState(170);
				exprStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStmtContext extends ParserRuleContext {
		public TerminalNode If() { return getToken(MxStarParser.If, 0); }
		public TerminalNode LParen() { return getToken(MxStarParser.LParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RParen() { return getToken(MxStarParser.RParen, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode Else() { return getToken(MxStarParser.Else, 0); }
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_ifStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(If);
			setState(174);
			match(LParen);
			setState(175);
			expr(0);
			setState(176);
			match(RParen);
			setState(177);
			statement();
			setState(180);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(178);
				match(Else);
				setState(179);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStmtContext extends ParserRuleContext {
		public TerminalNode While() { return getToken(MxStarParser.While, 0); }
		public TerminalNode LParen() { return getToken(MxStarParser.LParen, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RParen() { return getToken(MxStarParser.RParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStmtContext whileStmt() throws RecognitionException {
		WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_whileStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(While);
			setState(183);
			match(LParen);
			setState(184);
			expr(0);
			setState(185);
			match(RParen);
			setState(186);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStmtContext extends ParserRuleContext {
		public TerminalNode For() { return getToken(MxStarParser.For, 0); }
		public TerminalNode LParen() { return getToken(MxStarParser.LParen, 0); }
		public TerminalNode Semi() { return getToken(MxStarParser.Semi, 0); }
		public TerminalNode RParen() { return getToken(MxStarParser.RParen, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForInitContext forInit() {
			return getRuleContext(ForInitContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ForStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitForStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStmtContext forStmt() throws RecognitionException {
		ForStmtContext _localctx = new ForStmtContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_forStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(For);
			setState(189);
			match(LParen);
			setState(191);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(190);
				forInit();
				}
				break;
			}
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LNot) | (1L << BNot) | (1L << SelfAdd) | (1L << SelfSub) | (1L << LParen))) != 0)) {
				{
				setState(193);
				expr(0);
				}
			}

			setState(196);
			match(Semi);
			setState(198);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LNot) | (1L << BNot) | (1L << SelfAdd) | (1L << SelfSub) | (1L << LParen))) != 0)) {
				{
				setState(197);
				expr(0);
				}
			}

			setState(200);
			match(RParen);
			setState(201);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForInitContext extends ParserRuleContext {
		public VarDefContext varDef() {
			return getRuleContext(VarDefContext.class,0);
		}
		public ExprStmtContext exprStmt() {
			return getRuleContext(ExprStmtContext.class,0);
		}
		public ForInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitForInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_forInit);
		try {
			setState(205);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(203);
				varDef();
				}
				break;
			case Add:
			case Sub:
			case LNot:
			case BNot:
			case SelfAdd:
			case SelfSub:
			case LParen:
			case Semi:
				enterOuterAlt(_localctx, 2);
				{
				setState(204);
				exprStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BreakStmtContext extends ParserRuleContext {
		public TerminalNode Break() { return getToken(MxStarParser.Break, 0); }
		public TerminalNode Semi() { return getToken(MxStarParser.Semi, 0); }
		public BreakStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitBreakStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStmtContext breakStmt() throws RecognitionException {
		BreakStmtContext _localctx = new BreakStmtContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_breakStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			match(Break);
			setState(208);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ContinueStmtContext extends ParserRuleContext {
		public TerminalNode Continue() { return getToken(MxStarParser.Continue, 0); }
		public TerminalNode Semi() { return getToken(MxStarParser.Semi, 0); }
		public ContinueStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitContinueStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStmtContext continueStmt() throws RecognitionException {
		ContinueStmtContext _localctx = new ContinueStmtContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_continueStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(Continue);
			setState(211);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStmtContext extends ParserRuleContext {
		public TerminalNode Return() { return getToken(MxStarParser.Return, 0); }
		public TerminalNode Semi() { return getToken(MxStarParser.Semi, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStmtContext returnStmt() throws RecognitionException {
		ReturnStmtContext _localctx = new ReturnStmtContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_returnStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(Return);
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LNot) | (1L << BNot) | (1L << SelfAdd) | (1L << SelfSub) | (1L << LParen))) != 0)) {
				{
				setState(214);
				expr(0);
				}
			}

			setState(217);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprStmtContext extends ParserRuleContext {
		public TerminalNode Semi() { return getToken(MxStarParser.Semi, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprStmt; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprStmtContext exprStmt() throws RecognitionException {
		ExprStmtContext _localctx = new ExprStmtContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_exprStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LNot) | (1L << BNot) | (1L << SelfAdd) | (1L << SelfSub) | (1L << LParen))) != 0)) {
				{
				setState(219);
				expr(0);
				}
			}

			setState(222);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public TerminalNode LParen() { return getToken(MxStarParser.LParen, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RParen() { return getToken(MxStarParser.RParen, 0); }
		public PreAddSubContext preAddSub() {
			return getRuleContext(PreAddSubContext.class,0);
		}
		public OpLevel2Context opLevel2() {
			return getRuleContext(OpLevel2Context.class,0);
		}
		public OpLevel3Context opLevel3() {
			return getRuleContext(OpLevel3Context.class,0);
		}
		public OpLevel4Context opLevel4() {
			return getRuleContext(OpLevel4Context.class,0);
		}
		public OpLevel5Context opLevel5() {
			return getRuleContext(OpLevel5Context.class,0);
		}
		public OpLevel6Context opLevel6() {
			return getRuleContext(OpLevel6Context.class,0);
		}
		public OpLevel7Context opLevel7() {
			return getRuleContext(OpLevel7Context.class,0);
		}
		public TerminalNode BAnd() { return getToken(MxStarParser.BAnd, 0); }
		public TerminalNode BXor() { return getToken(MxStarParser.BXor, 0); }
		public TerminalNode BOr() { return getToken(MxStarParser.BOr, 0); }
		public TerminalNode LAnd() { return getToken(MxStarParser.LAnd, 0); }
		public TerminalNode LOr() { return getToken(MxStarParser.LOr, 0); }
		public TerminalNode Assign() { return getToken(MxStarParser.Assign, 0); }
		public TerminalNode LBracket() { return getToken(MxStarParser.LBracket, 0); }
		public TerminalNode RBracket() { return getToken(MxStarParser.RBracket, 0); }
		public TerminalNode Member() { return getToken(MxStarParser.Member, 0); }
		public TerminalNode Identifier() { return getToken(MxStarParser.Identifier, 0); }
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public PostAddSubContext postAddSub() {
			return getRuleContext(PostAddSubContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LParen:
				{
				setState(225);
				match(LParen);
				setState(226);
				expr(0);
				setState(227);
				match(RParen);
				}
				break;
			case SelfAdd:
			case SelfSub:
				{
				setState(229);
				preAddSub();
				setState(230);
				expr(14);
				}
				break;
			case Add:
			case Sub:
			case LNot:
			case BNot:
				{
				setState(232);
				opLevel2();
				setState(233);
				expr(12);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(293);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(291);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(237);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(238);
						opLevel3();
						setState(239);
						expr(12);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(241);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(242);
						opLevel4();
						setState(243);
						expr(11);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(245);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(246);
						opLevel5();
						setState(247);
						expr(10);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(249);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(250);
						opLevel6();
						setState(251);
						expr(9);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(253);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(254);
						opLevel7();
						setState(255);
						expr(8);
						}
						break;
					case 6:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(257);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(258);
						match(BAnd);
						setState(259);
						expr(7);
						}
						break;
					case 7:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(260);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(261);
						match(BXor);
						setState(262);
						expr(6);
						}
						break;
					case 8:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(263);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(264);
						match(BOr);
						setState(265);
						expr(5);
						}
						break;
					case 9:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(266);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(267);
						match(LAnd);
						setState(268);
						expr(4);
						}
						break;
					case 10:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(269);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(270);
						match(LOr);
						setState(271);
						expr(3);
						}
						break;
					case 11:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(272);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(273);
						match(Assign);
						setState(274);
						expr(1);
						}
						break;
					case 12:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(275);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(276);
						match(LBracket);
						setState(277);
						expr(0);
						setState(278);
						match(RBracket);
						}
						break;
					case 13:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(280);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(281);
						match(Member);
						setState(282);
						match(Identifier);
						}
						break;
					case 14:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(283);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(284);
						match(LParen);
						setState(286);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LNot) | (1L << BNot) | (1L << SelfAdd) | (1L << SelfSub) | (1L << LParen))) != 0)) {
							{
							setState(285);
							exprList();
							}
						}

						setState(288);
						match(RParen);
						}
						break;
					case 15:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(289);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(290);
						postAddSub();
						}
						break;
					}
					} 
				}
				setState(295);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExprListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxStarParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxStarParser.Comma, i);
		}
		public ExprListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitExprList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprListContext exprList() throws RecognitionException {
		ExprListContext _localctx = new ExprListContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_exprList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			expr(0);
			setState(301);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(297);
				match(Comma);
				setState(298);
				expr(0);
				}
				}
				setState(303);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PreAddSubContext extends ParserRuleContext {
		public TerminalNode SelfAdd() { return getToken(MxStarParser.SelfAdd, 0); }
		public TerminalNode SelfSub() { return getToken(MxStarParser.SelfSub, 0); }
		public PreAddSubContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_preAddSub; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitPreAddSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PreAddSubContext preAddSub() throws RecognitionException {
		PreAddSubContext _localctx = new PreAddSubContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_preAddSub);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			_la = _input.LA(1);
			if ( !(_la==SelfAdd || _la==SelfSub) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostAddSubContext extends ParserRuleContext {
		public TerminalNode SelfAdd() { return getToken(MxStarParser.SelfAdd, 0); }
		public TerminalNode SelfSub() { return getToken(MxStarParser.SelfSub, 0); }
		public PostAddSubContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postAddSub; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitPostAddSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostAddSubContext postAddSub() throws RecognitionException {
		PostAddSubContext _localctx = new PostAddSubContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_postAddSub);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			_la = _input.LA(1);
			if ( !(_la==SelfAdd || _la==SelfSub) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpLevel2Context extends ParserRuleContext {
		public TerminalNode LNot() { return getToken(MxStarParser.LNot, 0); }
		public TerminalNode BNot() { return getToken(MxStarParser.BNot, 0); }
		public TerminalNode Add() { return getToken(MxStarParser.Add, 0); }
		public TerminalNode Sub() { return getToken(MxStarParser.Sub, 0); }
		public OpLevel2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opLevel2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitOpLevel2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpLevel2Context opLevel2() throws RecognitionException {
		OpLevel2Context _localctx = new OpLevel2Context(_ctx, getState());
		enterRule(_localctx, 50, RULE_opLevel2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Add) | (1L << Sub) | (1L << LNot) | (1L << BNot))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpLevel3Context extends ParserRuleContext {
		public TerminalNode Mul() { return getToken(MxStarParser.Mul, 0); }
		public TerminalNode Div() { return getToken(MxStarParser.Div, 0); }
		public TerminalNode Mod() { return getToken(MxStarParser.Mod, 0); }
		public OpLevel3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opLevel3; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitOpLevel3(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpLevel3Context opLevel3() throws RecognitionException {
		OpLevel3Context _localctx = new OpLevel3Context(_ctx, getState());
		enterRule(_localctx, 52, RULE_opLevel3);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(310);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Mul) | (1L << Div) | (1L << Mod))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpLevel4Context extends ParserRuleContext {
		public TerminalNode Add() { return getToken(MxStarParser.Add, 0); }
		public TerminalNode Sub() { return getToken(MxStarParser.Sub, 0); }
		public OpLevel4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opLevel4; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitOpLevel4(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpLevel4Context opLevel4() throws RecognitionException {
		OpLevel4Context _localctx = new OpLevel4Context(_ctx, getState());
		enterRule(_localctx, 54, RULE_opLevel4);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			_la = _input.LA(1);
			if ( !(_la==Add || _la==Sub) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpLevel5Context extends ParserRuleContext {
		public TerminalNode LShift() { return getToken(MxStarParser.LShift, 0); }
		public TerminalNode RShift() { return getToken(MxStarParser.RShift, 0); }
		public OpLevel5Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opLevel5; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitOpLevel5(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpLevel5Context opLevel5() throws RecognitionException {
		OpLevel5Context _localctx = new OpLevel5Context(_ctx, getState());
		enterRule(_localctx, 56, RULE_opLevel5);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			_la = _input.LA(1);
			if ( !(_la==RShift || _la==LShift) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpLevel6Context extends ParserRuleContext {
		public TerminalNode LThan() { return getToken(MxStarParser.LThan, 0); }
		public TerminalNode GThan() { return getToken(MxStarParser.GThan, 0); }
		public TerminalNode LEqual() { return getToken(MxStarParser.LEqual, 0); }
		public TerminalNode GEqual() { return getToken(MxStarParser.GEqual, 0); }
		public OpLevel6Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opLevel6; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitOpLevel6(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpLevel6Context opLevel6() throws RecognitionException {
		OpLevel6Context _localctx = new OpLevel6Context(_ctx, getState());
		enterRule(_localctx, 58, RULE_opLevel6);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GThan) | (1L << LThan) | (1L << GEqual) | (1L << LEqual))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpLevel7Context extends ParserRuleContext {
		public TerminalNode EEqual() { return getToken(MxStarParser.EEqual, 0); }
		public TerminalNode NEqual() { return getToken(MxStarParser.NEqual, 0); }
		public OpLevel7Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opLevel7; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MxStarVisitor ) return ((MxStarVisitor<? extends T>)visitor).visitOpLevel7(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpLevel7Context opLevel7() throws RecognitionException {
		OpLevel7Context _localctx = new OpLevel7Context(_ctx, getState());
		enterRule(_localctx, 60, RULE_opLevel7);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			_la = _input.LA(1);
			if ( !(_la==NEqual || _la==EEqual) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 21:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 11);
		case 2:
			return precpred(_ctx, 10);
		case 3:
			return precpred(_ctx, 9);
		case 4:
			return precpred(_ctx, 8);
		case 5:
			return precpred(_ctx, 7);
		case 6:
			return precpred(_ctx, 6);
		case 7:
			return precpred(_ctx, 5);
		case 8:
			return precpred(_ctx, 4);
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 2);
		case 11:
			return precpred(_ctx, 1);
		case 12:
			return precpred(_ctx, 17);
		case 13:
			return precpred(_ctx, 16);
		case 14:
			return precpred(_ctx, 15);
		case 15:
			return precpred(_ctx, 13);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3:\u0143\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \3\2"+
		"\3\2\3\2\7\2D\n\2\f\2\16\2G\13\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3O\n\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\4\3\4\5\4X\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5a\n\5"+
		"\f\5\16\5d\13\5\3\6\3\6\7\6h\n\6\f\6\16\6k\13\6\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\7\7s\n\7\f\7\16\7v\13\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t"+
		"\3\t\3\t\3\t\7\t\u0086\n\t\f\t\16\t\u0089\13\t\3\t\3\t\3\n\3\n\3\n\5\n"+
		"\u0090\n\n\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u0098\n\13\f\13\16\13\u009b"+
		"\13\13\3\f\3\f\5\f\u009f\n\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\5\16\u00ae\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\5\17\u00b7\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\5\21\u00c2"+
		"\n\21\3\21\5\21\u00c5\n\21\3\21\3\21\5\21\u00c9\n\21\3\21\3\21\3\21\3"+
		"\22\3\22\5\22\u00d0\n\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\5\25"+
		"\u00da\n\25\3\25\3\25\3\26\5\26\u00df\n\26\3\26\3\26\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u00ee\n\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\5\27\u0121\n\27\3\27\3\27\3\27\7\27\u0126\n\27\f"+
		"\27\16\27\u0129\13\27\3\30\3\30\3\30\7\30\u012e\n\30\f\30\16\30\u0131"+
		"\13\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37"+
		"\3\37\3 \3 \3 \2\4\24,!\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*"+
		",.\60\62\64\668:<>\2\n\3\2)+\3\2\30\31\5\2\3\4\20\20\26\26\3\2\5\7\3\2"+
		"\3\4\3\2\21\22\3\2\b\13\3\2\f\r\2\u0153\2E\3\2\2\2\4J\3\2\2\2\6W\3\2\2"+
		"\2\bY\3\2\2\2\ni\3\2\2\2\fl\3\2\2\2\16z\3\2\2\2\20\u0081\3\2\2\2\22\u008c"+
		"\3\2\2\2\24\u0091\3\2\2\2\26\u009e\3\2\2\2\30\u00a0\3\2\2\2\32\u00ad\3"+
		"\2\2\2\34\u00af\3\2\2\2\36\u00b8\3\2\2\2 \u00be\3\2\2\2\"\u00cf\3\2\2"+
		"\2$\u00d1\3\2\2\2&\u00d4\3\2\2\2(\u00d7\3\2\2\2*\u00de\3\2\2\2,\u00ed"+
		"\3\2\2\2.\u012a\3\2\2\2\60\u0132\3\2\2\2\62\u0134\3\2\2\2\64\u0136\3\2"+
		"\2\2\66\u0138\3\2\2\28\u013a\3\2\2\2:\u013c\3\2\2\2<\u013e\3\2\2\2>\u0140"+
		"\3\2\2\2@D\5\4\3\2AD\5\f\7\2BD\5\20\t\2C@\3\2\2\2CA\3\2\2\2CB\3\2\2\2"+
		"DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2FH\3\2\2\2GE\3\2\2\2HI\7\2\2\3I\3\3\2\2"+
		"\2JK\5\6\4\2KL\79\2\2LN\7\35\2\2MO\5\b\5\2NM\3\2\2\2NO\3\2\2\2OP\3\2\2"+
		"\2PQ\7\36\2\2QR\7!\2\2RS\5\n\6\2ST\7\"\2\2T\5\3\2\2\2UX\5\24\13\2VX\7"+
		"(\2\2WU\3\2\2\2WV\3\2\2\2X\7\3\2\2\2YZ\5\24\13\2Z[\79\2\2[b\3\2\2\2\\"+
		"]\7 \2\2]^\5\24\13\2^_\79\2\2_a\3\2\2\2`\\\3\2\2\2ad\3\2\2\2b`\3\2\2\2"+
		"bc\3\2\2\2c\t\3\2\2\2db\3\2\2\2eh\5\20\t\2fh\5\32\16\2ge\3\2\2\2gf\3\2"+
		"\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2j\13\3\2\2\2ki\3\2\2\2lm\7-\2\2mn\7"+
		"9\2\2nt\7!\2\2os\5\20\t\2ps\5\16\b\2qs\5\4\3\2ro\3\2\2\2rp\3\2\2\2rq\3"+
		"\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2uw\3\2\2\2vt\3\2\2\2wx\7\"\2\2xy\7"+
		"\37\2\2y\r\3\2\2\2z{\79\2\2{|\7\35\2\2|}\7\36\2\2}~\7!\2\2~\177\5\n\6"+
		"\2\177\u0080\7\"\2\2\u0080\17\3\2\2\2\u0081\u0082\5\24\13\2\u0082\u0087"+
		"\5\22\n\2\u0083\u0084\7 \2\2\u0084\u0086\5\22\n\2\u0085\u0083\3\2\2\2"+
		"\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008a"+
		"\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008b\7\37\2\2\u008b\21\3\2\2\2\u008c"+
		"\u008f\79\2\2\u008d\u008e\7\27\2\2\u008e\u0090\5,\27\2\u008f\u008d\3\2"+
		"\2\2\u008f\u0090\3\2\2\2\u0090\23\3\2\2\2\u0091\u0092\b\13\1\2\u0092\u0093"+
		"\5\26\f\2\u0093\u0099\3\2\2\2\u0094\u0095\f\4\2\2\u0095\u0096\7\33\2\2"+
		"\u0096\u0098\7\34\2\2\u0097\u0094\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097"+
		"\3\2\2\2\u0099\u009a\3\2\2\2\u009a\25\3\2\2\2\u009b\u0099\3\2\2\2\u009c"+
		"\u009f\5\30\r\2\u009d\u009f\79\2\2\u009e\u009c\3\2\2\2\u009e\u009d\3\2"+
		"\2\2\u009f\27\3\2\2\2\u00a0\u00a1\t\2\2\2\u00a1\31\3\2\2\2\u00a2\u00a3"+
		"\7!\2\2\u00a3\u00a4\5\n\6\2\u00a4\u00a5\7\"\2\2\u00a5\u00ae\3\2\2\2\u00a6"+
		"\u00ae\5\34\17\2\u00a7\u00ae\5\36\20\2\u00a8\u00ae\5 \21\2\u00a9\u00ae"+
		"\5$\23\2\u00aa\u00ae\5&\24\2\u00ab\u00ae\5(\25\2\u00ac\u00ae\5*\26\2\u00ad"+
		"\u00a2\3\2\2\2\u00ad\u00a6\3\2\2\2\u00ad\u00a7\3\2\2\2\u00ad\u00a8\3\2"+
		"\2\2\u00ad\u00a9\3\2\2\2\u00ad\u00aa\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad"+
		"\u00ac\3\2\2\2\u00ae\33\3\2\2\2\u00af\u00b0\7\62\2\2\u00b0\u00b1\7\35"+
		"\2\2\u00b1\u00b2\5,\27\2\u00b2\u00b3\7\36\2\2\u00b3\u00b6\5\32\16\2\u00b4"+
		"\u00b5\7\63\2\2\u00b5\u00b7\5\32\16\2\u00b6\u00b4\3\2\2\2\u00b6\u00b7"+
		"\3\2\2\2\u00b7\35\3\2\2\2\u00b8\u00b9\7\65\2\2\u00b9\u00ba\7\35\2\2\u00ba"+
		"\u00bb\5,\27\2\u00bb\u00bc\7\36\2\2\u00bc\u00bd\5\32\16\2\u00bd\37\3\2"+
		"\2\2\u00be\u00bf\7\64\2\2\u00bf\u00c1\7\35\2\2\u00c0\u00c2\5\"\22\2\u00c1"+
		"\u00c0\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4\3\2\2\2\u00c3\u00c5\5,"+
		"\27\2\u00c4\u00c3\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"\u00c8\7\37\2\2\u00c7\u00c9\5,\27\2\u00c8\u00c7\3\2\2\2\u00c8\u00c9\3"+
		"\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\7\36\2\2\u00cb\u00cc\5\32\16\2"+
		"\u00cc!\3\2\2\2\u00cd\u00d0\5\20\t\2\u00ce\u00d0\5*\26\2\u00cf\u00cd\3"+
		"\2\2\2\u00cf\u00ce\3\2\2\2\u00d0#\3\2\2\2\u00d1\u00d2\7\66\2\2\u00d2\u00d3"+
		"\7\37\2\2\u00d3%\3\2\2\2\u00d4\u00d5\7\67\2\2\u00d5\u00d6\7\37\2\2\u00d6"+
		"\'\3\2\2\2\u00d7\u00d9\78\2\2\u00d8\u00da\5,\27\2\u00d9\u00d8\3\2\2\2"+
		"\u00d9\u00da\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dc\7\37\2\2\u00dc)\3"+
		"\2\2\2\u00dd\u00df\5,\27\2\u00de\u00dd\3\2\2\2\u00de\u00df\3\2\2\2\u00df"+
		"\u00e0\3\2\2\2\u00e0\u00e1\7\37\2\2\u00e1+\3\2\2\2\u00e2\u00e3\b\27\1"+
		"\2\u00e3\u00e4\7\35\2\2\u00e4\u00e5\5,\27\2\u00e5\u00e6\7\36\2\2\u00e6"+
		"\u00ee\3\2\2\2\u00e7\u00e8\5\60\31\2\u00e8\u00e9\5,\27\20\u00e9\u00ee"+
		"\3\2\2\2\u00ea\u00eb\5\64\33\2\u00eb\u00ec\5,\27\16\u00ec\u00ee\3\2\2"+
		"\2\u00ed\u00e2\3\2\2\2\u00ed\u00e7\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ee\u0127"+
		"\3\2\2\2\u00ef\u00f0\f\r\2\2\u00f0\u00f1\5\66\34\2\u00f1\u00f2\5,\27\16"+
		"\u00f2\u0126\3\2\2\2\u00f3\u00f4\f\f\2\2\u00f4\u00f5\58\35\2\u00f5\u00f6"+
		"\5,\27\r\u00f6\u0126\3\2\2\2\u00f7\u00f8\f\13\2\2\u00f8\u00f9\5:\36\2"+
		"\u00f9\u00fa\5,\27\f\u00fa\u0126\3\2\2\2\u00fb\u00fc\f\n\2\2\u00fc\u00fd"+
		"\5<\37\2\u00fd\u00fe\5,\27\13\u00fe\u0126\3\2\2\2\u00ff\u0100\f\t\2\2"+
		"\u0100\u0101\5> \2\u0101\u0102\5,\27\n\u0102\u0126\3\2\2\2\u0103\u0104"+
		"\f\b\2\2\u0104\u0105\7\23\2\2\u0105\u0126\5,\27\t\u0106\u0107\f\7\2\2"+
		"\u0107\u0108\7\25\2\2\u0108\u0126\5,\27\b\u0109\u010a\f\6\2\2\u010a\u010b"+
		"\7\24\2\2\u010b\u0126\5,\27\7\u010c\u010d\f\5\2\2\u010d\u010e\7\16\2\2"+
		"\u010e\u0126\5,\27\6\u010f\u0110\f\4\2\2\u0110\u0111\7\17\2\2\u0111\u0126"+
		"\5,\27\5\u0112\u0113\f\3\2\2\u0113\u0114\7\27\2\2\u0114\u0126\5,\27\3"+
		"\u0115\u0116\f\23\2\2\u0116\u0117\7\33\2\2\u0117\u0118\5,\27\2\u0118\u0119"+
		"\7\34\2\2\u0119\u0126\3\2\2\2\u011a\u011b\f\22\2\2\u011b\u011c\7\32\2"+
		"\2\u011c\u0126\79\2\2\u011d\u011e\f\21\2\2\u011e\u0120\7\35\2\2\u011f"+
		"\u0121\5.\30\2\u0120\u011f\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0122\3\2"+
		"\2\2\u0122\u0126\7\36\2\2\u0123\u0124\f\17\2\2\u0124\u0126\5\62\32\2\u0125"+
		"\u00ef\3\2\2\2\u0125\u00f3\3\2\2\2\u0125\u00f7\3\2\2\2\u0125\u00fb\3\2"+
		"\2\2\u0125\u00ff\3\2\2\2\u0125\u0103\3\2\2\2\u0125\u0106\3\2\2\2\u0125"+
		"\u0109\3\2\2\2\u0125\u010c\3\2\2\2\u0125\u010f\3\2\2\2\u0125\u0112\3\2"+
		"\2\2\u0125\u0115\3\2\2\2\u0125\u011a\3\2\2\2\u0125\u011d\3\2\2\2\u0125"+
		"\u0123\3\2\2\2\u0126\u0129\3\2\2\2\u0127\u0125\3\2\2\2\u0127\u0128\3\2"+
		"\2\2\u0128-\3\2\2\2\u0129\u0127\3\2\2\2\u012a\u012f\5,\27\2\u012b\u012c"+
		"\7 \2\2\u012c\u012e\5,\27\2\u012d\u012b\3\2\2\2\u012e\u0131\3\2\2\2\u012f"+
		"\u012d\3\2\2\2\u012f\u0130\3\2\2\2\u0130/\3\2\2\2\u0131\u012f\3\2\2\2"+
		"\u0132\u0133\t\3\2\2\u0133\61\3\2\2\2\u0134\u0135\t\3\2\2\u0135\63\3\2"+
		"\2\2\u0136\u0137\t\4\2\2\u0137\65\3\2\2\2\u0138\u0139\t\5\2\2\u0139\67"+
		"\3\2\2\2\u013a\u013b\t\6\2\2\u013b9\3\2\2\2\u013c\u013d\t\7\2\2\u013d"+
		";\3\2\2\2\u013e\u013f\t\b\2\2\u013f=\3\2\2\2\u0140\u0141\t\t\2\2\u0141"+
		"?\3\2\2\2\34CENWbgirt\u0087\u008f\u0099\u009e\u00ad\u00b6\u00c1\u00c4"+
		"\u00c8\u00cf\u00d9\u00de\u00ed\u0120\u0125\u0127\u012f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}