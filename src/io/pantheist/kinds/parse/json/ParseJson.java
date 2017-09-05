package io.pantheist.kinds.parse.json;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.pantheist.kinds.parse.IParseColor;
import io.pantheist.kinds.parse.IParseListener;
import io.pantheist.kinds.parse.IParseOption;
import io.pantheist.kinds.parse.IParseState;
import io.pantheist.kinds.parse.Parser;

public class ParseJson
{
	public static void main(final String[] args)
	{
		final String text = "[x";
		new Parser<>(init(), text, new IParseListener() {

			@Override
			public void acceptSequence(final int start, final int end, final IParseColor color)
			{
				System.out.println(color + " " + text.subSequence(start, end));
			}
		}).parse();
	}

	public static Js init()
	{
		final Js value = new Js(); // mutable; we'll add things to this.

		// Empty array
		value.add(j(eow("\\[") + eow("\\]"), ParseColorJson.BLACK));

		// Array
		final Js arrayMiddle = new Js();
		arrayMiddle.add(j(eow(","), ParseColorJson.BLACK, value, arrayMiddle));
		arrayMiddle.add(j(eow("\\]"), ParseColorJson.BLACK));
		value.add(j(eow("\\["), ParseColorJson.BLACK, value, arrayMiddle));

		// Empty object
		value.add(j(eow("\\{") + eow("\\}"), ParseColorJson.BLACK));

		// Object
		final Js objectMiddle = new Js();
		final Js label = string(ParseColorJson.LABEL);
		final Js colon = j(eow(":"), ParseColorJson.BLACK);
		objectMiddle.add(j(eow(","), ParseColorJson.BLACK, label, colon, value, objectMiddle));
		objectMiddle.add(j(eow("\\}"), ParseColorJson.BLACK));
		value.add(j(eow("\\{"), ParseColorJson.BLACK, label, colon, value, objectMiddle));

		// Misc
		value.add(j(eow("true"), ParseColorJson.TOKEN));
		value.add(j(eow("false"), ParseColorJson.TOKEN));
		value.add(j(eow("null"), ParseColorJson.TOKEN));

		// Number
		value.add(j(eow("(?:0|[1-9][0-9]*)(?:\\.[0-9]+)?(?:[eE][+-]?[0-9]+)?"), ParseColorJson.NUMBER));

		// String
		value.add(string(ParseColorJson.STRING));

		return j(eow(""), ParseColorJson.BLACK, value);
	}

	private static Js string(final ParseColorJson color)
	{
		final Js string = new Js();

		string.add(j("[ -~&&[^\\\\\"]]+", color, string));
		string.add(j("\\\\[\"\\\\/bfnrt]", ParseColorJson.SPECIAL_CHAR, string));
		string.add(j("\\\\u[0-9a-fA-F]{4}", ParseColorJson.SPECIAL_CHAR, string));
		string.add(j(eow("\""), color));
		return j("\"", color, string);
	}

	private static String eow(final String regex)
	{
		return regex + "[ \\t\\r\\n]*";
	}

	private static Js j(final String regex, final ParseColorJson color, final Js... items)
	{
		return new Js().add(new JOption(regex, color, items));
	}

	private static Pattern compile(final String regex)
	{
		return Pattern.compile("^" + regex);
	}

	static final class JOption implements IParseOption<Js>
	{
		private final Pattern pattern;
		private final ParseColorJson color;
		private final List<IParseState<Js>> stack;

		protected JOption(final String regex, final ParseColorJson color, final Js... stack)
		{
			this.pattern = compile(regex);
			this.color = color;

			this.stack = new ArrayList<>();
			for (int i = stack.length - 1; i >= 0; i--)
			{
				this.stack.add(stack[i]);
			}
		}

		@Override
		public final Pattern pattern()
		{
			return pattern;
		}

		@Override
		public final ParseColorJson color()
		{
			return color;
		}

		@Override
		public List<IParseState<Js>> stack()
		{
			return stack;
		}
	}
}
