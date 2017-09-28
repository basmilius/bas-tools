package com.basmilius.bastools.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileSystemItem
import com.intellij.psi.PsiManager

/**
 * Object FileUtils
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.util
 */
object FileUtils
{

	val TYPE_ALL = 0
	val TYPE_DIRECTORY = 2
	val TYPE_FILE = 1

	/**
	 * Gets all the project files.
	 *
	 * @param project Project
	 *
	 * @return Array<String>
	 *
	 * @author Bas Milius
	 */
	fun getProjectFiles(project: Project): Array<String>
	{
		val files = ArrayList<String>()
		val projectDir = project.baseDir
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
	 * @author Bas Milius
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
	 * @author Bas Milius
	 */
	fun getRelativeFilesByName(baseFile: PsiFile, fileType: Int): Map<String, PsiFileSystemItem>
	{
		val files = HashMap<String, PsiFileSystemItem>()
		val project = baseFile.project
		val projectDirectory = project.baseDir
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
				{
					files.put(".", psiDirectory)
				}
			}

			fileIndex.iterateContentUnderDirectory(originDirectory) {
				val relativePath = it.path.replace(originDirectory.path + "/", "")

				if ((fileType == TYPE_ALL || fileType == TYPE_FILE) && !it.isDirectory && !relativePath.startsWith(".idea/"))
				{
					val psiFile = psiManager.findFile(it)

					if (psiFile != null)
					{
						files.put(relativePath, psiFile)
					}
				}

				if ((fileType == TYPE_ALL || fileType == TYPE_DIRECTORY) && it.isDirectory && !relativePath.startsWith(".idea/") && it.path != originDirectory.path)
				{
					val psiDirectory = psiManager.findDirectory(it)

					if (psiDirectory != null)
					{
						files.put(relativePath, psiDirectory)
					}
				}

				true
			}
		}

		return files
	}

}
