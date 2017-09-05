package io.pantheist.kinds.parse.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.pantheist.kinds.parse.IParseState;
import io.pantheist.kinds.parse.json.ParseJson.JOption;

public class Js implements IParseState<Js>
{
	private final List<JOption> options;

	Js()
	{
		this.options = new ArrayList<>();
	}

	/**
	 * Mutates this
	 */
	public Js add(final JOption option)
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
	public Collection<JOption> options()
	{
		return options;
	}

}