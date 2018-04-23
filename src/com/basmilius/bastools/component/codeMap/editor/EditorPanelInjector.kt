/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.editor

import com.basmilius.bastools.component.codeMap.CMWidth
import com.basmilius.bastools.component.codeMap.renderer.TaskQueueRunner
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.JBSplitter
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JLayeredPane
import javax.swing.JPanel

/**
 * Class EditorPanelInjector
 *
 * @constructor
 * @param project Project
 * @param queue TaskQueueRunner
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.textEditor
 * @since 1.4.0
 */
class EditorPanelInjector(private val project: Project, private val queue: TaskQueueRunner): FileEditorManagerListener
{

	private val panels = HashMap<TextEditor, CodeMapPanel>()

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun fileClosed(fem: FileEditorManager, virtualFile: VirtualFile)
	{
		this.freeUnusedPanels(fem)
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun fileOpened(fem: FileEditorManager, virtualFile: VirtualFile)
	{
		fem.allEditors
				.mapNotNull { it as? TextEditor }
				.forEach { this.inject(it) }

		this.freeUnusedPanels(fem)
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun selectionChanged(fileEditorManagerEvent: FileEditorManagerEvent)
	{
	}

	/**
	 * Gets a panel.
	 *
	 * @param editor TextEditor
	 *
	 * @return JPanel?
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun getPanel(editor: TextEditor): JPanel?
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

	/**
	 * Injects the codemap panel.
	 *
	 * @param editor TextEditor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun inject(editor: TextEditor)
	{
		val panel = this.getPanel(editor) ?: return

		val editorComponent = panel.parent.parent
		val editorComponentClass = editorComponent.javaClass
		val editorField = editorComponentClass.superclass.declaredFields
				.find { it.type == Editor::class.java } ?: return

		editorField.isAccessible = true

		val editorImplementation = editorField.get(editorComponent) as? EditorImpl ?: return
		val scrollBar = editorImplementation.scrollPane.verticalScrollBar
		editorImplementation.scrollPane.remove(scrollBar)

		val codeMapPanel = CodeMapPanel(project, editor, panel, this.queue)
		val mount = JPanel(BorderLayout())
		mount.minimumSize = JBUI.size(CMWidth + 18, 10)

		mount.add(codeMapPanel, BorderLayout.LINE_START)
		mount.add(scrollBar, BorderLayout.LINE_END)

		panel.add(mount, BorderLayout.LINE_END)

		this.panels[editor] = codeMapPanel
	}

	/**
	 * Uninjects the codemap panel.
	 *
	 * @param editor TextEditor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun uninject(editor: TextEditor)
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

	/**
	 * Frees unused panels.
	 *
	 * @param fem FileEditorManager
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun freeUnusedPanels(fem: FileEditorManager)
	{
		val unseen = HashSet(this.panels.keys)

		for (editor in fem.allEditors)
			if (unseen.contains(editor))
				unseen.remove(editor)

		var panel: CodeMapPanel
		for (editor in unseen)
		{
			panel = panels[editor]!!
			panel.onClose()

			this.uninject(editor)
			this.panels.remove(editor)
		}
	}

}
