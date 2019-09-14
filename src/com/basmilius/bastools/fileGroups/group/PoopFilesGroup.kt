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
import com.intellij.openapi.project.guessProjectDir

/**
 * Class PoopFilesGroup
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.fileGroups.group
 * @since 1.5.0
 */
class PoopFilesGroup: Group(name = "Poop files", icon = Icons.Poop, weight = 99999, entries = arrayListOf(
		Entry.ExactMatch(".env"),
		Entry.ExactMatch(".gitignore"),
		Entry.ExactMatch(".gitmodules"),
		Entry.ExactMatch(".htaccess"),
		Entry.ExactMatch("composer.json"),
		Entry.ExactMatch("composer.lock"),
		Entry.ExactMatch("nginx.conf"),
		Entry.ExactMatch("package.json"),
		Entry.ExactMatch("package-lock.json"),
		Entry.ExactMatch("postcss.config.js"),
		Entry.ExactMatch("webpack.config.js"),
		Entry.ExactMatch("yarn.lock"),
		Entry.LooseMatch("CODE_OF_CONDUCT"),
		Entry.LooseMatch("CONTRIBUTORS"),
		Entry.LooseMatch("LICENSE"),
		Entry.LooseMatch("README")
))
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.5.0
	 */
	override fun getActingVirtualFile(parent: AnyNode, child: AnyNode) = if (parent is PsiNode)
		parent.virtualFile
	else
		null

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
