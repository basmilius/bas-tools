/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui.laf.ui

import com.basmilius.bastools.core.util.ColorUtil.toJBColor
import com.intellij.ide.navigationToolbar.NavBarItem
import com.intellij.ide.navigationToolbar.NavBarPanel
import com.intellij.ide.navigationToolbar.ui.DarculaNavBarUI
import com.intellij.ide.ui.UISettings
import com.intellij.util.ui.JBInsets
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import gnu.trove.THashMap
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.Path2D
import java.awt.image.BufferedImage

/**
 * Class BTUINavBarItemUI
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.laf.ui
 * @since 1.4.0
 */
class BTUINavBarItemUI: DarculaNavBarUI()
{

	/**
	 * Enum Class ImageType
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.ui.laf.ui.BTUINavBarItemUI
	 * @since 1.4.0
	 */
	enum class ImageType
	{

		Inactive,
		NextActive,
		Active,
		InactiveFloating,
		NextActiveFloating,
		ActiveFloating,
		InactiveNoToolbar,
		NextActiveNoToolbar,
		ActiveNoToolbar

	}

	/**
	 * Companion Object ImageType
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.ui.laf.ui
	 * @since 1.4.0
	 */
	companion object
	{

		private val ImageCache = THashMap<NavBarItem, THashMap<ImageType, BufferedImage>>()

		/**
		 * Draws our item to the cache buffer.
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		private fun drawToBuffer(item: NavBarItem, navbar: NavBarPanel, isFloating: Boolean, isToolbarVisible: Boolean, isSelected: Boolean): BufferedImage
		{
			val width = item.width
			val height = item.height
			val offset = width - getDecorationOffset()
			val heightHalf = height / 2

			val result = UIUtil.createImage(width, height, BufferedImage.TYPE_INT_ARGB)

			val defaultBg = Color(255, 255, 255, 16).toJBColor()
			val bg = if (isFloating) defaultBg else null
			val selection = UIUtil.getListSelectionBackground()

			val graphics = result.createGraphics()
			graphics.stroke = BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND)
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

			val shape = Path2D.Double()
			shape.moveTo(0.0, 0.0)
			shape.lineTo(offset.toDouble(), 0.0)
			shape.lineTo(width.toDouble(), heightHalf.toDouble())
			shape.lineTo(offset.toDouble(), height.toDouble())
			shape.lineTo(0.0, height.toDouble())
			shape.closePath()

			val endShape = Path2D.Double()
			endShape.moveTo(offset.toDouble(), 0.0)
			endShape.lineTo(width.toDouble(), 0.0)
			endShape.lineTo(width.toDouble(), height.toDouble())
			endShape.lineTo(offset.toDouble(), height.toDouble())
			endShape.lineTo(width.toDouble(), heightHalf.toDouble())
			endShape.closePath()

			if (bg != null && isToolbarVisible)
			{
				graphics.paint = bg
				graphics.fill(shape)
				graphics.fill(endShape)
			}

			if (isSelected)
			{
				val focusShape = Path2D.Double()

				if (isToolbarVisible || isFloating)
				{
					focusShape.moveTo(offset.toDouble(), 0.0)
				}
				else
				{
					focusShape.moveTo(0.0, 0.0)
					focusShape.lineTo(offset.toDouble(), 0.0)
				}

				focusShape.lineTo((width - 1).toDouble(), heightHalf.toDouble())
				focusShape.lineTo(offset.toDouble(), (height - 1).toDouble())

				if (!isToolbarVisible && !isFloating)
					focusShape.lineTo(0.0, (height - 1).toDouble())

				graphics.color = selection

				if (isFloating && item.isLastElement)
				{
					graphics.fillRect(0, 0, width, height)
				}
				else
				{
					graphics.fill(shape)

					graphics.color = Color(0, 0, 0, 12).toJBColor()
					graphics.draw(focusShape)
				}
			}

			if (item.isNextSelected && navbar.isFocused)
			{
				graphics.color = selection
				graphics.fill(endShape)

				val endFocusShape = Path2D.Double()
				if (isToolbarVisible || isFloating)
				{
					endFocusShape.moveTo(offset.toDouble(), 0.0)
				}
				else
				{
					endFocusShape.moveTo(width.toDouble(), 0.0)
					endFocusShape.lineTo(offset.toDouble(), 0.0)
				}

				endFocusShape.lineTo((width - 1).toDouble(), heightHalf.toDouble())
				endFocusShape.lineTo(offset.toDouble(), (height - 1).toDouble())

				if (!isToolbarVisible && !isFloating)
					endFocusShape.lineTo(width.toDouble(), (height - 1).toDouble())

				graphics.color = Color(0, 0, 0, 12).toJBColor()
				graphics.draw(endFocusShape)
			}

			graphics.translate(offset, 0)

			if ((!isFloating || !item.isLastElement) && !item.isLastElement)
				drawArrow(graphics, Color(255, 255, 255, 16).toJBColor(), getDecorationOffset(), height)

			graphics.dispose()

			return result
		}

		/**
		 * Draws the arrow.
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		private fun drawArrow(graphics: Graphics2D, light: Color, decorationOffset: Int, height: Int)
		{
			graphics.translate(0, 0)
			graphics.color = light
			graphics.drawLine(0, 0, decorationOffset, height / 2)
			graphics.drawLine(decorationOffset, height / 2, 0, height)
		}

		/**
		 * Gets the decoration offset.
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.4.0
		 */
		private fun getDecorationOffset() = JBUI.scale(6)

	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun doPaintNavBarItem(graphics: Graphics2D, item: NavBarItem, navbar: NavBarPanel)
	{
		val isFloating = navbar.isInFloatingMode
		val isToolbarVisible = UISettings.instance.showMainToolbar
		val isSelected = item.isSelected && item.isFocused
		val isNextSelected = item.isNextSelected && navbar.isFocused

		val type = when
		{
			isFloating -> when
			{
				isSelected -> ImageType.ActiveFloating
				isNextSelected -> ImageType.NextActiveFloating
				else -> ImageType.InactiveFloating
			}
			isToolbarVisible -> when
			{
				isSelected -> ImageType.Active
				isNextSelected -> ImageType.NextActive
				else -> ImageType.Inactive
			}
			else -> when
			{
				isSelected -> ImageType.ActiveNoToolbar
				isNextSelected -> ImageType.NextActiveNoToolbar
				else -> ImageType.InactiveNoToolbar
			}
		}

		val cached = ImageCache.computeIfAbsent(item) { THashMap() }
		val image = cached.computeIfAbsent(type) { drawToBuffer(item, navbar, isFloating, isToolbarVisible, isSelected) }

		UIUtil.drawImage(graphics, image, 0, 0, null)

		val icon = item.icon
		val offset = if (item.isFirstElement) getDecorationOffset() else 0

		val iconOffset = elementPadding.left + offset
		icon.paintIcon(item, graphics, iconOffset, (item.height - icon.iconHeight) / 2)

		val textOffset = icon.iconWidth + elementPadding.width() + offset
		item.doPaintText(graphics, textOffset)
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getElementPadding(): JBInsets = JBUI.insets(3, 7, 3, 9)

}
