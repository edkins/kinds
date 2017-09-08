package io.pantheist.kinds.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;

public class WordDoubleClickStrategy implements ITextDoubleClickStrategy
{
	protected ITextViewer fText;

	@Override
	public void doubleClicked(final ITextViewer part)
	{
		final int pos = part.getSelectedRange().x;

		if (pos < 0)
		{
			return;
		}

		fText = part;
		selectWord(pos);
	}

	protected boolean selectWord(final int caretPos)
	{

		final IDocument doc = fText.getDocument();
		int startPos, endPos;

		try
		{

			int pos = caretPos;
			char c;

			while (pos >= 0)
			{
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c))
				{
					break;
				}
				--pos;
			}

			startPos = pos;

			pos = caretPos;
			final int length = doc.getLength();

			while (pos < length)
			{
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c))
				{
					break;
				}
				++pos;
			}

			endPos = pos;
			selectRange(startPos, endPos);
			return true;

		}
		catch (final BadLocationException x)
		{
		}

		return false;
	}

	private void selectRange(final int startPos, final int stopPos)
	{
		final int offset = startPos + 1;
		final int length = stopPos - offset;
		fText.setSelectedRange(offset, length);
	}
}