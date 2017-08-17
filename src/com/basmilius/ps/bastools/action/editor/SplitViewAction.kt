package com.basmilius.ps.bastools.action.editor

import com.basmilius.ps.bastools.util.EditorUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.fileEditor.impl.EditorWindow
import com.intellij.openapi.wm.ToolWindowManager

/**
 * Class SplitViewAction
 *
 * @constructor
 * @param orientation Int
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.action.editor
 */
abstract class SplitViewAction(private val orientation : Int) : AnAction("Split Editor")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun actionPerformed(aae : AnActionEvent?)
	{
		if (aae == null)
			return

		val project = aae.project ?: return
		val fileEditorManager = FileEditorManagerEx.getInstanceEx(project)
		val window = aae.getData(EditorWindow.DATA_KEY)
		val file = aae.getData(CommonDataKeys.VIRTUAL_FILE)
		val windowManager = ToolWindowManager.getInstance(project)

		if (!windowManager.isEditorComponentActive)
			return

		fileEditorManager.createSplitter(orientation, window)

		if (window != null && file != null)
		{
			window.closeFile(file, false, false)
		}

		EditorUtils.switchToTab(project, aae.dataContext, -1)
	}

}
