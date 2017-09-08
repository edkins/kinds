package io.pantheist.kinds.parse.logic;

import org.eclipse.swt.graphics.RGB;

import io.pantheist.kinds.parse.IParseColor;

public enum ParseColorLogic implements IParseColor
{
	BLACK(0, 0, 0),
	KEYWORD(255, 0, 0),
	VARIABLE(0, 0, 128),
	INVALID(255, 0, 255);

	private RGB rgb;

	private ParseColorLogic(final int r, final int g, final int b)
	{
		this.rgb = new RGB(r, g, b);
	}

	@Override
	public RGB rgb()
	{
		return rgb;
	}
}
