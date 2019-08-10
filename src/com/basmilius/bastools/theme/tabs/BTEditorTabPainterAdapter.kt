/*
 * Copyright © 2019 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.theme.tabs

import com.intellij.ui.tabs.JBTabPainter
import com.intellij.ui.tabs.newImpl.JBTabsImpl
import com.intellij.ui.tabs.newImpl.TabLabel
import com.intellij.ui.tabs.newImpl.TabPainterAdapter
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * Class BTEditorTabPainterAdapter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.theme
 * @since 1.5.0
 */
class BTEditorTabPainterAdapter: TabPainterAdapter
{

	private val painter = BTEditorTabPainter()

	override val tabPainter: JBTabPainter
		get() = this.painter

	override fun paintBackground(label: TabLabel, g: Graphics, tabs: JBTabsImpl)
	{
		val info = label.info
		val isFirst = tabs.tabs.first() == info
		val isSelected = info == tabs.selectedInfo

		val rect = Rectangle(0, 0, label.width, label.height)
		val g2d = g as Graphics2D

		if (isSelected)
			painter.paintSelectedTab(tabs.tabsPosition, g2d, rect, tabs.borderThickness, info.tabColor, active = false, hovered = false, first = isFirst)
		else
			painter.paintTab(tabs.tabsPosition, g2d, rect, tabs.borderThickness, info.tabColor, false)
	}

}
