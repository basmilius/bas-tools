package com.basmilius.bastools.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages

/**
 * Class AboutAction
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.action
 */
class AboutAction: AnAction("About Bas Tools")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		ApplicationManager.getApplication().invokeLater { Messages.showMessageDialog(aae.project, "Bas Tools 1.1.0, go to bas-tools.atbm.nl for more information about this plugin.", "Bas Tools", Messages.getInformationIcon()) }
	}

}
