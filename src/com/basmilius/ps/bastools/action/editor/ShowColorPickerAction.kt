package com.basmilius.ps.bastools.action.editor

import com.basmilius.ps.bastools.util.EditorUtils
import com.basmilius.ps.bastools.util.ExceptionUtils
import com.basmilius.ps.bastools.util.JUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.ui.ColorPicker
import com.intellij.ui.ColorUtil
import com.intellij.ui.JBColor

/**
 * Class ShowColorPickerAction
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.action.editor
 */
class ShowColorPickerAction: AnAction("Show Color Picker")
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		val project = aae.project
		val root = JUtils.getRootComponent(aae.project)

		if (project == null || root == null)
			return

		val color = ColorPicker.showDialog(root, "Pick a Color", JBColor.CYAN, false, null, false) ?: return
		val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return

		ExceptionUtils.executeIgnore(Runnable { EditorUtils.insertOrReplaceMultiCaret(editor, project, "#" + ColorUtil.toHex(color)) })
	}

}
