package io.pantheist.kinds.parse.components;

import java.util.regex.Pattern;

import io.pantheist.kinds.parse.IParseColor;

public interface IParseComponentOption
{
	Pattern pattern();

	IParseColor color();

	ParseOperation operation();

	IParseComponentOption detail();
}
