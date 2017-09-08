package io.pantheist.kinds.parse;

import java.util.List;
import java.util.regex.Pattern;

public interface IParseOption<S>
{
	Pattern pattern();

	IParseColor color();

	List<? extends IParseState<S>> stack();
}
