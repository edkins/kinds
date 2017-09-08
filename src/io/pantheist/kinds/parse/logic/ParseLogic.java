package io.pantheist.kinds.parse.logic;

import io.pantheist.kinds.misc.Arrays;
import io.pantheist.kinds.parse.ParseOption;

public class ParseLogic
{
	public static LogicState init()
	{
		final LogicState theory = new LogicState();
		final LogicState semicolon = ls(eow(";"), ParseColorLogic.BLACK, theory);

		final LogicState statement = new LogicState();

		final LogicState colon = ls(eow(":"), ParseColorLogic.BLACK);

		final LogicState name = ls(eow("[a-z]+"), ParseColorLogic.VARIABLE);

		final LogicState expr_colon = ls("", ParseColorLogic.BLACK, name, colon, name);

		final LogicState global = ls(eow("global"), ParseColorLogic.KEYWORD, expr_colon, semicolon);

		statement.add(global);

		theory.add(statement);
		theory.add(ls("$", ParseColorLogic.BLACK));
		return ls(eow(""), ParseColorLogic.BLACK, theory);
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
