package com.basmilius.ps.bastools.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.Messages

class AboutAction : AnAction("About Bas Tools")
{

	/**
	 * {@inheritdoc}
	 */
	override fun actionPerformed(aae: AnActionEvent)
	{
		ApplicationManager.getApplication().invokeLater { Messages.showMessageDialog(aae.project, "Bas Tools version 1.0.0.", "Bas Tools", Messages.getInformationIcon()) }
	}

}
