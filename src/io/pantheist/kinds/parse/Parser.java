package io.pantheist.kinds.parse;

import java.util.regex.Matcher;

import io.pantheist.kinds.parse.json.ParseColorJson;

public final class Parser<S>
{
	private final CharSequence text;
	private int position;
	private final IParseStack<IParseState<S>> stack;
	private boolean finished;
	private final IParseListener listener;

	public Parser(final IParseState<S> initialState, final CharSequence text, final IParseListener listener)
	{
		this.text = text;
		this.position = 0;
		this.stack = new ParseStackSimple<>();
		this.finished = false;
		this.listener = listener;

		this.stack.push(initialState);
	}

	public void parse()
	{
		if (finished)
		{
			throw new IllegalStateException("Already parsed");
		}

		try
		{
			while (!stack.isEmpty())
			{
				considerOptions();
			}

			if (position != text.length())
			{
				throw new ParserException("Stack is empty and still text left to consume");
			}
		}
		catch (final ParserException ex)
		{
			System.out.println(ex.getMessage());
			listener.acceptSequence(position, text.length(), ParseColorJson.INVALID);
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
			System.out.println(spaces() + text.subSequence(position, position + matcher.end()));
			for (final IParseState<S> item : option.stack())
			{
				stack.push(item);
			}

			final int newPosition = position + matcher.end();
			listener.acceptSequence(position, newPosition, option.color());

			position = newPosition;
			return true;
		}
		else
		{
			return false;
		}
	}

	private String spaces()
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < stack.size(); i++)
		{
			sb.append(' ');
		}
		return sb.toString();
	}
}
