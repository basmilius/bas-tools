package com.basmilius.math.mathcollection

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Object MathFunctions
 *
 * @author Bas Milius
 * @package com.basmilius.math.mathcollection
 */
object MathFunctions
{

	/**
	 * Bell Numbers.
	 *
	 * @param num Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun bellNumber(num: Int): Double
	{
		var n = num
		var result = Double.NaN

		if (n > 1)
		{
			n -= 1

			val bellTriangle = Array(n + 1) { LongArray(n + 1) }
			bellTriangle[0][0] = 1
			bellTriangle[1][0] = 1

			for (r in 1..n)
			{
				for (k in 0 until r)
				{
					bellTriangle[r][k + 1] = bellTriangle[r - 1][k] + bellTriangle[r][k]
				}

				if (r < n)
				{
					bellTriangle[r + 1][0] = bellTriangle[r][r]
				}
			}
			result = bellTriangle[n][n].toDouble()
		}
		else if (n >= 0)
		{
			result = 1.0
		}

		return result
	}

	/**
	 * Bell Numbers.
	 *
	 * @param num Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun bellNumber(num: Double): Double
	{
		return if (num.isNaN()) Double.NaN else bellNumber(Math.round(num).toInt())
	}

	/**
	 * Euler numbers.
	 *
	 * @param n Int
	 * @param k Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun eulerNumber(n: Int, k: Int): Double
	{
		if (n < 0) return Double.NaN
		if (k < 0) return 0.0
		if (n == 0) return if (k == 0) 1.0 else 0.0

		return (k + 1) * eulerNumber(n - 1, k) + (n - k) * eulerNumber(n - 1, k - 1)
	}

	/**
	 * Euler numbers.
	 *
	 * @param n Double
	 * @param k Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun eulerNumber(n: Double, k: Double): Double
	{
		return if (n.isNaN() || k.isNaN()) Double.NaN else eulerNumber(Math.round(n).toInt(), Math.round(k).toInt())
	}

	/**
	 * Factorial.
	 *
	 * @param n Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun factorial(n: Int): Double
	{
		var f = Double.NaN

		if (n >= 0)
		{
			if (n < 2)
			{
				f = 1.0
			}
			else
			{
				f = 1.0

				for (i in 1..n)
					f *= i
			}
		}

		return f
	}

	/**
	 * Factorial.
	 *
	 * @param n Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun factorial(n: Double): Double
	{
		return if (n.isNaN()) Double.NaN else factorial(Math.round(n).toInt())
	}

	/**
	 * Generalized binomial coefficient.
	 *
	 * @param n Double
	 * @param k Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun binomCoeff(n: Double, k: Int): Double
	{
		if (n.isNaN()) return Double.NaN

		var result = Double.NaN

		if (k >= 0)
		{
			var numerator = 1.0
			if (k > 0)
				for (i in 0 until k)
					numerator *= n - i

			var denominator = 1.0
			if (k > 1)
				for (i in 1..k)
					denominator *= i.toDouble()

			result = numerator / denominator
		}

		return result
	}

	/**
	 * Generalized binomial coefficient.
	 *
	 * @param n Double
	 * @param k Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun binomCoeff(n: Double, k: Double): Double
	{
		return if (n.isNaN() || k.isNaN()) Double.NaN else binomCoeff(n, Math.round(k).toInt())
	}

	/**
	 * Bernoulli Numbers.
	 *
	 * @param m Int
	 * @param n Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun bernoulliNumber(m: Int, n: Int): Double
	{
		var result = Double.NaN

		if (m >= 0 && n >= 0)
		{
			result = 0.0
			for (k in 0..m)
				for (v in 0..k)
					result += Math.pow(-1.0, v.toDouble()) * binomCoeff(k.toDouble(), v) * (Math.pow((n + v).toDouble(), m.toDouble()) / (k + 1))
		}

		return result
	}

	/**
	 * Bernoulli Numbers.
	 *
	 * @param m Double
	 * @param n Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun bernoulliNumber(m: Double, n: Double): Double
	{
		return if (m.isNaN() || n.isNaN()) Double.NaN else bernoulliNumber(Math.round(m).toInt(), Math.round(n).toInt())
	}

	/**
	 * Stirling numbers of the first kind.
	 *
	 * @param n Int
	 * @param k Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun Stirling1Number(n: Int, k: Int): Double
	{
		if (k > n) return 0.0
		if (n == 0) return if (k == 0) 1.0 else 0.0

		return if (k == 0) if (n == 0) 1.0 else 0.0 else (n - 1) * Stirling1Number(n - 1, k) + Stirling1Number(n - 1, k - 1)
	}

	/**
	 * Stirling numbers of the first kind.
	 *
	 * @param n Double
	 * @param k Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun Stirling1Number(n: Double, k: Double): Double
	{
		return if (n.isNaN() || k.isNaN()) Double.NaN else Stirling1Number(Math.round(n).toInt(), Math.round(k).toInt())
	}

	/**
	 * Stirling numbers of the second kind.
	 *
	 * @param n Int
	 * @param k Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun Stirling2Number(n: Int, k: Int): Double
	{
		if (k > n) return 0.0
		if (n == 0) return if (k == 0) 1.0 else 0.0

		return if (k == 0) if (n == 0) 1.0 else 0.0 else k * Stirling2Number(n - 1, k) + Stirling2Number(n - 1, k - 1)
	}

	/**
	 * Stirling numbers of the second kind.
	 *
	 * @param n Double
	 * @param k Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun Stirling2Number(n: Double, k: Double): Double
	{
		return if (n.isNaN() || k.isNaN()) Double.NaN else Stirling2Number(Math.round(n).toInt(), Math.round(k).toInt())
	}

	/**
	 * Worpitzky numbers.
	 *
	 * @param n Int
	 * @param k Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun worpitzkyNumber(n: Int, k: Int): Double
	{
		var result = java.lang.Double.NaN

		if (n >= 0 && k >= 0 && k <= n)
		{
			result = (0..k).sumByDouble { Math.pow(-1.0, (it + k).toDouble()) * Math.pow((it + 1).toDouble(), n.toDouble()) * binomCoeff(k.toDouble(), it) }
		}

		return result
	}

	/**
	 * Worpitzky numbers.
	 *
	 * @param n Double
	 * @param k Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun worpitzkyNumber(n: Double, k: Double): Double
	{
		return if (n.isNaN() || k.isNaN()) Double.NaN else worpitzkyNumber(Math.round(n).toInt(), Math.round(k).toInt())
	}

	/**
	 * Harmonic Numbers
	 *
	 * @param n Int
	 *
	 * @return Double
	 *
	 * @author bas Milius
	 */
	fun harmonicNumber(n: Int): Double
	{
		if (n <= 0) return 0.0
		if (n == 1) return 1.0

		return 1.0 + (2..n).sumByDouble { 1.0 / it }
	}

	/**
	 * Harmonic Numbers
	 *
	 * @param n Int
	 *
	 * @return Double
	 *
	 * @author bas Milius
	 */
	fun harmonicNumber(n: Double): Double
	{
		return if (n.isNaN()) Double.NaN else harmonicNumber(Math.round(n).toInt())
	}

	/**
	 * Harmonic number 1/1 + 1/2^x + ... + 1/n^x.
	 *
	 * @param x Double
	 * @param n Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun harmonicNumber(x: Double, n: Int): Double
	{
		if (x.isNaN() || x < 0) return Double.NaN
		if (n <= 0) return 0.0
		if (n == 1) return x

		return 1.0 + (2..n).sumByDouble { 1 / power(it.toDouble(), x) }
	}

	/**
	 * Harmonic number 1/1 + 1/2^x + ... + 1/n^x.
	 *
	 * @param x Double
	 * @param n Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun harmonicNumber(x: Double, n: Double): Double
	{
		return if (x.isNaN() || n.isNaN()) Double.NaN else harmonicNumber(Math.round(x).toDouble(), Math.round(n).toInt())
	}

	/**
	 * Catalan numbers.
	 *
	 * @param n Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun catalanNumber(n: Int): Double
	{
		return binomCoeff(2.0 * n, n) * div(1.0, n + 1.0)
	}

	/**
	 * Catalan numbers.
	 *
	 * @param n Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun catalanNumber(n: Double): Double
	{
		return if (n.isNaN()) Double.NaN else catalanNumber(Math.round(n).toInt())
	}

	/**
	 * Fibonacci numbers.
	 *
	 * @param n Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun fibonacciNumber(n: Int): Double
	{
		if (n < 0) return Double.NaN
		if (n == 0) return 0.0
		if (n == 1) return 1.0

		return fibonacciNumber(n - 1) + fibonacciNumber(n - 2)
	}

	/**
	 * Fibonacci numbers.
	 *
	 * @param n Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun fibonacciNumber(n: Double): Double
	{
		return if (n.isNaN()) Double.NaN else fibonacciNumber(Math.round(n).toInt())
	}

	/**
	 * Lucas numbers.
	 *
	 * @param n Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun lucasNumber(n: Int): Double
	{
		if (n < 0) return Double.NaN
		if (n == 0) return 2.0

		return if (n == 1) 1.0 else lucasNumber(n - 1) + lucasNumber(n - 2)
	}

	/**
	 * Lucas numbers.
	 *
	 * @param n Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun lucasNumber(n: Double): Double
	{
		return if (n.isNaN()) Double.NaN else lucasNumber(Math.round(n).toInt())
	}

	/**
	 * Kronecker delta.
	 *
	 * @param i Double
	 * @param j Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun kroneckerDelta(i: Double, j: Double): Double
	{
		if (i.isNaN() || j.isNaN())
			return Double.NaN

		return if (i == j) 1.0 else 0.0
	}

	/**
	 * Kronecker delta.
	 *
	 * @param i Int
	 * @param j Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun kroneckerDelta(i: Int, j: Int): Double
	{
		return if (i == j) 1.0 else 0.0
	}

	/**
	 * Continued fraction.
	 *
	 * @param sequence Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun continuedFraction(vararg sequence: Double): Double
	{
		if (sequence.isEmpty()) return Double.NaN

		var cf = 0.0
		var a: Double

		if (sequence.size == 1) return sequence[0]

		val lastIndex = sequence.size - 1
		for (i in lastIndex downTo 0)
		{
			a = sequence[i]
			if (a.isNaN()) return Double.NaN

			cf = if (i == lastIndex) a else if (cf == 0.0) Double.NaN else a + 1.0 / cf
		}

		return cf
	}

	/**
	 * Private function calculating continued polynomial recursively.
	 *
	 * @param n Int
	 * @param x DoubleArray
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	private fun continuedPolynomial(n: Int, x: DoubleArray): Double
	{
		if (x.isEmpty()) return Double.NaN
		if (n == 0) return 1.0

		return if (n == 1) x[0] else x[n - 1] * continuedPolynomial(n - 1, x) + continuedPolynomial(n - 2, x)
	}

	/**
	 * Continued polynomial.
	 *
	 * @param x ...Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun continuedPolynomial(vararg x: Double): Double
	{
		if (x.isEmpty()) return Double.NaN

		return if (x.any { it.isNaN() }) Double.NaN else continuedPolynomial(x.size, x)
	}

	/**
	 * Euler polynomial.
	 *
	 * @param m Int
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun eulerPolynomial(m: Int, x: Double): Double
	{
		if (x.isNaN()) return Double.NaN

		var result = Double.NaN

		if (m >= 0)
		{
			result = 0.0

			for (n in 0..m)
			{
				for (k in 0..n)
					result += Math.pow(-1.0, k.toDouble()) * binomCoeff(n.toDouble(), k) * Math.pow(x + k, m.toDouble())

				result /= Math.pow(2.0, n.toDouble())
			}
		}

		return result
	}

	/**
	 * Euler polynomial.
	 *
	 * @param m Double
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun eulerPolynomial(m: Double, x: Double): Double
	{
		return if (m.isNaN() || x.isNaN()) Double.NaN else eulerPolynomial(Math.round(m).toInt(), Math.round(x).toDouble())
	}

	/**
	 * Characteristic function x in (a,b).
	 *
	 * @param x Double
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun chi(x: Double, a: Double, b: Double): Double
	{
		if (x.isNaN() || a.isNaN() || b.isNaN()) return Double.NaN

		return if (x > a && x < b) 1.0 else 0.0
	}

	/**
	 * Characteristic function x in [a,b].
	 *
	 * @param x Double
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun chi_LR(x: Double, a: Double, b: Double): Double
	{
		if (x.isNaN() || a.isNaN() || b.isNaN()) return Double.NaN

		return if (x in a..b) 1.0 else 0.0
	}

	/**
	 * Characteristic function x in [a,b)
	 *
	 * @param x Double
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun chi_L(x: Double, a: Double, b: Double): Double
	{
		if (x.isNaN() || a.isNaN() || b.isNaN()) return Double.NaN

		return if (x >= a && x < b) 1.0 else 0.0
	}

	/**
	 * Characteristic function x in (a,b]
	 *
	 * @param x Double
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun chi_R(x: Double, a: Double, b: Double): Double
	{
		if (x.isNaN() || a.isNaN() || b.isNaN()) return Double.NaN

		return if (x > a && x <= b) 1.0 else 0.0
	}

	/**
	 * Power function a^b.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun power(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN()) return Double.NaN

		when
		{
			a >= 0 -> return Math.pow(a, b)
			abs(b) >= 1 -> return Math.pow(a, b)
			b == 0.0 -> return Math.pow(a, b)
			else ->
			{
				val ndob = 1.0 / abs(b)
				val nint = Math.round(ndob).toDouble()

				return if (abs(ndob - nint) <= BinaryRelations.epsilon)
				{
					if (nint.toLong() % 2 == 1L) if (b > 0) -Math.pow(abs(a), 1.0 / ndob) else -Math.pow(abs(a), -1.0 / ndob) else Double.NaN
				}
				else
				{
					Double.NaN
				}
			}
		}
	}

	/**
	 * Nth order root of a number.
	 *
	 * @param n Double
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun root(n: Double, x: Double): Double
	{
		if (n.isNaN() || x.isNaN()) return Double.NaN
		if (n.isInfinite() || x.isInfinite()) return Double.NaN
		if (n < -BinaryRelations.DEFAULT_COMPARISON_EPSILON) return Double.NaN

		if (abs(n) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON)
			return when
			{
				abs(x) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON -> 0.0
				abs(x - 1) <= BinaryRelations.DEFAULT_COMPARISON_EPSILON -> 1.0
				else -> Double.NaN
			}

		val nint = floor(n).toLong()
		if (nint == 1L) return x
		if (nint == 2L) return sqrt(x)

		return if (nint % 2 == 1L) if (x >= 0) Math.pow(x, 1.0 / nint) else -Math.pow(abs(x), 1.0 / nint) else if (x >= 0) Math.pow(x, 1.0 / nint) else Double.NaN
	}

	/**
	 * Modulo operator a % b.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun mod(a: Double, b: Double): Double
	{
		return if (a.isNaN() || b.isNaN()) Double.NaN else a % b
	}

	/**
	 * Division a/b.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun div(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN()) return Double.NaN

		return if (b != 0.0) a / b else Double.NaN
	}

	/**
	 * Sine trigonometric function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sin(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.sin(a)
	}

	/**
	 * Cosine trigonometric function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun cos(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.cos(a)
	}

	/**
	 * Tangent trigonometric function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun tan(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.tan(a)
	}

	/**
	 * Cotangent trigonometric function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun ctan(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		val tg = Math.tan(a)

		return if (tg != 0.0) 1.0 / tg else Double.NaN
	}

	/**
	 * Secant trigonometric function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sec(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		val cos = Math.cos(a)

		return if (cos != 0.0) 1.0 / cos else Double.NaN
	}

	/**
	 * Cosecant trigonometric function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun cosec(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		val sin = Math.sin(a)

		return if (sin != 0.0) 1.0 / sin else Double.NaN
	}

	/**
	 * Arcus sine - inverse trigonometric sine function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun asin(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.asin(a)
	}

	/**
	 * Arcus cosine - inverse trigonometric cosine function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun acos(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.acos(a)
	}

	/**
	 * Arcus tangent - inverse trigonometric tangent function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun atan(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.atan(a)
	}

	/**
	 * Arcus cotangent - inverse trigonometric cotangent function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun actan(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.atan(1.0 / a)
	}

	/**
	 * Arcus secant - inverse trigonometric secant function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun asec(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.acos(1 / a)
	}

	/**
	 * Arcus cosecant - inverse trigonometric cosecant function
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun acosec(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.asin(1 / a)
	}

	/**
	 * Natural logarithm
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun ln(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.log(a)
	}

	/**
	 * Binary logarithm.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun log2(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.log(a) / Math.log(2.0)
	}

	/**
	 * Common logarithm.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun log10(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.log10(a)
	}

	/**
	 * Degrees to radius translation.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun rad(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.toRadians(a)
	}

	/**
	 * Exponential function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun exp(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.exp(a)
	}

	/**
	 * Square root.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sqrt(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.sqrt(a)
	}

	/**
	 * Hyperbolic sine function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sinh(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.sinh(a)
	}

	/**
	 * Hyperbolic cosine function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun cosh(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.cosh(a)
	}

	/**
	 * Hyperbolic tangent function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun tanh(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.tanh(a)
	}

	/**
	 * Hyperbolic cotangent function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun coth(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		val tanh = Math.tanh(a)

		return if (tanh != 0.0) 1.0 / tanh else Double.NaN
	}

	/**
	 * Hyperbolic secant function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sech(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		val cosh = Math.cosh(a)

		return if (cosh != 0.0) 1.0 / cosh else Double.NaN
	}

	/**
	 * Hyperbolic cosecant function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun csch(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		val sinh = Math.sinh(a)

		return if (sinh != 0.0) 1.0 / sinh else Double.NaN
	}

	/**
	 * Radius to degrees translation.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun deg(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.toDegrees(a)
	}

	/**
	 * Absolute value.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun abs(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.abs(a)
	}

	/**
	 * Signum function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sgn(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.signum(a)
	}

	/**
	 * Floor function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun floor(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.floor(a)
	}

	/**
	 * Ceiling function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun ceil(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.ceil(a)
	}

	/**
	 * Arcus hyperbolic sine - inverse hyperbolic sine function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun arsinh(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.log(a + Math.sqrt(a * a + 1))
	}

	/**
	 * Arcus hyperbolic cosine - inverse hyperbolic cosine function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun arcosh(a: Double): Double
	{
		return if (a.isNaN()) Double.NaN else Math.log(a + Math.sqrt(a * a - 1))
	}

	/**
	 * Arcus hyperbolic tangent - inverse hyperbolic tangent function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun artanh(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		return if (1 - a != 0.0) 0.5 * Math.log((1 + a) / (1 - a)) else Double.NaN
	}

	/**
	 * Arcus hyperbolic tangent - inverse hyperbolic tangent function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun arcoth(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		return if (1 - a != 0.0) 0.5 * Math.log((a + 1) / (a - 1)) else Double.NaN
	}

	/**
	 * Arcus hyperbolic secant - inverse hyperbolic secant function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun arsech(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		return if (a != 0.0) Math.log((1 + Math.sqrt(1 - a * a)) / a) else Double.NaN
	}

	/**
	 * Arcus hyperbolic cosecant - inverse hyperbolic cosecant function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun arcsch(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		return if (a != 0.0) Math.log(1 / a + Math.sqrt(1 + a * a) / Math.abs(a)) else Double.NaN
	}

	/**
	 * Normalized sinc function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sa(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		val x = MathConstants.PI * a

		return if (x != 0.0) Math.sin(x) / x else Double.NaN
	}

	/**
	 * Sinc function.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sinc(a: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		return if (a != 0.0) Math.sin(a) / a else Double.NaN
	}

	/**
	 * General logarithm.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun log(a: Double, b: Double): Double
	{
		if (a.isNaN()) return Double.NaN

		val logb = Math.log(b)

		return if (logb != 0.0) Math.log(a) / logb else Double.NaN
	}

	/**
	 * Double rounding.
	 *
	 * @param value Double
	 * @param places Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun round(value: Double, places: Double): Double
	{
		if (value.isNaN()) return Double.NaN
		if (places < 0) return Double.NaN

		return BigDecimal(value.toString()).setScale(places.toInt(), RoundingMode.HALF_UP).toDouble()
	}

	/**
	 * For very small number returns number of zeros before first significant digit.
	 *
	 * @param value Double
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun decimalDigitsBefore(value: Double): Int
	{
		return when
		{
			value <= 1e-90 -> when
			{
				value <= 1e-99 -> 99
				value <= 1e-98 -> 98
				value <= 1e-97 -> 97
				value <= 1e-96 -> 96
				value <= 1e-95 -> 95
				value <= 1e-94 -> 94
				value <= 1e-93 -> 93
				value <= 1e-92 -> 92
				value <= 1e-91 -> 91
				else -> 90
			}
			value <= 1e-80 -> when
			{
				value <= 1e-89 -> 89
				value <= 1e-88 -> 88
				value <= 1e-87 -> 87
				value <= 1e-86 -> 86
				value <= 1e-85 -> 85
				value <= 1e-84 -> 84
				value <= 1e-83 -> 83
				value <= 1e-82 -> 82
				value <= 1e-81 -> 81
				else -> 80
			}
			value <= 1e-70 -> when
			{
				value <= 1e-79 -> 79
				value <= 1e-78 -> 78
				value <= 1e-77 -> 77
				value <= 1e-76 -> 76
				value <= 1e-75 -> 75
				value <= 1e-74 -> 74
				value <= 1e-73 -> 73
				value <= 1e-72 -> 72
				value <= 1e-71 -> 71
				else -> 70
			}
			value <= 1e-60 -> when
			{
				value <= 1e-69 -> 69
				value <= 1e-68 -> 68
				value <= 1e-67 -> 67
				value <= 1e-66 -> 66
				value <= 1e-65 -> 65
				value <= 1e-64 -> 64
				value <= 1e-63 -> 63
				value <= 1e-62 -> 62
				value <= 1e-61 -> 61
				else -> 60
			}
			value <= 1e-50 -> when
			{
				value <= 1e-59 -> 59
				value <= 1e-58 -> 58
				value <= 1e-57 -> 57
				value <= 1e-56 -> 56
				value <= 1e-55 -> 55
				value <= 1e-54 -> 54
				value <= 1e-53 -> 53
				value <= 1e-52 -> 52
				value <= 1e-51 -> 51
				else -> 50
			}
			value <= 1e-40 -> when
			{
				value <= 1e-49 -> 49
				value <= 1e-48 -> 48
				value <= 1e-47 -> 47
				value <= 1e-46 -> 46
				value <= 1e-45 -> 45
				value <= 1e-44 -> 44
				value <= 1e-43 -> 43
				value <= 1e-42 -> 42
				value <= 1e-41 -> 41
				else -> 40
			}
			value <= 1e-40 -> when
			{
				value <= 1e-49 -> 49
				value <= 1e-48 -> 48
				value <= 1e-47 -> 47
				value <= 1e-46 -> 46
				value <= 1e-45 -> 45
				value <= 1e-44 -> 44
				value <= 1e-43 -> 43
				value <= 1e-42 -> 42
				value <= 1e-41 -> 41
				else -> 40
			}
			value <= 1e-30 -> when
			{
				value <= 1e-39 -> 39
				value <= 1e-38 -> 38
				value <= 1e-37 -> 37
				value <= 1e-36 -> 36
				value <= 1e-35 -> 35
				value <= 1e-34 -> 34
				value <= 1e-33 -> 33
				value <= 1e-32 -> 32
				value <= 1e-31 -> 31
				else -> 30
			}
			value <= 1e-20 -> when
			{
				value <= 1e-29 -> 29
				value <= 1e-28 -> 28
				value <= 1e-27 -> 27
				value <= 1e-26 -> 26
				value <= 1e-25 -> 25
				value <= 1e-24 -> 24
				value <= 1e-23 -> 23
				value <= 1e-22 -> 22
				value <= 1e-21 -> 21
				else -> 20
			}
			value <= 1e-10 -> when
			{
				value <= 1e-19 -> 19
				value <= 1e-18 -> 18
				value <= 1e-17 -> 17
				value <= 1e-16 -> 16
				value <= 1e-15 -> 15
				value <= 1e-14 -> 14
				value <= 1e-13 -> 13
				value <= 1e-12 -> 12
				value <= 1e-11 -> 11
				else -> 10
			}
			else -> when
			{
				value <= 1e-9 -> 9
				value <= 1e-8 -> 8
				value <= 1e-7 -> 7
				value <= 1e-6 -> 6
				value <= 1e-5 -> 5
				value <= 1e-4 -> 4
				value <= 1e-3 -> 3
				value <= 1e-2 -> 2
				value <= 1e-1 -> 1
				value <= 1.0 -> 0
				else -> -1
			}
		}
	}

	/**
	 * Unit in the last place(ULP) for double.
	 *
	 * @param value Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun ulp(value: Double): Double
	{
		return Math.ulp(value)
	}

	/**
	 * Unit in The Last Place - number of decimal digits before.
	 *
	 * @param value Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun ulpDecimalDigitsBefore(value: Double): Double
	{
		return if (value.isNaN()) -2.0 else decimalDigitsBefore(ulp(value)).toDouble()
	}

	/**
	 * Returns the first non-NaN value.
	 *
	 * @param values Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun coalesce(vararg values: Double): Double
	{
		if (values.isEmpty()) return Double.NaN

		return values.firstOrNull { !it.isNaN() } ?: Double.NaN
	}

}
