package io.pantheist.kinds.parse;

public interface IParseStack<S>
{
	void push(S obj);

	S pop() throws ParserException;

	boolean isEmpty();
}
