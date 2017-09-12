package io.pantheist.kinds.editors.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.Token;

public class TokenColorer
{
	private final List<ParseColorLogic> tokenColors;

	private static final Pattern quotedKeyword = Pattern.compile("^'[A-Za-z]+'$");

	private static final Pattern id = Pattern.compile("^ID$");

	//private static final Pattern quotedThing = Pattern.compile("^'.*'$");

	public TokenColorer(final String[] tokenNames)
	{
		tokenColors = new ArrayList<>();
		for (final String tokenName : tokenNames)
		{
			tokenColors.add(asdf(tokenName));
		}
	}

	private static ParseColorLogic asdf(final String tokenName)
	{
		if (quotedKeyword.matcher(tokenName).matches())
		{
			return ParseColorLogic.KEYWORD;
		}
		else if (id.matcher(tokenName).matches())
		{
			return ParseColorLogic.VARIABLE;
		}
		else
		{
			return ParseColorLogic.BLACK;
		}

	}

	public ParseColorLogic color(final Token token)
	{
		if (token.getType() >= 0 && token.getType() < tokenColors.size())
		{
			return tokenColors.get(token.getType());
		}
		else
		{
			return ParseColorLogic.BLACK;
		}
	}
}
