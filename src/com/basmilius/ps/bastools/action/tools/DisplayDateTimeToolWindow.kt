package com.basmilius.ps.bastools.action.tools

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager

/**
 * Class DisplayDateTimeToolWindow
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.action.tools
 */
class DisplayDateTimeToolWindow : AnAction("Show Date Time Tool Window")
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun actionPerformed(aae : AnActionEvent?)
	{
		if (aae === null)
			return

		val project = aae.project ?: return
		val toolWindow : ToolWindow = ToolWindowManager.getInstance(project).getToolWindow("Date Time Helpers") ?: return

		if (toolWindow.isVisible)
		{
			toolWindow.hide(null)
		}
		else
		{
			toolWindow.show(null)
		}
	}

}
