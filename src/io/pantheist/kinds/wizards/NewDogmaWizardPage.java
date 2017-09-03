package io.pantheist.kinds.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class NewDogmaWizardPage extends WizardPage
{
	private Text projectNameText;

	/**
	 * Constructor for SampleNewWizardPage.
	 *
	 * @param pageName
	 */
	public NewDogmaWizardPage()
	{
		super("wizardPage");
		setTitle("Dogma");
		setDescription(
				"This wizard creates a new dogma, a project specifying the kinds of object to be used in other projects");
	}

	@Override
	public void createControl(final Composite parent)
	{
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		final Label label = new Label(container, SWT.NULL);
		label.setText("&Project name:");

		projectNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		projectNameText.setLayoutData(gd);
		projectNameText.addModifyListener(e -> dialogChanged());
		projectNameText.setFocus();

		dialogChanged();
		setControl(container);
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged()
	{
		final String fileName = getProjectName();

		if (fileName.length() == 0)
		{
			updateStatus("Project name must be specified");
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(final String message)
	{
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getProjectName()
	{
		return projectNameText.getText();
	}
}