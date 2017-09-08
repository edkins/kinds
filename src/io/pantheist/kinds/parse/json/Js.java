package io.pantheist.kinds.parse.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.pantheist.kinds.parse.IParseOption;
import io.pantheist.kinds.parse.IParseState;

public class Js implements IParseState<Js>
{
	private final List<IParseOption<Js>> options;

	Js()
	{
		this.options = new ArrayList<>();
	}

	/**
	 * Mutates this
	 */
	public Js add(final IParseOption<Js> option)
	{
		this.options.add(option);
		return this;
	}

	public Js add(final Js js)
	{
		this.options.addAll(js.options());
		return this;
	}

	@Override
	public Collection<IParseOption<Js>> options()
	{
		return options;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		for (final IParseOption<Js> option : options)
		{
			sb.append(option);
			sb.append(' ');
		}
		return sb.toString();
	}
}