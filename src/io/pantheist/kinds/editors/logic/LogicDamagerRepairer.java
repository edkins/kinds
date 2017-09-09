package io.pantheist.kinds.editors.logic;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.swt.custom.StyleRange;

import io.pantheist.kinds.editors.ColorManager;
import io.pantheist.kinds.parse.IParseColor;
import io.pantheist.kinds.parse.IParseListener;
import io.pantheist.kinds.parse.logic.LogicColorer;
import io.pantheist.kinds.parse.logic.LogicLexer;
import io.pantheist.kinds.parse.logic.LogicParser;

public class LogicDamagerRepairer implements IPresentationDamager, IPresentationRepairer
{
	private IDocument document;
	private final ColorManager colorManager;

	public LogicDamagerRepairer(final ColorManager colorManager)
	{
		this.colorManager = colorManager;
	}

	@Override
	public void createPresentation(final TextPresentation presentation, final ITypedRegion damage)
	{
		final DocumentCharStream charStream = new DocumentCharStream(document);

		final LogicLexer lexer = new LogicLexer(charStream);
		final TokenStream tokenStream = new CommonTokenStream(lexer);
		final LogicParser parser = new LogicParser(tokenStream);

		final LogicColorer colorer = new LogicColorer(new IParseListener() {

			@Override
			public void acceptSequence(final int start, final int end, final IParseColor color)
			{
				if (start >= 0 && start <= end && end <= document.getLength())
				{
					presentation
							.addStyleRange(
									new StyleRange(start, end - start, colorManager.getColor(color.rgb()), null));
				}
				else
				{
					System.out.println("start=" + start + ",end=" + end);
				}
			}
		});

		colorer.visitR(parser.r());
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
