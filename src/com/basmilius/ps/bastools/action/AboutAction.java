package com.basmilius.ps.bastools.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;

public class AboutAction extends AnAction
{

	public AboutAction()
	{
		super("About Bas Tools");
	}

	@Override
	public final void actionPerformed (final AnActionEvent aae)
	{
		ApplicationManager.getApplication().invokeLater(() -> Messages.showMessageDialog(aae.getProject(), "Bas Tools version 1.0.0.", "Bas Tools", Messages.getInformationIcon()));
	}

}
