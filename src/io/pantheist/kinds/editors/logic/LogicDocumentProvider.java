package io.pantheist.kinds.editors.logic;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class LogicDocumentProvider extends FileDocumentProvider
{

	@Override
	protected IDocument createDocument(final Object element) throws CoreException
	{
		final IDocument document = super.createDocument(element);
		if (document != null)
		{
		}
		return document;
	}
}