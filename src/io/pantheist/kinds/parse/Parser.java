package io.pantheist.kinds.parse;

import java.util.regex.Matcher;

public final class Parser<S>
{
	private final CharSequence text;
	private int position;
	private final IParseStack<IParseState<S>> stack;
	private boolean finished;

	public Parser(final IParseState<S> initialState, final CharSequence text)
	{
		this.text = text;
		this.position = 0;
		this.stack = new ParseStackSimple<>();
		this.finished = false;

		this.stack.push(initialState);
	}

	public void parse() throws ParserException
	{
		if (finished)
		{
			throw new ParserException("Already parsed");
		}

		while (position != text.length())
		{
			considerOptions();
		}

		if (!stack.isEmpty())
		{
			throw new ParserException("Stack is not empty at the end");
		}

		finished = true;
	}

	private void considerOptions() throws ParserException
	{
		final IParseState<S> state = stack.pop();
		for (final IParseOption<S> option : state.options())
		{
			if (considerOption(option))
			{
				return;
			}
		}

		throw nothingMatchesException(state);
	}

	private ParserException nothingMatchesException(final IParseState<S> state)
	{
		final StringBuilder msg = new StringBuilder();
		msg.append("Nothing matches:");
		for (final IParseOption<S> option : state.options())
		{
			msg.append(" /").append(option.pattern().toString()).append('/');
		}
		msg.append("\nat text: ").append(text.subSequence(position, Math.min(position + 10, text.length())));
		return new ParserException(msg.toString());
	}

	private boolean considerOption(final IParseOption<S> option) throws ParserException
	{
		final CharSequence subsequence = text.subSequence(position, text.length());
		final Matcher matcher = option.pattern().matcher(subsequence);
		if (matcher.find())
		{
			System.out.println(option.color() + " " + matcher.group());

			for (final IParseState<S> item : option.stack())
			{
				stack.push(item);
			}
			position += matcher.end();
			return true;
		}
		else
		{
			return false;
		}
	}
}
