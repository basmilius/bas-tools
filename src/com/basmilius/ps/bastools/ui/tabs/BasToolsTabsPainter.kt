package com.basmilius.ps.bastools.ui.tabs

import com.intellij.ui.tabs.impl.DefaultEditorTabsPainter
import com.intellij.ui.tabs.impl.JBEditorTabs

import java.awt.*

open class BasToolsTabsPainter : DefaultEditorTabsPainter
{

	/**
	 * BasToolsTabsPainter Constructor.
	 *
	 * Suppress unused, because we need this in Reflection.
	 */
	@Suppress("unused")
	constructor() : super(null)

	/**
	 * BasToolsTabsPainter Constructor.
	 *
	 * @param tabs Editor tabs.
	 */
	constructor(tabs: JBEditorTabs) : super(tabs)

	/**
	 * Paints the inactive.
	 */
	override fun doPaintInactive(g: Graphics2D, effectiveBounds: Rectangle?, x: Int, y: Int, w: Int, h: Int, color: Color?, row: Int, column: Int, vertical: Boolean)
	{
		g.color = color ?: this.defaultTabColor
		g.fillRect(x - 1, y - 1, w + 1, h + 1)
	}

	/**
	 * Paints the background.
	 */
	override fun doPaintBackground(g: Graphics2D, clip: Rectangle, vertical: Boolean, rectangle: Rectangle?)
	{
		g.color = this.defaultTabColor
		g.fillRect(rectangle!!.x - 1, rectangle.y - 1, rectangle.width + 1, rectangle.height + 1)
	}

	/**
	 * Fills the selection and border.
	 */
	fun fillSelectionAndBorder(g: Graphics2D, color: Color?, x: Int, y: Int, width: Int, height: Int)
	{
		g.color = color ?: this.defaultTabColor
		g.fillRect(x - 1, y - 1, width + 1, height + 1)
	}

	/**
	 * Gets the background color.
	 */
	override fun getBackgroundColor(): Color
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor
	}

	/**
	 * Gets the empty space color.
	 */
	override fun getEmptySpaceColor(): Color
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor
	}

	/**
	 * Gets the default tab color.
	 */
	public override fun getDefaultTabColor(): Color
	{
		return this.backgroundColor
	}

	/**
	 * Gets the inactive mask color.
	 */
	override fun getInactiveMaskColor(): Color
	{
		return this.backgroundColor
	}

	val tabsComponent: JBEditorTabs get() = this.myTabs

}
