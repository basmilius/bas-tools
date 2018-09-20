/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileSystemItem
import com.intellij.psi.PsiManager

/**
 * Object FileUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.0.0
 */
object FileUtils
{

	const val TYPE_ALL = 0
	const val TYPE_DIRECTORY = 2
	const val TYPE_FILE = 1

	/**
	 * Gets all the project files.
	 *
	 * @param project Project
	 *
	 * @return Array<String>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun getProjectFiles(project: Project): Array<String>
	{
		val files = ArrayList<String>()
		val projectPath = project.basePath ?: return files.toTypedArray()
		val projectDir = LocalFileSystem.getInstance().findFileByPath(projectPath) ?: return files.toTypedArray()

		val fileIndex = ProjectFileIndex.SERVICE.getInstance(project)

		fileIndex.iterateContentUnderDirectory(projectDir) {
			val relativePath = it.path.replace(projectDir.path + "/", "")

			if (!it.isDirectory && !relativePath.startsWith(".idea"))
				files.add(relativePath)

			true
		}

		return files.toTypedArray()
	}

	/**
	 * Gets relative files.
	 *
	 * @param baseFile PsiFile
	 *
	 * @return Array<String>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun getRelativeFiles(baseFile: PsiFile): Array<String>
	{
		val files = ArrayList<String>()
		val project = baseFile.project
		val directory = baseFile.containingDirectory ?: return arrayOf()
		val originDirectory = directory.virtualFile
		val fileIndex = ProjectFileIndex.SERVICE.getInstance(project)

		fileIndex.iterateContentUnderDirectory(originDirectory) {
			if (!it.isDirectory)
				files.add(it.path.replace(originDirectory.path + "/", ""))

			true
		}

		return files.toTypedArray()
	}

	/**
	 * Gets files relative to another file.
	 *
	 * @param baseFile PsiFile
	 * @param fileType Int
	 *
	 * @return Map<String, PsiFileSystemItem>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun getRelativeFilesByName(baseFile: PsiFile, fileType: Int): Map<String, PsiFileSystemItem>
	{
		val files = HashMap<String, PsiFileSystemItem>()
		val project = baseFile.project
		val projectDirectory = project.basePath
		val directory = baseFile.containingDirectory ?: baseFile.containingFile.containingDirectory ?: return files
		val originDirectory = directory.virtualFile
		val fileIndex = ProjectFileIndex.SERVICE.getInstance(project)
		val psiManager = PsiManager.getInstance(project)

		if (projectDirectory != null)
		{
			if (fileType == TYPE_DIRECTORY)
			{
				val psiDirectory = psiManager.findDirectory(originDirectory)

				if (psiDirectory != null)
					files["."] = psiDirectory
			}

			fileIndex.iterateContentUnderDirectory(originDirectory) {
				val relativePath = it.path.replace(originDirectory.path + "/", "")

				if ((fileType == TYPE_ALL || fileType == TYPE_FILE) && !it.isDirectory && !relativePath.startsWith(".idea/"))
				{
					val psiFile = psiManager.findFile(it)

					if (psiFile != null)
						files[relativePath] = psiFile
				}

				if ((fileType == TYPE_ALL || fileType == TYPE_DIRECTORY) && it.isDirectory && !relativePath.startsWith(".idea/") && it.path != originDirectory.path)
				{
					val psiDirectory = psiManager.findDirectory(it)

					if (psiDirectory != null)
						files[relativePath] = psiDirectory
				}

				true
			}
		}

		return files
	}

}
