package io.pantheist.kinds.parse.logic;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;

public class LogicOpTable
{
	private final List<LogicMut> states;

	public LogicOpTable(final int maxLevel)
	{
		this.states = new ArrayList<>();
		for (int i = 0; i <= maxLevel; i++)
		{
			this.states.add(new LogicMut());
		}
	}

	public LogicState get(final int i)
	{
		return states.get(i);
	}

	public LogicOpTable add(final int level, final LogicState ls)
	{
		Assert.isTrue(level >= 0 && level < states.size(), "Op table level out of bounds");
		for (int i = 0; i <= level; i++)
		{
			states.get(i).add(ls);
		}
		return this;
	}
}
