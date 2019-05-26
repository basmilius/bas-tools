/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.action.editor

import com.basmilius.bastools.core.util.*
import com.basmilius.bastools.ui.DefaultColorPipette
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.ColorUtil
import com.intellij.ui.picker.ColorListener
import com.intellij.ui.picker.ColorPipette
import java.awt.Color
import java.awt.event.WindowEvent
import java.awt.event.WindowListener

/**
 * Class ShowColorPipetteAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.textEditor
 * @since 1.0.0
 */
class ShowColorPipetteAction: AnAction("Show Color Pipette"), ColorListener, Disposable, WindowListener
{

	private var currentColor: Color? = null
	private var editor: Editor? = null
	private var project: Project? = null

	/**
	 * ShowColorPipetteAction Constructor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	init
	{
		this.currentColor = null
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		this.project = aae.project

		val project = this.project ?: return
		val root = JUtils.getRootComponent(project) ?: return

		this.editor = FileEditorManager.getInstance(project).selectedTextEditor

		val editor = this.editor ?: return
		val pipette = getPipetteIfAvailable(DefaultColorPipette(root, this), this) ?: return showErrorHint(editor, "Could launch color pipette because no implementation could launch.")

		pipette.setInitialColor(Color.BLACK)
		pipette.show().addWindowListener(this)
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun colorChanged(color: Color, o: Any)
	{
		this.currentColor = color
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun windowOpened(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun windowClosing(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun windowClosed(e: WindowEvent)
	{
		val editor = this.editor ?: return
		val project = this.project ?: return
		val currentColor = this.currentColor ?: return showInfoHint(editor, "Could not insert color because no color was selected.")

		editor.selectionModel.removeSelection()

		processDontCare {
			EditorUtils.insertOrReplaceMultiCaret(editor, project, "#" + ColorUtil.toHex(currentColor))
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun windowIconified(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun windowDeiconified(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun windowActivated(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun windowDeactivated(e: WindowEvent)
	{
		e.window.dispose()
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun dispose()
	{

	}

	/**
	 * Gets a color pipette implementation if available.
	 *
	 * @param pipette          Base instance.
	 * @param parentDisposable Parent disposable.
	 *
	 * @return ColorPipette
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun getPipetteIfAvailable(pipette: ColorPipette, parentDisposable: Disposable): ColorPipette?
	{
		return if (pipette.isAvailable)
		{
			Disposer.register(parentDisposable, pipette)
			pipette
		}
		else
		{
			Disposer.dispose(pipette)
			null
		}
	}

}
