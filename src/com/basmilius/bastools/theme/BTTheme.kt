/*
 * Copyright © 2019 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.theme

import com.basmilius.bastools.core.util.ReflectionUtils
import com.basmilius.bastools.core.util.dontCare
import com.basmilius.bastools.resource.Icons
import com.basmilius.bastools.theme.tabs.BTEditorTabPainter
import com.basmilius.bastools.theme.ui.icon.BTUIDefaultMenuArrowIcon
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.laf.LafManagerImpl
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.tabs.JBTabPainter
import com.intellij.ui.tabs.newImpl.JBEditorTabsBorder
import com.intellij.ui.tabs.newImpl.SameHeightTabs
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

	fun isUsed(): Boolean = LafManagerImpl.getInstance().currentLookAndFeel?.name == themeName

	fun apply() = dontCare {
		patch()
	}

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
			val project = ProjectManager.getInstance().openProjects[0]
			val editorManager = FileEditorManager.getInstance(project)

			onLafChanged(it)
			patchEditorTabs(editorManager)
		}

		onLafChanged(LafManagerImpl.getInstance())
	}

	private fun patch()
	{
		logger.info("Applying BTTheme patches...")
		ReflectionUtils.setCompanionVal(JBTabPainter::class, "EDITOR", BTEditorTabPainter())
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
		val currentEditor = source.selectedEditor ?: return
		val tabbedContainer = currentEditor.component.parent?.parent?.parent?.parent as SameHeightTabs? ?: return
		val tabsBorder = tabbedContainer.border as JBEditorTabsBorder

		tabsBorder.thickness = if (isUsed()) 0 else 1
	}

	class FileEditorListener: FileEditorManagerListener
	{

		override fun fileOpened(source: FileEditorManager, file: VirtualFile) = patchEditorTabs(source)

	}

}
