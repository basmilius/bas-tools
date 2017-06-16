package com.basmilius.ps.bastools.framework.identity

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

import java.util.ArrayList

object IdentityFramework
{

	private val theseAreIdentityProjects = ArrayList<Project>()

	/**
	 * Gets the sources root directory for a project.
	 *
	 * @param project Project instance.
	 *
	 * @return Directory as virtual file.
	 */
	fun getSourcesRoot(project: Project): VirtualFile
	{
		val projectRoot = project.baseDir
		val srcDirectory = projectRoot.findChild("src")
		return if (srcDirectory != null && srcDirectory.exists() && srcDirectory.isDirectory) srcDirectory else projectRoot
	}

	/**
	 * Checks if a project is an Identity Framework project (And therefore awesome!).
	 *
	 * @param project Project instance.
	 *
	 * @return True if it's an Identity Project.
	 */
	fun isIdentityFrameworkProject(project: Project): Boolean
	{
		if (theseAreIdentityProjects.contains(project))
			return true

		val projectRoot = IdentityFramework.getSourcesRoot(project)
		val identityFiles = arrayOf("wp-admin", "wp-content", "wp-includes", "wp-content/themes/idty", "wp-content/themes/idty/IdentityFramework.php", "wp-config.php")

		identityFiles
				.map { projectRoot.findFileByRelativePath(it) }
				.filterNot { it != null && it.exists() }
				.forEach { return false }

		theseAreIdentityProjects.add(project)

		return true
	}

}
