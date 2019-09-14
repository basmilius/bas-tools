/*
 * Copyright © 2019 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.theme

import com.basmilius.bastools.core.notifications.NotificationManager
import com.basmilius.bastools.core.util.ReflectionUtils
import com.basmilius.bastools.resource.Icons
import com.basmilius.bastools.theme.tabs.BTEditorTabPainterAdapter
import com.basmilius.bastools.theme.ui.icon.BTUIDefaultMenuArrowIcon
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.laf.LafManagerImpl
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.tabs.impl.EditorTabPainterAdapter
import com.intellij.ui.tabs.impl.JBEditorTabs
import com.intellij.ui.tabs.impl.JBEditorTabsBorder
import com.intellij.ui.tabs.impl.TabPainterAdapter
import com.intellij.util.IconUtil
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import javax.swing.UIDefaults
import javax.swing.UIManager

/**
 * Class BTTheme
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.theme
 * @since 1.5.0
 */
object BTTheme
{

	private val logger = logger("BTTheme")
	private const val themeName = "Bas Tools"

	private var isFancyTabsEnabled: Boolean = true

	fun isUsed(): Boolean = LafManagerImpl.getInstance().currentLookAndFeel?.name == themeName

	fun initFileListener()
	{
		ApplicationManager.getApplication().messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, FileEditorListener())
	}

	fun initLafListener()
	{
		fun onLafChanged(manager: LafManager)
		{
			val laf = manager.currentLookAndFeel ?: return

			if (laf.name != themeName)
				return

			overrideUIDefaults(UIManager.getLookAndFeelDefaults())
		}

		LafManagerImpl.getInstance().addLafManagerListener {
			val projects = ProjectManager.getInstance().openProjects

			if (projects.count() == 0)
				return@addLafManagerListener

			val project = projects[0]
			val editorManager = FileEditorManager.getInstance(project)

			onLafChanged(it)
			patchEditorTabs(editorManager)
		}

		onLafChanged(LafManagerImpl.getInstance())
	}

	private fun overrideUIDefaults(defaults: UIDefaults = UIManager.getDefaults())
	{
		logger.info("Applying BTTheme ui overrides...")

		defaults["Menu.arrowIcon"] = BTUIDefaultMenuArrowIcon(IconUtil.scale(Icons.ChevronRight, null, 0.75f))

		defaults["Tree.collapsedIcon"] = IconUtil.darker(IconUtil.scale(Icons.ChevronRight, null, .8f), 10)
		defaults["Tree.collapsedSelectedIcon"] = IconUtil.darker(IconUtil.scale(Icons.ChevronRight, null, .8f), 10)
		defaults["Tree.expandedIcon"] = IconUtil.darker(IconUtil.scale(Icons.ChevronDown, null, .8f), 10)
		defaults["Tree.expandedSelectedIcon"] = IconUtil.darker(IconUtil.scale(Icons.ChevronDown, null, .8f), 10)

		defaults["ToolWindow.HeaderTab.verticalPadding"] = 9
		defaults["TabbedPane.tabInsets"] = JBInsets.JBInsetsUIResource(JBUI.insets(6, 12))
	}

	private fun patchEditorTabs(source: FileEditorManager)
	{
		if (!this.isFancyTabsEnabled)
			return

		try
		{
			val currentEditor = source.selectedEditor ?: return
			val tabbedContainer = currentEditor.component.parent?.parent?.parent?.parent as JBEditorTabs? ?: return
			val tabsBorder = tabbedContainer.border as JBEditorTabsBorder

			val currentPainter = ReflectionUtils.getField(JBEditorTabs::class, tabbedContainer, TabPainterAdapter::class, "myTabPainterAdapter")

			if (isUsed() && currentPainter !is BTEditorTabPainterAdapter)
				ReflectionUtils.setField(JBEditorTabs::class, tabbedContainer, TabPainterAdapter::class, "myTabPainterAdapter", BTEditorTabPainterAdapter())
			else if (!isUsed() && currentPainter !is EditorTabPainterAdapter)
				ReflectionUtils.setField(JBEditorTabs::class, tabbedContainer, TabPainterAdapter::class, "myTabPainterAdapter", EditorTabPainterAdapter())

			tabsBorder.thickness = if (isUsed()) 0 else 1
		}
		catch (err: NoClassDefFoundError)
		{
			this.isFancyTabsEnabled = false

			NotificationManager.notify("Bas Tools", "Fancy tabs are disabled because of an error: ${err.message}", NotificationType.WARNING)
		}
	}

	class FileEditorListener: FileEditorManagerListener
	{

		override fun fileOpened(source: FileEditorManager, file: VirtualFile) = patchEditorTabs(source)

	}

}
