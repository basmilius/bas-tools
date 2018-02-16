/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.action.editor

import com.basmilius.bastools.core.util.EditorUtils
import com.basmilius.bastools.core.util.ExceptionUtils
import com.basmilius.bastools.core.util.JUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.ui.ColorPicker
import com.intellij.ui.ColorUtil
import com.intellij.ui.JBColor

/**
 * Class ShowColorPickerAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.editor
 * @since 1.0.0
 */
class ShowColorPickerAction: AnAction("Show Color Picker")
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		val project = aae.project
		val root = JUtils.getRootComponent(aae.project)

		if (project == null || root == null)
			return

		val color = ColorPicker.showDialog(root, "Pick a Color", JBColor.CYAN, false, null, false) ?: return
		val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return

		ExceptionUtils.executeIgnore {
			EditorUtils.insertOrReplaceMultiCaret(editor, project, "#" + ColorUtil.toHex(color))
		}
	}

}
