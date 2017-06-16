package com.basmilius.ps.bastools.ui.tabs

import com.intellij.openapi.fileEditor.impl.EditorTabColorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

import java.awt.*

class BasToolsEditorTabColorProvider : EditorTabColorProvider
{

	/**
	 * {@inheritdoc}
	 */
	override fun getEditorTabColor(project: Project, virtualFile: VirtualFile): Color?
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor
	}

}
