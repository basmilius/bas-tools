/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.tabs

import com.basmilius.bastools.ui.BTUI
import com.intellij.ui.tabs.impl.DefaultEditorTabsPainter
import com.intellij.ui.tabs.impl.JBEditorTabs
import com.intellij.util.ui.JBUI
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * Open Class BTTabsPainter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.tabs
 * @since 1.0.0
 */
open class BTTabsPainter: DefaultEditorTabsPainter
{

	private fun Int.scale() = JBUI.scale(this)

	/**
	 * BTTabsPainter Constructor.
	 *
	 * Suppress unused, because we need this in Reflection.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	@Suppress("unused")
	constructor(): super(null)

	/**
	 * BTTabsPainter Constructor.
	 *
	 * @param tabs Editor tabs.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	constructor(tabs: JBEditorTabs): super(tabs)

	/**
	 * Paints the inactive.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun doPaintInactive(g: Graphics2D, effectiveBounds: Rectangle?, x: Int, y: Int, w: Int, h: Int, color: Color?, row: Int, column: Int, vertical: Boolean)
	{
		g.color = color ?: BTUI.Colors.BackgroundColor.asJBColor()
		g.fillRect(x - 1.scale(), y - 1.scale(), w + 2.scale(), h + 2.scale())

		g.color = BTUI.Colors.OutlineColor.asJBColor()
		g.stroke = BasicStroke(1f)
		g.drawLine(x, y + h, x + w, y + h)
	}

	/**
	 * Paints the background.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun doPaintBackground(g: Graphics2D, clip: Rectangle, vertical: Boolean, rectangle: Rectangle)
	{
		g.color = BTUI.Colors.BackgroundColor.asJBColor()
		g.fillRect(rectangle.x - 1.scale(), rectangle.y - 1.scale(), rectangle.width + 2.scale(), rectangle.height + 2.scale())

		g.color = BTUI.Colors.OutlineColor.asJBColor()
		g.stroke = BasicStroke(1f)
		g.drawLine(rectangle.x, rectangle.y + rectangle.height, rectangle.x + rectangle.width, rectangle.y + rectangle.height)
	}

	/**
	 * Fills the selection and border.
	 *
	 * @param g Graphics2D
	 * @param x Int
	 * @param y Int
	 * @param width Int
	 * @param height Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun fillSelectionAndBorder(g: Graphics2D, x: Int, y: Int, width: Int, height: Int)
	{
		g.color = BTUI.Colors.BackgroundLightColor.asJBColor()
		g.fillRect(x, y, width, height + 1.scale())

		g.color = BTUI.Colors.OutlineColor.asJBColor()
		g.stroke = BasicStroke(JBUI.scale(1f))
		g.drawLine(x - 1.scale(), y + height, x - 1.scale(), y)
		g.drawLine(x - 1.scale(), y, x + width, y)
		g.drawLine(x + width, y + height, x + width, y)
	}

	/**
	 * Gets the background color.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getBackgroundColor(): Color
	{
		return BTUI.Colors.BackgroundColor.asJBColor()
	}

	/**
	 * Gets the empty space color.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getEmptySpaceColor(): Color
	{
		return BTUI.Colors.BackgroundColor.asJBColor()
	}

	/**
	 * Gets the default tab color.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getDefaultTabColor(): Color
	{
		return BTUI.Colors.BackgroundColor.asJBColor()
	}

	/**
	 * Gets the inactive mask color.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getInactiveMaskColor(): Color
	{
		return BTUI.Colors.BackgroundColor.asJBColor()
	}

	/**
	 * Gets the textEditor tabs.
	 *
	 * @return JBEditorTabs
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	val tabsComponent: JBEditorTabs get() = this.myTabs

}
