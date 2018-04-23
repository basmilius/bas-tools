/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf

import com.basmilius.bastools.resource.Icons
import com.basmilius.bastools.ui.laf.border.BTUIButtonBorder
import com.basmilius.bastools.ui.laf.border.BTUIMenuItemBorder
import com.basmilius.bastools.ui.laf.icon.BTUIDefaultMenuArrowIcon
import com.basmilius.bastools.ui.laf.patch.*
import com.basmilius.bastools.ui.laf.ui.*
import com.intellij.ide.ui.laf.darcula.DarculaLaf
import com.intellij.openapi.diagnostic.logger
import com.intellij.util.IconUtil
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import javax.swing.UIDefaults
import kotlin.reflect.full.createInstance

/**
 * Class BasToolsLaf
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf
 * @since 1.0.0
 */
class BasToolsLaf: DarculaLaf()
{

	companion object
	{

		private val logger = logger("BTUI")

		fun patch()
		{
			val patches = arrayOf(
					UIUtilPatch::class,
					IconPatch::class,

					CaptionPanelPatch::class,
					IdePanePanelPatch::class,
					NavBarUIManagerPatch::class,
					TabsUtilPatch::class,
					InternalDecoratorPatch::class,
					ToolWindowPatch::class
			)

			this.logger.debug("Performing ${patches.size} ui patches..")

			patches.forEach {

				try
				{
					this.logger.debug("Performing ui patch: ${it.simpleName}")

					it.createInstance().patch()

					this.logger.debug("Done with ui patch: ${it.simpleName}")
				}
				catch (err: Exception)
				{
					this.logger.error("Oh no! Ui patch failed: ${it.simpleName}")
					this.logger.error(err)
				}

			}

			this.logger.debug("Done with ${patches.size} ui patches")
		}

	}

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
		defaults["Button.border"] = BTUIButtonBorder()
		defaults["ButtonUI"] = BTUIButtonUI::class.qualifiedName
		defaults["ToggleButtonUI"] = BTUIButtonUI::class.qualifiedName
		defaults[BTUIButtonUI::class.qualifiedName] = BTUIButtonUIProxy::class.java
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
