package com.basmilius.ps.bastools.framework.identity.provider

import com.basmilius.ps.bastools.framework.identity.IdentityFramework
import com.basmilius.ps.bastools.resource.Icons
import com.intellij.ide.FileIconProvider
import com.intellij.ide.IconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Iconable
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement

import javax.swing.*

class IdentityIconProvider : IconProvider(), FileIconProvider
{

	override fun getIcon(file: VirtualFile, @Iconable.IconFlags flags: Int, project: Project?): Icon?
	{
		if (project == null)
			return Icons.Default

		if (file.name == ".angular-cli.json")
			return Icons.Angular

		return Icons.Default
	}

	override fun getIcon(psi: PsiElement, @Iconable.IconFlags flags: Int): Icon?
	{
		val project = psi.project

		if (IdentityFramework.isIdentityFrameworkProject(project))
		{
			if (psi is PsiDirectory)
			{
				val dir = psi
				val projectRoot = IdentityFramework.getSourcesRoot(project)

				if (dir.virtualFile.path == projectRoot.path)
					return Icons.Rhombus

				if (dir.parent != null && (dir.name == "plugins" || dir.parent!!.name == "plugins"))
					return Icons.Puzzle

				if (dir.parent != null && (dir.name == "themes" || dir.parent!!.name == "themes"))
					return Icons.Creation
			}
		}

		return Icons.Default
	}

}
