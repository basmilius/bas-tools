/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.identity

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile

/**
 * Object IdentityFramework
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity
 * @since 1.0.0
 */
object IdentityFramework
{

	private val theseAreIdentityProjects = ArrayList<Project>()

	/**
	 * Gets the sources root directory for a project.
	 *
	 * @param project Project
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
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
	 * @param project Project
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
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

	/**
	 * Returns TRUE if a file is inside a particular directory.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun isChildOf(project: Project, file: PsiFile, dir: String): Boolean
	{
		val sourcesRoot: VirtualFile = this.getSourcesRoot(project)
		val directoryRoot: String = sourcesRoot.path + dir

		return file.virtualFile.path.startsWith(directoryRoot)
	}

}
