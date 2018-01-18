package com.basmilius.math.mathcollection

import com.basmilius.math.Argument
import com.basmilius.math.Expression
import com.basmilius.math.MathParser

/**
 * Object Statistics
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.mathcollection
 */
object Statistics
{

	/**
	 * Avarage from sample function values - iterative operator.
	 *
	 * @param f Expression
	 * @param index Argument
	 * @param from Double
	 * @param to Double
	 * @param delta Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun avg(f: Expression, index: Argument, from: Double, to: Double, delta: Double): Double
	{
		if (from == Double.NaN || to == Double.NaN || delta == Double.NaN || delta == 0.0)
			return Double.NaN

		var sum = 0.0
		var n = 0

		if (to >= from && delta > 0)
		{
			var i = from

			while (i < to)
			{
				sum += MathParser.getFunctionValue(f, index, i)
				n++
				i += delta
			}

			if (delta - (i - to) > 0.5 * delta)
			{
				sum += MathParser.getFunctionValue(f, index, to)
				n++
			}
		}
		else if (to <= from && delta < 0)
		{
			var i = from

			while (i > to)
			{
				sum += MathParser.getFunctionValue(f, index, i)
				n++
				i += delta
			}

			if (delta - (to - i) > 0.5 * delta)
			{
				sum += MathParser.getFunctionValue(f, index, to)
				n++
			}
		}
		else if (from == to)
		{
			return MathParser.getFunctionValue(f, index, from)
		}

		return sum / n
	}

	/**
	 * Bias-corrected variance from sample function values - iterative operator.
	 *
	 * @param f Expression
	 * @param index Argument
	 * @param from Double
	 * @param to Double
	 * @param delta Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun `var`(f: Expression, index: Argument, from: Double, to: Double, delta: Double): Double
	{
		if (from == Double.NaN || to == Double.NaN || delta == Double.NaN || delta == 0.0)
			return Double.NaN

		return `var`(*MathParser.getFunctionValues(f, index, from, to, delta))
	}

	/**
	 * Bias-corrected standard deviation from sample function values - iterative operator.
	 *
	 * @param f Expression
	 * @param index Argument
	 * @param from Double
	 * @param to Double
	 * @param delta Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun std(f: Expression, index: Argument, from: Double, to: Double, delta: Double): Double
	{
		if (from == Double.NaN || to == Double.NaN || delta == Double.NaN || delta == 0.0)
			return Double.NaN

		return std(*MathParser.getFunctionValues(f, index, from, to, delta))
	}

	/**
	 * Sample Average
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun avg(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return Double.NaN
		if (numbers.size == 1) return numbers[0]

		var sum = 0.0

		for (xi in numbers)
		{
			if (xi == Double.NaN)
				return Double.NaN

			sum += xi
		}

		return sum / numbers.size
	}

	/**
	 * Sample variance (biased-corrected).
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun `var`(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return Double.NaN
		if (numbers.size == 1)
		{
			if (numbers[0] == Double.NaN)
				return Double.NaN

			return 0.0
		}

		val m = avg(*numbers)
		var sum = 0.0

		for (xi in numbers)
		{
			if (xi == Double.NaN)
				return Double.NaN

			sum += (xi - m) * (xi - m)
		}

		return sum / (numbers.size - 1)
	}

	/**
	 * Sample standard deviation (biased-corrected).
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun std(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return Double.NaN
		if (numbers.size == 1)
		{
			if (numbers[0] == Double.NaN)
				return Double.NaN

			return 0.0
		}

		return MathFunctions.sqrt(`var`(*numbers))
	}

	/**
	 * Sample median
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun median(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return Double.NaN
		if (numbers.size == 1) return numbers[0]
		if (numbers.size == 2) return (numbers[0] + numbers[1]) / 2.0

		numbers
				.filter { it == Double.NaN }
				.forEach { return Double.NaN }

		NumberTheory.sortAsc(numbers)

		if ((numbers.size % 2) == 1)
		{
			return numbers[(numbers.size - 1) / 2]
		}

		val i = (numbers.size / 2) - 1

		return (numbers[i] + numbers[i] + 1) / 2.0
	}

	/**
	 * Sample mode.
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun mode(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return Double.NaN
		if (numbers.size == 1) return numbers[0]

		numbers
				.filter { it == Double.NaN }
				.forEach { return Double.NaN }

		val dist = NumberTheory.getDistValues(numbers, true) ?: return Double.NaN

		return dist[0][0]
	}

}
