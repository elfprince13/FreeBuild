// Generated from /Users/thomas/FreeBuild/FreeBuildJ/net/cemetech/sfgp/ldraw/LDrawParser.g4 by ANTLR 4.1
package net.cemetech.sfgp.ldraw;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LDrawParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ORIENTATION=34, FOUR=2, CLIPPING=35, EDGE=21, NO=32, RUBBER=26, STEP=12, 
		SAVE=17, PEARLESCENT=25, FLOAT=9, INT=8, ONE=5, HEXINT=7, TWO=4, VALUE=20, 
		LUMINANCE=23, FILE=30, WRITE=14, PRINT=15, ALPHA=22, ZERO=6, CLEAR=16, 
		CODE=19, METAL=28, GARBAGE=39, MATTE_METALLIC=27, EOL=11, WS=10, PAUSE=13, 
		BFC=31, FIVE=1, CHROME=24, INVERTNEXT=36, CERTIFICATION=33, IDENT=37, 
		MATERIAL=29, COLOUR=18, THREE=3, STRING=38;
	public static final String[] tokenNames = {
		"<INVALID>", "'5'", "'4'", "'3'", "'2'", "'1'", "'0'", "HEXINT", "INT", 
		"FLOAT", "WS", "EOL", "'STEP'", "'PAUSE'", "WRITE", "'PRINT'", "'CLEAR'", 
		"'SAVE'", "COLOUR", "'CODE'", "'VALUE'", "'EDGE'", "'ALPHA'", "'LUMINANCE'", 
		"'CHROME'", "'PEARLESCENT'", "'RUBBER'", "'MATTE_METALLIC'", "'METAL'", 
		"'MATERIAL'", "'FILE'", "'BFC'", "'NO'", "'CERTIFY'", "ORIENTATION", "'CLIP'", 
		"'INVERTNEXT'", "IDENT", "STRING", "GARBAGE"
	};
	public static final int
		RULE_parsedModel = 0, RULE_mpdModel = 1, RULE_mpdEmbeddedModel = 2, RULE_mpdDiscard = 3, 
		RULE_ldrdatModel = 4, RULE_ldrawLines = 5, RULE_ldrawStmt = 6, RULE_decInt = 7, 
		RULE_number = 8, RULE_stepLine = 9, RULE_pauseLine = 10, RULE_writeLine = 11, 
		RULE_clearLine = 12, RULE_colorNum = 13, RULE_colorLine = 14, RULE_mpdFileLine = 15, 
		RULE_mpdNoFileLine = 16, RULE_bfcMetaLine = 17, RULE_bfcCert = 18, RULE_bfcNoCert = 19, 
		RULE_bfcClipWind = 20, RULE_bfcNoClip = 21, RULE_bfcPrefix = 22, RULE_saveLine = 23, 
		RULE_keyWord = 24, RULE_name = 25, RULE_commentLine = 26, RULE_transMatrix = 27, 
		RULE_position = 28, RULE_objectLine = 29, RULE_lineLine = 30, RULE_triLine = 31, 
		RULE_quadLine = 32, RULE_fiveLine = 33, RULE_unkeyWord = 34, RULE_word = 35, 
		RULE_words = 36, RULE_blankLine = 37;
	public static final String[] ruleNames = {
		"parsedModel", "mpdModel", "mpdEmbeddedModel", "mpdDiscard", "ldrdatModel", 
		"ldrawLines", "ldrawStmt", "decInt", "number", "stepLine", "pauseLine", 
		"writeLine", "clearLine", "colorNum", "colorLine", "mpdFileLine", "mpdNoFileLine", 
		"bfcMetaLine", "bfcCert", "bfcNoCert", "bfcClipWind", "bfcNoClip", "bfcPrefix", 
		"saveLine", "keyWord", "name", "commentLine", "transMatrix", "position", 
		"objectLine", "lineLine", "triLine", "quadLine", "fiveLine", "unkeyWord", 
		"word", "words", "blankLine"
	};

	@Override
	public String getGrammarFileName() { return "LDrawParser.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public LDrawParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ParsedModelContext extends ParserRuleContext {
		public LdrdatModelContext ldrdatModel() {
			return getRuleContext(LdrdatModelContext.class,0);
		}
		public MpdModelContext mpdModel() {
			return getRuleContext(MpdModelContext.class,0);
		}
		public ParsedModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parsedModel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterParsedModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitParsedModel(this);
		}
	}

	public final ParsedModelContext parsedModel() throws RecognitionException {
		ParsedModelContext _localctx = new ParsedModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parsedModel);
		try {
			setState(78);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(76); mpdModel();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(77); ldrdatModel();
				}
				break;
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

	public static class MpdModelContext extends ParserRuleContext {
		public List<MpdEmbeddedModelContext> mpdEmbeddedModel() {
			return getRuleContexts(MpdEmbeddedModelContext.class);
		}
		public MpdEmbeddedModelContext mpdEmbeddedModel(int i) {
			return getRuleContext(MpdEmbeddedModelContext.class,i);
		}
		public MpdModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mpdModel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterMpdModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitMpdModel(this);
		}
	}

	public final MpdModelContext mpdModel() throws RecognitionException {
		MpdModelContext _localctx = new MpdModelContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_mpdModel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(80); mpdEmbeddedModel();
				}
				}
				setState(83); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ZERO );
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

	public static class MpdEmbeddedModelContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(LDrawParser.EOF, 0); }
		public LdrdatModelContext ldrdatModel() {
			return getRuleContext(LdrdatModelContext.class,0);
		}
		public MpdFileLineContext mpdFileLine() {
			return getRuleContext(MpdFileLineContext.class,0);
		}
		public MpdNoFileLineContext mpdNoFileLine() {
			return getRuleContext(MpdNoFileLineContext.class,0);
		}
		public List<TerminalNode> EOL() { return getTokens(LDrawParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(LDrawParser.EOL, i);
		}
		public MpdDiscardContext mpdDiscard() {
			return getRuleContext(MpdDiscardContext.class,0);
		}
		public MpdEmbeddedModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mpdEmbeddedModel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterMpdEmbeddedModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitMpdEmbeddedModel(this);
		}
	}

	public final MpdEmbeddedModelContext mpdEmbeddedModel() throws RecognitionException {
		MpdEmbeddedModelContext _localctx = new MpdEmbeddedModelContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_mpdEmbeddedModel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85); mpdFileLine();
			setState(86); match(EOL);
			setState(87); ldrdatModel();
			setState(92);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(88); mpdNoFileLine();
				setState(89);
				_la = _input.LA(1);
				if ( !(_la==EOF || _la==EOL) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(90); mpdDiscard();
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

	public static class MpdDiscardContext extends ParserRuleContext {
		public List<TerminalNode> EOL() { return getTokens(LDrawParser.EOL); }
		public TerminalNode GARBAGE(int i) {
			return getToken(LDrawParser.GARBAGE, i);
		}
		public TerminalNode EOL(int i) {
			return getToken(LDrawParser.EOL, i);
		}
		public List<TerminalNode> GARBAGE() { return getTokens(LDrawParser.GARBAGE); }
		public LdrawStmtContext ldrawStmt(int i) {
			return getRuleContext(LdrawStmtContext.class,i);
		}
		public List<LdrawStmtContext> ldrawStmt() {
			return getRuleContexts(LdrawStmtContext.class);
		}
		public MpdDiscardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mpdDiscard; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterMpdDiscard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitMpdDiscard(this);
		}
	}

	public final MpdDiscardContext mpdDiscard() throws RecognitionException {
		MpdDiscardContext _localctx = new MpdDiscardContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_mpdDiscard);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=1 && _alt!=-1 ) {
				if ( _alt==1+1 ) {
					{
					setState(98);
					switch (_input.LA(1)) {
					case GARBAGE:
						{
						setState(94); match(GARBAGE);
						}
						break;
					case FIVE:
					case FOUR:
					case THREE:
					case TWO:
					case ONE:
					case ZERO:
					case EOL:
						{
						{
						setState(95); ldrawStmt();
						setState(96); match(EOL);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(102);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
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

	public static class LdrdatModelContext extends ParserRuleContext {
		public LdrawLinesContext ldrawLines() {
			return getRuleContext(LdrawLinesContext.class,0);
		}
		public LdrdatModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldrdatModel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterLdrdatModel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitLdrdatModel(this);
		}
	}

	public final LdrdatModelContext ldrdatModel() throws RecognitionException {
		LdrdatModelContext _localctx = new LdrdatModelContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ldrdatModel);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103); ldrawLines();
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

	public static class LdrawLinesContext extends ParserRuleContext {
		public List<TerminalNode> EOL() { return getTokens(LDrawParser.EOL); }
		public TerminalNode EOL(int i) {
			return getToken(LDrawParser.EOL, i);
		}
		public LdrawStmtContext ldrawStmt(int i) {
			return getRuleContext(LdrawStmtContext.class,i);
		}
		public List<LdrawStmtContext> ldrawStmt() {
			return getRuleContexts(LdrawStmtContext.class);
		}
		public LdrawLinesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldrawLines; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterLdrawLines(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitLdrawLines(this);
		}
	}

	public final LdrawLinesContext ldrawLines() throws RecognitionException {
		LdrawLinesContext _localctx = new LdrawLinesContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_ldrawLines);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(105); ldrawStmt();
			setState(108); 
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(106); match(EOL);
					setState(107); ldrawStmt();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(110); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			} while ( _alt!=2 && _alt!=-1 );
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EOL) {
				{
				{
				setState(112); match(EOL);
				}
				}
				setState(117);
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

	public static class LdrawStmtContext extends ParserRuleContext {
		public TriLineContext triLine() {
			return getRuleContext(TriLineContext.class,0);
		}
		public LineLineContext lineLine() {
			return getRuleContext(LineLineContext.class,0);
		}
		public StepLineContext stepLine() {
			return getRuleContext(StepLineContext.class,0);
		}
		public WriteLineContext writeLine() {
			return getRuleContext(WriteLineContext.class,0);
		}
		public ClearLineContext clearLine() {
			return getRuleContext(ClearLineContext.class,0);
		}
		public ColorLineContext colorLine() {
			return getRuleContext(ColorLineContext.class,0);
		}
		public QuadLineContext quadLine() {
			return getRuleContext(QuadLineContext.class,0);
		}
		public FiveLineContext fiveLine() {
			return getRuleContext(FiveLineContext.class,0);
		}
		public PauseLineContext pauseLine() {
			return getRuleContext(PauseLineContext.class,0);
		}
		public CommentLineContext commentLine() {
			return getRuleContext(CommentLineContext.class,0);
		}
		public BlankLineContext blankLine() {
			return getRuleContext(BlankLineContext.class,0);
		}
		public BfcMetaLineContext bfcMetaLine() {
			return getRuleContext(BfcMetaLineContext.class,0);
		}
		public ObjectLineContext objectLine() {
			return getRuleContext(ObjectLineContext.class,0);
		}
		public SaveLineContext saveLine() {
			return getRuleContext(SaveLineContext.class,0);
		}
		public LdrawStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldrawStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterLdrawStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitLdrawStmt(this);
		}
	}

	public final LdrawStmtContext ldrawStmt() throws RecognitionException {
		LdrawStmtContext _localctx = new LdrawStmtContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ldrawStmt);
		try {
			setState(132);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(118); stepLine();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(119); pauseLine();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(120); writeLine();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(121); clearLine();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(122); saveLine();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(123); objectLine();
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(124); lineLine();
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(125); triLine();
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(126); quadLine();
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(127); fiveLine();
				}
				break;

			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(128); colorLine();
				}
				break;

			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(129); bfcMetaLine();
				}
				break;

			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(130); blankLine();
				}
				break;

			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(131); commentLine();
				}
				break;
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

	public static class DecIntContext extends ParserRuleContext {
		public TerminalNode FIVE() { return getToken(LDrawParser.FIVE, 0); }
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public TerminalNode FOUR() { return getToken(LDrawParser.FOUR, 0); }
		public TerminalNode INT() { return getToken(LDrawParser.INT, 0); }
		public TerminalNode TWO() { return getToken(LDrawParser.TWO, 0); }
		public TerminalNode ONE() { return getToken(LDrawParser.ONE, 0); }
		public TerminalNode THREE() { return getToken(LDrawParser.THREE, 0); }
		public DecIntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decInt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterDecInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitDecInt(this);
		}
	}

	public final DecIntContext decInt() throws RecognitionException {
		DecIntContext _localctx = new DecIntContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_decInt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FIVE) | (1L << FOUR) | (1L << THREE) | (1L << TWO) | (1L << ONE) | (1L << ZERO) | (1L << INT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode FLOAT() { return getToken(LDrawParser.FLOAT, 0); }
		public DecIntContext decInt() {
			return getRuleContext(DecIntContext.class,0);
		}
		public TerminalNode HEXINT() { return getToken(LDrawParser.HEXINT, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_number);
		int _la;
		try {
			setState(138);
			switch (_input.LA(1)) {
			case HEXINT:
			case FLOAT:
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				_la = _input.LA(1);
				if ( !(_la==HEXINT || _la==FLOAT) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				break;
			case FIVE:
			case FOUR:
			case THREE:
			case TWO:
			case ONE:
			case ZERO:
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(137); decInt();
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

	public static class StepLineContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public TerminalNode STEP() { return getToken(LDrawParser.STEP, 0); }
		public StepLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stepLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterStepLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitStepLine(this);
		}
	}

	public final StepLineContext stepLine() throws RecognitionException {
		StepLineContext _localctx = new StepLineContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_stepLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140); match(ZERO);
			setState(141); match(STEP);
			setState(142); words();
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

	public static class PauseLineContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public TerminalNode PAUSE() { return getToken(LDrawParser.PAUSE, 0); }
		public PauseLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pauseLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterPauseLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitPauseLine(this);
		}
	}

	public final PauseLineContext pauseLine() throws RecognitionException {
		PauseLineContext _localctx = new PauseLineContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_pauseLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144); match(ZERO);
			setState(145); match(PAUSE);
			setState(146); words();
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

	public static class WriteLineContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public TerminalNode WRITE() { return getToken(LDrawParser.WRITE, 0); }
		public WriteLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writeLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterWriteLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitWriteLine(this);
		}
	}

	public final WriteLineContext writeLine() throws RecognitionException {
		WriteLineContext _localctx = new WriteLineContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_writeLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148); match(ZERO);
			setState(149); match(WRITE);
			setState(150); words();
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

	public static class ClearLineContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public TerminalNode CLEAR() { return getToken(LDrawParser.CLEAR, 0); }
		public ClearLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clearLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterClearLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitClearLine(this);
		}
	}

	public final ClearLineContext clearLine() throws RecognitionException {
		ClearLineContext _localctx = new ClearLineContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_clearLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152); match(ZERO);
			setState(153); match(CLEAR);
			setState(154); words();
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

	public static class ColorNumContext extends ParserRuleContext {
		public DecIntContext decInt() {
			return getRuleContext(DecIntContext.class,0);
		}
		public TerminalNode HEXINT() { return getToken(LDrawParser.HEXINT, 0); }
		public ColorNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colorNum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterColorNum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitColorNum(this);
		}
	}

	public final ColorNumContext colorNum() throws RecognitionException {
		ColorNumContext _localctx = new ColorNumContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_colorNum);
		try {
			setState(158);
			switch (_input.LA(1)) {
			case FIVE:
			case FOUR:
			case THREE:
			case TWO:
			case ONE:
			case ZERO:
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(156); decInt();
				}
				break;
			case HEXINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(157); match(HEXINT);
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

	public static class ColorLineContext extends ParserRuleContext {
		public TerminalNode CODE() { return getToken(LDrawParser.CODE, 0); }
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public TerminalNode VALUE() { return getToken(LDrawParser.VALUE, 0); }
		public List<ColorNumContext> colorNum() {
			return getRuleContexts(ColorNumContext.class);
		}
		public TerminalNode COLOUR() { return getToken(LDrawParser.COLOUR, 0); }
		public ColorNumContext colorNum(int i) {
			return getRuleContext(ColorNumContext.class,i);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ColorLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colorLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterColorLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitColorLine(this);
		}
	}

	public final ColorLineContext colorLine() throws RecognitionException {
		ColorLineContext _localctx = new ColorLineContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_colorLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160); match(ZERO);
			setState(161); match(COLOUR);
			setState(162); name();
			setState(163); match(CODE);
			setState(164); colorNum();
			setState(165); match(VALUE);
			setState(166); colorNum();
			setState(167); words();
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

	public static class MpdFileLineContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public TerminalNode FILE() { return getToken(LDrawParser.FILE, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public MpdFileLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mpdFileLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterMpdFileLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitMpdFileLine(this);
		}
	}

	public final MpdFileLineContext mpdFileLine() throws RecognitionException {
		MpdFileLineContext _localctx = new MpdFileLineContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_mpdFileLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169); match(ZERO);
			setState(170); match(FILE);
			setState(171); name();
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

	public static class MpdNoFileLineContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public TerminalNode FILE() { return getToken(LDrawParser.FILE, 0); }
		public TerminalNode NO() { return getToken(LDrawParser.NO, 0); }
		public MpdNoFileLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mpdNoFileLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterMpdNoFileLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitMpdNoFileLine(this);
		}
	}

	public final MpdNoFileLineContext mpdNoFileLine() throws RecognitionException {
		MpdNoFileLineContext _localctx = new MpdNoFileLineContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_mpdNoFileLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173); match(ZERO);
			setState(174); match(NO);
			setState(175); match(FILE);
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

	public static class BfcMetaLineContext extends ParserRuleContext {
		public BfcNoCertContext bfcNoCert() {
			return getRuleContext(BfcNoCertContext.class,0);
		}
		public BfcCertContext bfcCert() {
			return getRuleContext(BfcCertContext.class,0);
		}
		public BfcPrefixContext bfcPrefix() {
			return getRuleContext(BfcPrefixContext.class,0);
		}
		public TerminalNode INVERTNEXT() { return getToken(LDrawParser.INVERTNEXT, 0); }
		public BfcClipWindContext bfcClipWind() {
			return getRuleContext(BfcClipWindContext.class,0);
		}
		public BfcMetaLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bfcMetaLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterBfcMetaLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitBfcMetaLine(this);
		}
	}

	public final BfcMetaLineContext bfcMetaLine() throws RecognitionException {
		BfcMetaLineContext _localctx = new BfcMetaLineContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_bfcMetaLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177); bfcPrefix();
			setState(182);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(178); bfcCert();
				}
				break;

			case 2:
				{
				setState(179); bfcNoCert();
				}
				break;

			case 3:
				{
				setState(180); bfcClipWind();
				}
				break;

			case 4:
				{
				setState(181); match(INVERTNEXT);
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

	public static class BfcCertContext extends ParserRuleContext {
		public TerminalNode ORIENTATION() { return getToken(LDrawParser.ORIENTATION, 0); }
		public TerminalNode CERTIFICATION() { return getToken(LDrawParser.CERTIFICATION, 0); }
		public BfcCertContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bfcCert; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterBfcCert(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitBfcCert(this);
		}
	}

	public final BfcCertContext bfcCert() throws RecognitionException {
		BfcCertContext _localctx = new BfcCertContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_bfcCert);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184); match(CERTIFICATION);
			setState(186);
			_la = _input.LA(1);
			if (_la==ORIENTATION) {
				{
				setState(185); match(ORIENTATION);
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

	public static class BfcNoCertContext extends ParserRuleContext {
		public TerminalNode CERTIFICATION() { return getToken(LDrawParser.CERTIFICATION, 0); }
		public TerminalNode NO() { return getToken(LDrawParser.NO, 0); }
		public BfcNoCertContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bfcNoCert; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterBfcNoCert(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitBfcNoCert(this);
		}
	}

	public final BfcNoCertContext bfcNoCert() throws RecognitionException {
		BfcNoCertContext _localctx = new BfcNoCertContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_bfcNoCert);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188); match(NO);
			setState(189); match(CERTIFICATION);
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

	public static class BfcClipWindContext extends ParserRuleContext {
		public TerminalNode ORIENTATION() { return getToken(LDrawParser.ORIENTATION, 0); }
		public BfcNoClipContext bfcNoClip() {
			return getRuleContext(BfcNoClipContext.class,0);
		}
		public TerminalNode CLIPPING() { return getToken(LDrawParser.CLIPPING, 0); }
		public BfcClipWindContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bfcClipWind; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterBfcClipWind(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitBfcClipWind(this);
		}
	}

	public final BfcClipWindContext bfcClipWind() throws RecognitionException {
		BfcClipWindContext _localctx = new BfcClipWindContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_bfcClipWind);
		int _la;
		try {
			setState(200);
			switch (_input.LA(1)) {
			case CLIPPING:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(191); match(CLIPPING);
				setState(193);
				_la = _input.LA(1);
				if (_la==ORIENTATION) {
					{
					setState(192); match(ORIENTATION);
					}
				}

				}
				}
				break;
			case ORIENTATION:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(195); match(ORIENTATION);
				setState(197);
				_la = _input.LA(1);
				if (_la==CLIPPING) {
					{
					setState(196); match(CLIPPING);
					}
				}

				}
				}
				break;
			case NO:
				enterOuterAlt(_localctx, 3);
				{
				setState(199); bfcNoClip();
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

	public static class BfcNoClipContext extends ParserRuleContext {
		public TerminalNode NO() { return getToken(LDrawParser.NO, 0); }
		public TerminalNode CLIPPING() { return getToken(LDrawParser.CLIPPING, 0); }
		public BfcNoClipContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bfcNoClip; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterBfcNoClip(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitBfcNoClip(this);
		}
	}

	public final BfcNoClipContext bfcNoClip() throws RecognitionException {
		BfcNoClipContext _localctx = new BfcNoClipContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_bfcNoClip);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202); match(NO);
			setState(203); match(CLIPPING);
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

	public static class BfcPrefixContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public TerminalNode BFC() { return getToken(LDrawParser.BFC, 0); }
		public BfcPrefixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bfcPrefix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterBfcPrefix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitBfcPrefix(this);
		}
	}

	public final BfcPrefixContext bfcPrefix() throws RecognitionException {
		BfcPrefixContext _localctx = new BfcPrefixContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_bfcPrefix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205); match(ZERO);
			setState(206); match(BFC);
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

	public static class SaveLineContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public TerminalNode SAVE() { return getToken(LDrawParser.SAVE, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public SaveLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_saveLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterSaveLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitSaveLine(this);
		}
	}

	public final SaveLineContext saveLine() throws RecognitionException {
		SaveLineContext _localctx = new SaveLineContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_saveLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208); match(ZERO);
			setState(209); match(SAVE);
			setState(210); words();
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

	public static class KeyWordContext extends ParserRuleContext {
		public TerminalNode SAVE() { return getToken(LDrawParser.SAVE, 0); }
		public TerminalNode LUMINANCE() { return getToken(LDrawParser.LUMINANCE, 0); }
		public TerminalNode ORIENTATION() { return getToken(LDrawParser.ORIENTATION, 0); }
		public TerminalNode FILE() { return getToken(LDrawParser.FILE, 0); }
		public TerminalNode MATERIAL() { return getToken(LDrawParser.MATERIAL, 0); }
		public TerminalNode NO() { return getToken(LDrawParser.NO, 0); }
		public TerminalNode METAL() { return getToken(LDrawParser.METAL, 0); }
		public TerminalNode ALPHA() { return getToken(LDrawParser.ALPHA, 0); }
		public TerminalNode STEP() { return getToken(LDrawParser.STEP, 0); }
		public TerminalNode PAUSE() { return getToken(LDrawParser.PAUSE, 0); }
		public TerminalNode CLEAR() { return getToken(LDrawParser.CLEAR, 0); }
		public TerminalNode EDGE() { return getToken(LDrawParser.EDGE, 0); }
		public TerminalNode INVERTNEXT() { return getToken(LDrawParser.INVERTNEXT, 0); }
		public TerminalNode CODE() { return getToken(LDrawParser.CODE, 0); }
		public TerminalNode PEARLESCENT() { return getToken(LDrawParser.PEARLESCENT, 0); }
		public TerminalNode VALUE() { return getToken(LDrawParser.VALUE, 0); }
		public TerminalNode CERTIFICATION() { return getToken(LDrawParser.CERTIFICATION, 0); }
		public TerminalNode CHROME() { return getToken(LDrawParser.CHROME, 0); }
		public TerminalNode BFC() { return getToken(LDrawParser.BFC, 0); }
		public TerminalNode WRITE() { return getToken(LDrawParser.WRITE, 0); }
		public TerminalNode MATTE_METALLIC() { return getToken(LDrawParser.MATTE_METALLIC, 0); }
		public TerminalNode COLOUR() { return getToken(LDrawParser.COLOUR, 0); }
		public TerminalNode RUBBER() { return getToken(LDrawParser.RUBBER, 0); }
		public TerminalNode CLIPPING() { return getToken(LDrawParser.CLIPPING, 0); }
		public KeyWordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyWord; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterKeyWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitKeyWord(this);
		}
	}

	public final KeyWordContext keyWord() throws RecognitionException {
		KeyWordContext _localctx = new KeyWordContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_keyWord);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STEP) | (1L << PAUSE) | (1L << WRITE) | (1L << CLEAR) | (1L << SAVE) | (1L << COLOUR) | (1L << CODE) | (1L << VALUE) | (1L << EDGE) | (1L << ALPHA) | (1L << LUMINANCE) | (1L << CHROME) | (1L << PEARLESCENT) | (1L << RUBBER) | (1L << MATTE_METALLIC) | (1L << METAL) | (1L << MATERIAL) | (1L << FILE) | (1L << BFC) | (1L << NO) | (1L << CERTIFICATION) | (1L << ORIENTATION) | (1L << CLIPPING) | (1L << INVERTNEXT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static class NameContext extends ParserRuleContext {
		public List<TerminalNode> IDENT() { return getTokens(LDrawParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(LDrawParser.IDENT, i);
		}
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitName(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_name);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(215); 
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(214); match(IDENT);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(217); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			} while ( _alt!=2 && _alt!=-1 );
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

	public static class CommentLineContext extends ParserRuleContext {
		public TerminalNode ZERO() { return getToken(LDrawParser.ZERO, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public UnkeyWordContext unkeyWord() {
			return getRuleContext(UnkeyWordContext.class,0);
		}
		public CommentLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commentLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterCommentLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitCommentLine(this);
		}
	}

	public final CommentLineContext commentLine() throws RecognitionException {
		CommentLineContext _localctx = new CommentLineContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_commentLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219); match(ZERO);
			setState(223);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(220); unkeyWord();
				setState(221); words();
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

	public static class TransMatrixContext extends ParserRuleContext {
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public PositionContext position() {
			return getRuleContext(PositionContext.class,0);
		}
		public TransMatrixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transMatrix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterTransMatrix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitTransMatrix(this);
		}
	}

	public final TransMatrixContext transMatrix() throws RecognitionException {
		TransMatrixContext _localctx = new TransMatrixContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_transMatrix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225); position();
			setState(226); number();
			setState(227); number();
			setState(228); number();
			setState(229); number();
			setState(230); number();
			setState(231); number();
			setState(232); number();
			setState(233); number();
			setState(234); number();
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

	public static class PositionContext extends ParserRuleContext {
		public List<NumberContext> number() {
			return getRuleContexts(NumberContext.class);
		}
		public NumberContext number(int i) {
			return getRuleContext(NumberContext.class,i);
		}
		public PositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_position; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitPosition(this);
		}
	}

	public final PositionContext position() throws RecognitionException {
		PositionContext _localctx = new PositionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_position);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236); number();
			setState(237); number();
			setState(238); number();
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

	public static class ObjectLineContext extends ParserRuleContext {
		public TransMatrixContext transMatrix() {
			return getRuleContext(TransMatrixContext.class,0);
		}
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public ColorNumContext colorNum() {
			return getRuleContext(ColorNumContext.class,0);
		}
		public TerminalNode ONE() { return getToken(LDrawParser.ONE, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ObjectLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterObjectLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitObjectLine(this);
		}
	}

	public final ObjectLineContext objectLine() throws RecognitionException {
		ObjectLineContext _localctx = new ObjectLineContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_objectLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240); match(ONE);
			setState(241); colorNum();
			setState(242); transMatrix();
			setState(243); name();
			setState(244); words();
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

	public static class LineLineContext extends ParserRuleContext {
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public ColorNumContext colorNum() {
			return getRuleContext(ColorNumContext.class,0);
		}
		public TerminalNode TWO() { return getToken(LDrawParser.TWO, 0); }
		public List<PositionContext> position() {
			return getRuleContexts(PositionContext.class);
		}
		public PositionContext position(int i) {
			return getRuleContext(PositionContext.class,i);
		}
		public LineLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lineLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterLineLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitLineLine(this);
		}
	}

	public final LineLineContext lineLine() throws RecognitionException {
		LineLineContext _localctx = new LineLineContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_lineLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246); match(TWO);
			setState(247); colorNum();
			setState(248); position();
			setState(249); position();
			setState(250); words();
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

	public static class TriLineContext extends ParserRuleContext {
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public ColorNumContext colorNum() {
			return getRuleContext(ColorNumContext.class,0);
		}
		public List<PositionContext> position() {
			return getRuleContexts(PositionContext.class);
		}
		public PositionContext position(int i) {
			return getRuleContext(PositionContext.class,i);
		}
		public TerminalNode THREE() { return getToken(LDrawParser.THREE, 0); }
		public TriLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterTriLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitTriLine(this);
		}
	}

	public final TriLineContext triLine() throws RecognitionException {
		TriLineContext _localctx = new TriLineContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_triLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252); match(THREE);
			setState(253); colorNum();
			setState(254); position();
			setState(255); position();
			setState(256); position();
			setState(257); words();
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

	public static class QuadLineContext extends ParserRuleContext {
		public TerminalNode FOUR() { return getToken(LDrawParser.FOUR, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public ColorNumContext colorNum() {
			return getRuleContext(ColorNumContext.class,0);
		}
		public List<PositionContext> position() {
			return getRuleContexts(PositionContext.class);
		}
		public PositionContext position(int i) {
			return getRuleContext(PositionContext.class,i);
		}
		public QuadLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quadLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterQuadLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitQuadLine(this);
		}
	}

	public final QuadLineContext quadLine() throws RecognitionException {
		QuadLineContext _localctx = new QuadLineContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_quadLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259); match(FOUR);
			setState(260); colorNum();
			setState(261); position();
			setState(262); position();
			setState(263); position();
			setState(264); position();
			setState(265); words();
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

	public static class FiveLineContext extends ParserRuleContext {
		public TerminalNode FIVE() { return getToken(LDrawParser.FIVE, 0); }
		public WordsContext words() {
			return getRuleContext(WordsContext.class,0);
		}
		public ColorNumContext colorNum() {
			return getRuleContext(ColorNumContext.class,0);
		}
		public List<PositionContext> position() {
			return getRuleContexts(PositionContext.class);
		}
		public PositionContext position(int i) {
			return getRuleContext(PositionContext.class,i);
		}
		public FiveLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fiveLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterFiveLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitFiveLine(this);
		}
	}

	public final FiveLineContext fiveLine() throws RecognitionException {
		FiveLineContext _localctx = new FiveLineContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_fiveLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267); match(FIVE);
			setState(268); colorNum();
			setState(269); position();
			setState(270); position();
			setState(271); position();
			setState(272); position();
			setState(273); words();
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

	public static class UnkeyWordContext extends ParserRuleContext {
		public TerminalNode FLOAT() { return getToken(LDrawParser.FLOAT, 0); }
		public DecIntContext decInt() {
			return getRuleContext(DecIntContext.class,0);
		}
		public TerminalNode GARBAGE() { return getToken(LDrawParser.GARBAGE, 0); }
		public TerminalNode HEXINT() { return getToken(LDrawParser.HEXINT, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public UnkeyWordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unkeyWord; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterUnkeyWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitUnkeyWord(this);
		}
	}

	public final UnkeyWordContext unkeyWord() throws RecognitionException {
		UnkeyWordContext _localctx = new UnkeyWordContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_unkeyWord);
		int _la;
		try {
			setState(278);
			switch (_input.LA(1)) {
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(275); name();
				}
				break;
			case FIVE:
			case FOUR:
			case THREE:
			case TWO:
			case ONE:
			case ZERO:
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(276); decInt();
				}
				break;
			case HEXINT:
			case FLOAT:
			case GARBAGE:
				enterOuterAlt(_localctx, 3);
				{
				setState(277);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << HEXINT) | (1L << FLOAT) | (1L << GARBAGE))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				consume();
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

	public static class WordContext extends ParserRuleContext {
		public KeyWordContext keyWord() {
			return getRuleContext(KeyWordContext.class,0);
		}
		public UnkeyWordContext unkeyWord() {
			return getRuleContext(UnkeyWordContext.class,0);
		}
		public WordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_word; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterWord(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitWord(this);
		}
	}

	public final WordContext word() throws RecognitionException {
		WordContext _localctx = new WordContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_word);
		try {
			setState(282);
			switch (_input.LA(1)) {
			case STEP:
			case PAUSE:
			case WRITE:
			case CLEAR:
			case SAVE:
			case COLOUR:
			case CODE:
			case VALUE:
			case EDGE:
			case ALPHA:
			case LUMINANCE:
			case CHROME:
			case PEARLESCENT:
			case RUBBER:
			case MATTE_METALLIC:
			case METAL:
			case MATERIAL:
			case FILE:
			case BFC:
			case NO:
			case CERTIFICATION:
			case ORIENTATION:
			case CLIPPING:
			case INVERTNEXT:
				enterOuterAlt(_localctx, 1);
				{
				setState(280); keyWord();
				}
				break;
			case FIVE:
			case FOUR:
			case THREE:
			case TWO:
			case ONE:
			case ZERO:
			case HEXINT:
			case INT:
			case FLOAT:
			case IDENT:
			case GARBAGE:
				enterOuterAlt(_localctx, 2);
				{
				setState(281); unkeyWord();
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

	public static class WordsContext extends ParserRuleContext {
		public List<WordContext> word() {
			return getRuleContexts(WordContext.class);
		}
		public WordContext word(int i) {
			return getRuleContext(WordContext.class,i);
		}
		public WordsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_words; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterWords(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitWords(this);
		}
	}

	public final WordsContext words() throws RecognitionException {
		WordsContext _localctx = new WordsContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_words);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(284); word();
					}
					} 
				}
				setState(289);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
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

	public static class BlankLineContext extends ParserRuleContext {
		public BlankLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blankLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).enterBlankLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LDrawParserListener ) ((LDrawParserListener)listener).exitBlankLine(this);
		}
	}

	public final BlankLineContext blankLine() throws RecognitionException {
		BlankLineContext _localctx = new BlankLineContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_blankLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
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

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3)\u0127\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\5\2Q\n\2\3\3\6\3T"+
		"\n\3\r\3\16\3U\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4_\n\4\3\5\3\5\3\5\3\5\7"+
		"\5e\n\5\f\5\16\5h\13\5\3\6\3\6\3\7\3\7\3\7\6\7o\n\7\r\7\16\7p\3\7\7\7"+
		"t\n\7\f\7\16\7w\13\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\5\b\u0087\n\b\3\t\3\t\3\n\3\n\5\n\u008d\n\n\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\5\17\u00a1"+
		"\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\5\23\u00b9\n\23\3\24\3\24"+
		"\5\24\u00bd\n\24\3\25\3\25\3\25\3\26\3\26\5\26\u00c4\n\26\3\26\3\26\5"+
		"\26\u00c8\n\26\3\26\5\26\u00cb\n\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31"+
		"\3\31\3\31\3\31\3\32\3\32\3\33\6\33\u00da\n\33\r\33\16\33\u00db\3\34\3"+
		"\34\3\34\3\34\5\34\u00e2\n\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3 \3"+
		" \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#"+
		"\3#\3#\3#\3#\3#\3#\3#\3$\3$\3$\5$\u0119\n$\3%\3%\5%\u011d\n%\3&\7&\u0120"+
		"\n&\f&\16&\u0123\13&\3\'\3\'\3\'\3f(\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJL\2\7\3\3\r\r\4\2\3\b\n\n\4\2\t\t"+
		"\13\13\4\2\16\20\22&\5\2\t\t\13\13))\u0124\2P\3\2\2\2\4S\3\2\2\2\6W\3"+
		"\2\2\2\bf\3\2\2\2\ni\3\2\2\2\fk\3\2\2\2\16\u0086\3\2\2\2\20\u0088\3\2"+
		"\2\2\22\u008c\3\2\2\2\24\u008e\3\2\2\2\26\u0092\3\2\2\2\30\u0096\3\2\2"+
		"\2\32\u009a\3\2\2\2\34\u00a0\3\2\2\2\36\u00a2\3\2\2\2 \u00ab\3\2\2\2\""+
		"\u00af\3\2\2\2$\u00b3\3\2\2\2&\u00ba\3\2\2\2(\u00be\3\2\2\2*\u00ca\3\2"+
		"\2\2,\u00cc\3\2\2\2.\u00cf\3\2\2\2\60\u00d2\3\2\2\2\62\u00d6\3\2\2\2\64"+
		"\u00d9\3\2\2\2\66\u00dd\3\2\2\28\u00e3\3\2\2\2:\u00ee\3\2\2\2<\u00f2\3"+
		"\2\2\2>\u00f8\3\2\2\2@\u00fe\3\2\2\2B\u0105\3\2\2\2D\u010d\3\2\2\2F\u0118"+
		"\3\2\2\2H\u011c\3\2\2\2J\u0121\3\2\2\2L\u0124\3\2\2\2NQ\5\4\3\2OQ\5\n"+
		"\6\2PN\3\2\2\2PO\3\2\2\2Q\3\3\2\2\2RT\5\6\4\2SR\3\2\2\2TU\3\2\2\2US\3"+
		"\2\2\2UV\3\2\2\2V\5\3\2\2\2WX\5 \21\2XY\7\r\2\2Y^\5\n\6\2Z[\5\"\22\2["+
		"\\\t\2\2\2\\]\5\b\5\2]_\3\2\2\2^Z\3\2\2\2^_\3\2\2\2_\7\3\2\2\2`e\7)\2"+
		"\2ab\5\16\b\2bc\7\r\2\2ce\3\2\2\2d`\3\2\2\2da\3\2\2\2eh\3\2\2\2fg\3\2"+
		"\2\2fd\3\2\2\2g\t\3\2\2\2hf\3\2\2\2ij\5\f\7\2j\13\3\2\2\2kn\5\16\b\2l"+
		"m\7\r\2\2mo\5\16\b\2nl\3\2\2\2op\3\2\2\2pn\3\2\2\2pq\3\2\2\2qu\3\2\2\2"+
		"rt\7\r\2\2sr\3\2\2\2tw\3\2\2\2us\3\2\2\2uv\3\2\2\2v\r\3\2\2\2wu\3\2\2"+
		"\2x\u0087\5\24\13\2y\u0087\5\26\f\2z\u0087\5\30\r\2{\u0087\5\32\16\2|"+
		"\u0087\5\60\31\2}\u0087\5<\37\2~\u0087\5> \2\177\u0087\5@!\2\u0080\u0087"+
		"\5B\"\2\u0081\u0087\5D#\2\u0082\u0087\5\36\20\2\u0083\u0087\5$\23\2\u0084"+
		"\u0087\5L\'\2\u0085\u0087\5\66\34\2\u0086x\3\2\2\2\u0086y\3\2\2\2\u0086"+
		"z\3\2\2\2\u0086{\3\2\2\2\u0086|\3\2\2\2\u0086}\3\2\2\2\u0086~\3\2\2\2"+
		"\u0086\177\3\2\2\2\u0086\u0080\3\2\2\2\u0086\u0081\3\2\2\2\u0086\u0082"+
		"\3\2\2\2\u0086\u0083\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0085\3\2\2\2\u0087"+
		"\17\3\2\2\2\u0088\u0089\t\3\2\2\u0089\21\3\2\2\2\u008a\u008d\t\4\2\2\u008b"+
		"\u008d\5\20\t\2\u008c\u008a\3\2\2\2\u008c\u008b\3\2\2\2\u008d\23\3\2\2"+
		"\2\u008e\u008f\7\b\2\2\u008f\u0090\7\16\2\2\u0090\u0091\5J&\2\u0091\25"+
		"\3\2\2\2\u0092\u0093\7\b\2\2\u0093\u0094\7\17\2\2\u0094\u0095\5J&\2\u0095"+
		"\27\3\2\2\2\u0096\u0097\7\b\2\2\u0097\u0098\7\20\2\2\u0098\u0099\5J&\2"+
		"\u0099\31\3\2\2\2\u009a\u009b\7\b\2\2\u009b\u009c\7\22\2\2\u009c\u009d"+
		"\5J&\2\u009d\33\3\2\2\2\u009e\u00a1\5\20\t\2\u009f\u00a1\7\t\2\2\u00a0"+
		"\u009e\3\2\2\2\u00a0\u009f\3\2\2\2\u00a1\35\3\2\2\2\u00a2\u00a3\7\b\2"+
		"\2\u00a3\u00a4\7\24\2\2\u00a4\u00a5\5\64\33\2\u00a5\u00a6\7\25\2\2\u00a6"+
		"\u00a7\5\34\17\2\u00a7\u00a8\7\26\2\2\u00a8\u00a9\5\34\17\2\u00a9\u00aa"+
		"\5J&\2\u00aa\37\3\2\2\2\u00ab\u00ac\7\b\2\2\u00ac\u00ad\7 \2\2\u00ad\u00ae"+
		"\5\64\33\2\u00ae!\3\2\2\2\u00af\u00b0\7\b\2\2\u00b0\u00b1\7\"\2\2\u00b1"+
		"\u00b2\7 \2\2\u00b2#\3\2\2\2\u00b3\u00b8\5.\30\2\u00b4\u00b9\5&\24\2\u00b5"+
		"\u00b9\5(\25\2\u00b6\u00b9\5*\26\2\u00b7\u00b9\7&\2\2\u00b8\u00b4\3\2"+
		"\2\2\u00b8\u00b5\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b7\3\2\2\2\u00b9"+
		"%\3\2\2\2\u00ba\u00bc\7#\2\2\u00bb\u00bd\7$\2\2\u00bc\u00bb\3\2\2\2\u00bc"+
		"\u00bd\3\2\2\2\u00bd\'\3\2\2\2\u00be\u00bf\7\"\2\2\u00bf\u00c0\7#\2\2"+
		"\u00c0)\3\2\2\2\u00c1\u00c3\7%\2\2\u00c2\u00c4\7$\2\2\u00c3\u00c2\3\2"+
		"\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00cb\3\2\2\2\u00c5\u00c7\7$\2\2\u00c6"+
		"\u00c8\7%\2\2\u00c7\u00c6\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00cb\3\2"+
		"\2\2\u00c9\u00cb\5,\27\2\u00ca\u00c1\3\2\2\2\u00ca\u00c5\3\2\2\2\u00ca"+
		"\u00c9\3\2\2\2\u00cb+\3\2\2\2\u00cc\u00cd\7\"\2\2\u00cd\u00ce\7%\2\2\u00ce"+
		"-\3\2\2\2\u00cf\u00d0\7\b\2\2\u00d0\u00d1\7!\2\2\u00d1/\3\2\2\2\u00d2"+
		"\u00d3\7\b\2\2\u00d3\u00d4\7\23\2\2\u00d4\u00d5\5J&\2\u00d5\61\3\2\2\2"+
		"\u00d6\u00d7\t\5\2\2\u00d7\63\3\2\2\2\u00d8\u00da\7\'\2\2\u00d9\u00d8"+
		"\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc"+
		"\65\3\2\2\2\u00dd\u00e1\7\b\2\2\u00de\u00df\5F$\2\u00df\u00e0\5J&\2\u00e0"+
		"\u00e2\3\2\2\2\u00e1\u00de\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\67\3\2\2"+
		"\2\u00e3\u00e4\5:\36\2\u00e4\u00e5\5\22\n\2\u00e5\u00e6\5\22\n\2\u00e6"+
		"\u00e7\5\22\n\2\u00e7\u00e8\5\22\n\2\u00e8\u00e9\5\22\n\2\u00e9\u00ea"+
		"\5\22\n\2\u00ea\u00eb\5\22\n\2\u00eb\u00ec\5\22\n\2\u00ec\u00ed\5\22\n"+
		"\2\u00ed9\3\2\2\2\u00ee\u00ef\5\22\n\2\u00ef\u00f0\5\22\n\2\u00f0\u00f1"+
		"\5\22\n\2\u00f1;\3\2\2\2\u00f2\u00f3\7\7\2\2\u00f3\u00f4\5\34\17\2\u00f4"+
		"\u00f5\58\35\2\u00f5\u00f6\5\64\33\2\u00f6\u00f7\5J&\2\u00f7=\3\2\2\2"+
		"\u00f8\u00f9\7\6\2\2\u00f9\u00fa\5\34\17\2\u00fa\u00fb\5:\36\2\u00fb\u00fc"+
		"\5:\36\2\u00fc\u00fd\5J&\2\u00fd?\3\2\2\2\u00fe\u00ff\7\5\2\2\u00ff\u0100"+
		"\5\34\17\2\u0100\u0101\5:\36\2\u0101\u0102\5:\36\2\u0102\u0103\5:\36\2"+
		"\u0103\u0104\5J&\2\u0104A\3\2\2\2\u0105\u0106\7\4\2\2\u0106\u0107\5\34"+
		"\17\2\u0107\u0108\5:\36\2\u0108\u0109\5:\36\2\u0109\u010a\5:\36\2\u010a"+
		"\u010b\5:\36\2\u010b\u010c\5J&\2\u010cC\3\2\2\2\u010d\u010e\7\3\2\2\u010e"+
		"\u010f\5\34\17\2\u010f\u0110\5:\36\2\u0110\u0111\5:\36\2\u0111\u0112\5"+
		":\36\2\u0112\u0113\5:\36\2\u0113\u0114\5J&\2\u0114E\3\2\2\2\u0115\u0119"+
		"\5\64\33\2\u0116\u0119\5\20\t\2\u0117\u0119\t\6\2\2\u0118\u0115\3\2\2"+
		"\2\u0118\u0116\3\2\2\2\u0118\u0117\3\2\2\2\u0119G\3\2\2\2\u011a\u011d"+
		"\5\62\32\2\u011b\u011d\5F$\2\u011c\u011a\3\2\2\2\u011c\u011b\3\2\2\2\u011d"+
		"I\3\2\2\2\u011e\u0120\5H%\2\u011f\u011e\3\2\2\2\u0120\u0123\3\2\2\2\u0121"+
		"\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122K\3\2\2\2\u0123\u0121\3\2\2\2"+
		"\u0124\u0125\3\2\2\2\u0125M\3\2\2\2\26PU^dfpu\u0086\u008c\u00a0\u00b8"+
		"\u00bc\u00c3\u00c7\u00ca\u00db\u00e1\u0118\u011c\u0121";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}