package com.basmilius.bastools.action.tools

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.wm.ToolWindowType

/**
 * Class DisplayDateTimeToolWindow
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.tools
 * @since 1.1.0
 */
class DisplayDateTimeToolWindow: AnAction("Show Date Time Tool Window")
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun actionPerformed(aae: AnActionEvent?)
	{
		if (aae === null)
			return

		val project = aae.project ?: return
		val toolWindow: ToolWindow = ToolWindowManager.getInstance(project).getToolWindow("Date Time Helpers") ?: return

		if (toolWindow.isVisible)
		{
			toolWindow.hide(null)
		}
		else
		{
			toolWindow.setType(ToolWindowType.FLOATING, null)
			toolWindow.show(null)
		}
	}

}
