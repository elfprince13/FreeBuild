// Generated from /Users/thomas/FreeBuild/FreeBuildJ/net/cemetech/sfgp/ldraw/LDrawParser.g4 by ANTLR 4.1
package net.cemetech.sfgp.ldraw;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LDrawParser}.
 */
public interface LDrawParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LDrawParser#position}.
	 * @param ctx the parse tree
	 */
	void enterPosition(@NotNull LDrawParser.PositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#position}.
	 * @param ctx the parse tree
	 */
	void exitPosition(@NotNull LDrawParser.PositionContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#transMatrix}.
	 * @param ctx the parse tree
	 */
	void enterTransMatrix(@NotNull LDrawParser.TransMatrixContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#transMatrix}.
	 * @param ctx the parse tree
	 */
	void exitTransMatrix(@NotNull LDrawParser.TransMatrixContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#colorLine}.
	 * @param ctx the parse tree
	 */
	void enterColorLine(@NotNull LDrawParser.ColorLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#colorLine}.
	 * @param ctx the parse tree
	 */
	void exitColorLine(@NotNull LDrawParser.ColorLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#commentLine}.
	 * @param ctx the parse tree
	 */
	void enterCommentLine(@NotNull LDrawParser.CommentLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#commentLine}.
	 * @param ctx the parse tree
	 */
	void exitCommentLine(@NotNull LDrawParser.CommentLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#blankLine}.
	 * @param ctx the parse tree
	 */
	void enterBlankLine(@NotNull LDrawParser.BlankLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#blankLine}.
	 * @param ctx the parse tree
	 */
	void exitBlankLine(@NotNull LDrawParser.BlankLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#mpdModel}.
	 * @param ctx the parse tree
	 */
	void enterMpdModel(@NotNull LDrawParser.MpdModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#mpdModel}.
	 * @param ctx the parse tree
	 */
	void exitMpdModel(@NotNull LDrawParser.MpdModelContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#ldrawStmt}.
	 * @param ctx the parse tree
	 */
	void enterLdrawStmt(@NotNull LDrawParser.LdrawStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#ldrawStmt}.
	 * @param ctx the parse tree
	 */
	void exitLdrawStmt(@NotNull LDrawParser.LdrawStmtContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#colorNum}.
	 * @param ctx the parse tree
	 */
	void enterColorNum(@NotNull LDrawParser.ColorNumContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#colorNum}.
	 * @param ctx the parse tree
	 */
	void exitColorNum(@NotNull LDrawParser.ColorNumContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#mpdDiscard}.
	 * @param ctx the parse tree
	 */
	void enterMpdDiscard(@NotNull LDrawParser.MpdDiscardContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#mpdDiscard}.
	 * @param ctx the parse tree
	 */
	void exitMpdDiscard(@NotNull LDrawParser.MpdDiscardContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#keyWord}.
	 * @param ctx the parse tree
	 */
	void enterKeyWord(@NotNull LDrawParser.KeyWordContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#keyWord}.
	 * @param ctx the parse tree
	 */
	void exitKeyWord(@NotNull LDrawParser.KeyWordContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#parsedModel}.
	 * @param ctx the parse tree
	 */
	void enterParsedModel(@NotNull LDrawParser.ParsedModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#parsedModel}.
	 * @param ctx the parse tree
	 */
	void exitParsedModel(@NotNull LDrawParser.ParsedModelContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#mpdFileLine}.
	 * @param ctx the parse tree
	 */
	void enterMpdFileLine(@NotNull LDrawParser.MpdFileLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#mpdFileLine}.
	 * @param ctx the parse tree
	 */
	void exitMpdFileLine(@NotNull LDrawParser.MpdFileLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#words}.
	 * @param ctx the parse tree
	 */
	void enterWords(@NotNull LDrawParser.WordsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#words}.
	 * @param ctx the parse tree
	 */
	void exitWords(@NotNull LDrawParser.WordsContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#fiveLine}.
	 * @param ctx the parse tree
	 */
	void enterFiveLine(@NotNull LDrawParser.FiveLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#fiveLine}.
	 * @param ctx the parse tree
	 */
	void exitFiveLine(@NotNull LDrawParser.FiveLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(@NotNull LDrawParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(@NotNull LDrawParser.NameContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#quadLine}.
	 * @param ctx the parse tree
	 */
	void enterQuadLine(@NotNull LDrawParser.QuadLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#quadLine}.
	 * @param ctx the parse tree
	 */
	void exitQuadLine(@NotNull LDrawParser.QuadLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#writeLine}.
	 * @param ctx the parse tree
	 */
	void enterWriteLine(@NotNull LDrawParser.WriteLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#writeLine}.
	 * @param ctx the parse tree
	 */
	void exitWriteLine(@NotNull LDrawParser.WriteLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#saveLine}.
	 * @param ctx the parse tree
	 */
	void enterSaveLine(@NotNull LDrawParser.SaveLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#saveLine}.
	 * @param ctx the parse tree
	 */
	void exitSaveLine(@NotNull LDrawParser.SaveLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#bfcCert}.
	 * @param ctx the parse tree
	 */
	void enterBfcCert(@NotNull LDrawParser.BfcCertContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#bfcCert}.
	 * @param ctx the parse tree
	 */
	void exitBfcCert(@NotNull LDrawParser.BfcCertContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#unkeyWord}.
	 * @param ctx the parse tree
	 */
	void enterUnkeyWord(@NotNull LDrawParser.UnkeyWordContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#unkeyWord}.
	 * @param ctx the parse tree
	 */
	void exitUnkeyWord(@NotNull LDrawParser.UnkeyWordContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#objectLine}.
	 * @param ctx the parse tree
	 */
	void enterObjectLine(@NotNull LDrawParser.ObjectLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#objectLine}.
	 * @param ctx the parse tree
	 */
	void exitObjectLine(@NotNull LDrawParser.ObjectLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#pauseLine}.
	 * @param ctx the parse tree
	 */
	void enterPauseLine(@NotNull LDrawParser.PauseLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#pauseLine}.
	 * @param ctx the parse tree
	 */
	void exitPauseLine(@NotNull LDrawParser.PauseLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#decInt}.
	 * @param ctx the parse tree
	 */
	void enterDecInt(@NotNull LDrawParser.DecIntContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#decInt}.
	 * @param ctx the parse tree
	 */
	void exitDecInt(@NotNull LDrawParser.DecIntContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#bfcNoClip}.
	 * @param ctx the parse tree
	 */
	void enterBfcNoClip(@NotNull LDrawParser.BfcNoClipContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#bfcNoClip}.
	 * @param ctx the parse tree
	 */
	void exitBfcNoClip(@NotNull LDrawParser.BfcNoClipContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#bfcClipWind}.
	 * @param ctx the parse tree
	 */
	void enterBfcClipWind(@NotNull LDrawParser.BfcClipWindContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#bfcClipWind}.
	 * @param ctx the parse tree
	 */
	void exitBfcClipWind(@NotNull LDrawParser.BfcClipWindContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(@NotNull LDrawParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(@NotNull LDrawParser.NumberContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#lineLine}.
	 * @param ctx the parse tree
	 */
	void enterLineLine(@NotNull LDrawParser.LineLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#lineLine}.
	 * @param ctx the parse tree
	 */
	void exitLineLine(@NotNull LDrawParser.LineLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#clearLine}.
	 * @param ctx the parse tree
	 */
	void enterClearLine(@NotNull LDrawParser.ClearLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#clearLine}.
	 * @param ctx the parse tree
	 */
	void exitClearLine(@NotNull LDrawParser.ClearLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#bfcPrefix}.
	 * @param ctx the parse tree
	 */
	void enterBfcPrefix(@NotNull LDrawParser.BfcPrefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#bfcPrefix}.
	 * @param ctx the parse tree
	 */
	void exitBfcPrefix(@NotNull LDrawParser.BfcPrefixContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#ldrdatModel}.
	 * @param ctx the parse tree
	 */
	void enterLdrdatModel(@NotNull LDrawParser.LdrdatModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#ldrdatModel}.
	 * @param ctx the parse tree
	 */
	void exitLdrdatModel(@NotNull LDrawParser.LdrdatModelContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#stepLine}.
	 * @param ctx the parse tree
	 */
	void enterStepLine(@NotNull LDrawParser.StepLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#stepLine}.
	 * @param ctx the parse tree
	 */
	void exitStepLine(@NotNull LDrawParser.StepLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#triLine}.
	 * @param ctx the parse tree
	 */
	void enterTriLine(@NotNull LDrawParser.TriLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#triLine}.
	 * @param ctx the parse tree
	 */
	void exitTriLine(@NotNull LDrawParser.TriLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#bfcNoCert}.
	 * @param ctx the parse tree
	 */
	void enterBfcNoCert(@NotNull LDrawParser.BfcNoCertContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#bfcNoCert}.
	 * @param ctx the parse tree
	 */
	void exitBfcNoCert(@NotNull LDrawParser.BfcNoCertContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#word}.
	 * @param ctx the parse tree
	 */
	void enterWord(@NotNull LDrawParser.WordContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#word}.
	 * @param ctx the parse tree
	 */
	void exitWord(@NotNull LDrawParser.WordContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#mpdEmbeddedModel}.
	 * @param ctx the parse tree
	 */
	void enterMpdEmbeddedModel(@NotNull LDrawParser.MpdEmbeddedModelContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#mpdEmbeddedModel}.
	 * @param ctx the parse tree
	 */
	void exitMpdEmbeddedModel(@NotNull LDrawParser.MpdEmbeddedModelContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#mpdNoFileLine}.
	 * @param ctx the parse tree
	 */
	void enterMpdNoFileLine(@NotNull LDrawParser.MpdNoFileLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#mpdNoFileLine}.
	 * @param ctx the parse tree
	 */
	void exitMpdNoFileLine(@NotNull LDrawParser.MpdNoFileLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#bfcMetaLine}.
	 * @param ctx the parse tree
	 */
	void enterBfcMetaLine(@NotNull LDrawParser.BfcMetaLineContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#bfcMetaLine}.
	 * @param ctx the parse tree
	 */
	void exitBfcMetaLine(@NotNull LDrawParser.BfcMetaLineContext ctx);

	/**
	 * Enter a parse tree produced by {@link LDrawParser#ldrawLines}.
	 * @param ctx the parse tree
	 */
	void enterLdrawLines(@NotNull LDrawParser.LdrawLinesContext ctx);
	/**
	 * Exit a parse tree produced by {@link LDrawParser#ldrawLines}.
	 * @param ctx the parse tree
	 */
	void exitLdrawLines(@NotNull LDrawParser.LdrawLinesContext ctx);
}