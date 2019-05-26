/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.action.editor

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.wm.ToolWindowManager
import javax.swing.SwingConstants

/**
 * Class ShiftTabAction
 *
 * @constructor
 * @param orientation Int
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.textEditor
 * @since 1.1.0
 */
abstract class ShiftTabAction(private val orientation: Orientation): DumbAwareAction("Shift Tab")
{

	/**
	 * Enum Class Orientation
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.action.textEditor.ShiftTabAction
	 * @since 1.1.0
	 */
	enum class Orientation
	{
		LEFT,
		RIGHT,
		UP,
		DOWN
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		val project = aae.project ?: return
		val fileEditorManager = FileEditorManagerEx.getInstanceEx(project)
		val file = aae.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
		val windowManager = ToolWindowManager.getInstance(project)

		if (!windowManager.isEditorComponentActive)
			return

		val index = this.getCurrentSplitIndex(fileEditorManager)

		if (this.orientation == Orientation.RIGHT || this.orientation == Orientation.DOWN)
		{
			val isLastSplit = (fileEditorManager.windows.size - 1 == index)

			if (isLastSplit && fileEditorManager.currentWindow.tabCount == 1)
				return

			if (isLastSplit)
			{
				fileEditorManager.createSplitter(if (this.orientation == Orientation.RIGHT) SwingConstants.VERTICAL else SwingConstants.HORIZONTAL, fileEditorManager.currentWindow)
				fileEditorManager.windows[index].closeFile(file)
				fileEditorManager.windows[index + 1].setAsCurrentWindow(true)
			}
			else
			{
				val wasOnlyTab = fileEditorManager.windows[index].files.size == 1
				val shift = if (wasOnlyTab) 0 else 1

				fileEditorManager.windows[index].closeFile(file)
				fileEditorManager.windows[index + shift].setAsCurrentWindow(true)
				fileEditorManager.openFile(file, true)
			}
		}
		else
		{
			val isFirstSplit = (index == 0)

			if (isFirstSplit && fileEditorManager.currentWindow.tabCount == 1)
				return

			var newIndex = index - 1
			if (newIndex < 0)
				newIndex = fileEditorManager.windows.size - 1

			fileEditorManager.windows[index].closeFile(file)
			fileEditorManager.windows[newIndex].setAsCurrentWindow(true)
			fileEditorManager.openFile(file, true)
		}
	}

	/**
	 * Gets the current split index.
	 *
	 * @param fileEditorManagerEx FileEditorManagerEx
	 *
	 * @return Int
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun getCurrentSplitIndex(fileEditorManagerEx: FileEditorManagerEx): Int
	{
		return fileEditorManagerEx.windows.indexOfFirst { it == fileEditorManagerEx.currentWindow }
	}

}
