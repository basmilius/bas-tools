package com.basmilius.ps.bastools.action.angular

import com.basmilius.ps.bastools.util.CommandUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages

/**
 * Class VersionAction
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.action.angular
 */
class VersionAction : AnAction("Show Angular Version")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		val project = aae.project ?: return

		CommandUtils.run(project, "powershell ng version", { lines: Array<String> ->
			val result = lines.filter { it.startsWith("@angular/") || it.startsWith("node:") || it.startsWith("os:") }

			ApplicationManager.getApplication().invokeLater { Messages.showMessageDialog(project, result.joinToString(System.getProperty("line.separator")), "Angular Version", Messages.getInformationIcon()) }
		} as CommandUtils.ICommandResultHandler)

	}

}
