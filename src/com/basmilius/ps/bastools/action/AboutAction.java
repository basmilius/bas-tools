package com.basmilius.ps.bastools.action;

import com.basmilius.ps.bastools.dialog.AboutDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;

public class AboutAction extends AnAction
{

	public AboutAction()
	{
		super("About Bas Tools");
	}

	@Override
	public void actionPerformed (final AnActionEvent aae)
	{
		AboutDialog.showDialog();
	}

}
