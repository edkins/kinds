package io.pantheist.kinds.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import io.pantheist.kinds.natures.DogmaNature;

/**
 * This is a sample new wizard. Its role is to create a new file
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "mpe". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class NewDogmaWizard extends Wizard implements INewWizard
{
	private NewDogmaWizardPage page;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public NewDogmaWizard()
	{
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */
	@Override
	public void addPages()
	{
		page = new NewDogmaWizardPage();
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	@Override
	public boolean performFinish()
	{
		final String projectName = page.getProjectName();
		final IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(final IProgressMonitor monitor) throws InvocationTargetException
			{
				createProject(monitor, projectName);
			}
		};
		try
		{
			getContainer().run(true, false, op);
		}
		catch (final InterruptedException e)
		{
			return false;
		}
		catch (final InvocationTargetException e)
		{
			final Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection)
	{
	}

	private void createProject(final IProgressMonitor monitor, final String projectName)
			throws InvocationTargetException
	{
		try
		{
			final IProjectDescription projectDesc = ResourcesPlugin.getWorkspace()
					.newProjectDescription(projectName);

			final String natureId = DogmaNature.class.getName();
			projectDesc.setNatureIds(new String[] { natureId });

			final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			project.create(projectDesc, monitor);
			project.open(monitor);
		}
		catch (final CoreException e)
		{
			throw new InvocationTargetException(e);
		}
		finally
		{
			monitor.done();
		}
	}
}