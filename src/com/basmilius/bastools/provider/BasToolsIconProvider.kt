package com.basmilius.bastools.provider

import com.basmilius.bastools.resource.Icons
import com.intellij.ide.FileIconProvider
import com.intellij.ide.IconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Iconable
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon

/**
 * Class BasToolsIconProvider
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.provider
 */
class BasToolsIconProvider: IconProvider(), FileIconProvider
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun getIcon(file: VirtualFile, @Iconable.IconFlags flags: Int, project: Project?): Icon?
	{
		return Icons.Default
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun getIcon(psi: PsiElement, @Iconable.IconFlags flags: Int): Icon?
	{
		if (psi is PsiFile && psi.name == ".bas-tools.json")
			return Icons.BasTools

		return Icons.Default
	}

}
