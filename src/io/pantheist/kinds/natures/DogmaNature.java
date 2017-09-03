package io.pantheist.kinds.natures;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class DogmaNature implements IProjectNature
{
	private IProject project;

	@Override
	public void configure() throws CoreException
	{
		System.out.println("configure");
	}

	@Override
	public void deconfigure() throws CoreException
	{
		System.out.println("deconfigure");
	}

	@Override
	public IProject getProject()
	{
		return project;
	}

	@Override
	public void setProject(final IProject project)
	{
		System.out.println("setProject");
		this.project = project;
	}

}
