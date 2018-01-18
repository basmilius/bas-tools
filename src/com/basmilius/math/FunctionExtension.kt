package com.basmilius.math

/**
 * Interface FunctionExtension
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math
 */
interface FunctionExtension
{

	/**
	 * Gets parameters number.
	 *
	 * @return Int
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getParametersNumber(): Int

	/**
	 * Sets value of function parameter.
	 *
	 * @param parameterIndex Int
	 * @param parameterValue Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun setParameterValue(parameterIndex: Int, parameterValue: Double)

	/**
	 * Gets parameter name.
	 *
	 * @param parameterIndex Int
	 *
	 * @return String
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getParameterName(parameterIndex: Int): String

	/**
	 * Actual algorithm implementation.
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun calculate(): Double

	/**
	 *
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun clone(): FunctionExtension

}
