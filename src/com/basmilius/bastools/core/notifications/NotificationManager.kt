package com.basmilius.bastools.core.notifications

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project

/**
 * Object NotificationManager
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.notifications
 * @since 1.0.0
 */
object NotificationManager
{

	private val BTNotificationGroup = NotificationGroup("Bas Tools Notifications", NotificationDisplayType.BALLOON, true)

	/**
	 * Displays a notification message.
	 *
	 * @param project Project that should be notified.
	 * @param content Contents of the notification.
	 * @param type Notification type.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun notify(project: Project, content: String, type: NotificationType)
	{
		ApplicationManager.getApplication().invokeLater {
			val notification = BTNotificationGroup.createNotification(content, type)
			Notifications.Bus.notify(notification, project)
		}
	}

	/**
	 * Displays a notification message.
	 *
	 * @param project Project that should be notified.
	 * @param title Title of the notification.
	 * @param content Contents of the notification.
	 * @param type Notification type.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun notify(project: Project, title: String, content: String, type: NotificationType)
	{
		ApplicationManager.getApplication().invokeLater {
			val notification = BTNotificationGroup.createNotification(title, content, type, null)
			Notifications.Bus.notify(notification, project)
		}
	}

}
