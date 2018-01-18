package com.basmilius.math.mathcollection

/**
 * Object Evaluate
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.mathcollection
 */
object Evaluate
{

	/**
	 * Polynomial evaluation based on provided coefficients.
	 *
	 * @param x Double
	 * @param coefficients DoubleArray
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun polynomial(x: Double, coefficients: DoubleArray): Double
	{
		if (x.isNaN()) return Double.NaN
		if (coefficients.isEmpty()) return Double.NaN
		if (coefficients.size == 1) return coefficients[0]

		var sum = coefficients[coefficients.size - 1]
		if (sum.isNaN())
			return Double.NaN

		for (i in coefficients.size - 2 downTo 0)
		{
			if (coefficients[i].isNaN())
				return Double.NaN

			sum *= x
			sum += coefficients[i]
		}

		return sum
	}

}
