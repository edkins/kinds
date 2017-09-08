package io.pantheist.kinds.parse.logic;

import io.pantheist.kinds.misc.Arrays;
import io.pantheist.kinds.parse.ParseOption;

public class ParseLogic
{
	public static LogicState init()
	{
		return ls(eow("def"), ParseColorLogic.BLACK);
	}

	private static String eow(final String regex)
	{
		return regex + "[ \\t\\r\\n]*";
	}

	private static LogicState ls(final String regex, final ParseColorLogic color, final LogicState... items)
	{
		return new LogicState().add(new ParseOption<>(regex, color, Arrays.reverse(items)));
	}

}
