/*
 * Copyright © 2019 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.fileGroups

import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.project.impl.ProjectManagerImpl
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.SimpleTextAttributes
import javax.swing.Icon

/**
 * Class Group
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.fileGroups
 * @since 1.5.0
 */
abstract class Group(val name: String, val icon: Icon, val entries: ArrayList<Entry>, val removeFromTree: Boolean = true, val additionalText: String? = null, val weight: Number = 0)
{

	/**
	 * Creates a tree node for the project view.
	 *
	 * @param parent {AnyNode}
	 * @param child {AnyNode}
	 * @param children {Collection<AnyNode>}
	 * @param settings {ViewSettings}
	 *
	 * @return TreeNode?
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	open fun createNode(parent: AnyNode, child: AnyNode, children: Collection<AnyNode>, settings: ViewSettings): TreeNode?
	{
		val eligibleEntries = this.getEligibleEntries(children)

		if (eligibleEntries.isEmpty())
			return null

		val actingVirtialFile = this.getActingVirtualFile(parent, child)
		val project = child.project ?: return null

		return TreeNode(project, settings, this, eligibleEntries, actingVirtialFile)
	}

	/**
	 * When this function returns a {@see VirtualFile}, it'll be used as the acting node for the group tree node.
	 * If NULL is returned, a non-functioning node is inserted.
	 *
	 * @param parent {AnyNode}
	 * @param child {AnyNode}
	 *
	 * @return VirtualFile?
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	open fun getActingVirtualFile(parent: AnyNode, child: AnyNode): VirtualFile? = null

	/**
	 * When this function returns a {@see VirtualFile}, it'll be used as the acting node for the group tree node.
	 * If NULL is returned, a non-functioning node is inserted.
	 *
	 * @param children {Collection<AnyNode>}
	 *
	 * @return MutableList<PsiNode>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	open fun getEligibleEntries(children: Collection<AnyNode>): MutableList<PsiNode> = children
			.filterIsInstance<PsiNode>()
			.filter { this.isEntryEligible(it.virtualFile) }
			.toMutableList()

	/**
	 * Returns TRUE when the given {@see file} is eligible for this group.
	 *
	 * @param file {VirtualFile?}
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	open fun isEntryEligible(file: VirtualFile?): Boolean
	{
		if (file == null)
			return false

		if (this.isEntryExcluded(file))
			return false

		return this.entries.any { it.isMatch(file) }
	}

	/**
	 * Returns TRUE if the given {@see file} is excluded.
	 *
	 * @param file {VirtualFile?}
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	open fun isEntryExcluded(file: VirtualFile?): Boolean
	{
		val excludedUrls = (ProjectManagerImpl.getInstance() as ProjectManagerImpl).allExcludedUrls

		return file == null || excludedUrls.any { file.url.startsWith(it) }
	}

	/**
	 * Returns TRUE when this group should be available.
	 *
	 * @param parent {AnyNode}
	 * @param child {AnyNode}
	 * @param children {Collection<AnyNode>}
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	abstract fun isAvailable(parent: AnyNode, child: AnyNode, children: Collection<AnyNode>): Boolean

	/**
	 * Class Recursive
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.fileGroups.Group
	 * @since 1.5.0
	 */
	abstract class Recursive(name: String, icon: Icon, entries: ArrayList<Entry>, removeFromTree: Boolean = true, additionalText: String? = null, weight: Number = 0) : Group(name, icon, entries, removeFromTree, additionalText, weight)
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

			for (child in children)
			{
				if (child !is PsiNode)
					continue

				val project = child.project ?: continue
				val projectDir = project.guessProjectDir() ?: continue
				val virtualFile = child.virtualFile ?: continue

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

			return entries
		}

	}

}
