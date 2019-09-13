/*
 * Copyright © 2019 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.theme.tabs

import com.basmilius.bastools.theme.BTTheme
import com.intellij.openapi.rd.fill2DRect
import com.intellij.openapi.rd.paint2DLine
import com.intellij.ui.JBColor
import com.intellij.ui.paint.LinePainter2D
import com.intellij.ui.scale.JBUIScale
import com.intellij.ui.tabs.JBTabsPosition
import com.intellij.ui.tabs.impl.JBDefaultTabPainter
import com.intellij.ui.tabs.impl.themes.EditorTabTheme
import java.awt.*

/**
 * Class BTEditorTabPainter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.theme.tabs
 * @since 1.5.0
 */
class BTEditorTabPainter: JBDefaultTabPainter(EditorTabTheme())
{

	override fun paintBorderLine(g: Graphics2D, thickness: Int, from: Point, to: Point)
	{
		if (!BTTheme.isUsed())
			return super.paintBorderLine(g, thickness, from, to)

		if (from.y == 0)
			return

		g.paint2DLine(from, to, LinePainter2D.StrokeType.INSIDE, thickness.toDouble(), JBColor.namedColor("BT.Tabs.Outline", Color.WHITE))
	}

	override fun paintTab(position: JBTabsPosition, g: Graphics2D, rect: Rectangle, borderThickness: Int, tabColor: Color?, active: Boolean, hovered: Boolean)
	{
		if (!BTTheme.isUsed())
			return super.paintTab(position, g, rect, borderThickness, tabColor, active, hovered)

		val x = rect.x.toDouble()
		val y = rect.height.toDouble() - JBUIScale.scale(1)
		val w = rect.width.toDouble()

		g.fill2DRect(rect, JBColor.namedColor("BT.Tabs.Background", Color.WHITE))
		g.paint2DLine(x, y, x + w, y, LinePainter2D.StrokeType.INSIDE, JBUIScale.scale(1).toDouble(), JBColor.namedColor("BT.Tabs.Outline", Color.WHITE))
	}

	override fun paintSelectedTab(position: JBTabsPosition, g: Graphics2D, rect: Rectangle, borderThickness: Int, tabColor: Color?, active: Boolean, hovered: Boolean)
	{
		this.paintSelectedTab(position, g, rect, borderThickness, tabColor, active, hovered, false)
	}

	fun paintSelectedTab(position: JBTabsPosition, g: Graphics2D, rect: Rectangle, borderThickness: Int, tabColor: Color?, active: Boolean, hovered: Boolean, first: Boolean)
	{
		if (!BTTheme.isUsed())
			return super.paintSelectedTab(position, g, rect, borderThickness, tabColor, active, hovered)

		g.fill2DRect(rect, JBColor.namedColor("BT.Tabs.Selected", Color.WHITE))

		g.color = JBColor.namedColor("BT.Tabs.Outline", Color.WHITE)
		g.stroke = BasicStroke(JBUIScale.scale(1f))

		if (!first)
			g.drawLine(rect.x, rect.y + rect.height, rect.x, rect.y)

		g.drawLine(rect.x, rect.y, rect.x + rect.width - JBUIScale.scale(1), rect.y)
		g.drawLine(rect.x + rect.width - JBUIScale.scale(1), rect.y, rect.x + rect.width - JBUIScale.scale(1), rect.y + rect.height)
	}

	override fun underlineRectangle(position: JBTabsPosition, rect: Rectangle, thickness: Int): Rectangle = if (BTTheme.isUsed()) Rectangle(0, 0, 0, 0) else super.underlineRectangle(position, rect, thickness)

}
