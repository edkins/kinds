package io.pantheist.kinds.parse.logic;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import io.pantheist.kinds.parse.logic.LogicParser.RContext;

public class TestLogicParser
{
	public static void main(final String[] args)
	{
		final CharStream charStream = new ANTLRInputStream("global x : set;");
		final LogicLexer lexer = new LogicLexer(charStream);
		final TokenStream tokenStream = new CommonTokenStream(lexer);
		final LogicParser parser = new LogicParser(tokenStream);

		final RContext r = parser.r();

		r.enterRule(new MyListener());
	}
}
