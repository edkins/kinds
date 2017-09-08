package io.pantheist.kinds.editors.logic;

import org.eclipse.ui.editors.text.TextEditor;

import io.pantheist.kinds.editors.ColorManager;

public class LogicEditor extends TextEditor {

	private ColorManager colorManager;

	public LogicEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new LogicConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
