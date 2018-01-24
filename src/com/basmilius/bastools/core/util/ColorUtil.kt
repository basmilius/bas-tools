package com.basmilius.bastools.core.util

import java.awt.Color

/**
 * Object ColorUtil
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.4.0
 */
object ColorUtil
{

	/**
	 * Blends {@see color1} with {@see color2} with {@see weight}.
	 *
	 * @param color1 Color
	 * @param color2 Color
	 * @param weight Int
	 *
	 * @return Color
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun blend(color1: Color, color2: Color, weight: Int): Color
	{
		val clampedWeight = MathUtil.clamp(weight, 0, 100)
		val percentage = clampedWeight / 100
		val scaledWeight = percentage * 2 - 1
		val alphaDiff = color1.alpha - color2.alpha

		val weight1 = ((if (scaledWeight * alphaDiff == -1) scaledWeight else (scaledWeight + alphaDiff) / (1 + scaledWeight * alphaDiff)) + 1) / 2
		val weight2 = 1 - weight1

		return Color(
				(color1.red * weight1 + color2.red * weight2),
				(color1.green * weight1 + color2.green * weight2),
				(color1.blue * weight1 + color2.blue * weight2),
				(color1.alpha * weight1 + color2.alpha * weight2)
		)
	}

	/**
	 * Returns a shade of {@see color} with {@see weight}.
	 *
	 * @param color Color
	 * @param weight Int
	 *
	 * @return Color
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun shade(color: Color, weight: Int) = blend(Color.BLACK, color, weight)

	/**
	 * Returns a tint of {@see color} with {@see weight}.
	 *
	 * @param color Color
	 * @param weight Int
	 *
	 * @return Color
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun tint(color: Color, weight: Int) = blend(Color.WHITE, color, weight)

	/**
	 * Gets the luminance of {@see color}.
	 *
	 * @param color Color
	 *
	 * @return Float
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun luminance(color: Color): Float
	{
		val channels = arrayOf(color.red, color.green, color.blue)
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
	 * Calculates the YIQ value of a {@see color}.
	 *
	 * @param color Color
	 *
	 * @return Float
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun yiq(color: Color) = ((color.red * 299f) + (color.green * 587f) + (color.blue * 114)) / 1000

	/**
	 * Returns {@see dark} if {@see color} is a light color, otherwise it returns {@see light}.
	 *
	 * @param color Color
	 * @param dark Color
	 * @param light Color
	 * @param delta Float
	 *
	 * @return Color
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun lightOrDark(color: Color, dark: Color, light: Color, delta: Float) = if (color.getLuminance() < delta) light else dark

	/**
	 * Blends with another color using weight.
	 *
	 * @param other Color
	 * @param weight Int
	 *
	 * @return Color
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun Color.blendWith(other: Color, weight: Int) = blend(this, other, weight)

	/**
	 * Gets the luminance of the color.
	 *
	 * @return Float
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun Color.getLuminance(): Float = luminance(this)

	/**
	 * Calculates the YIQ value of the color.
	 *
	 * @return Float
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun Color.getYIQ() = yiq(this)

	/**
	 * Returns TRUE if the color is dark.
	 *
	 * @param delta Float
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun Color.isDark(delta: Float = 0.5f) = this.getLuminance() < delta

	/**
	 * Returns TRUE if the color is dark.
	 *
	 * @param delta Float
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun Color.isLight(delta: Float = 0.5f) = this.getLuminance() >= delta

}
