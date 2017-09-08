package com.basmilius.ps.bastools.action.editor

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.wm.ToolWindowManager
import javax.swing.SwingConstants

/**
 * Class ShiftTabAction
 *
 * @constructor
 * @param orientation Int
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.action.editor
 */
abstract class ShiftTabAction(private val orientation: Orientation): AnAction("Split Editor")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun actionPerformed(aae: AnActionEvent?)
	{
		if (aae == null)
			return

		val project = aae.project ?: return
		val fileEditorManager = FileEditorManagerEx.getInstanceEx(project)
		val file = aae.getData(CommonDataKeys.VIRTUAL_FILE)
		val windowManager = ToolWindowManager.getInstance(project)

		if (!windowManager.isEditorComponentActive)
			return

		if (file == null)
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
	 * @author Bas Milius
	 */
	private fun getCurrentSplitIndex(fileEditorManagerEx: FileEditorManagerEx): Int
	{
		return fileEditorManagerEx.windows.indexOfFirst { it == fileEditorManagerEx.currentWindow }
	}

	/**
	 * Enum Class Orientation
	 *
	 * @author Bas Milius
	 * @package com.basmilius.ps.bastools.action.editor.ShiftTabAction
	 */
	enum class Orientation
	{

		LEFT,
		RIGHT,
		UP,
		DOWN
	}

}
