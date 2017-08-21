package com.basmilius.math.mathcollection

/**
 * Object BinaryRelations
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser.mathcollection
 */
object BinaryRelations
{

	val DEFAULT_COMPARISON_EPSILON = 0.00000000000001
	var epsilon = DEFAULT_COMPARISON_EPSILON
	var epsilonComparison = true

	/**
	 * Comparison mode indicator.
	 *
	 * @author Bas Milius
	 */
	fun setExactComparison()
	{
		epsilonComparison = false
	}

	/**
	 * Sets comparison mode to EXACT.
	 *
	 * @author Bas Milius
	 */
	fun setEpsilonComparison()
	{
		epsilonComparison = true
	}

	/**
	 * Sets default epsilon value.
	 *
	 * @author Bas Milius
	 */
	fun setDefaultEpsilon()
	{
		epsilon = DEFAULT_COMPARISON_EPSILON
	}

	/**
	 * Checks if epsilon comparison mode is active.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun checkIfEpsilonMode(): Boolean
	{
		return epsilonComparison
	}

	/**
	 * Checks if exact comparison mode is active.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun checkIfExactMode(): Boolean
	{
		return !epsilonComparison
	}

	/**
	 * Equality relation.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun eq(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN()) return Double.NaN

		val eps = if (a.isInfinite() || b.isInfinite()) 0.0 else NumberTheory.max(epsilon, MathFunctions.ulp(b))
		var result = BooleanAlgebra.FALSE.toDouble()

		if (epsilonComparison)
		{
			if (MathFunctions.abs(a - b) <= eps)
				result = BooleanAlgebra.TRUE.toDouble()
		}
		else if (a == b)
		{
			result = BooleanAlgebra.TRUE.toDouble()
		}

		return result
	}

	/**
	 * Inequality relation.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun neq(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN()) return Double.NaN

		val eps = if (a.isInfinite() || b.isInfinite()) 0.0 else NumberTheory.max(epsilon, MathFunctions.ulp(b))
		var result = BooleanAlgebra.FALSE.toDouble()

		if (epsilonComparison)
		{
			if (MathFunctions.abs(a - b) > eps)
				result = BooleanAlgebra.TRUE.toDouble()
		}
		else if (a != b)
		{
			result = BooleanAlgebra.TRUE.toDouble()
		}

		return result
	}

	/**
	 * Lower than relation.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun lt(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN()) return Double.NaN

		val eps = if (a.isInfinite() || b.isInfinite()) 0.0 else NumberTheory.max(epsilon, MathFunctions.ulp(b))
		var result = BooleanAlgebra.FALSE.toDouble()

		if (epsilonComparison)
		{
			if (a < b - eps)
				result = BooleanAlgebra.TRUE.toDouble()
		}
		else if (a < b)
		{
			result = BooleanAlgebra.TRUE.toDouble()
		}

		return result
	}

	/**
	 * Greater than relation.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun gt(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN()) return Double.NaN

		val eps = if (a.isInfinite() || b.isInfinite()) 0.0 else NumberTheory.max(epsilon, MathFunctions.ulp(b))
		var result = BooleanAlgebra.FALSE.toDouble()

		if (epsilonComparison)
		{
			if (a > b + eps)
				result = BooleanAlgebra.TRUE.toDouble()
		}
		else if (a > b)
		{
			result = BooleanAlgebra.TRUE.toDouble()
		}

		return result
	}

	/**
	 * Lower or equal relation.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun leq(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN()) return Double.NaN

		val eps = if (a.isInfinite() || b.isInfinite()) 0.0 else NumberTheory.max(epsilon, MathFunctions.ulp(b))
		var result = BooleanAlgebra.FALSE.toDouble()

		if (epsilonComparison)
		{
			if (a <= b + eps)
				result = BooleanAlgebra.TRUE.toDouble()
		}
		else if (a <= b)
		{
			result = BooleanAlgebra.TRUE.toDouble()
		}

		return result
	}

	/**
	 * Greater or equal relation.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun geq(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN()) return Double.NaN

		val eps = if (a.isInfinite() || b.isInfinite()) 0.0 else NumberTheory.max(epsilon, MathFunctions.ulp(b))
		var result = BooleanAlgebra.FALSE.toDouble()

		if (epsilonComparison)
		{
			if (a >= b - eps)
				result = BooleanAlgebra.TRUE.toDouble()
		}
		else if (a >= b)
		{
			result = BooleanAlgebra.TRUE.toDouble()
		}

		return result
	}

}
