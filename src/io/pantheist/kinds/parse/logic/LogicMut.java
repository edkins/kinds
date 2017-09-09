package io.pantheist.kinds.parse.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.pantheist.kinds.parse.ParseOption;

public final class LogicMut extends LogicState
{
	private final List<LogicState> states;
	private List<ParseOption<LogicState>> options;

	public LogicMut()
	{
		this.states = new ArrayList<>();
		this.options = null;
	}

	public LogicState add(final LogicState ls)
	{
		this.states.add(ls);
		return this;
	}

	@Override
	public Collection<ParseOption<LogicState>> options()
	{
		if (options == null)
		{
			options = new ArrayList<>();
			for (final LogicState ls : states)
			{
				options.addAll(ls.options());
			}
		}
		return options;
	}
}