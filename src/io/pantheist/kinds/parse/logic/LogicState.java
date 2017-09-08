package io.pantheist.kinds.parse.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.pantheist.kinds.parse.IParseState;
import io.pantheist.kinds.parse.ParseOption;

public class LogicState implements IParseState<LogicState>
{
	private final List<ParseOption<LogicState>> options;

	LogicState()
	{
		this.options = new ArrayList<>();
	}

	/**
	 * Mutates this
	 */
	public LogicState add(final ParseOption<LogicState> option)
	{
		this.options.add(option);
		return this;
	}

	public LogicState add(final LogicState js)
	{
		this.options.addAll(js.options());
		return this;
	}

	@Override
	public Collection<ParseOption<LogicState>> options()
	{
		return options;
	}

}
