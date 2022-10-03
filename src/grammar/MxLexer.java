// Generated from /home/qweryy/Mx-Compiler/src/grammar/MxLexer.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Add", "Sub", "Mul", "Div", "Mod", "GThan", "LThan", "GEqual", "LEqual", 
			"NEqual", "EEqual", "LAnd", "LOr", "LNot", "RShift", "LShift", "BAnd", 
			"BOr", "BXor", "BNot", "Assign", "SelfAdd", "SelfSub", "Member", "LBracket", 
			"RBracket", "LParen", "RParen", "Semi", "Comma", "LBrace", "RBrace", 
			"Quote", "IntConst", "PChar", "StringConst", "Comment", "Void", "Bool", 
			"Int", "String", "New", "Class", "Null", "Ture", "False", "This", "If", 
			"Else", "For", "While", "Break", "Continue", "Return", "Identifier", 
			"WhiteSpace"
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


	public MxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MxLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2:\u0150\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27"+
		"\3\27\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35"+
		"\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\7#\u00c2\n#\f#\16#\u00c5"+
		"\13#\3#\5#\u00c8\n#\3$\3$\3$\3$\3$\3$\3$\5$\u00d1\n$\3%\3%\7%\u00d5\n"+
		"%\f%\16%\u00d8\13%\3%\3%\3&\3&\3&\3&\7&\u00e0\n&\f&\16&\u00e3\13&\3&\3"+
		"&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3*"+
		"\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3/\3/\3/"+
		"\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\62"+
		"\3\62\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67"+
		"\3\67\3\67\3\67\3\67\3\67\38\38\78\u0145\n8\f8\168\u0148\138\39\69\u014b"+
		"\n9\r9\169\u014c\39\39\2\2:\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a"+
		"\62c\63e\64g\65i\66k\67m8o9q:\3\2\t\3\2\63;\3\2\62;\3\2\"\u0080\4\2\f"+
		"\f\17\17\4\2C\\c|\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\2\u0158\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3"+
		"\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2"+
		"\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2"+
		"W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3"+
		"\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2"+
		"\2\2q\3\2\2\2\3s\3\2\2\2\5u\3\2\2\2\7w\3\2\2\2\ty\3\2\2\2\13{\3\2\2\2"+
		"\r}\3\2\2\2\17\177\3\2\2\2\21\u0081\3\2\2\2\23\u0084\3\2\2\2\25\u0087"+
		"\3\2\2\2\27\u008a\3\2\2\2\31\u008d\3\2\2\2\33\u0090\3\2\2\2\35\u0093\3"+
		"\2\2\2\37\u0095\3\2\2\2!\u0098\3\2\2\2#\u009b\3\2\2\2%\u009d\3\2\2\2\'"+
		"\u009f\3\2\2\2)\u00a1\3\2\2\2+\u00a3\3\2\2\2-\u00a5\3\2\2\2/\u00a8\3\2"+
		"\2\2\61\u00ab\3\2\2\2\63\u00ad\3\2\2\2\65\u00af\3\2\2\2\67\u00b1\3\2\2"+
		"\29\u00b3\3\2\2\2;\u00b5\3\2\2\2=\u00b7\3\2\2\2?\u00b9\3\2\2\2A\u00bb"+
		"\3\2\2\2C\u00bd\3\2\2\2E\u00c7\3\2\2\2G\u00d0\3\2\2\2I\u00d2\3\2\2\2K"+
		"\u00db\3\2\2\2M\u00e6\3\2\2\2O\u00eb\3\2\2\2Q\u00f0\3\2\2\2S\u00f4\3\2"+
		"\2\2U\u00fb\3\2\2\2W\u00ff\3\2\2\2Y\u0105\3\2\2\2[\u010a\3\2\2\2]\u010f"+
		"\3\2\2\2_\u0115\3\2\2\2a\u011a\3\2\2\2c\u011d\3\2\2\2e\u0122\3\2\2\2g"+
		"\u0126\3\2\2\2i\u012c\3\2\2\2k\u0132\3\2\2\2m\u013b\3\2\2\2o\u0142\3\2"+
		"\2\2q\u014a\3\2\2\2st\7-\2\2t\4\3\2\2\2uv\7/\2\2v\6\3\2\2\2wx\7,\2\2x"+
		"\b\3\2\2\2yz\7\61\2\2z\n\3\2\2\2{|\7\'\2\2|\f\3\2\2\2}~\7@\2\2~\16\3\2"+
		"\2\2\177\u0080\7>\2\2\u0080\20\3\2\2\2\u0081\u0082\7@\2\2\u0082\u0083"+
		"\7?\2\2\u0083\22\3\2\2\2\u0084\u0085\7>\2\2\u0085\u0086\7?\2\2\u0086\24"+
		"\3\2\2\2\u0087\u0088\7#\2\2\u0088\u0089\7?\2\2\u0089\26\3\2\2\2\u008a"+
		"\u008b\7?\2\2\u008b\u008c\7?\2\2\u008c\30\3\2\2\2\u008d\u008e\7(\2\2\u008e"+
		"\u008f\7(\2\2\u008f\32\3\2\2\2\u0090\u0091\7~\2\2\u0091\u0092\7~\2\2\u0092"+
		"\34\3\2\2\2\u0093\u0094\7#\2\2\u0094\36\3\2\2\2\u0095\u0096\7@\2\2\u0096"+
		"\u0097\7@\2\2\u0097 \3\2\2\2\u0098\u0099\7>\2\2\u0099\u009a\7>\2\2\u009a"+
		"\"\3\2\2\2\u009b\u009c\7(\2\2\u009c$\3\2\2\2\u009d\u009e\7~\2\2\u009e"+
		"&\3\2\2\2\u009f\u00a0\7`\2\2\u00a0(\3\2\2\2\u00a1\u00a2\7\u0080\2\2\u00a2"+
		"*\3\2\2\2\u00a3\u00a4\7?\2\2\u00a4,\3\2\2\2\u00a5\u00a6\7-\2\2\u00a6\u00a7"+
		"\7-\2\2\u00a7.\3\2\2\2\u00a8\u00a9\7/\2\2\u00a9\u00aa\7/\2\2\u00aa\60"+
		"\3\2\2\2\u00ab\u00ac\7\60\2\2\u00ac\62\3\2\2\2\u00ad\u00ae\7]\2\2\u00ae"+
		"\64\3\2\2\2\u00af\u00b0\7_\2\2\u00b0\66\3\2\2\2\u00b1\u00b2\7*\2\2\u00b2"+
		"8\3\2\2\2\u00b3\u00b4\7+\2\2\u00b4:\3\2\2\2\u00b5\u00b6\7=\2\2\u00b6<"+
		"\3\2\2\2\u00b7\u00b8\7.\2\2\u00b8>\3\2\2\2\u00b9\u00ba\7}\2\2\u00ba@\3"+
		"\2\2\2\u00bb\u00bc\7\177\2\2\u00bcB\3\2\2\2\u00bd\u00be\7$\2\2\u00beD"+
		"\3\2\2\2\u00bf\u00c3\t\2\2\2\u00c0\u00c2\t\3\2\2\u00c1\u00c0\3\2\2\2\u00c2"+
		"\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c8\3\2"+
		"\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00c8\7\62\2\2\u00c7\u00bf\3\2\2\2\u00c7"+
		"\u00c6\3\2\2\2\u00c8F\3\2\2\2\u00c9\u00d1\t\4\2\2\u00ca\u00cb\7^\2\2\u00cb"+
		"\u00d1\7p\2\2\u00cc\u00cd\7^\2\2\u00cd\u00d1\7^\2\2\u00ce\u00cf\7^\2\2"+
		"\u00cf\u00d1\7$\2\2\u00d0\u00c9\3\2\2\2\u00d0\u00ca\3\2\2\2\u00d0\u00cc"+
		"\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1H\3\2\2\2\u00d2\u00d6\5C\"\2\u00d3\u00d5"+
		"\5G$\2\u00d4\u00d3\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6"+
		"\u00d7\3\2\2\2\u00d7\u00d9\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9\u00da\5C"+
		"\"\2\u00daJ\3\2\2\2\u00db\u00dc\7\61\2\2\u00dc\u00dd\7\61\2\2\u00dd\u00e1"+
		"\3\2\2\2\u00de\u00e0\n\5\2\2\u00df\u00de\3\2\2\2\u00e0\u00e3\3\2\2\2\u00e1"+
		"\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e4\3\2\2\2\u00e3\u00e1\3\2"+
		"\2\2\u00e4\u00e5\b&\2\2\u00e5L\3\2\2\2\u00e6\u00e7\7x\2\2\u00e7\u00e8"+
		"\7q\2\2\u00e8\u00e9\7k\2\2\u00e9\u00ea\7f\2\2\u00eaN\3\2\2\2\u00eb\u00ec"+
		"\7d\2\2\u00ec\u00ed\7q\2\2\u00ed\u00ee\7q\2\2\u00ee\u00ef\7n\2\2\u00ef"+
		"P\3\2\2\2\u00f0\u00f1\7k\2\2\u00f1\u00f2\7p\2\2\u00f2\u00f3\7v\2\2\u00f3"+
		"R\3\2\2\2\u00f4\u00f5\7u\2\2\u00f5\u00f6\7v\2\2\u00f6\u00f7\7t\2\2\u00f7"+
		"\u00f8\7k\2\2\u00f8\u00f9\7p\2\2\u00f9\u00fa\7i\2\2\u00faT\3\2\2\2\u00fb"+
		"\u00fc\7p\2\2\u00fc\u00fd\7g\2\2\u00fd\u00fe\7y\2\2\u00feV\3\2\2\2\u00ff"+
		"\u0100\7e\2\2\u0100\u0101\7n\2\2\u0101\u0102\7c\2\2\u0102\u0103\7u\2\2"+
		"\u0103\u0104\7u\2\2\u0104X\3\2\2\2\u0105\u0106\7p\2\2\u0106\u0107\7w\2"+
		"\2\u0107\u0108\7n\2\2\u0108\u0109\7n\2\2\u0109Z\3\2\2\2\u010a\u010b\7"+
		"v\2\2\u010b\u010c\7t\2\2\u010c\u010d\7w\2\2\u010d\u010e\7g\2\2\u010e\\"+
		"\3\2\2\2\u010f\u0110\7h\2\2\u0110\u0111\7c\2\2\u0111\u0112\7n\2\2\u0112"+
		"\u0113\7u\2\2\u0113\u0114\7g\2\2\u0114^\3\2\2\2\u0115\u0116\7v\2\2\u0116"+
		"\u0117\7j\2\2\u0117\u0118\7k\2\2\u0118\u0119\7u\2\2\u0119`\3\2\2\2\u011a"+
		"\u011b\7k\2\2\u011b\u011c\7h\2\2\u011cb\3\2\2\2\u011d\u011e\7g\2\2\u011e"+
		"\u011f\7n\2\2\u011f\u0120\7u\2\2\u0120\u0121\7g\2\2\u0121d\3\2\2\2\u0122"+
		"\u0123\7h\2\2\u0123\u0124\7q\2\2\u0124\u0125\7t\2\2\u0125f\3\2\2\2\u0126"+
		"\u0127\7y\2\2\u0127\u0128\7j\2\2\u0128\u0129\7k\2\2\u0129\u012a\7n\2\2"+
		"\u012a\u012b\7g\2\2\u012bh\3\2\2\2\u012c\u012d\7d\2\2\u012d\u012e\7t\2"+
		"\2\u012e\u012f\7g\2\2\u012f\u0130\7c\2\2\u0130\u0131\7m\2\2\u0131j\3\2"+
		"\2\2\u0132\u0133\7e\2\2\u0133\u0134\7q\2\2\u0134\u0135\7p\2\2\u0135\u0136"+
		"\7v\2\2\u0136\u0137\7k\2\2\u0137\u0138\7p\2\2\u0138\u0139\7w\2\2\u0139"+
		"\u013a\7g\2\2\u013al\3\2\2\2\u013b\u013c\7t\2\2\u013c\u013d\7g\2\2\u013d"+
		"\u013e\7v\2\2\u013e\u013f\7w\2\2\u013f\u0140\7t\2\2\u0140\u0141\7p\2\2"+
		"\u0141n\3\2\2\2\u0142\u0146\t\6\2\2\u0143\u0145\t\7\2\2\u0144\u0143\3"+
		"\2\2\2\u0145\u0148\3\2\2\2\u0146\u0144\3\2\2\2\u0146\u0147\3\2\2\2\u0147"+
		"p\3\2\2\2\u0148\u0146\3\2\2\2\u0149\u014b\t\b\2\2\u014a\u0149\3\2\2\2"+
		"\u014b\u014c\3\2\2\2\u014c\u014a\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014e"+
		"\3\2\2\2\u014e\u014f\b9\2\2\u014fr\3\2\2\2\n\2\u00c3\u00c7\u00d0\u00d6"+
		"\u00e1\u0146\u014c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}