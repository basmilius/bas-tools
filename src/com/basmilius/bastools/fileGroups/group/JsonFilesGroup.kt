/*
 * Copyright © 2019 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.fileGroups.group

import com.basmilius.bastools.fileGroups.AnyNode
import com.basmilius.bastools.fileGroups.Entry
import com.basmilius.bastools.fileGroups.Group
import com.basmilius.bastools.fileGroups.PsiNode
import com.basmilius.bastools.resource.Icons
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.project.impl.ProjectManagerImpl
import com.intellij.ui.SimpleTextAttributes

/**
 * Class JsonFilesGroup
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.fileGroups.group
 * @since 1.5.0
 */
class JsonFilesGroup: Group(name = "Json files", icon = Icons.BasTools, additionalText = "All JSON files in your project", entries = arrayListOf(
		Entry { it.extension == "json" }
))
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	override fun getEligibleEntries(children: Collection<AnyNode>): MutableList<PsiNode>
	{
		val entries = arrayListOf<PsiNode>()
		val excludedUrls = (ProjectManagerImpl.getInstance() as ProjectManagerImpl).allExcludedUrls

		for (child in children)
		{
			if (child is PsiNode)
			{
				val project = child.project ?: continue
				val projectDir = project.guessProjectDir() ?: continue
				val virtualFile = child.virtualFile ?: continue

				if (excludedUrls.any { virtualFile.url.startsWith(it) })
					continue

				if (child is PsiFileNode && this.isEntryEligible(virtualFile))
				{
					child.presentation.addText("${virtualFile.name}  ", SimpleTextAttributes.REGULAR_ATTRIBUTES)
					child.presentation.addText(virtualFile.parent.path.replace(projectDir.path.trimEnd('/', '\\'), ""), SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES)
					entries.add(child)
					continue
				}

				if (child is PsiDirectoryNode)
				{
					entries.addAll(this.getEligibleEntries(child.children))
				}
			}
		}

		return entries
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	override fun isAvailable(parent: AnyNode, child: AnyNode, children: Collection<AnyNode>): Boolean
	{
		val project = child.project ?: return false
		val projectDir = project.guessProjectDir()?.path ?: return false

		return child is PsiNode && parent is PsiDirectoryNode && parent.virtualFile?.path == projectDir
	}

}
