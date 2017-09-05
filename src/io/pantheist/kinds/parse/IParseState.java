package io.pantheist.kinds.parse;

import java.util.Collection;

public interface IParseState<S>
{
	Collection<? extends IParseOption<S>> options();
}
