package io.pantheist.kinds.parse.json;

import io.pantheist.kinds.misc.Arrays;
import io.pantheist.kinds.parse.ParseOption;

public class ParseJson
{
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
		return new Js().add(new ParseOption<>(regex, color, Arrays.reverse(items)));
	}
}
