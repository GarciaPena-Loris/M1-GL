// Generated from f:/DossierLoris/MonProfil/MesDocumentEcole/Fac/M1/M1-GL/Compilation/TP analyse/Correction/Useless/pp.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ppParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, INTEGER=34, BOOLEAN=35, ID=36, WS=37;
	public static final int
		RULE_p = 0, RULE_d = 1, RULE_i = 2, RULE_e = 3, RULE_phi = 4, RULE_bop = 5, 
		RULE_uop = 6, RULE_k = 7, RULE_type = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"p", "d", "i", "e", "phi", "bop", "uop", "k", "type"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'var'", "':'", "'('", "')'", "':='", "'['", "']'", "'if'", "'then'", 
			"'else'", "'while'", "'do'", "'skip'", "';'", "'new array of'", "'read'", 
			"'write'", "'+'", "'-'", "'*'", "'/'", "'and'", "'or'", "'<'", "'<='", 
			"'='", "'!='", "'>='", "'>'", "'not'", "'integer'", "'boolean'", "'array of'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "INTEGER", 
			"BOOLEAN", "ID", "WS"
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
	public String getGrammarFileName() { return "pp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ppParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PContext extends ParserRuleContext {
		public IContext i() {
			return getRuleContext(IContext.class,0);
		}
		public List<DContext> d() {
			return getRuleContexts(DContext.class);
		}
		public DContext d(int i) {
			return getRuleContext(DContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(ppParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ppParser.ID, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public PContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_p; }
	}

	public final PContext p() throws RecognitionException {
		PContext _localctx = new PContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(18);
				match(T__0);
				setState(22); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(19);
						match(ID);
						setState(20);
						match(T__1);
						setState(21);
						type();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(24); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
			}

			setState(31);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(28);
					d();
					}
					} 
				}
				setState(33);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(34);
			i(0);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ppParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ppParser.ID, i);
		}
		public IContext i() {
			return getRuleContext(IContext.class,0);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public DContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_d; }
	}

	public final DContext d() throws RecognitionException {
		DContext _localctx = new DContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_d);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			match(ID);
			setState(37);
			match(T__2);
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(38);
				match(ID);
				setState(39);
				match(T__1);
				setState(40);
				type();
				}
				}
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(46);
			match(T__3);
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(47);
				match(T__1);
				setState(48);
				type();
				}
			}

			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(51);
				match(T__0);
				setState(55); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(52);
						match(ID);
						setState(53);
						match(T__1);
						setState(54);
						type();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(57); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
			}

			setState(61);
			i(0);
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

	@SuppressWarnings("CheckReturnValue")
	public static class IContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ppParser.ID, 0); }
		public List<EContext> e() {
			return getRuleContexts(EContext.class);
		}
		public EContext e(int i) {
			return getRuleContext(EContext.class,i);
		}
		public List<IContext> i() {
			return getRuleContexts(IContext.class);
		}
		public IContext i(int i) {
			return getRuleContext(IContext.class,i);
		}
		public PhiContext phi() {
			return getRuleContext(PhiContext.class,0);
		}
		public IContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_i; }
	}

	public final IContext i() throws RecognitionException {
		return i(0);
	}

	private IContext i(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		IContext _localctx = new IContext(_ctx, _parentState);
		IContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_i, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(64);
				match(ID);
				setState(65);
				match(T__4);
				setState(66);
				e(0);
				}
				break;
			case 2:
				{
				setState(67);
				e(0);
				setState(68);
				match(T__5);
				setState(69);
				e(0);
				setState(70);
				match(T__6);
				setState(71);
				match(T__4);
				setState(72);
				e(0);
				}
				break;
			case 3:
				{
				setState(74);
				match(T__7);
				setState(75);
				e(0);
				setState(76);
				match(T__8);
				setState(77);
				i(0);
				setState(78);
				match(T__9);
				setState(79);
				i(5);
				}
				break;
			case 4:
				{
				setState(81);
				match(T__10);
				setState(82);
				e(0);
				setState(83);
				match(T__11);
				setState(84);
				i(4);
				}
				break;
			case 5:
				{
				setState(86);
				phi();
				setState(87);
				match(T__2);
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 121333579776L) != 0)) {
					{
					{
					setState(88);
					e(0);
					}
					}
					setState(93);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(94);
				match(T__3);
				}
				break;
			case 6:
				{
				setState(96);
				match(T__12);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(104);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new IContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_i);
					setState(99);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(100);
					match(T__13);
					setState(101);
					i(2);
					}
					} 
				}
				setState(106);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class EContext extends ParserRuleContext {
		public KContext k() {
			return getRuleContext(KContext.class,0);
		}
		public TerminalNode ID() { return getToken(ppParser.ID, 0); }
		public UopContext uop() {
			return getRuleContext(UopContext.class,0);
		}
		public List<EContext> e() {
			return getRuleContexts(EContext.class);
		}
		public EContext e(int i) {
			return getRuleContext(EContext.class,i);
		}
		public PhiContext phi() {
			return getRuleContext(PhiContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public BopContext bop() {
			return getRuleContext(BopContext.class,0);
		}
		public EContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e; }
	}

	public final EContext e() throws RecognitionException {
		return e(0);
	}

	private EContext e(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		EContext _localctx = new EContext(_ctx, _parentState);
		EContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_e, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(108);
				k();
				}
				break;
			case 2:
				{
				setState(109);
				match(ID);
				}
				break;
			case 3:
				{
				setState(110);
				uop();
				setState(111);
				e(5);
				}
				break;
			case 4:
				{
				setState(113);
				phi();
				setState(114);
				match(T__2);
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 121333579776L) != 0)) {
					{
					{
					setState(115);
					e(0);
					}
					}
					setState(120);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(121);
				match(T__3);
				}
				break;
			case 5:
				{
				setState(123);
				match(T__14);
				setState(124);
				type();
				setState(125);
				match(T__5);
				setState(126);
				e(0);
				setState(127);
				match(T__6);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(142);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(140);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new EContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_e);
						setState(131);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(132);
						bop();
						setState(133);
						e(5);
						}
						break;
					case 2:
						{
						_localctx = new EContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_e);
						setState(135);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(136);
						match(T__5);
						setState(137);
						e(0);
						setState(138);
						match(T__6);
						}
						break;
					}
					} 
				}
				setState(144);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class PhiContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ppParser.ID, 0); }
		public PhiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_phi; }
	}

	public final PhiContext phi() throws RecognitionException {
		PhiContext _localctx = new PhiContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_phi);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 68719673344L) != 0)) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class BopContext extends ParserRuleContext {
		public BopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bop; }
	}

	public final BopContext bop() throws RecognitionException {
		BopContext _localctx = new BopContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_bop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1073479680L) != 0)) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class UopContext extends ParserRuleContext {
		public UopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_uop; }
	}

	public final UopContext uop() throws RecognitionException {
		UopContext _localctx = new UopContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_uop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			_la = _input.LA(1);
			if ( !(_la==T__18 || _la==T__29) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class KContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(ppParser.INTEGER, 0); }
		public TerminalNode BOOLEAN() { return getToken(ppParser.BOOLEAN, 0); }
		public KContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_k; }
	}

	public final KContext k() throws RecognitionException {
		KContext _localctx = new KContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_k);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			_la = _input.LA(1);
			if ( !(_la==INTEGER || _la==BOOLEAN) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type);
		try {
			setState(157);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__30:
				enterOuterAlt(_localctx, 1);
				{
				setState(153);
				match(T__30);
				}
				break;
			case T__31:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				match(T__31);
				}
				break;
			case T__32:
				enterOuterAlt(_localctx, 3);
				{
				setState(155);
				match(T__32);
				setState(156);
				type();
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return i_sempred((IContext)_localctx, predIndex);
		case 3:
			return e_sempred((EContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean i_sempred(IContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean e_sempred(EContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001%\u00a0\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0004\u0000"+
		"\u0017\b\u0000\u000b\u0000\f\u0000\u0018\u0003\u0000\u001b\b\u0000\u0001"+
		"\u0000\u0005\u0000\u001e\b\u0000\n\u0000\f\u0000!\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0005\u0001*\b\u0001\n\u0001\f\u0001-\t\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0003\u00012\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0004\u00018\b\u0001\u000b\u0001\f\u00019\u0003\u0001<\b"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002Z\b"+
		"\u0002\n\u0002\f\u0002]\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003"+
		"\u0002b\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002g\b\u0002"+
		"\n\u0002\f\u0002j\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003"+
		"u\b\u0003\n\u0003\f\u0003x\t\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"\u0082\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003\u008d\b\u0003"+
		"\n\u0003\f\u0003\u0090\t\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0003\b\u009e\b\b\u0001\b\u0000\u0002\u0004\u0006\t\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0000\u0004\u0002\u0000\u0010\u0011"+
		"$$\u0001\u0000\u0012\u001d\u0002\u0000\u0013\u0013\u001e\u001e\u0001\u0000"+
		"\"#\u00ad\u0000\u001a\u0001\u0000\u0000\u0000\u0002$\u0001\u0000\u0000"+
		"\u0000\u0004a\u0001\u0000\u0000\u0000\u0006\u0081\u0001\u0000\u0000\u0000"+
		"\b\u0091\u0001\u0000\u0000\u0000\n\u0093\u0001\u0000\u0000\u0000\f\u0095"+
		"\u0001\u0000\u0000\u0000\u000e\u0097\u0001\u0000\u0000\u0000\u0010\u009d"+
		"\u0001\u0000\u0000\u0000\u0012\u0016\u0005\u0001\u0000\u0000\u0013\u0014"+
		"\u0005$\u0000\u0000\u0014\u0015\u0005\u0002\u0000\u0000\u0015\u0017\u0003"+
		"\u0010\b\u0000\u0016\u0013\u0001\u0000\u0000\u0000\u0017\u0018\u0001\u0000"+
		"\u0000\u0000\u0018\u0016\u0001\u0000\u0000\u0000\u0018\u0019\u0001\u0000"+
		"\u0000\u0000\u0019\u001b\u0001\u0000\u0000\u0000\u001a\u0012\u0001\u0000"+
		"\u0000\u0000\u001a\u001b\u0001\u0000\u0000\u0000\u001b\u001f\u0001\u0000"+
		"\u0000\u0000\u001c\u001e\u0003\u0002\u0001\u0000\u001d\u001c\u0001\u0000"+
		"\u0000\u0000\u001e!\u0001\u0000\u0000\u0000\u001f\u001d\u0001\u0000\u0000"+
		"\u0000\u001f \u0001\u0000\u0000\u0000 \"\u0001\u0000\u0000\u0000!\u001f"+
		"\u0001\u0000\u0000\u0000\"#\u0003\u0004\u0002\u0000#\u0001\u0001\u0000"+
		"\u0000\u0000$%\u0005$\u0000\u0000%+\u0005\u0003\u0000\u0000&\'\u0005$"+
		"\u0000\u0000\'(\u0005\u0002\u0000\u0000(*\u0003\u0010\b\u0000)&\u0001"+
		"\u0000\u0000\u0000*-\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000"+
		"+,\u0001\u0000\u0000\u0000,.\u0001\u0000\u0000\u0000-+\u0001\u0000\u0000"+
		"\u0000.1\u0005\u0004\u0000\u0000/0\u0005\u0002\u0000\u000002\u0003\u0010"+
		"\b\u00001/\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u00002;\u0001\u0000"+
		"\u0000\u000037\u0005\u0001\u0000\u000045\u0005$\u0000\u000056\u0005\u0002"+
		"\u0000\u000068\u0003\u0010\b\u000074\u0001\u0000\u0000\u000089\u0001\u0000"+
		"\u0000\u000097\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:<\u0001"+
		"\u0000\u0000\u0000;3\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000"+
		"<=\u0001\u0000\u0000\u0000=>\u0003\u0004\u0002\u0000>\u0003\u0001\u0000"+
		"\u0000\u0000?@\u0006\u0002\uffff\uffff\u0000@A\u0005$\u0000\u0000AB\u0005"+
		"\u0005\u0000\u0000Bb\u0003\u0006\u0003\u0000CD\u0003\u0006\u0003\u0000"+
		"DE\u0005\u0006\u0000\u0000EF\u0003\u0006\u0003\u0000FG\u0005\u0007\u0000"+
		"\u0000GH\u0005\u0005\u0000\u0000HI\u0003\u0006\u0003\u0000Ib\u0001\u0000"+
		"\u0000\u0000JK\u0005\b\u0000\u0000KL\u0003\u0006\u0003\u0000LM\u0005\t"+
		"\u0000\u0000MN\u0003\u0004\u0002\u0000NO\u0005\n\u0000\u0000OP\u0003\u0004"+
		"\u0002\u0005Pb\u0001\u0000\u0000\u0000QR\u0005\u000b\u0000\u0000RS\u0003"+
		"\u0006\u0003\u0000ST\u0005\f\u0000\u0000TU\u0003\u0004\u0002\u0004Ub\u0001"+
		"\u0000\u0000\u0000VW\u0003\b\u0004\u0000W[\u0005\u0003\u0000\u0000XZ\u0003"+
		"\u0006\u0003\u0000YX\u0001\u0000\u0000\u0000Z]\u0001\u0000\u0000\u0000"+
		"[Y\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\^\u0001\u0000\u0000"+
		"\u0000][\u0001\u0000\u0000\u0000^_\u0005\u0004\u0000\u0000_b\u0001\u0000"+
		"\u0000\u0000`b\u0005\r\u0000\u0000a?\u0001\u0000\u0000\u0000aC\u0001\u0000"+
		"\u0000\u0000aJ\u0001\u0000\u0000\u0000aQ\u0001\u0000\u0000\u0000aV\u0001"+
		"\u0000\u0000\u0000a`\u0001\u0000\u0000\u0000bh\u0001\u0000\u0000\u0000"+
		"cd\n\u0001\u0000\u0000de\u0005\u000e\u0000\u0000eg\u0003\u0004\u0002\u0002"+
		"fc\u0001\u0000\u0000\u0000gj\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000"+
		"\u0000hi\u0001\u0000\u0000\u0000i\u0005\u0001\u0000\u0000\u0000jh\u0001"+
		"\u0000\u0000\u0000kl\u0006\u0003\uffff\uffff\u0000l\u0082\u0003\u000e"+
		"\u0007\u0000m\u0082\u0005$\u0000\u0000no\u0003\f\u0006\u0000op\u0003\u0006"+
		"\u0003\u0005p\u0082\u0001\u0000\u0000\u0000qr\u0003\b\u0004\u0000rv\u0005"+
		"\u0003\u0000\u0000su\u0003\u0006\u0003\u0000ts\u0001\u0000\u0000\u0000"+
		"ux\u0001\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000"+
		"\u0000wy\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000yz\u0005\u0004"+
		"\u0000\u0000z\u0082\u0001\u0000\u0000\u0000{|\u0005\u000f\u0000\u0000"+
		"|}\u0003\u0010\b\u0000}~\u0005\u0006\u0000\u0000~\u007f\u0003\u0006\u0003"+
		"\u0000\u007f\u0080\u0005\u0007\u0000\u0000\u0080\u0082\u0001\u0000\u0000"+
		"\u0000\u0081k\u0001\u0000\u0000\u0000\u0081m\u0001\u0000\u0000\u0000\u0081"+
		"n\u0001\u0000\u0000\u0000\u0081q\u0001\u0000\u0000\u0000\u0081{\u0001"+
		"\u0000\u0000\u0000\u0082\u008e\u0001\u0000\u0000\u0000\u0083\u0084\n\u0004"+
		"\u0000\u0000\u0084\u0085\u0003\n\u0005\u0000\u0085\u0086\u0003\u0006\u0003"+
		"\u0005\u0086\u008d\u0001\u0000\u0000\u0000\u0087\u0088\n\u0002\u0000\u0000"+
		"\u0088\u0089\u0005\u0006\u0000\u0000\u0089\u008a\u0003\u0006\u0003\u0000"+
		"\u008a\u008b\u0005\u0007\u0000\u0000\u008b\u008d\u0001\u0000\u0000\u0000"+
		"\u008c\u0083\u0001\u0000\u0000\u0000\u008c\u0087\u0001\u0000\u0000\u0000"+
		"\u008d\u0090\u0001\u0000\u0000\u0000\u008e\u008c\u0001\u0000\u0000\u0000"+
		"\u008e\u008f\u0001\u0000\u0000\u0000\u008f\u0007\u0001\u0000\u0000\u0000"+
		"\u0090\u008e\u0001\u0000\u0000\u0000\u0091\u0092\u0007\u0000\u0000\u0000"+
		"\u0092\t\u0001\u0000\u0000\u0000\u0093\u0094\u0007\u0001\u0000\u0000\u0094"+
		"\u000b\u0001\u0000\u0000\u0000\u0095\u0096\u0007\u0002\u0000\u0000\u0096"+
		"\r\u0001\u0000\u0000\u0000\u0097\u0098\u0007\u0003\u0000\u0000\u0098\u000f"+
		"\u0001\u0000\u0000\u0000\u0099\u009e\u0005\u001f\u0000\u0000\u009a\u009e"+
		"\u0005 \u0000\u0000\u009b\u009c\u0005!\u0000\u0000\u009c\u009e\u0003\u0010"+
		"\b\u0000\u009d\u0099\u0001\u0000\u0000\u0000\u009d\u009a\u0001\u0000\u0000"+
		"\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009e\u0011\u0001\u0000\u0000"+
		"\u0000\u000f\u0018\u001a\u001f+19;[ahv\u0081\u008c\u008e\u009d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}