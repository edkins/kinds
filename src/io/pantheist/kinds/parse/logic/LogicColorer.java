package io.pantheist.kinds.parse.logic;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.pantheist.kinds.parse.IParseListener;
import io.pantheist.kinds.parse.logic.LogicParser.RContext;

public class LogicColorer
{
	private final IParseListener listener;

	public LogicColorer(final IParseListener listener)
	{
		this.listener = listener;
	}

	public void visitR(final RContext ctx)
	{
		if (ctx != null && ctx.getChildCount() == 5)
		{
			visitKeyword((TerminalNode) ctx.getChild(0));
			visitVariable((TerminalNode) ctx.getChild(1));
		}
	}

	private void visitKeyword(final TerminalNode node)
	{
		colorTerminal(node, ParseColorLogic.KEYWORD);
	}

	private void visitVariable(final TerminalNode node)
	{
		colorTerminal(node, ParseColorLogic.VARIABLE);
	}

	private void colorTerminal(final TerminalNode node, final ParseColorLogic color)
	{
		final Token symbol = node.getSymbol();
		listener.acceptSequence(symbol.getStartIndex(), symbol.getStopIndex() + 1, color);
	}
}
