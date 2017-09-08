package io.pantheist.kinds.editors.json;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class JsonDocumentProvider extends FileDocumentProvider
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