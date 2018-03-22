/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.editor

import com.basmilius.bastools.component.codeMap.renderer.TaskQueueRunner
import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.JBSplitter
import java.awt.BorderLayout
import javax.swing.JLayeredPane
import javax.swing.JPanel

/**
 * Class EditorPanelInjector
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.textEditor
 * @since 1.4.0
 */
class EditorPanelInjector(private val project: Project, private val queue: TaskQueueRunner): FileEditorManagerListener
{

	private val panels = HashMap<FileEditor, CodeMapPanel>()

	override fun fileOpened(fem: FileEditorManager, virtualFile: VirtualFile)
	{
		val editors = fem.allEditors

		for (editor in editors)
			if (editor is TextEditor)
				inject(editor)

		freeUnusedPanels(fem)
	}

	private fun getPanel(editor: FileEditor): JPanel?
	{
		try
		{
			val outerPanel = editor.component as JPanel
			val outerLayout = outerPanel.layout as BorderLayout
			var layoutComponent = outerLayout.getLayoutComponent(BorderLayout.CENTER)

			if (layoutComponent is JBSplitter)
			{
				val editorComp = layoutComponent.firstComponent as JPanel
				layoutComponent = (editorComp.layout as BorderLayout).getLayoutComponent(BorderLayout.CENTER)
			}

			val pane = layoutComponent as JLayeredPane
			val panel = if (pane.componentCount > 1) pane.getComponent(1) as JPanel else pane.getComponent(0) as JPanel

			panel.layout as BorderLayout

			return panel
		}
		catch (e: ClassCastException)
		{
			e.printStackTrace()
			return null
		}
	}

	private fun inject(editor: TextEditor)
	{
		val panel = this.getPanel(editor) ?: return
		val innerLayout = panel.layout as BorderLayout

		if (innerLayout.getLayoutComponent(BorderLayout.LINE_END) == null)
		{
			val codeMapPanel = CodeMapPanel(project, editor, panel, this.queue)
			panel.add(codeMapPanel, BorderLayout.LINE_END)

			this.panels[editor] = codeMapPanel
		}
	}

	private fun uninject(editor: FileEditor)
	{
		val panel = getPanel(editor) ?: return
		val innerLayout = panel.layout as BorderLayout

		val rightPanel = innerLayout.getLayoutComponent(BorderLayout.LINE_END)
		if (rightPanel != null)
			panel.remove(rightPanel)

		val leftPanel = innerLayout.getLayoutComponent(BorderLayout.LINE_START)
		if (leftPanel != null)
			panel.remove(leftPanel)
	}

	override fun fileClosed(fem: FileEditorManager, virtualFile: VirtualFile)
	{
		freeUnusedPanels(fem)
	}

	private fun freeUnusedPanels(fem: FileEditorManager)
	{
		val unseen = HashSet(panels.keys)

		for (editor in fem.allEditors)
		{
			if (unseen.contains(editor))
			{
				unseen.remove(editor)
			}
		}

		var panel: CodeMapPanel
		for (editor in unseen)
		{
			panel = panels[editor]!!
			panel.onClose()
			uninject(editor)
			panels.remove(editor)
		}
	}

	override fun selectionChanged(fileEditorManagerEvent: FileEditorManagerEvent)
	{
	}

}
