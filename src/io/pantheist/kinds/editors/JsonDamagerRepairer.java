package io.pantheist.kinds.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.swt.custom.StyleRange;

import io.pantheist.kinds.parse.IParseState;
import io.pantheist.kinds.parse.Parser;

public class JsonDamagerRepairer<T> implements IPresentationDamager, IPresentationRepairer
{
	private IDocument document;
	private final IParseState<T> initialParseState;
	private final ColorManager colorManager;

	public JsonDamagerRepairer(final ColorManager colorManager, final IParseState<T> initialParseState)
	{
		this.initialParseState = initialParseState;
		this.colorManager = colorManager;
	}

	@Override
	public void createPresentation(final TextPresentation presentation, final ITypedRegion damage)
	{
		final CharSequence text = new DocumentCharSequence(document, 0, document.getLength());

		new Parser<>(initialParseState, text, (start, end, color) -> {
			presentation.addStyleRange(new StyleRange(start, end - start, colorManager.getColor(color.rgb()), null));
		}).parse();
	}

	private static class DocumentCharSequence implements CharSequence
	{
		private final IDocument document;
		private final int start;
		private final int end;

		private DocumentCharSequence(final IDocument document, final int start, final int end)
		{
			this.document = document;
			this.start = start;
			this.end = end;
		}

		@Override
		public char charAt(final int index)
		{
			try
			{
				return document.getChar(start + index);
			}
			catch (final BadLocationException e)
			{
				throw new IndexOutOfBoundsException(e.getMessage());
			}
		}

		@Override
		public int length()
		{
			return end - start;
		}

		@Override
		public CharSequence subSequence(final int start, final int end)
		{
			if (start < 0 || end < 0 || start > end || start > this.end || end > this.end)
			{
				throw new IndexOutOfBoundsException();
			}
			return new DocumentCharSequence(document, this.start + start, this.start + end);
		}

	}

	@Override
	public void setDocument(final IDocument document)
	{
		this.document = document;
	}

	@Override
	public IRegion getDamageRegion(final ITypedRegion partition, final DocumentEvent event,
			final boolean documentPartitioningChanged)
	{
		return new Region(0, document.getLength());
	}

}
