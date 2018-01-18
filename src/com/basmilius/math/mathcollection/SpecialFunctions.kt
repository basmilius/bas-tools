package com.basmilius.math.mathcollection

/**
 * Object SpecialFunctions
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.mathcollection
 */
object SpecialFunctions
{

	val EI_DBL_EPSILON = Math.ulp(1.0)
	val EI_EPSILON = 10.0 * EI_DBL_EPSILON

	/**
	 * Exponential integral function Ei(x)
	 *
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun exponentialIntegralEi(x: Double): Double
	{
		if (x.isNaN())
			return Double.NaN

		if (x < -5.0)
			return continuedFractionEi(x)

		if (x == 0.0)
			return -Double.MAX_VALUE

		if (x < 6.8)
			return powerSeriesEi(x)

		if (x < 50.0)
			return argumentAdditionSeriesEi(x)

		return continuedFractionEi(x)
	}

	/**
	 * Supporting function while Exponential integral function Ei(x) calculation.
	 *
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun continuedFractionEi(x: Double): Double
	{
		var Am1 = 1.0
		var A0 = 0.0
		var Bm1 = 0.0
		var B0 = 1.0
		var a = Math.exp(x)
		var b = -x + 1.0
		var Ap1 = b * A0 + a * Am1
		var Bp1 = b * B0 + a * Bm1
		var j = 1.0

		while (Math.abs(Ap1 * B0 - A0 * Bp1) > EI_EPSILON * Math.abs(A0 * Bp1))
		{
			if (Math.abs(Bp1) > 1.0)
			{
				Am1 = A0 / Bp1
				A0 = Ap1 / Bp1
				Bm1 = B0 / Bp1
				B0 = 1.0
			}
			else
			{
				Am1 = A0
				A0 = Ap1
				Bm1 = B0
				B0 = Bp1
			}

			a = -j * j
			b += 2.0
			Ap1 = b * A0 + a * Am1
			Bp1 = b * B0 + a * Bm1
			j += 1.0
		}

		return -Ap1 / Bp1
	}

	/**
	 * Supporting function while Exponential integral function Ei(x) calculation.
	 *
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun powerSeriesEi(x: Double): Double
	{
		var xn = -x
		var Sn = -x
		var Sm1 = 0.0
		var hsum = 1.0
		val g = MathConstants.EULER_MASCHERONI
		var y = 1.0
		var factorial = 1.0

		if (x == 0.0)
			return -Double.MAX_VALUE

		while (Math.abs(Sn - Sm1) > EI_EPSILON * Math.abs(Sm1))
		{
			Sm1 = Sn
			y += 1.0
			xn *= -x
			factorial *= y
			hsum += (1.0 / y)
			Sn += hsum * xn / factorial
		}

		return (g + Math.log(Math.abs(x)) - Math.exp(x) * Sn)
	}

	/**
	 * Supporting function while Exponential integral function Ei(x) calculation.
	 *
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun argumentAdditionSeriesEi(x: Double): Double
	{
		val k = (x + 0.5).toInt()
		var j = 0
		val xx = k.toDouble()
		val dx = x - xx
		var xxj = xx
		val edx = Math.exp(dx)
		var Sm = 1.0
		var Sn = (edx - 1.0) / xxj
		var term = Double.MAX_VALUE
		var factorial = 1.0
		var dxj = 1.0

		while (Math.abs(term) > EI_EPSILON * Math.abs(Sn))
		{
			j++
			factorial *= j
			xxj *= xx
			dxj *= -dx
			Sm += (dxj / factorial)
			term = (factorial * (edx * Sm - 1.0)) / xxj
			Sn += term
		}

		return Coefficients.EI[k - 7] + Sn * Math.exp(xx)
	}

	/**
	 * Logarithmic integral function li(x)
	 *
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun logarithmicIntegralLi(x: Double): Double
	{
		if (x.isNaN())
			return Double.NaN

		if (x < 0)
			return Double.NaN

		if (x == 0.0)
			return 0.0

		if (x == 2.0)
			return MathConstants.LI2

		return exponentialIntegralEi(MathFunctions.ln(x))
	}

	/**
	 * Offset logarithmic integral function Li(x)
	 *
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun offsetLogarithmicIntegralLi(x: Double): Double
	{
		if (x.isNaN())
			return Double.NaN

		if (x < 0.0)
			return Double.NaN

		if (x == 0.0)
			return -MathConstants.LI2

		return logarithmicIntegralLi(x) - MathConstants.LI2
	}

	/**
	 * Calculates the error function
	 *
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun erf(x: Double): Double
	{
		if (x.isNaN()) return Double.NaN
		if (x == 0.0) return 0.0
		if (x == Double.POSITIVE_INFINITY) return 1.0
		if (x == Double.NEGATIVE_INFINITY) return -1.0

		return erfImp(x, false)
	}

	/**
	 * Calculates the complementary error function.
	 *
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun erfc(x: Double): Double
	{
		if (x.isNaN()) return Double.NaN
		if (x == 0.0) return 1.0
		if (x == Double.POSITIVE_INFINITY) return 0.0
		if (x == Double.NEGATIVE_INFINITY) return 2.0

		return erfImp(x, true)
	}

	/**
	 * Calculates the inverse error function evaluated at x.
	 *
	 * @param x Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun erfInv(x: Double): Double
	{
		if (x == 0.0) return 0.0
		if (x >= 1.0) return Double.POSITIVE_INFINITY
		if (x <= -1.0) return Double.NEGATIVE_INFINITY

		val p: Double
		val q: Double
		val s: Double

		if (x < 0)
		{
			p = -x
			q = 1.0 - p
			s = -1.0
		}
		else
		{
			p = x
			q = 1.0 - x
			s = 1.0
		}

		return erfInvImpl(p, q, s)
	}

	/**
	 * Calculates the inverse error function evaluated at x.
	 *
	 * @param z Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun erfImp(z: Double, invert: Boolean): Double
	{
		var doInvert = invert

		if (z < 0.0)
		{
			if (!doInvert) return -erfImp(-z, false)
			if (z < 0.5) return 2 - erfImp(-z, true)
			return 1.0 + erfImp(-z, false)
		}

		var result: Double

		if (z < 0.5)
		{
			result = if (z < 1e-10)
				(z * 1.125) + (z * 0.003379167095512573896158903121545171688)
			else
				(z * 1.125) + (z * Evaluate.polynomial(z, Coefficients.erfImpAn) / Evaluate.polynomial(z, Coefficients.erfImpAd))
		}
		else if (z < 110 || (z < 110 && doInvert))
		{
			doInvert = !doInvert
			val r: Double
			val b: Double

			when
			{
				z < 0.75 ->
				{
					r = Evaluate.polynomial(z - 0.5, Coefficients.erfImpBn) / Evaluate.polynomial(z - 0.5, Coefficients.erfImpBd)
					b = 0.3440242112
				}
				z < 1.25 ->
				{
					r = Evaluate.polynomial(z - 0.75, Coefficients.erfImpCn) / Evaluate.polynomial(z - 0.75, Coefficients.erfImpCd)
					b = 0.419990927
				}
				z < 2.25 ->
				{
					r = Evaluate.polynomial(z - 1.25, Coefficients.erfImpDn) / Evaluate.polynomial(z - 1.25, Coefficients.erfImpDd)
					b = 0.4898625016
				}
				z < 3.5 ->
				{
					r = Evaluate.polynomial(z - 2.25, Coefficients.erfImpEn) / Evaluate.polynomial(z - 2.25, Coefficients.erfImpEd)
					b = 0.5317370892
				}
				z < 5.25 ->
				{
					r = Evaluate.polynomial(z - 3.5, Coefficients.erfImpFn) / Evaluate.polynomial(z - 3.5, Coefficients.erfImpFd)
					b = 0.5489973426
				}
				z < 8 ->
				{
					r = Evaluate.polynomial(z - 5.25, Coefficients.erfImpGn) / Evaluate.polynomial(z - 5.25, Coefficients.erfImpGd)
					b = 0.5571740866
				}
				z < 11.5 ->
				{
					r = Evaluate.polynomial(z - 8, Coefficients.erfImpHn) / Evaluate.polynomial(z - 8, Coefficients.erfImpHd)
					b = 0.5609807968
				}
				z < 17 ->
				{
					r = Evaluate.polynomial(z - 11.5, Coefficients.erfImpIn) / Evaluate.polynomial(z - 11.5, Coefficients.erfImpId)
					b = 0.5626493692
				}
				z < 24 ->
				{
					r = Evaluate.polynomial(z - 17, Coefficients.erfImpJn) / Evaluate.polynomial(z - 17, Coefficients.erfImpJd)
					b = 0.5634598136
				}
				z < 38 ->
				{
					r = Evaluate.polynomial(z - 24, Coefficients.erfImpKn) / Evaluate.polynomial(z - 24, Coefficients.erfImpKd)
					b = 0.5638477802
				}
				z < 60 ->
				{
					r = Evaluate.polynomial(z - 38, Coefficients.erfImpLn) / Evaluate.polynomial(z - 38, Coefficients.erfImpLd)
					b = 0.5640528202
				}
				z < 85 ->
				{
					r = Evaluate.polynomial(z - 60, Coefficients.erfImpMn) / Evaluate.polynomial(z - 60, Coefficients.erfImpMd)
					b = 0.5641309023
				}
				else ->
				{
					r = Evaluate.polynomial(z - 85, Coefficients.erfImpNn) / Evaluate.polynomial(z - 85, Coefficients.erfImpNd)
					b = 0.5641584396
				}
			}

			val g = MathFunctions.exp(-z * z) / z
			result = (g * b) + (g * r)
		}
		else
		{
			result = 0.0
			doInvert = !doInvert
		}

		if (doInvert)
			result = 1.0 - result

		return result
	}

	/**
	 * Calculates the complementary inverse error function evaluated at x.
	 *
	 * @param z Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun erfcInv(z: Double): Double
	{
		if (z <= 0.0) return Double.POSITIVE_INFINITY
		if (z >= 2.0) return Double.NEGATIVE_INFINITY

		val p: Double
		val q: Double
		val s: Double

		if (z > 1.0)
		{
			q = 2.0 - z
			p = 1.0 - q
			s = -1.0
		}
		else
		{
			p = 1.0 - z
			q = z
			s = 1.0
		}

		return erfInvImpl(p, q, s)
	}

	/**
	 * The implementation of the inverse error function.
	 *
	 * @param p Double
	 * @param q Double
	 * @param s Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun erfInvImpl(p: Double, q: Double, s: Double): Double
	{
		val result: Double
		if (p <= 0.5)
		{
			val y = 0.0891314744949340820313f
			val g = p * (p + 10)
			val r = Evaluate.polynomial(p, Coefficients.ervInvImpAn) / Evaluate.polynomial(p, Coefficients.ervInvImpAd)
			result = g * y + g * r
		}
		else if (q >= 0.25)
		{
			val y = 2.249481201171875f
			val g = MathFunctions.sqrt(-2 * MathFunctions.ln(q))
			val xs = q - 0.25
			val r = Evaluate.polynomial(xs, Coefficients.ervInvImpBn) / Evaluate.polynomial(xs, Coefficients.ervInvImpBd)
			result = g / (y + r)
		}
		else
		{
			val x = MathFunctions.sqrt(-MathFunctions.ln(q))
			if (x < 3)
			{
				val y = 0.807220458984375f
				val xs = x - 1.125
				val r = Evaluate.polynomial(xs, Coefficients.ervInvImpCn) / Evaluate.polynomial(xs, Coefficients.ervInvImpCd)
				result = y * x + r * x
			}
			else if (x < 6)
			{
				val y = 0.93995571136474609375f
				val xs = x - 3
				val r = Evaluate.polynomial(xs, Coefficients.ervInvImpDn) / Evaluate.polynomial(xs, Coefficients.ervInvImpDd)
				result = y * x + r * x
			}
			else if (x < 18)
			{
				val y = 0.98362827301025390625f
				val xs = x - 6
				val r = Evaluate.polynomial(xs, Coefficients.ervInvImpEn) / Evaluate.polynomial(xs, Coefficients.ervInvImpEd)
				result = y * x + r * x
			}
			else if (x < 44)
			{
				val y = 0.99714565277099609375f
				val xs = x - 18
				val r = Evaluate.polynomial(xs, Coefficients.ervInvImpFn) / Evaluate.polynomial(xs, Coefficients.ervInvImpFd)
				result = y * x + r * x
			}
			else
			{
				val y = 0.99941349029541015625f
				val xs = x - 44
				val r = Evaluate.polynomial(xs, Coefficients.ervInvImpGn) / Evaluate.polynomial(xs, Coefficients.ervInvImpGd)
				result = y * x + r * x
			}
		}
		return s * result
	}

}
