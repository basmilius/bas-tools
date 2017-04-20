package com.basmilius.ps.bastools.core.notifications;

import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;

public class NotificationManager
{

	private static NotificationGroup BTNotificationGroup = new NotificationGroup("Bas Tools Notifications", NotificationDisplayType.BALLOON, true);

	/**
	 * Displays a notification message.
	 * @param project Project that should be notified.
	 * @param content Contents of the notification.
	 * @param type Notification type.
	 */
	public static void notify (final Project project, final String content, final NotificationType type)
	{
		ApplicationManager.getApplication().invokeLater(() ->
		{
			final Notification notification = BTNotificationGroup.createNotification(content, type);
			Notifications.Bus.notify(notification, project);
		});
	}

	/**
	 * Displays a notification message.
	 * @param project Project that should be notified.
	 * @param title Title of the notification.
	 * @param content Contents of the notification.
	 * @param type Notification type.
	 */
	public static void notify (final Project project, final String title, final String content, final NotificationType type)
	{
		ApplicationManager.getApplication().invokeLater(() ->
		{
			final Notification notification = BTNotificationGroup.createNotification(title, content, type, null);
			Notifications.Bus.notify(notification, project);
		});
	}

}
