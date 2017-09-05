package io.pantheist.kinds.parse.json;

import org.eclipse.swt.graphics.RGB;

import io.pantheist.kinds.parse.IParseColor;

public enum ParseColorJson implements IParseColor
{
	BLACK(0, 0, 0),
	LABEL(0, 128, 128),
	STRING(128, 0, 0),
	SPECIAL_CHAR(64, 0, 64),
	NUMBER(0, 0, 224),
	TOKEN(255, 0, 0),
	INVALID(255, 0, 255);

	private RGB rgb;

	private ParseColorJson(final int r, final int g, final int b)
	{
		this.rgb = new RGB(r, g, b);
	}

	@Override
	public RGB rgb()
	{
		return rgb;
	}
}
