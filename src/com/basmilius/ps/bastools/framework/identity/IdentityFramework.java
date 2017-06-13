package com.basmilius.ps.bastools.framework.identity;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IdentityFramework
{

	private static List<Project> theseAreIdentityProjects = new ArrayList<>();

	/**
	 * Gets the sources root directory for a project.
	 *
	 * @param project Project instance.
	 * @return Directory as virtual file.
	 */
	public static VirtualFile getSourcesRoot (@NotNull final Project project)
	{
		final VirtualFile projectRoot = project.getBaseDir();
		final VirtualFile srcDirectory = projectRoot.findChild("src");
		return srcDirectory != null && srcDirectory.exists() && srcDirectory.isDirectory() ? srcDirectory : projectRoot;
	}

	/**
	 * Checks if a project is an Identity Framework project (And therefore awesome!).
	 *
	 * @param project Project instance.
	 * @return True if it's an Identity Project.
	 */
	public static boolean isIdentityFrameworkProject (@NotNull final Project project)
	{
		if (theseAreIdentityProjects.contains(project))
			return true;

		final VirtualFile projectRoot = IdentityFramework.getSourcesRoot(project);
		final String[] identityFiles = {
				"wp-admin", "wp-content", "wp-includes",
				"wp-content/themes/idty",
				"wp-content/themes/idty/IdentityFramework.php",
				"wp-config.php"
		};

		for (final String identityFilePath : identityFiles)
		{
			final VirtualFile identityFile = projectRoot.findFileByRelativePath(identityFilePath);
			if (identityFile != null && identityFile.exists())
				continue;

			return false;
		}

		theseAreIdentityProjects.add(project);

		return true;
	}

}
