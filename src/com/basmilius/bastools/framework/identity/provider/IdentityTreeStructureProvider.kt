/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.identity.provider

import com.basmilius.bastools.framework.identity.IdentityFramework
import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode

/**
 * Class IdentityTreeStructureProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity.provider
 * @since 1.0.0
 */
class IdentityTreeStructureProvider: TreeStructureProvider
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun modify(parent: AbstractTreeNode<*>, children: Collection<AbstractTreeNode<*>>, settings: ViewSettings): Collection<AbstractTreeNode<*>>
	{
		val nodes = ArrayList<AbstractTreeNode<*>>()
		val project = parent.project ?: return nodes

		if (IdentityFramework.isIdentityFrameworkProject(project))
		{
			for (child in children)
			{
				if (child is PsiDirectoryNode)
				{
					val dirFile = child.virtualFile ?: continue

					val dirName = dirFile.name

					if (dirName == ".identity-deployer")
						continue
				}

				if (child is PsiFileNode)
				{
					val virtualFile = child.virtualFile ?: continue

					if (virtualFile.parent.name == "wp-content" && virtualFile.name == "install.lock")
						continue

					if (virtualFile.name == "Thumbs.db")
						continue
				}

				nodes.add(child)
			}
		}
		else
		{
			nodes.addAll(children)
		}

		return nodes
	}

}
