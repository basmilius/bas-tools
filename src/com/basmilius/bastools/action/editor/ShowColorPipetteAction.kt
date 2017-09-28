package com.basmilius.bastools.action.editor

import com.basmilius.bastools.ui.DefaultColorPipette
import com.basmilius.bastools.util.EditorUtils
import com.basmilius.bastools.util.ExceptionUtils
import com.basmilius.bastools.util.JUtils
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
 * @author Bas Milius
 * @package com.basmilius.bastools.action.editor
 */
class ShowColorPipetteAction: AnAction("Show Color Pipette"), ColorListener, Disposable, WindowListener
{

	private var currentColor: Color? = null
	private var editor: Editor? = null
	private var project: Project? = null

	/**
	 * ShowColorPipetteAction Constructor.
	 *
	 * @author Bas Milius
	 */
	init
	{
		this.currentColor = null
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		this.project = aae.project
		val root = JUtils.getRootComponent(aae.project)

		if (project == null || root == null)
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
	 * @author Bas Milius
	 */
	override fun colorChanged(color: Color, o: Any)
	{
		this.currentColor = color
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun windowOpened(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun windowClosing(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun windowClosed(e: WindowEvent)
	{
		if (this.editor == null)
			return

		if (this.project == null)
			return

		if (this.currentColor == null)
		{
			HintManager.getInstance().showInformationHint(this.editor!!, "Could not insert color, you didn't select a color!")
			return
		}

		ExceptionUtils.executeIgnore(Runnable { EditorUtils.insertOrReplaceMultiCaret(this.editor!!, this.project!!, "#" + ColorUtil.toHex(this.currentColor!!)) })
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun windowIconified(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun windowDeiconified(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun windowActivated(e: WindowEvent)
	{

	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun windowDeactivated(e: WindowEvent)
	{
		e.window.dispose()
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
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
	 * @author Bas Milius
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
