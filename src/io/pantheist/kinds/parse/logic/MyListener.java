package io.pantheist.kinds.parse.logic;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import io.pantheist.kinds.parse.logic.LogicParser.RContext;

public final class MyListener implements LogicListener
{
	@Override
	public void visitTerminal(final TerminalNode arg0)
	{
		System.out.println("visitTerminal " + arg0.getText());
	}

	@Override
	public void visitErrorNode(final ErrorNode arg0)
	{
	}

	@Override
	public void exitEveryRule(final ParserRuleContext arg0)
	{
	}

	@Override
	public void enterEveryRule(final ParserRuleContext arg0)
	{
	}

	@Override
	public void exitR(final RContext ctx)
	{
		System.out.println("exitR");
	}

	@Override
	public void enterR(final RContext ctx)
	{
		System.out.println(ctx.toStringTree());
		System.out.println(((TerminalNode) ctx.getChild(0)).getSymbol().getStartIndex());
		System.out.println(((TerminalNode) ctx.getChild(0)).getSymbol().getStopIndex());
		System.out.println("enterR");
	}
}