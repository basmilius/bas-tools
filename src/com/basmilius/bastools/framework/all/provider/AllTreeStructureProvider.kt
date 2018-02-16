/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.all.provider

import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.NestingTreeNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode

/**
 * Class AllTreeStructureProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.provider
 * @since 1.2.0
 */
class AllTreeStructureProvider: TreeStructureProvider
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	override fun modify(parent: AbstractTreeNode<*>, children: Collection<AbstractTreeNode<*>>, settings: ViewSettings): Collection<AbstractTreeNode<*>>
	{
		val nodes = ArrayList<AbstractTreeNode<*>>()
		val poFiles = ArrayList<AbstractTreeNode<*>>()

		for (child in children)
		{
			// Remove .mo-files that have associated .po-files.
			if (child is PsiFileNode && child.virtualFile?.extension == "mo")
			{
				val associatedPoFiles = children
						.filter { it is PsiFileNode && it.virtualFile?.extension == "po" && it.virtualFile?.nameWithoutExtension == child.virtualFile?.nameWithoutExtension }
						.map { it as PsiFileNode }

				if (associatedPoFiles.isNotEmpty())
					continue
			}

			// Add associated .mo-files under their linked .po-file.
			if (child is PsiFileNode && child.virtualFile?.extension == "po")
			{
				val associatedMoFiles = children
						.filter { it is PsiFileNode && it.virtualFile?.extension == "mo" && it.virtualFile?.nameWithoutExtension == child.virtualFile?.nameWithoutExtension }
						.map { it as PsiFileNode }

				if (associatedMoFiles.isNotEmpty())
				{
					nodes.add(NestingTreeNode(child, associatedMoFiles))

					continue
				}
			}

			// Adds associated .po-files under their likely .pot-file.
			if (child is PsiFileNode && child.virtualFile?.extension == "pot")
			{
				val associatedPoFiles = children
						.filter { it is PsiFileNode && it.virtualFile?.nameWithoutExtension!!.startsWith("${child.virtualFile?.nameWithoutExtension}-") }
						.map { it as PsiFileNode }

				if (associatedPoFiles.isNotEmpty())
				{
					nodes.add(NestingTreeNode(child, associatedPoFiles))
					poFiles.addAll(associatedPoFiles)

					continue
				}
			}

			nodes.add(child)
		}

		// Remove .po-files that are grouped into .pot-files from the main tree structure.
		for (poFile in poFiles)
			nodes.remove(poFile)

		return nodes
	}

}
