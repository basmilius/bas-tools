/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.math.mathcollection

/**
 * Object BinaryRelations
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.mathcollection
 */
object BinaryRelations
{

	val DEFAULT_COMPARISON_EPSILON = 0.00000000000001
	var epsilon = DEFAULT_COMPARISON_EPSILON
	var epsilonComparison = true

	/**
	 * Comparison mode indicator.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun setExactComparison()
	{
		epsilonComparison = false
	}

	/**
	 * Sets comparison mode to EXACT.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun setEpsilonComparison()
	{
		epsilonComparison = true
	}

	/**
	 * Sets default epsilon value.
	 *
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
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
	 * @author Bas Milius <bas@mili.us>
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
