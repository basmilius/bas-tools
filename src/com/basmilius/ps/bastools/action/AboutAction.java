package com.basmilius.ps.bastools.action;

import com.basmilius.ps.bastools.dialog.AboutDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class AboutAction extends AnAction
{

	public AboutAction()
	{
		super("About Bas Tools");
	}

	@Override
	public void actionPerformed (final AnActionEvent anActionEvent)
	{
		AboutDialog.showDialog();
	}

}
