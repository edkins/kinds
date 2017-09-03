package io.pantheist.kinds.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class JsonSchemaEditor extends TextEditor {

	private ColorManager colorManager;

	public JsonSchemaEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
