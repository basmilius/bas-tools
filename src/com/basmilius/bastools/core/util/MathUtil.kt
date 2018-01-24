package com.basmilius.bastools.core.util

/**
 * Object MathUtil
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.4.0
 */
object MathUtil
{

	/**
	 * Clamps a {@see value} between {@see min} and {@see max}.
	 *
	 * @param value Double
	 * @param min Double
	 * @param max Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun clamp(value: Double, min: Double, max: Double): Double = Math.max(min, Math.min(max, value))

	/**
	 * Clamps a {@see value} between {@see min} and {@see max}.
	 *
	 * @param value Float
	 * @param min Float
	 * @param max Float
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun clamp(value: Float, min: Float, max: Float): Float = Math.max(min, Math.min(max, value))

	/**
	 * Clamps a {@see value} between {@see min} and {@see max}.
	 *
	 * @param value Int
	 * @param min Int
	 * @param max Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun clamp(value: Int, min: Int, max: Int): Int = Math.max(min, Math.min(max, value))

}
