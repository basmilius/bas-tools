package com.basmilius.ps.bastools.action;

import com.basmilius.ps.bastools.core.notifications.NotificationManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class TestAction extends AnAction
{

	public TestAction ()
	{
		super("Test");
	}

	@Override
	public void actionPerformed (final AnActionEvent aae)
	{
		NotificationManager.notify(aae.getProject(), "Notification test", "You pressed the test action!", NotificationType.INFORMATION);
	}

}