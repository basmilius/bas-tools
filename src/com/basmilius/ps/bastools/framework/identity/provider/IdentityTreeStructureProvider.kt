package com.basmilius.ps.bastools.framework.identity.provider

import com.basmilius.ps.bastools.framework.identity.IdentityFramework
import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import java.util.*

/**
 * Class IdentityTreeStructureProvider
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity.provider
 */
class IdentityTreeStructureProvider: TreeStructureProvider
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
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

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getData(selected: Collection<AbstractTreeNode<*>>?, dataName: String?): Any?
	{
		return null
	}

}
