package com.basmilius.bastools.ui.tabs

import com.intellij.openapi.fileEditor.impl.EditorTabColorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.awt.Color

/**
 * Class BasToolsEditorTabColorProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.tabs
 * @since 1.0.0
 */
class BasToolsEditorTabColorProvider: EditorTabColorProvider
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getEditorTabColor(project: Project, virtualFile: VirtualFile): Color?
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor
	}

}
