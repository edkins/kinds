package io.pantheist.kinds.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import io.pantheist.kinds.parse.json.Js;
import io.pantheist.kinds.parse.json.ParseJson;

public class JsonConfiguration extends SourceViewerConfiguration
{
	private XMLDoubleClickStrategy doubleClickStrategy;
	private final ColorManager colorManager;
	private final Js initialParseState;

	public JsonConfiguration(final ColorManager colorManager)
	{
		this.colorManager = colorManager;
		this.initialParseState = ParseJson.init();
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			final ISourceViewer sourceViewer,
			final String contentType)
	{
		if (doubleClickStrategy == null)
		{
			doubleClickStrategy = new XMLDoubleClickStrategy();
		}
		return doubleClickStrategy;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer)
	{
		final PresentationReconciler reconciler = new PresentationReconciler();
		final JsonDamagerRepairer<Js> dr = new JsonDamagerRepairer<>(colorManager, initialParseState);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		return reconciler;
	}

}