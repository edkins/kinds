package io.pantheist.kinds.parse.components;

import java.util.regex.Pattern;

public final class ParseAtom implements IParseComponent
{
	private final Pattern pattern;

	public ParseAtom(final String regex)
	{
		this.pattern = Pattern.compile(regex);
	}

}
