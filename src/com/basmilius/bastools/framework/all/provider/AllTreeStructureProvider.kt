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

		for (child in children)
		{
			if (child is PsiFileNode && child.virtualFile?.extension == "mo")
				if (children.filter { it is PsiFileNode && it.virtualFile?.extension == "pot" && child.virtualFile!!.nameWithoutExtension.startsWith(it.virtualFile!!.nameWithoutExtension + "-") }.map { it as PsiFileNode }.isNotEmpty())
					continue

			if (child is PsiFileNode && child.virtualFile?.extension == "po")
				if (children.filter { it is PsiFileNode && it.virtualFile?.extension == "pot" && child.virtualFile!!.nameWithoutExtension.startsWith(it.virtualFile!!.nameWithoutExtension + "-") }.map { it as PsiFileNode }.isNotEmpty())
					continue

			if (child is PsiFileNode && child.virtualFile?.extension == "pot")
			{
				val associatedMoFiles = children
						.filter { it is PsiFileNode && it.virtualFile?.extension == "mo" && it.virtualFile!!.nameWithoutExtension.startsWith(child.virtualFile!!.nameWithoutExtension + "-") }
						.map { it as PsiFileNode }

				val associatedPoFiles = children
						.filter { it is PsiFileNode && it.virtualFile?.extension == "po" && it.virtualFile!!.nameWithoutExtension.startsWith(child.virtualFile!!.nameWithoutExtension + "-") }
						.map { it as PsiFileNode }

				val sub = ArrayList<PsiFileNode>()
				sub.addAll(associatedPoFiles)
				sub.addAll(associatedMoFiles)

				nodes.add(NestingTreeNode(child, sub))

				continue
			}

			nodes.add(child)
		}

		return nodes
	}

}
