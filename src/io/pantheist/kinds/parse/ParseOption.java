package io.pantheist.kinds.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public final class ParseOption<T> implements IParseOption<T>
{
	private final Pattern pattern;
	private final IParseColor color;
	private final List<? extends IParseState<T>> stack;

	public ParseOption(final String regex, final IParseColor color,
			final Collection<? extends IParseState<T>> stack)
	{
		this.pattern = compile(regex);
		this.color = color;

		this.stack = new ArrayList<>(stack);
	}

	private static Pattern compile(final String regex)
	{
		return Pattern.compile("^" + regex);
	}

	@Override
	public final Pattern pattern()
	{
		return pattern;
	}

	@Override
	public final IParseColor color()
	{
		return color;
	}

	@Override
	public List<? extends IParseState<T>> stack()
	{
		return stack;
	}

	@Override
	public String toString()
	{
		return pattern.toString();
	}
}