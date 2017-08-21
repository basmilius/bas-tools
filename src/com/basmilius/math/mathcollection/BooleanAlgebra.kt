package com.basmilius.math.mathcollection

/**
 * Object BooleanAlgebra
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser.mathcollection
 */
object BooleanAlgebra
{

	val FALSE = 0
	val TRUE = 1
	val NULL = 2
	val F = 0.0
	val T = 1.0
	val N = Double.NaN

	private val AND_TRUTH_TABLE = arrayOf(
			doubleArrayOf(F, F, F),
			doubleArrayOf(F, T, N),
			doubleArrayOf(F, N, N))

	private val NAND_TRUTH_TABLE = arrayOf(
			doubleArrayOf(T, T, T),
			doubleArrayOf(T, F, N),
			doubleArrayOf(T, N, N))

	private val OR_TRUTH_TABLE = arrayOf(
			doubleArrayOf(F, T, N),
			doubleArrayOf(T, T, T),
			doubleArrayOf(N, T, N))

	private val NOR_TRUTH_TABLE = arrayOf(
			doubleArrayOf(T, F, N),
			doubleArrayOf(F, F, F),
			doubleArrayOf(N, F, N))

	private val XOR_TRUTH_TABLE = arrayOf(
			doubleArrayOf(F, T, N),
			doubleArrayOf(T, F, N),
			doubleArrayOf(N, N, N))

	private val XNOR_TRUTH_TABLE = arrayOf(
			doubleArrayOf(T, F, N),
			doubleArrayOf(F, T, N),
			doubleArrayOf(N, N, N))

	private val IMP_TRUTH_TABLE = arrayOf(
			doubleArrayOf(T, T, T),
			doubleArrayOf(F, T, N),
			doubleArrayOf(N, T, N))

	private val CIMP_TRUTH_TABLE = arrayOf(
			doubleArrayOf(T, F, N),
			doubleArrayOf(T, T, T),
			doubleArrayOf(T, N, N))

	private val EQV_TRUTH_TABLE = arrayOf(
			doubleArrayOf(T, F, N),
			doubleArrayOf(F, T, N),
			doubleArrayOf(N, N, N))

	private val NIMP_TRUTH_TABLE = arrayOf(
			doubleArrayOf(F, F, F),
			doubleArrayOf(T, F, N),
			doubleArrayOf(N, F, N))

	private val CNIMP_TRUTH_TABLE = arrayOf(
			doubleArrayOf(F, T, N),
			doubleArrayOf(F, F, F),
			doubleArrayOf(F, N, N))

	val NOT_TRUTH_TABLE = doubleArrayOf(T, F, N)

	/**
	 * Double to integer boolean translation.
	 *
	 * @param a Double
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun double2IntBoolean(a: Double): Int
	{
		if (a.isNaN()) return NULL

		return if (BinaryRelations.epsilonComparison) if (MathFunctions.abs(a) > BinaryRelations.epsilon) TRUE else FALSE else if (a != 0.0) TRUE else FALSE
	}

	/**
	 * Boolean AND.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun and(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return AND_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean OR.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun or(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return OR_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean XOR.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun xor(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return XOR_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean NAND.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun nand(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return NAND_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean NOR.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun nor(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return NOR_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean XNOR.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun xnor(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return XNOR_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean IMP.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun imp(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return IMP_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean EQV.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun eqv(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return EQV_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean NOT.
	 *
	 * @param a Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun not(a: Double): Double
	{
		val A = double2IntBoolean(a)

		return NOT_TRUTH_TABLE[A]
	}

	/**
	 * Boolean CIMP.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun cimp(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return CIMP_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean NIMP.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun nimp(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return NIMP_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean CNIMP.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun cnimp(a: Double, b: Double): Double
	{
		val A = double2IntBoolean(a)
		val B = double2IntBoolean(b)

		return CNIMP_TRUTH_TABLE[A][B]
	}

	/**
	 * Boolean AND variadic.
	 *
	 * @param values Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun andVariadic(vararg values: Double): Double
	{
		if (values.isEmpty()) return Double.NaN

		var cntTrue = 0
		for (value in values)
		{
			val bv = double2IntBoolean(value)
			if (bv == FALSE) return FALSE.toDouble()
			if (bv == TRUE) cntTrue++
		}

		return if (cntTrue == values.size) TRUE.toDouble() else Double.NaN
	}

	/**
	 * Boolean OR variadic.
	 *
	 * @param values Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun orVariadic(vararg values: Double): Double
	{
		if (values.isEmpty()) return Double.NaN

		var cntFalse = 0
		for (value in values)
		{
			val bv = double2IntBoolean(value)
			if (bv == TRUE) return TRUE.toDouble()
			if (bv == FALSE) cntFalse++
		}

		return if (cntFalse == values.size) FALSE.toDouble() else Double.NaN
	}

	/**
	 * Boolean XOR variadic.
	 *
	 * @param values Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun xorVariadic(vararg values: Double): Double
	{
		if (values.isEmpty()) return Double.NaN

		var cntTrue = 0
		for (value in values)
		{
			val bv = double2IntBoolean(value)
			if (bv == TRUE)
			{
				cntTrue++

				if (cntTrue > 1)
					return FALSE.toDouble()
			}
			if (bv == NULL) return Double.NaN
		}

		return if (cntTrue == values.size) TRUE.toDouble() else FALSE.toDouble()
	}

}
