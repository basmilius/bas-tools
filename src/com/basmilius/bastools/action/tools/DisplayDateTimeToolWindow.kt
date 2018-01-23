package com.basmilius.bastools.action.tools

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.wm.ToolWindowType
import com.intellij.openapi.wm.WindowManager
import com.intellij.openapi.wm.impl.ToolWindowImpl
import com.intellij.util.ui.UIUtil
import java.awt.Dimension
import java.awt.Point

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
		val toolWindowManager = ToolWindowManager.getInstance(project)
		val toolWindow = toolWindowManager.getToolWindow("Datetime Tools") as? ToolWindowImpl ?: return

		if (toolWindow.isVisible)
		{
			toolWindow.hide { }
		}
		else
		{
			toolWindow.setType(ToolWindowType.FLOATING, null)
			toolWindow.show { }

			val ideWindow = WindowManager.getInstance().suggestParentWindow(project) ?: return
			val toolWindowPanel = UIUtil.getWindow(toolWindow.decorator) ?: return

			toolWindowPanel.location = Point(ideWindow.location.x + ideWindow.size.width - 332, ideWindow.location.y + 24 + 101)
			toolWindowPanel.size = Dimension(308, 233)
		}
	}

}
