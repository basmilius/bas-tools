package com.basmilius.ps.bastools.component;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

public class DefaultProjectComponent implements ProjectComponent
{

	private final Project project;

	public DefaultProjectComponent (Project project)
	{
		this.project = project;
	}

	@Override
	public void initComponent ()
	{
	}

	@Override
	public void disposeComponent ()
	{
	}

	@Override
	@NotNull
	public String getComponentName ()
	{
		return "Bas Tools Default Project";
	}

	@Override
	public void projectOpened ()
	{
		final VirtualFile workspace = project.getWorkspaceFile();

		if (workspace == null)
			return;

		final String username = System.getProperty("user.name").toLowerCase();
		final String workspacePerUserFilename = "workspace." + username + ".xml";
		final VirtualFile workspacePerUser = workspace.getParent().findChild(workspacePerUserFilename);

		ApplicationManager.getApplication().runWriteAction(() ->
		{
			try
			{
				if (workspacePerUser != null && workspacePerUser.exists())
				{
					final byte[] workspacePerUserContents = workspacePerUser.contentsToByteArray();
					final OutputStream workspacePerUserStream = workspace.getOutputStream(this);

					workspacePerUserStream.write(workspacePerUserContents);
					workspacePerUserStream.close();

					workspacePerUser.delete(this);
				}

				workspace.rename(this, workspacePerUserFilename);
				workspace.copy(this, workspace.getParent(), "workspace.xml");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
	}

	@Override
	public void projectClosed ()
	{
		// called when project is being closed
	}

}
