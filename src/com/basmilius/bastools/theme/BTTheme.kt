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
import com.basmilius.bastools.resource.Icons
import com.basmilius.bastools.theme.tabs.BTEditorTabPainter
import com.basmilius.bastools.theme.ui.icon.BTUIDefaultMenuArrowIcon
import com.intellij.ide.ui.laf.LafManagerImpl
import com.intellij.openapi.diagnostic.logger
import com.intellij.ui.tabs.JBTabPainter
import com.intellij.util.IconUtil
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
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

	val logger = logger("BTTheme")

	fun isUsed(): Boolean = LafManagerImpl.getInstance().currentLookAndFeel?.name == "Bas Tools"

	fun apply()
	{
		patch()
		overrideUIDefaults()
	}

	fun patch()
	{
		logger.info("Applying BTTheme patches...")
		ReflectionUtils.setCompanionVal(JBTabPainter::class, "EDITOR", BTEditorTabPainter())
	}

	fun overrideUIDefaults()
	{
		logger.info("Applying BTTheme ui overrides...")

		UIManager.getDefaults()["Menu.arrowIcon"] = BTUIDefaultMenuArrowIcon(IconUtil.scale(Icons.ChevronRight, null, 0.75f))

		UIManager.getDefaults()["Tree.collapsedIcon"] = IconUtil.darker(IconUtil.scale(Icons.ChevronRight, null, .8f), 10)
		UIManager.getDefaults()["Tree.collapsedSelectedIcon"] = IconUtil.darker(IconUtil.scale(Icons.ChevronRight, null, .8f), 10)
		UIManager.getDefaults()["Tree.expandedIcon"] = IconUtil.darker(IconUtil.scale(Icons.ChevronDown, null, .8f), 10)
		UIManager.getDefaults()["Tree.expandedSelectedIcon"] = IconUtil.darker(IconUtil.scale(Icons.ChevronDown, null, .8f), 10)

		UIManager.getDefaults()["ToolWindow.HeaderTab.verticalPadding"] = 9
		UIManager.getDefaults()["TabbedPane.tabInsets"] = JBInsets.JBInsetsUIResource(JBUI.insets(6, 12))
	}

}
