package com.basmilius.ps.bastools.framework.identity

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import java.util.*

/**
 * Object IdentityFramework
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity
 */
object IdentityFramework
{

	private val theseAreIdentityProjects = ArrayList<Project>()

	/**
	 * Gets the sources root directory for a project.
	 *
	 * @param project Project instance.
	 *
	 * @return Directory as virtual file.
	 *
	 * @author Bas Milius
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
	 *
	 * @author Bas Milius
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
	 * @return True if it's inside the specified directory.
	 *
	 * @author Bas Milius
	 */
	fun isChildOf(project: Project, file: PsiFile, dir: String): Boolean
	{
		val sourcesRoot: VirtualFile = this.getSourcesRoot(project)
		val directoryRoot: String = sourcesRoot.path + dir

		return file.virtualFile.path.startsWith(directoryRoot)
	}

}
