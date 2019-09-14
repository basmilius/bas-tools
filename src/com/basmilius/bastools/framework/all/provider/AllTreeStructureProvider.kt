/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.all.provider

import com.basmilius.bastools.fileGroups.AnyNode
import com.basmilius.bastools.fileGroups.Group
import com.basmilius.bastools.fileGroups.PsiNode
import com.basmilius.bastools.fileGroups.group.JsonFilesGroup
import com.basmilius.bastools.fileGroups.group.PoopFilesGroup
import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.NestingTreeNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode

/**
 * Class AllTreeStructureProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.provider
 * @since 1.2.0
 */
class AllTreeStructureProvider: TreeStructureProvider
{

	private val fileGroups = arrayListOf(
//			JsonFilesGroup(),
			PoopFilesGroup()
	)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	override fun modify(parent: AnyNode, children: Collection<AnyNode>, settings: ViewSettings): Collection<AnyNode>
	{
		val nodes = ArrayList<AnyNode>()

		for (child in children)
		{
			if (this.checkGetText(nodes, child, children))
				continue

			if (this.checkGroups(nodes, parent, child, children, settings))
				continue

			nodes.add(child)
		}

		return nodes
	}

	private fun checkGetText(nodes: ArrayList<AnyNode>, child: AnyNode, children: Collection<AnyNode>): Boolean
	{
		if (child is PsiFileNode && (child.virtualFile?.extension == "mo" || child.virtualFile?.extension == "po"))
			if (children.filter { it is PsiFileNode && it.virtualFile?.extension == "pot" && child.virtualFile!!.nameWithoutExtension.startsWith(it.virtualFile!!.nameWithoutExtension + "-") }.map { it as PsiFileNode }.isNotEmpty())
				return true

		if (child is PsiFileNode && child.virtualFile?.extension == "pot")
		{
			val associatedGetTextFiles = children
					.filter { it is PsiFileNode && (it.virtualFile?.extension == "mo" || it.virtualFile?.extension == "po") && it.virtualFile!!.nameWithoutExtension.startsWith(child.virtualFile!!.nameWithoutExtension + "-") }
					.map { it as PsiFileNode }

			val sub = ArrayList<PsiFileNode>()
			sub.addAll(associatedGetTextFiles)

			nodes.add(NestingTreeNode(child, sub))

			return true
		}

		return false
	}

	private fun checkGroups(nodes: ArrayList<AnyNode>, parent: AnyNode, child: AnyNode, children: Collection<AnyNode>, settings: ViewSettings): Boolean
	{
		var didSomething = false

		for (group in fileGroups)
		{
			if (!group.isAvailable(parent, child, children))
				continue

			val childIsEligible = child is PsiNode && group.isEntryEligible(child.virtualFile)
			val hasExistingGroupNode = nodes
					.filterIsInstance<Group.TreeNode>()
					.any{ it.group.name == group.name }

			if (childIsEligible && !hasExistingGroupNode)
			{
				val node = group.createNode(parent, child, children, settings)

				if (node != null)
					nodes.add(node)
			}

			didSomething = didSomething || (group.removeFromTree && child !is Group.TreeNode && childIsEligible)
		}

		return didSomething
	}

}
