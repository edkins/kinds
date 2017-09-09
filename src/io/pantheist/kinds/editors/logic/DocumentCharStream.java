package io.pantheist.kinds.editors.logic;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.misc.Interval;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

public class DocumentCharStream implements CharStream
{
	private final IDocument document;
	private int position;

	public DocumentCharStream(final IDocument document)
	{
		this.document = document;
		this.position = 0;
	}

	@Override
	public int LA(final int i)
	{
		try
		{
			if (i == -1 && position > 0)
			{
				return document.getChar(position - 1);
			}
			else if (i >= 1)
			{
				return document.getChar(position + i - 1);
			}
			else
			{
				throw new UnsupportedOperationException();
			}
		}
		catch (final BadLocationException e)
		{
			return EOF;
		}
	}

	@Override
	public void consume()
	{
		position++;
	}

	@Override
	public String getSourceName()
	{
		return "document"; // TODO: actual filename?
	}

	@Override
	public int index()
	{
		return position;
	}

	@Override
	public int mark()
	{
		return 0;
	}

	@Override
	public void release(final int marker)
	{
	}

	@Override
	public void seek(final int index)
	{
		if (index < 0)
		{
			throw new IllegalArgumentException();
		}
		else if (index >= document.getLength())
		{
			position = document.getLength();
		}
		else
		{
			position = index;
		}
	}

	@Override
	public int size()
	{
		return document.getLength();
	}

	@Override
	public String getText(final Interval interval)
	{
		try
		{
			return document.get(interval.a, interval.b - interval.a + 1);
		}
		catch (final BadLocationException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

}
