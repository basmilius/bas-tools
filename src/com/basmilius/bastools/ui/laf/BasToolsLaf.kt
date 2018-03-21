/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf

import com.basmilius.bastools.core.util.StaticPatcher
import com.basmilius.bastools.resource.Icons
import com.basmilius.bastools.ui.laf.border.BTUIButtonBorder
import com.basmilius.bastools.ui.laf.border.BTUIMenuItemBorder
import com.basmilius.bastools.ui.laf.icon.BTUIDefaultMenuArrowIcon
import com.basmilius.bastools.ui.laf.ui.*
import com.intellij.ide.navigationToolbar.ui.NavBarUIManager
import com.intellij.ide.ui.laf.darcula.DarculaLaf
import com.intellij.ui.JBColor
import com.intellij.util.IconUtil
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import javax.swing.UIDefaults
import javax.swing.UIManager

/**
 * Class BasToolsLaf
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf
 * @since 1.0.0
 */
class BasToolsLaf: DarculaLaf()
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getDescription() = "Better dark theme for IntelliJ IDEA."

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getName() = "BasTools Darcula"

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getPrefix() = "bastools"

	/**
	 * Start patching the UI.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun patchUI()
	{
		this.patchUIUtil()
	}

	/**
	 * Patches the UI with utils.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun patchUIUtil()
	{
		val color = UIManager.getColor("Panel.background")

		StaticPatcher.setFinalStatic(UIUtil::class, "CONTRAST_BORDER_COLOR", color)
		StaticPatcher.setFinalStatic(UIUtil::class, "BORDER_COLOR", color)
		StaticPatcher.setFinalStatic(UIUtil::class, "AQUA_SEPARATOR_FOREGROUND_COLOR", color)

		StaticPatcher.setFinalStatic(UIUtil::class, "BORDER_COLOR", JBColor(0xff0000, 0xff0000))
		StaticPatcher.setFinalStatic(UIUtil::class, "CONTRAST_BORDER_COLOR", JBColor(0x2c3134, 0x2c3134))

		StaticPatcher.setFinalStatic(UIUtil::class, "SIDE_PANEL_BACKGROUND", JBColor(0x292d30, 0x292d30))

		StaticPatcher.setFinalStatic(UIUtil::class, "AQUA_SEPARATOR_FOREGROUND_COLOR", JBColor(0x3A3F43, 0x3A3F43))
		StaticPatcher.setFinalStatic(UIUtil::class, "AQUA_SEPARATOR_BACKGROUND_COLOR", JBColor(0x3A3F43, 0x3A3F43))

		StaticPatcher.setFinalStatic(NavBarUIManager::class, "DARCULA", BTUINavBarItemUI())
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getDefaults(): UIDefaults
	{
		val defaults = super.getDefaults()

		defaults.forEach { key, value ->
			System.out.println("$key => $value")
		}

		return defaults
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun loadDefaults(defaults: UIDefaults)
	{
		super.loadDefaults(defaults)

		this.uiDefaultsButton(defaults)
		this.uiDefaultsMenu(defaults)
		this.uiDefaultsStatusBar(defaults)
		this.uiDefaultsTabbedPane(defaults)
		this.uiDefaultsTree(defaults)
	}

	/**
	 * Adds new Button default UI values.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun uiDefaultsButton(defaults: UIDefaults)
	{
//		defaults["Button.border"] = BTUIButtonBorder()
//		defaults["ButtonUI"] = BTUIButtonUI::class.qualifiedName
//		defaults["ToggleButtonUI"] = BTUIButtonUI::class.qualifiedName
//		defaults[BTUIButtonUI::class.qualifiedName] = BTUIButtonUIProxy::class.java
	}

	/**
	 * Adds new Menu/MenuItem default UI values.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun uiDefaultsMenu(defaults: UIDefaults)
	{
		defaults["Menu.arrowIcon"] = BTUIDefaultMenuArrowIcon(IconUtil.scale(Icons.ChevronRight, 0.75))
		defaults["Menu.border"] = BTUIMenuItemBorder()
		defaults["Menu.maxGutterIconWidth"] = JBUI.scale(24)
		defaults["Menu.submenuPopupOffsetY"] = JBUI.scale(-1)
		defaults["MenuItem.border"] = BTUIMenuItemBorder()
		defaults["MenuItem.maxGutterIconWidth"] = JBUI.scale(24)
	}

	/**
	 * Adds new StatusBar default UI values.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun uiDefaultsStatusBar(defaults: UIDefaults)
	{
		defaults["IdeStatusBarUI"] = BTUIStatusBarUI::class.qualifiedName
		defaults[BTUIStatusBarUI::class.qualifiedName] = BTUIStatusBarUIProxy::class.java
	}

	/**
	 * Adds new TabbedPane default UI values.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun uiDefaultsTabbedPane(defaults: UIDefaults)
	{
		defaults["TabbedPane.tabInsets"] = JBInsets.JBInsetsUIResource(JBUI.insets(6, 12))
	}

	/**
	 * Adds new Tree/TreeItem default UI values.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun uiDefaultsTree(defaults: UIDefaults)
	{
		defaults["TreeUI"] = BTUITreeUI::class.qualifiedName
		defaults[BTUITreeUI::class.qualifiedName] = BTUITreeUIProxy::class.java
	}

}
