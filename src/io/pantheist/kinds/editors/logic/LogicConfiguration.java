package io.pantheist.kinds.editors.logic;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import io.pantheist.kinds.editors.ColorManager;
import io.pantheist.kinds.editors.WordDoubleClickStrategy;
import io.pantheist.kinds.parse.logic.LogicParser;

public class LogicConfiguration extends SourceViewerConfiguration
{
	private WordDoubleClickStrategy doubleClickStrategy;
	private final ColorManager colorManager;
	private final TokenColorer tokenColorer;

	public LogicConfiguration(final ColorManager colorManager)
	{
		this.colorManager = colorManager;
		this.tokenColorer = new TokenColorer(LogicParser.tokenNames);
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			final ISourceViewer sourceViewer,
			final String contentType)
	{
		if (doubleClickStrategy == null)
		{
			doubleClickStrategy = new WordDoubleClickStrategy();
		}
		return doubleClickStrategy;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer)
	{
		final PresentationReconciler reconciler = new PresentationReconciler();
		final LogicDamagerRepairer dr = new LogicDamagerRepairer(colorManager,
				tokenColorer);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		return reconciler;
	}

}