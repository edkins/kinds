package io.pantheist.kinds.parse.logic;

import java.util.ArrayList;
import java.util.Collection;

import io.pantheist.kinds.parse.IParseState;
import io.pantheist.kinds.parse.ParseOption;

public abstract class LogicState implements IParseState<LogicState>
{
	@Override
	public abstract Collection<ParseOption<LogicState>> options();

	public static LogicState singleton(final ParseOption<LogicState> option)
	{
		return new Singleton(option);
	}

	private static final class Singleton extends LogicState
	{
		private final ArrayList<ParseOption<LogicState>> options;

		private Singleton(final ParseOption<LogicState> option)
		{
			this.options = new ArrayList<>();
			this.options.add(option);

		}

		@Override
		public Collection<ParseOption<LogicState>> options()
		{
			return options;
		}
	}
}
