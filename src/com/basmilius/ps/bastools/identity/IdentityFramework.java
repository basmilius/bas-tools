package com.basmilius.ps.bastools.identity;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IdentityFramework
{

	private static List<Project> theseAreIdentityProjects = new ArrayList<>();

	public static boolean isIdentityFrameworkProject (@NotNull final Project project)
	{
		if (theseAreIdentityProjects.contains(project))
			return true;

		final VirtualFile projectDirectory = project.getBaseDir();
		final String[] identityFiles = {
				"wp-admin", "wp-content", "wp-includes",
				"wp-content/themes/idty",
				"wp-content/themes/idty/IdentityFramework.php",
				"wp-config.php"
		};

		for (final String identityFilePath : identityFiles)
		{
			final VirtualFile identityFile = projectDirectory.findFileByRelativePath(identityFilePath);
			if (identityFile != null && identityFile.exists())
				continue;

			return false;
		}

		theseAreIdentityProjects.add(project);

		return true;
	}

}
