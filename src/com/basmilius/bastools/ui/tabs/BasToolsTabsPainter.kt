package com.basmilius.bastools.ui.tabs

import com.intellij.ui.tabs.impl.DefaultEditorTabsPainter
import com.intellij.ui.tabs.impl.JBEditorTabs
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle

/**
 * Class BasToolsTabsPainter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.tabs
 * @since 1.0.0
 */
open class BasToolsTabsPainter: DefaultEditorTabsPainter
{

	/**
	 * BasToolsTabsPainter Constructor.
	 *
	 * Suppress unused, because we need this in Reflection.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	@Suppress("unused")
	constructor(): super(null)

	/**
	 * BasToolsTabsPainter Constructor.
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
		g.color = color ?: this.defaultTabColor
		g.fillRect(x - 1, y - 1, w + 2, h + 2)
	}

	/**
	 * Paints the background.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun doPaintBackground(g: Graphics2D, clip: Rectangle, vertical: Boolean, rectangle: Rectangle?)
	{
		g.color = this.defaultTabColor
		g.fillRect(rectangle!!.x - 1, rectangle.y - 1, rectangle.width + 2, rectangle.height + 2)
	}

	/**
	 * Fills the selection and border.
	 *
	 * @param g Graphics2D
	 * @param color Color?
	 * @param x Int
	 * @param y Int
	 * @param width Int
	 * @param height Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun fillSelectionAndBorder(g: Graphics2D, color: Color?, x: Int, y: Int, width: Int, height: Int)
	{
		g.color = color ?: this.defaultTabColor
		g.fillRect(x - 1, y - 1, width + 2, height + 2)
	}

	/**
	 * Gets the background color.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getBackgroundColor(): Color
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor
	}

	/**
	 * Gets the empty space color.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getEmptySpaceColor(): Color
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor
	}

	/**
	 * Gets the default tab color.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getDefaultTabColor(): Color
	{
		return this.backgroundColor
	}

	/**
	 * Gets the inactive mask color.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getInactiveMaskColor(): Color
	{
		return this.backgroundColor
	}

	/**
	 * Gets the editor tabs.
	 *
	 * @return JBEditorTabs
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	val tabsComponent: JBEditorTabs get() = this.myTabs

}
