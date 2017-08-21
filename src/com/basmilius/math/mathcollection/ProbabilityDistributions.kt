package com.basmilius.math.mathcollection

import java.util.*

/**
 * Object ProbabilityDistributions
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser.mathcollection
 */
object ProbabilityDistributions
{

	var randomGenerator = Random()

	/**
	 * Random number from Uniform Continuous distribution over interval (a, b).
	 *
	 * @param a Double
	 * @param b Double
	 * @param rnd Random
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun rndUniformContinuous(a: Double, b: Double, rnd: Random = randomGenerator): Double
	{
		if (a.isNaN()) return Double.NaN
		if (b.isNaN()) return Double.NaN
		if (b < a) return Double.NaN
		if (a == b) return a

		return a + rnd.nextDouble() * (b - a)
	}

	/**
	 * Random number from Uniform Continuous distribution over interval (0, 1).
	 *
	 * @param rnd Random
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun rndUniformContinuous(rnd: Random = randomGenerator): Double
	{
		return rnd.nextDouble()
	}

	/**
	 * PDF - Probability Distribution Function - Uniform Continuous distribution over interval (a, b).
	 *
	 * @param x Double
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun pdfUniformContinuous(x: Double, a: Double, b: Double): Double
	{
		if (x.isNaN()) return Double.NaN
		if (a.isNaN()) return Double.NaN
		if (b.isNaN()) return Double.NaN
		if (b < a) return Double.NaN
		if (a == b) return if (x == a) 1.0 else 0.0
		if (x < a || x > 0) return 0.0
		if (x == Double.NEGATIVE_INFINITY) return 0.0
		if (x == Double.POSITIVE_INFINITY) return 0.0

		return 1.0 / (b - a)
	}

	/**
	 * CDF - Cumulative Distribution Function - Uniform Continuous distribution over interval (a, b).
	 *
	 * @param x Double
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun cdfUniformContinuous(x: Double, a: Double, b: Double): Double
	{
		if (x.isNaN()) return Double.NaN
		if (a.isNaN()) return Double.NaN
		if (b.isNaN()) return Double.NaN
		if (b < a) return Double.NaN
		if (a == b) return if (x < a) return 0.0 else 1.0
		if (x < a) return 0.0
		if (x >= b) return 1.0
		if (x == Double.NEGATIVE_INFINITY) return 0.0
		if (x == Double.POSITIVE_INFINITY) return 1.0

		return (x - a) / (b - a)
	}

	/**
	 * QNT - Quantile Function - Uniform Continuous distribution over interval (a, b). (Inverse of Cumulative Distribution Function).
	 *
	 * @param q Double
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun qntUniformContinuous(q: Double, a: Double, b: Double): Double
	{
		if (q.isNaN()) return Double.NaN
		if (a.isNaN()) return Double.NaN
		if (b.isNaN()) return Double.NaN
		if (q < 0.0 || q > 1.0) return Double.NaN
		if (b < a) return Double.NaN
		if (a == b) return if (q == 1.0) b else Double.NaN
		if (q == 0.0) return a
		if (q == 1.0) return b

		return a + q * (b - a)
	}

	/**
	 * Random number from Uniform Discrete distribution. over set interval (a, a+1, ..., b-1, b).
	 *
	 * @param a Int
	 * @param b Int
	 * @param rnd Random
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun rndInteger(a: Int, b: Int, rnd: Random = randomGenerator): Double
	{
		if (a.toDouble().isNaN()) return Double.NaN
		if (b.toDouble().isNaN()) return Double.NaN
		if (b < a) return Double.NaN
		if (a == b) return a.toDouble()

		val n = (b - a) + 1

		return (a + rnd.nextInt(n)).toDouble()
	}

	/**
	 * Random integer.
	 *
	 * @param rnd Random
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun rndInteger(rnd: Random = randomGenerator): Int
	{
		return rnd.nextInt()
	}

	/**
	 * Random index from 0 to n-1.
	 *
	 * @param n Int
	 * @param rnd Random
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun rndIndex(n: Int, rnd: Random = randomGenerator): Int
	{
		if (n < 0) return -1

		return rnd.nextInt(n)
	}

	/**
	 * Random number from normal distribution N(mean, stddev).
	 *
	 * @param mean Double
	 * @param stddev Double
	 * @param rnd Random
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun rndNormal(mean: Double, stddev: Double, rnd: Random? = randomGenerator): Double
	{
		if (mean.isNaN()) return Double.NaN
		if (stddev.isNaN()) return Double.NaN
		if (rnd == null) return Double.NaN
		if (stddev < 0) return Double.NaN
		if (stddev == mean) return mean

		var x: Double
		var a: Double
		var b: Double
		var v1: Double
		var v2: Double
		var r: Double
		var fac: Double
		var polarTransform: Boolean

		do
		{
			a = rnd.nextDouble()
			b = rnd.nextDouble()
			v1 = 2.0 * a - 1.0
			v2 = 2.0 * b - 1.0
			r = (v1 * v1) + (v2 * v2)

			if (r >= 1.0 || r == 0.0)
			{
				x = 0.0
				polarTransform = false
			}
			else
			{
				fac = MathFunctions.sqrt(-2.0 * MathFunctions.ln(r) / r)
				x = v1 * fac
				polarTransform = true
			}
		}
		while (!polarTransform)

		return mean + (stddev * x)
	}

	/**
	 * PDF - Probability Distribution Function - Normal distribution N(mean, stddev).
	 *
	 * @param x Double
	 * @param mean Double
	 * @param stddev Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun pdfNormal(x: Double, mean: Double, stddev: Double): Double
	{
		if (x.isNaN()) return Double.NaN
		if (mean.isNaN()) return Double.NaN
		if (stddev.isNaN()) return Double.NaN
		if (stddev < 0.0) return Double.NaN
		if (stddev == 0.0) return if (x == mean) 1.0 else 0.0
		if (x == Double.NEGATIVE_INFINITY) return 0.0
		if (x == Double.POSITIVE_INFINITY) return 0.0

		val d = (x - mean) / stddev

		return MathFunctions.exp(-0.5 * d * d) / (MathConstants.SQRT2Pi * stddev)
	}

	/**
	 * CDF - Cumulative Distribution Function - Normal distribution N(mean, stddev).
	 *
	 * @param x Double
	 * @param mean Double
	 * @param stddev Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun cdfNormal(x: Double, mean: Double, stddev: Double): Double
	{
		if (x.isNaN()) return Double.NaN
		if (mean.isNaN()) return Double.NaN
		if (stddev.isNaN()) return Double.NaN
		if (stddev < 0.0) return Double.NaN
		if (stddev == 0.0) return if (x < mean) 0.0 else 1.0
		if (x == Double.NEGATIVE_INFINITY) return 0.0
		if (x == Double.POSITIVE_INFINITY) return 1.0

		return 0.5 * SpecialFunctions.erfc((mean - x) / (stddev * MathConstants.SQRT2))
	}

	/**
	 * QNT - Quantile Function - Normal distribution N(mean, stddev). (Inverse of Cumulative Distribution Function).
	 *
	 * @param q Double
	 * @param mean Double
	 * @param stddev Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun qntNormal(q: Double, mean: Double, stddev: Double): Double
	{
		if (q.isNaN()) return Double.NaN
		if (mean.isNaN()) return Double.NaN
		if (stddev.isNaN()) return Double.NaN
		if (q < 0.0 || q > 1.0) return Double.NaN
		if (stddev < 0.0) return Double.NaN
		if (stddev == 0.0) return if (q == 1.0) mean else Double.NaN
		if (q == 0.0) return Double.NEGATIVE_INFINITY
		if (q == 1.0) return Double.POSITIVE_INFINITY

		return mean - (stddev * MathConstants.SQRT2 * SpecialFunctions.erfcInv(2.0 * q))
	}

}
