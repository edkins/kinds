package io.pantheist.kinds.parse.logic;

import io.pantheist.kinds.misc.Arrays;
import io.pantheist.kinds.parse.ParseOption;

public class ParseLogic
{
	public static LogicState init()
	{
		final LogicMut theory = new LogicMut();

		final LogicMut statement = new LogicMut();

		final LogicState name = ls(eow("[a-z]+"), ParseColorLogic.VARIABLE);

		final int n = 3;
		final LogicOpTable opTable = new LogicOpTable(n);

		final LogicMut atom = new LogicMut();
		atom.add(ls(eow("\\("), ParseColorLogic.BLACK, atom, opTable.get(0)));
		atom.add(name);

		opTable.add(0, ls(eowc(",", "\\|\\|"), ParseColorLogic.BLACK, atom, opTable.get(0)));
		opTable.add(1, ls(eowc("=", ":"), ParseColorLogic.BLACK, atom, opTable.get(2)));
		opTable.add(2, ls(eowc("\\+", "-"), ParseColorLogic.BLACK, atom, opTable.get(2)));
		opTable.add(3, ls(eowc("\\*", "\\/"), ParseColorLogic.BLACK, atom, opTable.get(3)));
		opTable.add(n, ls(eow("\\)"), ParseColorLogic.BLACK));
		opTable.add(n, ls(eow(";"), ParseColorLogic.BLACK));

		statement.add(ls(eow("global"), ParseColorLogic.KEYWORD, atom, opTable.get(0), theory));
		statement.add(ls(eow("def"), ParseColorLogic.KEYWORD, atom, opTable.get(0), theory));

		theory.add(statement);
		theory.add(ls("$", ParseColorLogic.BLACK));
		return ls(eow(""), ParseColorLogic.BLACK, theory);
	}

	private static String eow(final String regex)
	{
		return regex + "[ \\t\\r\\n]*";
	}

	private static String eowc(final String... regexes)
	{
		return eow("(?:" + String.join("|", regexes) + ")");
	}

	private static LogicState ls(final String regex, final ParseColorLogic color, final LogicState... items)
	{
		return LogicState.singleton(new ParseOption<>(regex, color, Arrays.reverse(items)));
	}

}
