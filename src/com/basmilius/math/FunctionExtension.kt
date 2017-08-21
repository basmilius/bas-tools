package com.basmilius.math

/**
 * Interface FunctionExtension
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser
 */
interface FunctionExtension
{

	/**
	 * Gets parameters number.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getParametersNumber(): Int

	/**
	 * Sets value of function parameter.
	 *
	 * @param parameterIndex Int
	 * @param parameterValue Double
	 *
	 * @author Bas Milius
	 */
	fun setParameterValue(parameterIndex: Int, parameterValue: Double)

	/**
	 * Gets parameter name.
	 *
	 * @param parameterIndex Int
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getParameterName(parameterIndex: Int): String

	/**
	 * Actual algorithm implementation.
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun calculate(): Double

	/**
	 *
	 *
	 * @author Bas Milius
	 */
	fun clone(): FunctionExtension

}
