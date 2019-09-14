/*
 * Copyright © 2019 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.fileGroups

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ViewSettings
import com.intellij.openapi.project.Project
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
	open fun isEntryEligible(file: VirtualFile?) = file != null && this.entries.any { it.isMatch(file) }

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
	 * Class TreeNode
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.fileGroups.Group
	 * @since 1.5.0
	 */
	class TreeNode(project: Project, settings: ViewSettings, val group: Group, private val nodes: MutableList<PsiNode>, private val actAs: VirtualFile? = null): ProjectViewNode<String>(project, group.name, settings)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.5.0
		 */
		override fun contains(file: VirtualFile) = this.nodes.any { it.virtualFile == file }

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.5.0
		 */
		override fun update(presentation: PresentationData)
		{
			presentation.addText(this.group.name, SimpleTextAttributes.REGULAR_ATTRIBUTES)
			if (this.group.additionalText != null)
				presentation.addText("  ${this.group.additionalText}", SimpleTextAttributes.GRAYED_ATTRIBUTES)

			presentation.presentableText = "zzz-${this.group.weight}-${this.group.name}"
			presentation.setIcon(this.group.icon)
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.5.0
		 */
		override fun getChildren() = this.nodes

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.5.0
		 */
		override fun getVirtualFile() = this.actAs

	}

}
