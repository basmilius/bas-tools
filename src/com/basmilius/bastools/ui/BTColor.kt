/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.ui

import com.basmilius.bastools.core.util.MathUtil
import com.intellij.ui.JBColor
import java.awt.Color

/**
 * Class BTColor.
 *
 * @constructor
 * @param red {Int}
 * @param green {Int}
 * @param blue {Int}
 * @param alpha {Int}
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui
 * @since 1.4.0
 */
data class BTColor(var red: Int, var green: Int, var blue: Int, var alpha: Int = 255)
{

	private var lastColor: Color? = null

	/**
	 * Companion Object BTColor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.ui
	 * @since 1.4.0
	 */
	companion object
	{

		val BLACK = BTColor(0, 0, 0)
		val WHITE = BTColor(255, 255, 255)

	}

	/**
	 * Returns our color as {@see Color}.
	 *
	 * @return {Color}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun asColor(): Color
	{
		val lastColor = this.lastColor

		if (lastColor != null)
		{
			if (lastColor.red == this.red && lastColor.green == this.green && lastColor.blue == this.blue && lastColor.alpha == this.alpha)
				return lastColor
		}

		val color = Color(this.red, this.green, this.blue, this.alpha)

		this.lastColor = color

		return color
	}

	/**
	 * Returns our color as {@see JBColor}.
	 *
	 * @return {JBColor}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun asJBColor(): JBColor
	{
		val color = this.asColor()

		return JBColor(color, color)
	}

	/**
	 * Blends our color with another {@see color}.
	 *
	 * @param color {BTColor}
	 * @param weight {Int}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun blend(color: BTColor, weight: Int)
	{
		val clampedWeight = MathUtil.clamp(weight, 0, 100)
		val percentage = clampedWeight / 100f
		val scaledWeight = percentage * 2f - 1f
		val alphaDiff = this.alpha - color.alpha

		val weight1 = ((if (scaledWeight * alphaDiff == -1f) scaledWeight else (scaledWeight + alphaDiff) / (1f + scaledWeight * alphaDiff)) + 1f) / 2f
		val weight2 = 1 - weight1

		this.red = (this.red * weight1 + color.red * weight2).toInt()
		this.green = (this.green * weight1 + color.green * weight2).toInt()
		this.blue = (this.blue * weight1 + color.blue * weight2).toInt()
		this.alpha = (this.alpha * weight1 + color.alpha * weight2).toInt()
	}

	/**
	 * Returns {@see dark} if our color is light and {@see light} if it's dark.
	 *
	 * @param dark {Color}
	 * @param light {Color}
	 * @param delta {Float}
	 *
	 * @return {BTColor}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun lightOrDark(dark: BTColor, light: BTColor, delta: Float) = if (this.luminance() < delta) light else dark

	/**
	 * Gets the luminance of our color.
	 *
	 * @return {Float}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun luminance(): Float
	{
		val channels = arrayOf(this.red, this.green, this.blue)
				.map {
					val value = it / 255

					if (value < 0.03928)
						value / 12.92f
					else
						Math.pow((value + .055) / 1.055, 2.4).toFloat()
				}

		return (channels[0] * .2126f) + (channels[1] * .7152f) + (channels[2] * .00722f)
	}

	/**
	 * Shades our color.
	 *
	 * @param weight {Int}
	 *
	 * @see BTColor.blend
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun shade(weight: Int) = this.blend(BLACK, weight)

	/**
	 * Tints our color.
	 *
	 * @param weight {Int}
	 *
	 * @see BTColor.blend
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun tint(weight: Int) = this.blend(WHITE, weight)

	/**
	 * Calculates the YIQ value of our color.
	 *
	 * @return {Float}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun yiq() = ((this.red * 299f) + (this.green * 587f) + (this.blue * 114)) / 1000

}
