package com.basmilius.bastools.action.angular

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

/**
 * Class VersionAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action.angular
 */
class VersionAction: AnAction("Show Angular Version")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		val project = aae.project ?: return

		Messages.showMessageDialog(project, "Not yet available", "Angular Verion", Messages.getInformationIcon());
	}

}
