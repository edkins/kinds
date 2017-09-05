package io.pantheist.kinds.parse;

public interface IParseListener
{
	public void acceptSequence(int start, int end, IParseColor color);
}
