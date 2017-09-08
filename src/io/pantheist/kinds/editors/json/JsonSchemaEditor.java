package io.pantheist.kinds.editors.json;

import org.eclipse.ui.editors.text.TextEditor;

import io.pantheist.kinds.editors.ColorManager;

public class JsonSchemaEditor extends TextEditor {

	private ColorManager colorManager;

	public JsonSchemaEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new JsonConfiguration(colorManager));
		setDocumentProvider(new JsonDocumentProvider());
	}
	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
