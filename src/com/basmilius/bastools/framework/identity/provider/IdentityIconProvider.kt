package com.basmilius.bastools.framework.identity.provider

import com.basmilius.bastools.framework.identity.IdentityFramework
import com.basmilius.bastools.resource.Icons
import com.intellij.ide.FileIconProvider
import com.intellij.ide.IconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Iconable
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement

import javax.swing.*

/**
 * Class IdentityIconProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity.provider
 */
class IdentityIconProvider: IconProvider(), FileIconProvider
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getIcon(file: VirtualFile, @Iconable.IconFlags flags: Int, project: Project?): Icon?
	{
		if (project == null)
			return Icons.Default

		return Icons.Default
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getIcon(psi: PsiElement, @Iconable.IconFlags flags: Int): Icon?
	{
		val project = psi.project

		if (IdentityFramework.isIdentityFrameworkProject(project))
		{
			if (psi is PsiDirectory)
			{
				val projectRoot = IdentityFramework.getSourcesRoot(project)

				if (psi.virtualFile.path == projectRoot.path)
					return Icons.IdeeMedia

				if (psi.parent != null && (psi.name == "plugins" || psi.parent!!.name == "plugins"))
					return Icons.IdentityPlugin

				if (psi.parent != null && (psi.name == "themes" || psi.parent!!.name == "themes"))
					return Icons.IdentityTheme
			}
		}

		return Icons.Default
	}

}
