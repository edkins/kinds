package io.pantheist.kinds.editors.logic;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.custom.StyleRange;

import io.pantheist.kinds.editors.ColorManager;

public class LogicParseTreeListener implements ParseTreeListener, ANTLRErrorListener
{
	private static class StackState
	{
		int rule;
		int tokenCount;
		boolean containsErrors;
		int lastGoodIndex;

		public StackState(final int rule, final int lastGoodIndex)
		{
			this.rule = rule;
			this.tokenCount = 0;
			this.containsErrors = false;
			this.lastGoodIndex = lastGoodIndex;
		}
	}

	private static final ParseColorLogic[][] colors = {
			{ ParseColorLogic.KEYWORD, ParseColorLogic.VARIABLE, ParseColorLogic.BLACK, ParseColorLogic.VARIABLE,
					ParseColorLogic.BLACK, ParseColorLogic.BLACK }
	};

	private final List<StackState> stack;
	private final TextPresentation presentation;
	private final ColorManager colorManager;

	public LogicParseTreeListener(final TextPresentation presentation, final ColorManager colorManager)
	{
		this.presentation = presentation;
		this.stack = new ArrayList<>();
		this.colorManager = colorManager;
	}

	@Override
	public void enterEveryRule(final ParserRuleContext arg0)
	{
		System.out.println("enterEveryRule " + arg0.getRuleIndex());
		push(arg0.getRuleIndex(), arg0.getStart().getStartIndex());
	}

	@Override
	public void exitEveryRule(final ParserRuleContext arg0)
	{
		System.out.println("exitEveryRule " + arg0.getRuleIndex());
		pop(arg0.getRuleIndex(), arg0.getStop().getStopIndex() + 1);
	}

	@Override
	public void visitErrorNode(final ErrorNode arg0)
	{
		System.out.println("visitErrorNode");
	}

	@Override
	public void visitTerminal(final TerminalNode node)
	{
		final StackState top = this.stack.get(this.stack.size() - 1);
		System.out.println("visitTerminal errors=" + top.containsErrors);
		if (!top.containsErrors)
		{
			final int t = top.tokenCount;
			if (t >= colors[top.rule].length)
			{
				throw new IllegalStateException("Too many tokens for rule " + top.rule);
			}
			final int stopIndex = node.getSymbol().getStopIndex() + 1;
			top.tokenCount++;
			top.lastGoodIndex = stopIndex;
			addStyleRange(node.getSymbol().getStartIndex(), stopIndex,
					colors[top.rule][t]);
		}
	}

	private void addStyleRange(final int startIndex, final int stopIndex,
			final ParseColorLogic color)
	{
		System.out.println(String.format("addStyleRange %s %s %s", startIndex, stopIndex, color));
		presentation.addStyleRange(new StyleRange(startIndex,
				stopIndex - startIndex,
				colorManager.getColor(color.rgb()),
				null));
	}

	private void push(final int rule, final int startIndex)
	{
		stack.add(new StackState(rule, startIndex));
	}

	private void pop(final int rule, final int stopIndex)
	{
		final int i = stack.size() - 1;
		if (stack.size() < 1)
		{
			throw new IllegalStateException("pop: empty stack");
		}
		else if (stack.get(i).rule != rule)
		{
			throw new IllegalStateException("Expecting rule " + rule + ", got rule "
					+ stack.get(i).rule);
		}

		if (stack.get(i).containsErrors)
		{
			final int startIndex = stack.get(i).lastGoodIndex;
			addStyleRange(startIndex,
					stopIndex,
					ParseColorLogic.INVALID);
		}

		stack.remove(i);
	}

	@Override
	public void reportAmbiguity(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final boolean arg4,
			final BitSet arg5, final ATNConfigSet arg6)
	{
	}

	@Override
	public void reportAttemptingFullContext(final Parser arg0, final DFA arg1, final int arg2, final int arg3,
			final BitSet arg4, final ATNConfigSet arg5)
	{
	}

	@Override
	public void reportContextSensitivity(final Parser arg0, final DFA arg1, final int arg2, final int arg3,
			final int arg4, final ATNConfigSet arg5)
	{
	}

	@Override
	public void syntaxError(final Recognizer<?, ?> arg0, final Object arg1, final int arg2, final int arg3,
			final String arg4,
			final RecognitionException arg5)
	{
		System.out.println("syntax error");
		stack.get(stack.size() - 1).containsErrors = true;
	}
}
