package com.basmilius.bastools.fileGroups

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ViewSettings
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.SimpleTextAttributes

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