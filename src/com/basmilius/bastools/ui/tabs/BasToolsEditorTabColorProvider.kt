package com.basmilius.bastools.ui.tabs

import com.intellij.openapi.fileEditor.impl.EditorTabColorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

import java.awt.*

/**
 * Class BasToolsEditorTabColorProvider
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.ui.tabs
 */
class BasToolsEditorTabColorProvider: EditorTabColorProvider
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getEditorTabColor(project: Project, virtualFile: VirtualFile): Color?
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor
	}

}
