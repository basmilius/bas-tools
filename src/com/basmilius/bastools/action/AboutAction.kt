package com.basmilius.bastools.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages

/**
 * Class AboutAction
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.action
 * @since 1.0.0
 */
class AboutAction: AnAction("About Bas Tools")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		ApplicationManager.getApplication().invokeLater { Messages.showMessageDialog(aae.project, "Bas Tools 1.3.1, go to bas.tools for more information about this plugin.", "Bas Tools", Messages.getInformationIcon()) }
	}

}
