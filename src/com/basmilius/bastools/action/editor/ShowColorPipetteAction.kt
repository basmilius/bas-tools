package com.basmilius.bastools.action.editor

import com.basmilius.bastools.ui.DefaultColorPipette
import com.basmilius.bastools.core.util.EditorUtils
import com.basmilius.bastools.core.util.ExceptionUtils
import com.basmilius.bastools.core.util.JUtils
import com.intellij.codeInsight.hint.HintManager
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
 * @package com.basmilius.bastools.action.editor
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
		val root = JUtils.getRootComponent(aae.project)

		if (this.project == null || root == null)
			return

		this.editor = FileEditorManager.getInstance(this.project!!).selectedTextEditor

		if (this.editor == null)
			return

		val pipette = getPipetteIfAvailable(DefaultColorPipette(root, this), this)

		if (pipette == null)
		{
			HintManager.getInstance().showErrorHint(this.editor!!, "Could not insert color, no pipette implementation found!")
			return
		}

		pipette.setInitialColor(ColorUtil.fromHex("#000000"))
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
		if (this.editor == null)
			return

		if (this.project == null)
			return

		val currentColor = this.currentColor
		val editor = this.editor ?: return
		val project = this.project ?: return

		if (currentColor == null)
		{
			HintManager.getInstance().showInformationHint(editor, "Could not insert color, you didn't select a color!")
			return
		}

		editor.selectionModel.removeSelection()

		ExceptionUtils.executeIgnore {
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
