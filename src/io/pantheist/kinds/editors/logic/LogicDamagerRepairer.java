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

import io.pantheist.kinds.editors.ColorManager;
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

		final LogicParseTreeListener listener = new LogicParseTreeListener(presentation, colorManager);
		parser.addErrorListener(listener);
		parser.addParseListener(listener);

		parser.r();
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
		System.out.println("Damage region length is " + document.getLength());
		return new Region(0, document.getLength());
	}

}
