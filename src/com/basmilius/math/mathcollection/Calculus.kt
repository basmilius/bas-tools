package com.basmilius.math.mathcollection

import com.basmilius.math.Argument
import com.basmilius.math.Expression
import com.basmilius.math.MathParser

/**
 * Object Calculus
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser.mathcollection
 */
object Calculus
{

	val LEFT_DERIVATIVE = 1
	val RIGHT_DERIVATIVE = 2
	val GENERAL_DERIVATIVE = 3

	/**
	 * Trapezoid numerical integration.
	 *
	 * @param f Expression
	 * @param x Argument
	 * @param a Double
	 * @param b Double
	 * @param eps Double
	 * @param maxSteps Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun integralTrapezoid(f: Expression, x: Argument, a: Double, b: Double, eps: Double, maxSteps: Int): Double
	{
		var h = 0.5 * (b - a)
		var s = MathParser.getFunctionValue(f, x, a) + MathParser.getFunctionValue(f, x, b) + 2 * MathParser.getFunctionValue(f, x, a + h)
		var intF = s * h * 0.5
		var intFprev: Double
		var t: Double
		var i = 1
		var j: Int
		var n = 1

		while (i <= maxSteps)
		{
			n += n
			t = a + 0.5 * h
			intFprev = intF
			j = 1

			while (j <= n)
			{
				s += 2 * MathParser.getFunctionValue(f, x, t)
				t += h
				j++
			}

			h *= 0.5
			intF = s * h * 0.5

			if (Math.abs(intF - intFprev) <= eps)
				return intF

			i++
		}

		return intF
	}

	/**
	 * Numerical derivative at x = x0.
	 *
	 * @param f expression
	 * @param x Argument
	 * @param x0 Double
	 * @param derType Int
	 * @param eps Double
	 * @param maxSteps Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun derivative(f: Expression, x: Argument, x0: Double, derType: Int, eps: Double, maxSteps: Int): Double
	{
		val START_DX = 0.1
		var step = 0
		var error: Double
		var y0 = 0.0
		var derF: Double
		var derFprev: Double
		var dx = if (derType == LEFT_DERIVATIVE) -START_DX else START_DX
		var dy: Double

		if (derType == LEFT_DERIVATIVE || derType == RIGHT_DERIVATIVE)
		{
			y0 = MathParser.getFunctionValue(f, x, x0)
			dy = MathParser.getFunctionValue(f, x, x0 + dx) - y0
			derF = dy / dx
		}
		else
		{
			derF = (MathParser.getFunctionValue(f, x, x0 + dx) - MathParser.getFunctionValue(f, x, x0 - dx)) / (2.0 * dx)
		}

		do
		{
			derFprev = derF
			dx /= 2.0

			if (derType == LEFT_DERIVATIVE || derType == RIGHT_DERIVATIVE)
			{
				dy = MathParser.getFunctionValue(f, x, x0 + dx) - y0
				derF = dy / dx
			}
			else
			{
				derF = (MathParser.getFunctionValue(f, x, x0 + dx) - MathParser.getFunctionValue(f, x, x0 - dx)) / (2.0 * dx)
			}

			error = Math.abs(derF - derFprev)
			step++
		}
		while (step < maxSteps && (error > eps || java.lang.Double.isNaN(derF)))

		return derF
	}

	/**
	 * Numerical n-th derivative at x = x0 (you should avoid calculation of derivatives with order higher than 2).
	 *
	 * @param f Expression
	 * @param n Double
	 * @param x Argument
	 * @param x0 Double
	 * @param derType Int
	 * @param eps Double
	 * @param maxSteps Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun derivativeNth(f: Expression, n: Double, x: Argument, x0: Double, derType: Int, eps: Double, maxSteps: Int): Double
	{
		val num = Math.round(n).toDouble()
		var step = 0
		var error: Double
		var derFprev: Double
		var dx = 0.01
		var derF = 0.0

		if (derType == RIGHT_DERIVATIVE)
		{
			var i = 1

			while (i <= num)
			{
				derF += MathFunctions.binomCoeff(-1.0, num - i) * MathFunctions.binomCoeff(num, i) * MathParser.getFunctionValue(f, x, x0 + i * dx)
				i++
			}
		}
		else
		{
			var i = 1

			while (i <= num)
			{
				derF += MathFunctions.binomCoeff(-1.0, i) * MathFunctions.binomCoeff(num, i) * MathParser.getFunctionValue(f, x, x0 - i * dx)
				i++
			}
		}

		derF /= Math.pow(dx, num)

		do
		{
			derFprev = derF
			dx /= 2.0
			derF = 0.0

			if (derType == RIGHT_DERIVATIVE)
			{
				var i = 1

				while (i <= num)
				{
					derF += MathFunctions.binomCoeff(-1.0, num - i) * MathFunctions.binomCoeff(num, i) * MathParser.getFunctionValue(f, x, x0 + i * dx)
					i++
				}
			}
			else
			{
				var i = 1

				while (i <= num)
				{
					derF += MathFunctions.binomCoeff(-1.0, i) * MathFunctions.binomCoeff(num, i) * MathParser.getFunctionValue(f, x, x0 - i * dx)
					i++
				}
			}

			derF /= Math.pow(dx, num)
			error = Math.abs(derF - derFprev)
			step++
		}
		while (step < maxSteps && (error > eps || java.lang.Double.isNaN(derF)))

		return derF
	}

	/**
	 * Forward difference(1) operator (at x = x0).
	 *
	 * @param f Expression
	 * @param x Argument
	 * @param x0 Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun forwardDifference(f: Expression, x: Argument, x0: Double): Double
	{
		if (x0.isNaN()) return Double.NaN

		val xb = x.argumentValue
		val delta = MathParser.getFunctionValue(f, x, x0 + 1) - MathParser.getFunctionValue(f, x, x0)
		x.argumentValue = xb

		return delta
	}

	/**
	 * Forward difference(1) operator (at current value of argument x).
	 *
	 * @param f Expression
	 * @param x Argument
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun forwardDifference(f: Expression, x: Argument): Double
	{
		val xb = x.argumentValue

		if (xb.isNaN()) return Double.NaN

		val fv = f.calculate()
		x.argumentValue = xb + 1
		val delta = f.calculate() - fv
		x.argumentValue = xb

		return delta
	}

	/**
	 * Backward difference(1) operator (at x = x0).
	 *
	 * @param f Expression
	 * @param x Argument
	 * @param x0 Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun backwardDifference(f: Expression, x: Argument, x0: Double): Double
	{
		if (x0.isNaN()) return Double.NaN

		val xb = x.argumentValue
		val delta = MathParser.getFunctionValue(f, x, x0) - MathParser.getFunctionValue(f, x, x0 - 1)
		x.argumentValue = xb

		return delta
	}

	/**
	 * Backward difference(1) operator (at current value of argument x).
	 *
	 * @param f Expression
	 * @param x Argument
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun backwardDifference(f: Expression, x: Argument): Double
	{
		val xb = x.argumentValue

		if (xb.isNaN()) return Double.NaN

		val fv = f.calculate()
		x.argumentValue = xb - 1
		val delta = fv - f.calculate()
		x.argumentValue = xb

		return delta
	}

	/**
	 * Forward difference(h) operator (at x = x0).
	 *
	 * @param f Expression
	 * @param h Double
	 * @param x Argument
	 * @param x0 Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun forwardDifference(f: Expression, h: Double, x: Argument, x0: Double): Double
	{
		if (x0.isNaN()) return Double.NaN

		val xb = x.argumentValue
		val delta = MathParser.getFunctionValue(f, x, x0 + h) - MathParser.getFunctionValue(f, x, x0)
		x.argumentValue = xb

		return delta
	}

	/**
	 * Forward difference(h) operator (at the current value of the argument x).
	 *
	 * @param f Expression
	 * @param h Double
	 * @param x Argument
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun forwardDifference(f: Expression, h: Double, x: Argument): Double
	{
		val xb = x.argumentValue

		if (xb.isNaN()) return Double.NaN

		val fv = f.calculate()
		x.argumentValue = xb + h
		val delta = f.calculate() - fv
		x.argumentValue = xb

		return delta
	}

	/**
	 * Backward difference(h) operator (at x = x0).
	 *
	 * @param f Expression
	 * @param h Double
	 * @param x Argument
	 * @param x0 Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun backwardDifference(f: Expression, h: Double, x: Argument, x0: Double): Double
	{
		if (x0.isNaN()) return Double.NaN

		val xb = x.argumentValue
		val delta = MathParser.getFunctionValue(f, x, x0) - MathParser.getFunctionValue(f, x, x0 - h)
		x.argumentValue = xb

		return delta
	}

	/**
	 * Backward difference(h) operator (at the current value of the argument x).
	 *
	 * @param f Expression
	 * @param h Double
	 * @param x Argument
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun backwardDifference(f: Expression, h: Double, x: Argument): Double
	{
		val xb = x.argumentValue

		if (xb.isNaN()) return Double.NaN

		val fv = f.calculate()
		x.argumentValue = xb - h
		val delta = fv - f.calculate()
		x.argumentValue = xb

		return delta
	}

	/**
	 * Brent solver (Brent root finder).
	 *
	 * @param f Expression
	 * @param x Argument
	 * @param a Double
	 * @param b Double
	 * @param eps Double
	 * @param maxSteps Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun solveBrent(f: Expression, x: Argument, a: Double, b: Double, eps: Double, maxSteps: Double): Double
	{
		var aa = a
		var bb = b

		var fa: Double
		var fb: Double
		var fc: Double
		var fs: Double
		var c: Double
		var c0: Double
		var c1: Double
		var c2: Double
		var tmp: Double
		var d: Double
		var s: Double
		var mflag: Boolean
		var iter = 0

		if (bb < aa)
		{
			tmp = aa
			aa = bb
			bb = tmp
		}

		fa = MathParser.getFunctionValue(f, x, aa)
		fb = MathParser.getFunctionValue(f, x, bb)

		if (MathFunctions.abs(fa) <= eps) return aa
		if (MathFunctions.abs(fb) <= eps) return bb

		if (bb == aa)
			return Double.NaN

		if (fa * fb > 0)
		{
			var rndflag = false
			var ap: Double
			var bp: Double
			var i = 0

			while (i < maxSteps)
			{
				ap = ProbabilityDistributions.rndUniformContinuous(aa, bb)
				bp = ProbabilityDistributions.rndUniformContinuous(aa, bb)

				if (bp < ap)
				{
					tmp = ap
					ap = bp
					bp = tmp
				}

				fa = MathParser.getFunctionValue(f, x, ap)
				fb = MathParser.getFunctionValue(f, x, bp)

				if (MathFunctions.abs(fa) <= eps) return ap
				if (MathFunctions.abs(fb) <= eps) return bp

				if (fa * fb < 0)
				{
					rndflag = true
					aa = ap
					bb = bp
					break
				}

				i++
			}

			if (!rndflag)
				return Double.NaN
		}
		c = aa
		d = c
		fc = MathParser.getFunctionValue(f, x, c)

		if (MathFunctions.abs(fa) < MathFunctions.abs(fb))
		{
			tmp = aa
			aa = bb
			bb = tmp
			tmp = fa
			fa = fb
			fb = tmp
		}

		mflag = true

		while (MathFunctions.abs(fb) > eps && MathFunctions.abs(bb - aa) > eps && iter < maxSteps)
		{
			if (fa != fc && fb != fc)
			{
				c0 = aa * fb * fc / ((fa - fb) * (fa - fc))
				c1 = bb * fa * fc / ((fb - fa) * (fb - fc))
				c2 = c * fa * fb / ((fc - fa) * (fc - fb))
				s = c0 + c1 + c2
			}
			else
			{
				s = bb - fb * (bb - aa) / (fb - fa)
			}

			if (s < 3 * (aa + bb) / 4 || s > bb || mflag && MathFunctions.abs(s - bb) >= MathFunctions.abs(bb - c) / 2 || !mflag && MathFunctions.abs(s - bb) >= MathFunctions.abs(c - d) / 2)
			{
				s = (aa + bb) / 2
				mflag = true
			}
			else
			{
				mflag = true
			}

			fs = MathParser.getFunctionValue(f, x, s)
			d = c
			c = bb
			fc = fb

			if (fa * fs < 0)
			{
				bb = s
			}
			else
			{
				aa = s
			}

			if (MathFunctions.abs(fa) < MathFunctions.abs(fb))
			{
				tmp = aa
				aa = bb
				bb = tmp
				tmp = fa
				fa = fb
				fb = tmp
			}

			iter++
		}
		return MathFunctions.round(bb, (MathFunctions.decimalDigitsBefore(eps) - 1).toDouble())
	}

}
