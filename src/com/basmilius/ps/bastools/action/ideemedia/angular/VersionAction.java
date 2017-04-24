package com.basmilius.ps.bastools.action.ideemedia.angular;

import com.basmilius.ps.bastools.util.CommandUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.util.ArrayList;
import java.util.List;

public class VersionAction extends AnAction
{

	public VersionAction ()
	{
		super();
	}

	@Override
	public void actionPerformed (final AnActionEvent aae)
	{
		final Project project = aae.getProject();

		if (project == null)
		{
			return;
		}

		CommandUtils.run(project, "powershell ng version", lines ->
		{
			final List<String> result = new ArrayList<>();

			for (final String line : lines)
			{
				if (line.startsWith("@angular/") || line.startsWith("node:") || line.startsWith("os:"))
				{
					result.add(line);
				}
			}

			ApplicationManager.getApplication().invokeLater(() -> Messages.showMessageDialog(project, String.join(System.getProperty("line.separator"), result), "Angular Version", Messages.getInformationIcon()));
		});
	}

}
