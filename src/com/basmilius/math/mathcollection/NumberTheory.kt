package com.basmilius.math.mathcollection

import com.basmilius.math.Argument
import com.basmilius.math.Expression
import com.basmilius.math.MathParser
import com.basmilius.math.parsertokens.ParserSymbol
import java.util.*
import kotlin.collections.ArrayList

/**
 * Object NumberTheory
 *
 * @author Bas Milius
 * @package com.basmilius.math.mathcollection
 */
object NumberTheory
{

	/**
	 * Minimum function.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun min(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN())
			return Double.NaN

		return Math.min(a, b)
	}

	/**
	 * Minimum function.
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun min(vararg numbers: Double): Double
	{
		if (numbers.isEmpty())
			return Double.NaN

		var min = Double.POSITIVE_INFINITY

		for (number in numbers)
		{
			if (number.isNaN())
				return Double.NaN

			if (number < min)
				min = number
		}

		return min
	}

	/**
	 * Arg-Min function.
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun argmin(vararg numbers: Double): Double
	{
		if (numbers.isEmpty())
			return Double.NaN

		var min = Double.POSITIVE_INFINITY
		var minIndex = -1

		for (i in numbers.indices)
		{
			val number = numbers[i]

			if (number.isNaN())
				return Double.NaN

			if (BinaryRelations.lt(number, min) == BooleanAlgebra.TRUE.toDouble())
			{
				min = number
				minIndex = i
			}
		}

		return minIndex + 1.0
	}

	/**
	 * Maximum function.
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun max(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN())
			return Double.NaN

		return Math.max(a, b)
	}

	/**
	 * Maximum function.
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun max(vararg numbers: Double): Double
	{
		if (numbers.isEmpty())
			return Double.NaN

		var max = Double.NEGATIVE_INFINITY

		for (number in numbers)
		{
			if (number.isNaN())
				return Double.NaN

			if (number > max)
				max = number
		}

		return max
	}

	/**
	 * Arg-Max function.
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun argmax(vararg numbers: Double): Double
	{
		if (numbers.isEmpty())
			return Double.NaN

		var max = Double.NEGATIVE_INFINITY
		var maxIndex = -1

		for (i in numbers.indices)
		{
			val number = numbers[i]

			if (number.isNaN())
				return Double.NaN

			if (BinaryRelations.gt(number, max) == BooleanAlgebra.TRUE.toDouble())
			{
				max = number
				maxIndex = i
			}
		}

		return maxIndex + 1.0
	}

	/**
	 * Sorting array - ascending - quick sort algorithm.
	 *
	 * @param array DoubleArray
	 * @param initOrder IntArray
	 * @param leftIndex Int
	 * @param rightIndex Int
	 *
	 * @author Bas Milius
	 */
	private fun sortAsc(array: DoubleArray, initOrder: IntArray, leftIndex: Int, rightIndex: Int)
	{
		var i = leftIndex
		var j = rightIndex
		val x = array[(leftIndex + rightIndex) / 2]
		var w: Double
		var v: Int

		do
		{
			while (BinaryRelations.lt(array[i], x) == BooleanAlgebra.TRUE.toDouble()) i++
			while (BinaryRelations.gt(array[j], x) == BooleanAlgebra.TRUE.toDouble()) j--

			if (i <= j)
			{
				w = array[i]
				array[i] = array[j]
				array[j] = w
				v = initOrder[i]
				initOrder[i] = initOrder[j]
				initOrder[j] = v
				i++
				j--
			}
		}
		while (i <= j)

		if (leftIndex < j)
			sortAsc(array, initOrder, leftIndex, j)

		if (i < rightIndex)
			sortAsc(array, initOrder, i, rightIndex)
	}

	/**
	 * Sorting array - ascending - quick sort algorithm.
	 *
	 * @param array DoubleArray?
	 *
	 * @return IntArray?
	 *
	 * @author Bas Milius
	 */
	fun sortAsc(array: DoubleArray?): IntArray?
	{
		if (array == null) return null

		val initOrder = IntArray(array.size)

		for (i in array.indices)
			initOrder[i] = i

		if (array.size < 2)
			return initOrder

		sortAsc(array, initOrder, 0, array.size - 1)

		return initOrder
	}

	/**
	 * Returns a list of distict values found in a given array.
	 *
	 * @param array DoubleArray?
	 * @param returnOrderByDescFreqAndAscOrigPos Boolean
	 *
	 * @return Array<DoubleArray>?
	 *
	 * @author Bas Milius
	 */
	fun getDistValues(array: DoubleArray?, returnOrderByDescFreqAndAscOrigPos: Boolean): Array<DoubleArray>?
	{
		if (array == null) return null

		val value = 0
		val count = 1
		val initPosFirst = 2
		val distVal = Array(array.size) { DoubleArray(3) }

		if (array.isEmpty()) return distVal
		if (array.size == 1)
		{
			distVal[0][value] = array[0]
			distVal[0][count] = 1.0
			distVal[0][initPosFirst] = 0.0

			return distVal
		}

		val initPos = sortAsc(array) ?: return null
		var unqValue = array[0]
		var unqValCnt = 1
		var unqValMinPos = initPos[0]
		var unqCnt = 0

		for (i in 1 until array.size)
		{
			if (BinaryRelations.eq(unqValue, array[i]) == BooleanAlgebra.TRUE.toDouble())
			{
				unqValCnt++
				if (initPos[i] < unqValMinPos)
				{
					unqValMinPos = initPos[i]
				}
			}

			if (BinaryRelations.eq(unqValue, array[i]) == BooleanAlgebra.FALSE.toDouble() && i < (array.size - 1))
			{
				distVal[unqCnt][value] = unqValue
				distVal[unqCnt][count] = unqValCnt.toDouble()
				distVal[unqCnt][initPosFirst] = unqValMinPos.toDouble()

				unqCnt++

				unqValue = array[i]
				unqValCnt = 1
				unqValMinPos = initPos[i]
			}
			else if (BinaryRelations.eq(unqValue, array[i]) == BooleanAlgebra.FALSE.toDouble() && i == (array.size - 1))
			{
				distVal[unqCnt][value] = unqValue
				distVal[unqCnt][count] = unqValCnt.toDouble()
				distVal[unqCnt][initPosFirst] = unqValMinPos.toDouble()

				unqCnt++

				distVal[unqCnt][value] = array[i]
				distVal[unqCnt][count] = 1.0
				distVal[unqCnt][initPosFirst] = initPos[i].toDouble()

				unqCnt++
			}
			else if (i == (array.size - 1))
			{
				distVal[unqCnt][value] = unqValue
				distVal[unqCnt][count] = unqValCnt.toDouble()
				distVal[unqCnt][initPosFirst] = unqValMinPos.toDouble()

				unqCnt++
			}
		}

		val distValFinal = Array(array.size) { DoubleArray(3) }
		var maxBase = 0.0

		for (i in 0 until unqCnt)
		{
			distValFinal[i][value] = distVal[i][value]
			distValFinal[i][count] = distVal[i][count]
			distValFinal[i][initPosFirst] = distVal[i][initPosFirst]

			if (distVal[i][count] > maxBase)
				maxBase = distVal[i][count]

			if (distVal[i][initPosFirst] > maxBase)
				maxBase = distVal[i][initPosFirst]
		}

		if (!returnOrderByDescFreqAndAscOrigPos)
			return distValFinal

		maxBase++

		val key = DoubleArray(unqCnt)

		for (i in 0 until unqCnt)
			key[i] = (maxBase - distVal[i][count] - 1) * maxBase + distVal[i][initPosFirst]

		val keyInitOrder = sortAsc(key)

		for (i in 0 until unqCnt)
		{
			if (keyInitOrder == null)
				continue

			distValFinal[i][value] = distVal[keyInitOrder[i]][value]
			distValFinal[i][count] = distVal[keyInitOrder[i]][count]
			distValFinal[i][initPosFirst] = distVal[keyInitOrder[i]][initPosFirst]
		}

		return distValFinal
	}

	/**
	 * Returns number of unique values found the list of numbers
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun numberOfDistValues(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return 0.0

		numbers
				.filter { it.isNaN() }
				.forEach { return Double.NaN }

		if (numbers.size == 1) return 1.0

		val distValues = getDistValues(numbers, false) ?: return Double.NaN

		return distValues.size.toDouble()
	}

	/**
	 * Greatest common divisor (GCD).
	 *
	 * @param a Long
	 * @param b Long
	 *
	 * @return Long
	 *
	 * @author Bas Milius
	 */
	fun gcd(a: Long, b: Long): Long
	{
		var aa = Math.abs(a)
		var bb = Math.abs(b)

		if (aa == 0L)
			return bb

		while (bb != 0L)
		{
			if (aa > bb)
				aa -= bb
			else
				bb -= aa
		}

		return aa
	}

	/**
	 * Greatest common divisor (GCD).
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun gcd(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN())
			return Double.NaN

		var aa = MathFunctions.floor(MathFunctions.abs(a))
		var bb = MathFunctions.floor(MathFunctions.abs(b))

		if (aa == 0.0)
			return bb

		while (bb != 0.0)
		{
			if (aa > bb)
				aa = MathFunctions.floor(aa - bb)
			else
				bb = MathFunctions.floor(bb - aa)
		}

		return aa
	}

	/**
	 * Greatest common divisor (GCD).
	 *
	 * @param numbers Long[]
	 *
	 * @return Long
	 *
	 * @author Bas Milius
	 */
	fun gcd(vararg numbers: Long): Long
	{
		if (numbers.isEmpty()) return -1L
		if (numbers.size == 1) return if (numbers[0] >= 0L) numbers[0] else -numbers[0]
		if (numbers.size == 2) return gcd(numbers[0], numbers[1])

		for (i in 1 until numbers.size)
			numbers[i] = gcd(numbers[i - 1], numbers[i])

		return numbers[numbers.size - 1]
	}

	/**
	 * Greatest common divisor (GCD).
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun gcd(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return Double.NaN
		if (numbers.size == 1) return MathFunctions.floor(MathFunctions.abs(numbers[0]))
		if (numbers.size == 2) return gcd(numbers[0], numbers[1])

		for (i in 1 until numbers.size)
			numbers[i] = gcd(numbers[i - 1], numbers[i])

		return numbers[numbers.size - 1]
	}

	/**
	 * Latest common multiply (LCM).
	 *
	 * @param a Long
	 * @param b Long
	 *
	 * @return Long
	 *
	 * @author Bas Milius
	 */
	fun lcm(a: Long, b: Long): Long
	{
		val aa = Math.abs(a)
		val bb = Math.abs(b)

		if (aa == 0L || bb == 0L)
			return 0L

		return (aa * bb) / gcd(aa, bb)
	}

	/**
	 * Latest common multiply (LCM).
	 *
	 * @param a Double
	 * @param b Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun lcm(a: Double, b: Double): Double
	{
		if (a.isNaN() || b.isNaN())
			return Double.NaN

		val aa = MathFunctions.floor(MathFunctions.abs(a))
		val bb = MathFunctions.floor(MathFunctions.abs(b))

		return (aa * bb) / gcd(aa, bb)
	}

	/**
	 * Latest common multiply (LCM).
	 *
	 * @param numbers Long[]
	 *
	 * @return Long
	 *
	 * @author Bas Milius
	 */
	fun lcm(vararg numbers: Long): Long
	{
		if (numbers.isEmpty()) return -1L
		if (numbers.size == 1) return if (numbers[0] >= 0L) numbers[0] else -numbers[0]
		if (numbers.size == 2) return lcm(numbers[0], numbers[1])

		for (i in 1 until numbers.size)
			numbers[i] = lcm(numbers[i - 1], numbers[i])

		return numbers[numbers.size - 1]
	}

	/**
	 * Latest common multiply (LCM).
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun lcm(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return Double.NaN
		if (numbers.size == 1) return MathFunctions.floor(MathFunctions.abs(numbers[0]))
		if (numbers.size == 2) return lcm(numbers[0], numbers[1])

		for (i in 1 until numbers.size)
			numbers[i] = lcm(numbers[i - 1], numbers[i])

		return numbers[numbers.size - 1]
	}

	/**
	 * Adding numbers.
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sum(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return Double.NaN
		if (numbers.size == 1) return numbers[0]

		var sum = 0.0

		for (num in numbers)
		{
			if (num.isNaN())
				return Double.NaN

			sum += num
		}

		return sum
	}

	/**
	 * Numbers multiplication.
	 *
	 * @param numbers Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun prod(vararg numbers: Double): Double
	{
		if (numbers.isEmpty()) return Double.NaN
		if (numbers.size == 1) return numbers[0]

		var prod = 1.0

		for (num in numbers)
		{
			if (num.isNaN())
				return Double.NaN

			prod *= num
		}

		return prod
	}

	/**
	 * Prime test
	 *
	 * @param n Long
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun primeTest(n: Long): Boolean
	{
		if (n == 2L) return true
		if (n % 2 == 0L) return false
		if (n <= 1L) return false

		val top = Math.sqrt(n.toDouble()).toLong()
		var primesCacheOddEnd = 3

		if (MathParser.primesCache != null)
		{
			if (MathParser.primesCache!!.cacheStatus == PrimesCache.CACHING_FINISHED)
			{
				if (n <= MathParser.primesCache!!.maxNumInCache)
					return MathParser.primesCache!!.isPrime(n.toInt())

				val topCache = Math.min(top, MathParser.primesCache!!.maxNumInCache.toLong())
				var i = 3

				while (i <= topCache)
				{
					if (MathParser.primesCache!!.isPrime(i) && n % i == 0L)
						return false

					i += 2
				}

				primesCacheOddEnd = i
			}
		}

		var i = primesCacheOddEnd

		while (i <= top)
		{
			if (n % i == 0L)
				return false
			i += 2
		}

		return true
	}

	/**
	 * Prime test
	 *
	 * @param n Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun primeTest(n: Double): Double
	{
		if (n.isNaN())
			return Double.NaN

		return if (primeTest(n.toLong())) 1.0 else 0.0
	}

	/**
	 * Prime counting function.
	 *
	 * @param n Long
	 *
	 * @return Long
	 *
	 * @author Bas Milius
	 */
	fun primeCount(n: Long): Long
	{
		if (n <= 1L) return 0
		if (n == 2L) return 1

		var numberOfPrimes = 1L

		(3..n)
				.filter { primeTest(it) }
				.forEach { numberOfPrimes++ }

		return numberOfPrimes
	}

	/**
	 * Prime counting function.
	 *
	 * @param n Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun primeCount(n: Double): Double
	{
		return primeCount(n.toLong()).toDouble()
	}

	/**
	 * Summation operator (SIGMA FROM i = a, to b,  f(i) by delta.
	 *
	 * @param f Expression
	 * @param index Argument
	 * @param from Double
	 * @param to Double
	 * @param delta Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun sigmaSummation(f: Expression, index: Argument, from: Double, to: Double, delta: Double): Double
	{
		var result = 0.0

		if (from.isNaN() || to.isNaN() || delta.isNaN() || delta == 0.0)
			return Double.NaN

		if (to >= from && delta > 0)
		{
			var i = from

			while (i < to)
			{
				result += MathParser.getFunctionValue(f, index, i)
				i += delta
			}

			if (delta - (i - to) > 0.5 * delta)
				result += MathParser.getFunctionValue(f, index, to)
		}
		else if (to <= from && delta < 0.0)
		{
			var i = from

			while (i > to)
			{
				result += MathParser.getFunctionValue(f, index, i)
				i += delta
			}

			if (delta - (to - i) > 0.5 * delta)
				result += MathParser.getFunctionValue(f, index, to)
		}
		else if (from == to)
		{
			result += MathParser.getFunctionValue(f, index, from)
		}

		return result
	}

	/**
	 * Product operator.
	 *
	 * @param f Expression
	 * @param index Argument
	 * @param from Double
	 * @param to Double
	 * @param delta Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun piProduct(f: Expression, index: Argument, from: Double, to: Double, delta: Double): Double
	{
		var result = 1.0

		if (from.isNaN() || to.isNaN() || delta.isNaN() || delta == 0.0)
			return Double.NaN

		if (to >= from && delta > 0)
		{
			var i = from

			while (i < to)
			{
				result *= MathParser.getFunctionValue(f, index, i)
				i += delta
			}

			if (delta - (i - to) > 0.5 * delta)
				result *= MathParser.getFunctionValue(f, index, to)
		}
		else if (to <= from && delta < 0.0)
		{
			var i = from

			while (i > to)
			{
				result *= MathParser.getFunctionValue(f, index, i)
				i += delta
			}

			if (delta - (to - i) > 0.5 * delta)
				result *= MathParser.getFunctionValue(f, index, to)
		}
		else if (from == to)
		{
			result *= MathParser.getFunctionValue(f, index, from)
		}

		return result
	}

	/**
	 * Minimum value - iterative operator.
	 *
	 * @param f Expression
	 * @param index Argument
	 * @param from Double
	 * @param to Double
	 * @param delta Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun min(f: Expression, index: Argument, from: Double, to: Double, delta: Double): Double
	{
		if (from.isNaN() || to.isNaN() || delta.isNaN() || delta == 0.0)
			return Double.NaN

		var min = Double.POSITIVE_INFINITY
		var v: Double

		if (to >= from && delta > 0)
		{
			var i = from

			while (i < to)
			{
				v = MathParser.getFunctionValue(f, index, i)

				if (v < min)
					min = v

				i += delta
			}

			v = MathParser.getFunctionValue(f, index, to)

			if (v < min)
				min = v
		}
		else if (to <= from && delta < 0)
		{
			var i = from

			while (i > to)
			{
				v = MathParser.getFunctionValue(f, index, i)

				if (v < min)
					min = v

				i += delta
			}

			v = MathParser.getFunctionValue(f, index, to)

			if (v < min)
				min = v
		}
		else if (from == to)
		{
			min = MathParser.getFunctionValue(f, index, from)
		}

		return min
	}

	/**
	 * Maximum value - iterative operator.
	 *
	 * @param f Expression
	 * @param index Argument
	 * @param from Double
	 * @param to Double
	 * @param delta Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun max(f: Expression, index: Argument, from: Double, to: Double, delta: Double): Double
	{
		if (from.isNaN() || to.isNaN() || delta.isNaN() || delta == 0.0)
			return Double.NaN

		var max = Double.NEGATIVE_INFINITY
		var v: Double

		if (to >= from && delta > 0)
		{
			var i = from

			while (i < to)
			{
				v = MathParser.getFunctionValue(f, index, i)

				if (v > max)
					max = v

				i += delta
			}

			v = MathParser.getFunctionValue(f, index, to)

			if (v > max)
				max = v
		}
		else if (to <= from && delta < 0)
		{
			var i = from

			while (i > to)
			{
				v = MathParser.getFunctionValue(f, index, i)

				if (v > max)
					max = v

				i += delta
			}

			v = MathParser.getFunctionValue(f, index, to)

			if (v > max)
				max = v
		}
		else if (from == to)
		{
			max = MathParser.getFunctionValue(f, index, from)
		}

		return max
	}

	/**
	 * Returns the regular expression representing number literal string in given numeral system with base between 1 and 36.
	 *
	 * @param numeralSystemBase Int
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	private fun getRegExpForNumeralSystem(numeralSystemBase: Int): String
	{
		return when (numeralSystemBase)
		{
			1 -> ParserSymbol.BASE1_REG_EXP
			2 -> ParserSymbol.BASE2_REG_EXP
			3 -> ParserSymbol.BASE3_REG_EXP
			4 -> ParserSymbol.BASE4_REG_EXP
			5 -> ParserSymbol.BASE5_REG_EXP
			6 -> ParserSymbol.BASE6_REG_EXP
			7 -> ParserSymbol.BASE7_REG_EXP
			8 -> ParserSymbol.BASE8_REG_EXP
			9 -> ParserSymbol.BASE9_REG_EXP
			10 -> ParserSymbol.BASE10_REG_EXP
			11 -> ParserSymbol.BASE11_REG_EXP
			12 -> ParserSymbol.BASE12_REG_EXP
			13 -> ParserSymbol.BASE13_REG_EXP
			14 -> ParserSymbol.BASE14_REG_EXP
			15 -> ParserSymbol.BASE15_REG_EXP
			16 -> ParserSymbol.BASE16_REG_EXP
			17 -> ParserSymbol.BASE17_REG_EXP
			18 -> ParserSymbol.BASE18_REG_EXP
			19 -> ParserSymbol.BASE19_REG_EXP
			20 -> ParserSymbol.BASE20_REG_EXP
			21 -> ParserSymbol.BASE21_REG_EXP
			22 -> ParserSymbol.BASE22_REG_EXP
			23 -> ParserSymbol.BASE23_REG_EXP
			24 -> ParserSymbol.BASE24_REG_EXP
			25 -> ParserSymbol.BASE25_REG_EXP
			26 -> ParserSymbol.BASE26_REG_EXP
			27 -> ParserSymbol.BASE27_REG_EXP
			28 -> ParserSymbol.BASE28_REG_EXP
			29 -> ParserSymbol.BASE29_REG_EXP
			30 -> ParserSymbol.BASE30_REG_EXP
			31 -> ParserSymbol.BASE31_REG_EXP
			32 -> ParserSymbol.BASE32_REG_EXP
			33 -> ParserSymbol.BASE33_REG_EXP
			34 -> ParserSymbol.BASE34_REG_EXP
			35 -> ParserSymbol.BASE35_REG_EXP
			36 -> ParserSymbol.BASE36_REG_EXP
			else -> "\\b\\B"
		}
	}

	/**
	 * Digit index based on digit character for numeral systems with base between 1 and 36.
	 *
	 * @param digitChar Char
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun digitIndex(digitChar: Char): Int
	{
		return when (digitChar)
		{
			'0' -> 0
			'1' -> 1
			'2' -> 2
			'3' -> 3
			'4' -> 4
			'5' -> 5
			'6' -> 6
			'7' -> 7
			'8' -> 8
			'9' -> 9
			'A' -> 10
			'B' -> 11
			'C' -> 12
			'D' -> 13
			'E' -> 14
			'F' -> 15
			'G' -> 16
			'H' -> 17
			'I' -> 18
			'J' -> 19
			'K' -> 20
			'L' -> 21
			'M' -> 22
			'N' -> 23
			'O' -> 24
			'P' -> 25
			'Q' -> 26
			'R' -> 27
			'S' -> 28
			'T' -> 29
			'U' -> 30
			'V' -> 31
			'W' -> 32
			'X' -> 33
			'Y' -> 34
			'Z' -> 35
			'a' -> 10
			'b' -> 11
			'c' -> 12
			'd' -> 13
			'e' -> 14
			'f' -> 15
			'g' -> 16
			'h' -> 17
			'i' -> 18
			'j' -> 19
			'k' -> 20
			'l' -> 21
			'm' -> 22
			'n' -> 23
			'o' -> 24
			'p' -> 25
			'q' -> 26
			'r' -> 27
			's' -> 28
			't' -> 29
			'u' -> 30
			'v' -> 31
			'w' -> 32
			'x' -> 33
			'y' -> 34
			'z' -> 35
			else -> -1
		}
	}

	/**
	 * Character representing digit for numeral systems with base between 1 and 36.
	 *
	 * @param digitIndex Int
	 *
	 * @return Char
	 *
	 * @author Bas Milius
	 */
	fun digitChar(digitIndex: Int): Char
	{
		return when (digitIndex)
		{
			0 -> '0'
			1 -> '1'
			2 -> '2'
			3 -> '3'
			4 -> '4'
			5 -> '5'
			6 -> '6'
			7 -> '7'
			8 -> '8'
			9 -> '9'
			10 -> 'A'
			11 -> 'B'
			12 -> 'C'
			13 -> 'D'
			14 -> 'E'
			15 -> 'F'
			16 -> 'G'
			17 -> 'H'
			18 -> 'I'
			19 -> 'J'
			20 -> 'K'
			21 -> 'L'
			22 -> 'M'
			23 -> 'N'
			24 -> 'O'
			25 -> 'P'
			26 -> 'Q'
			27 -> 'R'
			28 -> 'S'
			29 -> 'T'
			30 -> 'U'
			31 -> 'V'
			32 -> 'W'
			33 -> 'X'
			34 -> 'Y'
			35 -> 'Z'
			else -> '?'
		}
	}

	/**
	 * Recognition of numeral system base in which number literal representsnumber.
	 * Examples: 2 for b2.1001 or b.1001, 1 for b1.111, 23 for b23.123afg 16 for b16.123acdf or h.123acdf.
	 *
	 * @param numberLiteral String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getNumeralSystemBase(numberLiteral: String): Int
	{
		return (0..36).firstOrNull { MathParser.regexMatch(numberLiteral, getRegExpForNumeralSystem(it)) } ?: -1
	}

	/**
	 * Other base (base between 1 and 36) number literal conversion to decimal number.
	 *
	 * @param numberLiteral String?
	 * @param numeralSystemBase Int
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(numberLiteral: String?, numeralSystemBase: Int): Double
	{
		if (numberLiteral == null)
			return Double.NaN

		var numberLiteralReal = numberLiteral.trim { it <= ' ' }

		if (numberLiteralReal.isEmpty()) return if (numeralSystemBase == 1) 0.0 else Double.NaN
		if (numeralSystemBase < 1) return Double.NaN
		if (numeralSystemBase > 36) return Double.NaN

		val signChar = numberLiteralReal[0]
		var sign = 1.0

		if (signChar == '-')
		{
			sign = -1.0
			numberLiteralReal = numberLiteralReal.substring(1)
		}
		else if (signChar == '+')
		{
			sign = 1.0
			numberLiteralReal = numberLiteralReal.substring(1)
		}

		val length = numberLiteralReal.length
		var decValue = 0.0
		var digit: Int

		for (i in 0 until length)
		{
			digit = digitIndex(numberLiteralReal[i])

			if (numeralSystemBase > 1)
			{
				if (digit in 0..(numeralSystemBase - 1))
				{
					decValue = numeralSystemBase * decValue + digit
				}
				else
				{
					return Double.NaN
				}
			}
			else
			{
				if (digit == 1)
				{
					decValue = numeralSystemBase * decValue + digit
				}
				else
				{
					return Double.NaN
				}
			}
		}

		return sign * decValue
	}

	/**
	 * Other base (base between 1 and 36) number literal conversion to decimal number. Base specification included in number literal.
	 * Examples: 2 for b2.1001 or b.1001, 1 for b1.111, 23 for b23.123afg 16 for b16.123acdf or h.123acdf.
	 *
	 * @param numberLiteral String?
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(numberLiteral: String?): Double
	{
		if (numberLiteral == null)
			return Double.NaN

		val numberLiteralReal = numberLiteral.trim { it <= ' ' }
		val numberLiteralStrLenght = numberLiteralReal.length
		if (numberLiteralStrLenght < 2)
			return Double.NaN

		val numeralSystemBase = getNumeralSystemBase(numberLiteralReal)
		if (numeralSystemBase == -1)
			return Double.NaN

		val dotPos = numberLiteralReal.indexOf('.')
		if (dotPos == 0)
			return Double.NaN

		val signChar = numberLiteralReal[0]
		var sign = 1.0
		if (signChar == '-')
			sign = -1.0

		var finalLiteral = ""
		if (numberLiteralStrLenght > dotPos + 1)
			finalLiteral = numberLiteral.substring(dotPos + 1)

		return sign * convOthBase2Decimal(finalLiteral, numeralSystemBase)
	}

	/**
	 * Other base to decimal conversion.
	 *
	 * @param numeralSystemBase Int
	 * @param digits Int[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(numeralSystemBase: Int, vararg digits: Int): Double
	{
		if (numeralSystemBase < 1)
			return Double.NaN

		val length = digits.size
		if (length == 0)
			return if (numeralSystemBase == 1) 0.0 else Double.NaN

		var decValue = 0.0
		var digit: Int

		for (i in 0 until length)
		{
			digit = digits[i]

			if (numeralSystemBase > 1)
			{
				if (digit in 0..(numeralSystemBase - 1))
				{
					decValue = numeralSystemBase * decValue + digit
				}
				else
				{
					return Double.NaN
				}
			}
			else
			{
				if (digit == 1)
				{
					decValue = numeralSystemBase * decValue + digit
				}
				else
				{
					return Double.NaN
				}
			}
		}

		return decValue
	}

	/**
	 * Other base to decimal conversion.
	 *
	 * @param numeralSystemBase Double
	 * @param digits Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(numeralSystemBase: Double, vararg digits: Double): Double
	{
		if (numeralSystemBase.isNaN())
			return Double.NaN

		if (numeralSystemBase < 0)
			return Double.NaN

		val numeralSystemBaseInt = MathFunctions.floor(numeralSystemBase).toInt()
		val length = digits.size
		if (length == 0)
			return if (numeralSystemBaseInt == 1) 0.0 else Double.NaN

		val digitsInt = IntArray(length)
		var digit: Double

		for (i in 0 until length)
		{
			digit = digits[i]

			if (digit.isNaN())
				return Double.NaN

			digitsInt[i] = digit.toInt()
		}

		return convOthBase2Decimal(numeralSystemBaseInt, *digitsInt)
	}

	/**
	 * Other base to decimal conversion.
	 *
	 * @param baseAndDigits IntArray?
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(baseAndDigits: IntArray?): Double
	{
		if (baseAndDigits == null)
			return Double.NaN

		if (baseAndDigits.isEmpty())
			return Double.NaN

		val numeralSystemBase = baseAndDigits[0]
		val digits = IntArray(baseAndDigits.size - 1)

		for (i in 1 until baseAndDigits.size)
			digits[i - 1] = baseAndDigits[i]

		return convOthBase2Decimal(numeralSystemBase, *digits)
	}

	/**
	 * Other base to decimal conversion.
	 *
	 * @param baseAndDigits DoubleArray?
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(baseAndDigits: DoubleArray?): Double
	{
		if (baseAndDigits == null)
			return Double.NaN

		if (baseAndDigits.isEmpty())
			return Double.NaN

		val numeralSystemBase = baseAndDigits[0]
		val digits = DoubleArray(baseAndDigits.size - 1)

		for (i in 1 until baseAndDigits.size)
			digits[i - 1] = baseAndDigits[i]

		return convOthBase2Decimal(numeralSystemBase, *digits)
	}

	/**
	 * Decimal number to other numeral system conversion with base between 1 and 36.
	 *
	 * @param decimalNumber Double
	 * @param numeralSystemBase Int
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun convDecimal2OthBase(decimalNumber: Double, numeralSystemBase: Int): String
	{
		if (decimalNumber.isNaN()) return "NaN"
		if (numeralSystemBase < 1) return "NaN"
		if (numeralSystemBase > 36) return "NaN"
		if (decimalNumber == 0.0) return if (numeralSystemBase > 1) "0" else ""

		val intPart = MathFunctions.floor(MathFunctions.abs(decimalNumber))
		val sign = MathFunctions.sgn(decimalNumber)
		var signChar = ""
		if (sign < 0)
			signChar = "-"

		if (intPart < numeralSystemBase)
			return signChar + digitChar(intPart.toInt())

		var numberLiteral = ""
		var quotient = intPart
		var reminder: Int

		if (numeralSystemBase > 1)
		{
			while (quotient >= 1.0)
			{
				reminder = (quotient % numeralSystemBase).toInt()
				quotient = MathFunctions.floor(quotient / numeralSystemBase)
				numberLiteral = digitChar(reminder) + numberLiteral
			}
		}
		else
		{
			val repeat = CharArray(intPart.toInt())
			Arrays.fill(repeat, '1')
			numberLiteral = String(repeat)
		}

		return signChar + numberLiteral
	}

	/**
	 * Decimal number to other numeral system conversion with base between 1 and 36.
	 *
	 * @param decimalNumber Double
	 * @param numeralSystemBase Int
	 * @param format Int
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun convDecimal2OthBase(decimalNumber: Double, numeralSystemBase: Int, format: Int): String
	{
		if (decimalNumber.isNaN()) return "NaN"
		if (numeralSystemBase < 1) return "NaN"
		if (numeralSystemBase > 36) return "NaN"

		var prefix = ""

		if (format == 1 || format == 2)
			prefix = "b$numeralSystemBase."

		if (format == 2)
		{
			if (numeralSystemBase == 2) prefix = "b."
			if (numeralSystemBase == 8) prefix = "o."
			if (numeralSystemBase == 16) prefix = "h."
		}
		var sign = ""

		if (decimalNumber < 0)
			sign = "-"

		return sign + prefix + convDecimal2OthBase(MathFunctions.abs(decimalNumber), numeralSystemBase)
	}

	/**
	 * Number of digits needed to represent given number in base 10 numeral system.
	 *
	 * @param number Long
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun numberOfDigits(number: Long): Int
	{
		var num = number

		if (num < 0)
			num = -num

		return when
		{
			num < 10 -> 1
			number < 100 -> 2
			number < 1000 -> 3
			number < 10000 -> 4
			number < 100000 -> 5
			number < 1000000 -> 6
			number < 10000000 -> 7
			number < 100000000 -> 8
			number < 1000000000 -> 9
			else -> 10
		}
	}

	/**
	 * Number of digits needed to represent given number in base 10 numeral system.
	 *
	 * @param number Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun numberOfDigits(number: Double): Double
	{
		var num = number

		if (num.isNaN())
			return Double.NaN

		if (num.isFinite())
			return Double.POSITIVE_INFINITY

		if (num < 0.0)
			num = -num

		num = MathFunctions.floor(num)

		return when
		{
			num < 1.0e1 -> 1
			num < 1.0e2 -> 2
			num < 1.0e3 -> 3
			num < 1.0e4 -> 4
			num < 1.0e5 -> 5
			num < 1.0e6 -> 6
			num < 1.0e7 -> 7
			num < 1.0e8 -> 8
			num < 1.0e9 -> 9
			num < 1.0e10 -> 10
			num < 1.0e11 -> 11
			num < 1.0e12 -> 12
			num < 1.0e13 -> 13
			num < 1.0e14 -> 14
			num < 1.0e15 -> 15
			num < 1.0e16 -> 16
			num < 1.0e17 -> 17
			num < 1.0e18 -> 18
			num < 1.0e19 -> 19
			num < 1.0e20 -> 20
			num < 1.0e21 -> 21
			num < 1.0e22 -> 22
			num < 1.0e23 -> 23
			num < 1.0e24 -> 24
			num < 1.0e25 -> 25
			num < 1.0e26 -> 26
			num < 1.0e27 -> 27
			num < 1.0e28 -> 28
			num < 1.0e29 -> 29
			num < 1.0e30 -> 30
			num < 1.0e31 -> 31
			num < 1.0e32 -> 32
			num < 1.0e33 -> 33
			num < 1.0e34 -> 34
			num < 1.0e35 -> 35
			num < 1.0e36 -> 36
			num < 1.0e37 -> 37
			num < 1.0e38 -> 38
			num < 1.0e39 -> 39
			num < 1.0e40 -> 40
			num < 1.0e41 -> 41
			num < 1.0e42 -> 42
			num < 1.0e43 -> 43
			num < 1.0e44 -> 44
			num < 1.0e45 -> 45
			num < 1.0e46 -> 46
			num < 1.0e47 -> 47
			num < 1.0e48 -> 48
			num < 1.0e49 -> 49
			num < 1.0e50 -> 50
			num < 1.0e51 -> 51
			num < 1.0e52 -> 52
			num < 1.0e53 -> 53
			num < 1.0e54 -> 54
			num < 1.0e55 -> 55
			num < 1.0e56 -> 56
			num < 1.0e57 -> 57
			num < 1.0e58 -> 58
			num < 1.0e59 -> 59
			num < 1.0e60 -> 60
			num < 1.0e61 -> 61
			num < 1.0e62 -> 62
			num < 1.0e63 -> 63
			num < 1.0e64 -> 64
			num < 1.0e65 -> 65
			num < 1.0e66 -> 66
			num < 1.0e67 -> 67
			num < 1.0e68 -> 68
			num < 1.0e69 -> 69
			num < 1.0e70 -> 70
			num < 1.0e71 -> 71
			num < 1.0e72 -> 72
			num < 1.0e73 -> 73
			num < 1.0e74 -> 74
			num < 1.0e75 -> 75
			num < 1.0e76 -> 76
			num < 1.0e77 -> 77
			num < 1.0e78 -> 78
			num < 1.0e79 -> 79
			num < 1.0e80 -> 80
			num < 1.0e81 -> 81
			num < 1.0e82 -> 82
			num < 1.0e83 -> 83
			num < 1.0e84 -> 84
			num < 1.0e85 -> 85
			num < 1.0e86 -> 86
			num < 1.0e87 -> 87
			num < 1.0e88 -> 88
			num < 1.0e89 -> 89
			num < 1.0e90 -> 90
			num < 1.0e91 -> 91
			num < 1.0e92 -> 92
			num < 1.0e93 -> 93
			num < 1.0e94 -> 94
			num < 1.0e95 -> 95
			num < 1.0e96 -> 96
			num < 1.0e97 -> 97
			num < 1.0e98 -> 98
			num < 1.0e99 -> 99
			num < 1.0e100 -> 100
			num < 1.0e101 -> 101
			num < 1.0e102 -> 102
			num < 1.0e103 -> 103
			num < 1.0e104 -> 104
			num < 1.0e105 -> 105
			num < 1.0e106 -> 106
			num < 1.0e107 -> 107
			num < 1.0e108 -> 108
			num < 1.0e109 -> 109
			num < 1.0e110 -> 110
			num < 1.0e111 -> 111
			num < 1.0e112 -> 112
			num < 1.0e113 -> 113
			num < 1.0e114 -> 114
			num < 1.0e115 -> 115
			num < 1.0e116 -> 116
			num < 1.0e117 -> 117
			num < 1.0e118 -> 118
			num < 1.0e119 -> 119
			num < 1.0e120 -> 120
			num < 1.0e121 -> 121
			num < 1.0e122 -> 122
			num < 1.0e123 -> 123
			num < 1.0e124 -> 124
			num < 1.0e125 -> 125
			num < 1.0e126 -> 126
			num < 1.0e127 -> 127
			num < 1.0e128 -> 128
			num < 1.0e129 -> 129
			num < 1.0e130 -> 130
			num < 1.0e131 -> 131
			num < 1.0e132 -> 132
			num < 1.0e133 -> 133
			num < 1.0e134 -> 134
			num < 1.0e135 -> 135
			num < 1.0e136 -> 136
			num < 1.0e137 -> 137
			num < 1.0e138 -> 138
			num < 1.0e139 -> 139
			num < 1.0e140 -> 140
			num < 1.0e141 -> 141
			num < 1.0e142 -> 142
			num < 1.0e143 -> 143
			num < 1.0e144 -> 144
			num < 1.0e145 -> 145
			num < 1.0e146 -> 146
			num < 1.0e147 -> 147
			num < 1.0e148 -> 148
			num < 1.0e149 -> 149
			num < 1.0e150 -> 150
			num < 1.0e151 -> 151
			num < 1.0e152 -> 152
			num < 1.0e153 -> 153
			num < 1.0e154 -> 154
			num < 1.0e155 -> 155
			num < 1.0e156 -> 156
			num < 1.0e157 -> 157
			num < 1.0e158 -> 158
			num < 1.0e159 -> 159
			num < 1.0e160 -> 160
			num < 1.0e161 -> 161
			num < 1.0e162 -> 162
			num < 1.0e163 -> 163
			num < 1.0e164 -> 164
			num < 1.0e165 -> 165
			num < 1.0e166 -> 166
			num < 1.0e167 -> 167
			num < 1.0e168 -> 168
			num < 1.0e169 -> 169
			num < 1.0e170 -> 170
			num < 1.0e171 -> 171
			num < 1.0e172 -> 172
			num < 1.0e173 -> 173
			num < 1.0e174 -> 174
			num < 1.0e175 -> 175
			num < 1.0e176 -> 176
			num < 1.0e177 -> 177
			num < 1.0e178 -> 178
			num < 1.0e179 -> 179
			num < 1.0e180 -> 180
			num < 1.0e181 -> 181
			num < 1.0e182 -> 182
			num < 1.0e183 -> 183
			num < 1.0e184 -> 184
			num < 1.0e185 -> 185
			num < 1.0e186 -> 186
			num < 1.0e187 -> 187
			num < 1.0e188 -> 188
			num < 1.0e189 -> 189
			num < 1.0e190 -> 190
			num < 1.0e191 -> 191
			num < 1.0e192 -> 192
			num < 1.0e193 -> 193
			num < 1.0e194 -> 194
			num < 1.0e195 -> 195
			num < 1.0e196 -> 196
			num < 1.0e197 -> 197
			num < 1.0e198 -> 198
			num < 1.0e199 -> 199
			num < 1.0e200 -> 200
			num < 1.0e201 -> 201
			num < 1.0e202 -> 202
			num < 1.0e203 -> 203
			num < 1.0e204 -> 204
			num < 1.0e205 -> 205
			num < 1.0e206 -> 206
			num < 1.0e207 -> 207
			num < 1.0e208 -> 208
			num < 1.0e209 -> 209
			num < 1.0e210 -> 210
			num < 1.0e211 -> 211
			num < 1.0e212 -> 212
			num < 1.0e213 -> 213
			num < 1.0e214 -> 214
			num < 1.0e215 -> 215
			num < 1.0e216 -> 216
			num < 1.0e217 -> 217
			num < 1.0e218 -> 218
			num < 1.0e219 -> 219
			num < 1.0e220 -> 220
			num < 1.0e221 -> 221
			num < 1.0e222 -> 222
			num < 1.0e223 -> 223
			num < 1.0e224 -> 224
			num < 1.0e225 -> 225
			num < 1.0e226 -> 226
			num < 1.0e227 -> 227
			num < 1.0e228 -> 228
			num < 1.0e229 -> 229
			num < 1.0e230 -> 230
			num < 1.0e231 -> 231
			num < 1.0e232 -> 232
			num < 1.0e233 -> 233
			num < 1.0e234 -> 234
			num < 1.0e235 -> 235
			num < 1.0e236 -> 236
			num < 1.0e237 -> 237
			num < 1.0e238 -> 238
			num < 1.0e239 -> 239
			num < 1.0e240 -> 240
			num < 1.0e241 -> 241
			num < 1.0e242 -> 242
			num < 1.0e243 -> 243
			num < 1.0e244 -> 244
			num < 1.0e245 -> 245
			num < 1.0e246 -> 246
			num < 1.0e247 -> 247
			num < 1.0e248 -> 248
			num < 1.0e249 -> 249
			num < 1.0e250 -> 250
			num < 1.0e251 -> 251
			num < 1.0e252 -> 252
			num < 1.0e253 -> 253
			num < 1.0e254 -> 254
			num < 1.0e255 -> 255
			num < 1.0e256 -> 256
			num < 1.0e257 -> 257
			num < 1.0e258 -> 258
			num < 1.0e259 -> 259
			num < 1.0e260 -> 260
			num < 1.0e261 -> 261
			num < 1.0e262 -> 262
			num < 1.0e263 -> 263
			num < 1.0e264 -> 264
			num < 1.0e265 -> 265
			num < 1.0e266 -> 266
			num < 1.0e267 -> 267
			num < 1.0e268 -> 268
			num < 1.0e269 -> 269
			num < 1.0e270 -> 270
			num < 1.0e271 -> 271
			num < 1.0e272 -> 272
			num < 1.0e273 -> 273
			num < 1.0e274 -> 274
			num < 1.0e275 -> 275
			num < 1.0e276 -> 276
			num < 1.0e277 -> 277
			num < 1.0e278 -> 278
			num < 1.0e279 -> 279
			num < 1.0e280 -> 280
			num < 1.0e281 -> 281
			num < 1.0e282 -> 282
			num < 1.0e283 -> 283
			num < 1.0e284 -> 284
			num < 1.0e285 -> 285
			num < 1.0e286 -> 286
			num < 1.0e287 -> 287
			num < 1.0e288 -> 288
			num < 1.0e289 -> 289
			num < 1.0e290 -> 290
			num < 1.0e291 -> 291
			num < 1.0e292 -> 292
			num < 1.0e293 -> 293
			num < 1.0e294 -> 294
			num < 1.0e295 -> 295
			num < 1.0e296 -> 296
			num < 1.0e297 -> 297
			num < 1.0e298 -> 298
			num < 1.0e299 -> 299
			num < 1.0e300 -> 300
			num < 1.0e301 -> 301
			num < 1.0e302 -> 302
			num < 1.0e303 -> 303
			num < 1.0e304 -> 304
			num < 1.0e305 -> 305
			num < 1.0e306 -> 306
			num < 1.0e307 -> 307
			num < 1.0e308 -> 308
			else -> 309
		}.toDouble()
	}

	/**
	 * Number of digits needed to represent given number in numeral system with given base.
	 *
	 * @param number Long
	 * @param numeralSystemBase Long
	 *
	 * @return Long
	 *
	 * @author Bas Milius
	 */
	fun numberOfDigits(number: Long, numeralSystemBase: Long): Long
	{
		var num = number

		if (numeralSystemBase < 1L)
			return -1L

		if (num < 0)
			num = -num

		if (numeralSystemBase == 10L)
			return numberOfDigits(num).toLong()

		if (numeralSystemBase == 1L)
			return num

		if (num < numeralSystemBase)
			return 1L

		var quotient = num
		var digitsNum: Long = 0

		while (quotient >= 1)
		{
			quotient /= numeralSystemBase
			digitsNum++
		}

		return digitsNum
	}

	/**
	 * Number of digits needed to represent given number (its integer part) in numeral system with given base.
	 *
	 * @param number Double
	 * @param numeralSystemBase Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun numberOfDigits(number: Double, numeralSystemBase: Double): Double
	{
		var num = number
		var nsb = numeralSystemBase

		if (num.isNaN()) return Double.NaN
		if (nsb.isNaN()) return Double.NaN
		if (nsb.isInfinite()) return Double.NaN
		if (nsb < 1.0) return Double.NaN
		if (num.isInfinite()) return Double.POSITIVE_INFINITY

		if (num < 0.0)
			num = -num

		num = MathFunctions.floor(num)
		nsb = MathFunctions.floor(nsb)

		if (nsb == 10.0) return numberOfDigits(num)
		if (nsb == 1.0) return num
		if (num < nsb) return 1.0

		var quotient = num
		var digitsNum = 0.0

		while (quotient >= 1.0)
		{
			quotient = MathFunctions.floor(quotient / nsb)
			digitsNum++
		}

		return digitsNum
	}

	/**
	 * Digit at position - numeral system with given base.
	 *
	 * @param number Long
	 * @param position Int
	 * @param numeralSystemBase Int
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun digitAtPosition(number: Long, position: Int, numeralSystemBase: Int): Int
	{
		var num = number

		if (numeralSystemBase < 1) return -1
		if (num < 0) num = -num

		val digitsNum = numberOfDigits(number, numeralSystemBase.toLong()).toInt()

		if (position <= -digitsNum) return if (numeralSystemBase > 1) 0 else -1
		if (position > digitsNum) return -1
		if (numeralSystemBase == 1) return 1

		val digits = IntArray(digitsNum)
		var quotient = num
		var digit: Int
		var digitIndex = digitsNum

		while (quotient >= 1)
		{
			digit = (quotient % numeralSystemBase).toInt()
			quotient /= numeralSystemBase
			digitIndex--
			digits[digitIndex] = digit
		}

		return if (position >= 1) digits[position - 1] else digits[digitsNum + position - 1]
	}

	/**
	 * Digit at position - numeral system with base 10.
	 *
	 * @param number Long
	 * @param position Int
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun digitAtPosition(number: Long, position: Int): Int
	{
		return digitAtPosition(number, position, 10)
	}

	/**
	 * Digit at position - numeral system with given base.
	 *
	 * @param number Double
	 * @param position Double
	 * @param numeralSystemBase Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun digitAtPosition(number: Double, position: Double, numeralSystemBase: Double): Double
	{
		var num = number
		var nsb = numeralSystemBase

		if (num.isNaN()) return Double.NaN
		if (position.isNaN()) return Double.NaN
		if (nsb.isNaN()) return Double.NaN
		if (num.isInfinite()) return Double.NaN
		if (position.isInfinite()) return Double.NaN
		if (nsb.isInfinite()) return Double.NaN
		if (nsb < 1.0) return Double.NaN
		if (num < 0) num = -num

		num = MathFunctions.floor(num)
		nsb = MathFunctions.floor(nsb)

		val digitsNum = numberOfDigits(num, nsb).toInt()

		if (position <= -digitsNum) return if (nsb > 1.0) 0.0 else Double.NaN
		if (position > digitsNum) return Double.NaN
		if (nsb == 1.0) return 1.0

		val digits = DoubleArray(digitsNum)
		var quotient = num
		var digit: Double
		var digitIndex: Int = digitsNum

		while (quotient >= 1.0)
		{
			digit = MathFunctions.floor(quotient % nsb)
			quotient = MathFunctions.floor(quotient / nsb)
			digitIndex--
			digits[digitIndex] = digit
		}

		return if (position >= 1.0) digits[(position - 1).toInt()] else digits[(digitsNum + position - 1).toInt()]
	}

	/**
	 * Digit at position - numeral system with base 10.
	 *
	 * @param number Double
	 * @param position Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun digitAtPosition(number: Double, position: Double): Double
	{
		return digitAtPosition(number, position, 10.0)
	}

	/**
	 * Prime descomposition (prime factorization).
	 *
	 * @param number Long
	 *
	 * @return LongArray
	 *
	 * @author Bas Milius
	 */
	fun primeFactors(number: Long): LongArray
	{
		var num = number

		val longZeroArray = LongArray(0)
		val factors: LongArray

		if (num == 0L) return longZeroArray
		if (num < 0L) num = -num
		if (num == 1L)
		{
			factors = LongArray(1)
			factors[0] = 1L
			return factors
		}

		if (MathParser.primesCache != null && MathParser.primesCache!!.cacheStatus == PrimesCache.CACHING_FINISHED && num <= Integer.MAX_VALUE && MathParser.primesCache!!.primeTest(num.toInt()) == PrimesCache.IS_PRIME)
		{
			factors = LongArray(1)
			factors[0] = num
			return factors
		}

		val factorsList = ArrayList<Long>()
		var i = 2L

		while (i <= (num / i))
		{
			while (num % i == 0L)
			{
				factorsList.add(i)
				num /= i
			}

			i++
		}

		if (num > 1)
			factorsList.add(num)

		factors = LongArray(factorsList.size)

		for (x in 0 until factors.size)
			factors[x] = factorsList[x]

		return factors
	}

	/**
	 * Prime descomposition (prime factorization).
	 *
	 * @param number Double
	 *
	 * @return DoubleArray
	 *
	 * @author Bas Milius
	 */
	fun primeFactors(number: Double): DoubleArray
	{
		var num = number

		val doubleZeroArray = DoubleArray(0)
		val factors: DoubleArray

		if (num.isNaN() || num.isInfinite()) return doubleZeroArray

		num = MathFunctions.floor(MathFunctions.abs(num))

		if (num == 0.0) return doubleZeroArray
		if (num == 1.0)
		{
			factors = DoubleArray(1)
			factors[0] = 1.0
			return factors
		}

		if (MathParser.primesCache != null && MathParser.primesCache!!.cacheStatus == PrimesCache.CACHING_FINISHED && num <= Integer.MAX_VALUE && MathParser.primesCache!!.primeTest(num.toInt()) == PrimesCache.IS_PRIME)
		{
			factors = DoubleArray(1)
			factors[0] = num
			return factors
		}

		val factorsList = ArrayList<Double>()
		var i = 2.0

		while (i <= MathFunctions.floor(num / i))
		{
			while (num % i == 0.0)
			{
				factorsList.add(i)
				num = MathFunctions.floor(num / i)
			}

			i++
		}

		if (num > 1.0)
			factorsList.add(num)

		factors = DoubleArray(factorsList.size)

		for (x in 0 until factors.size)
			factors[x] = factorsList[x]

		return factors
	}

	/**
	 * Prime decomposition (prime factorization) - returns number of distinct prime factors.
	 *
	 * @param number Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun numberOfPrimeFactors(number: Double): Double
	{
		if (number.isNaN()) return Double.NaN

		val factors = primeFactors(number)

		if (factors.size <= 1)
			return factors.size.toDouble()

		val factorsDist = getDistValues(factors, false) ?: return 0.0

		return factorsDist.size.toDouble()
	}

	/**
	 * Prime decomposition (prime factorization) - returns prime factor value.
	 *
	 * @param number Double
	 * @param id Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun primeFactorValue(number: Double, id: Double): Double
	{
		var num = number
		var ID = id

		if (num.isNaN() || num.isInfinite()) return Double.NaN
		if (ID.isNaN() || ID.isInfinite()) return Double.NaN

		num = MathFunctions.floor(MathFunctions.abs(num))

		if (num == 0.0) return Double.NaN
		if (ID < 1.0) return 1.0

		ID = MathFunctions.floor(ID)

		if (ID > Integer.MAX_VALUE) return 1.0

		val factors = primeFactors(num)
		val factorsDist = getDistValues(factors, false) ?: return 1.0

		return if (ID > factorsDist.size) 1.0 else factorsDist[(ID - 1).toInt()][0]
	}

	/**
	 * Prime decomposition (prime factorization) - returns prime factor exponent.
	 *
	 * @param number Double
	 * @param id Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun primeFactorExponent(number: Double, id: Double): Double
	{
		var num = number
		var ID = id

		if (num.isNaN() || num.isInfinite()) return Double.NaN
		if (ID.isNaN() || ID.isInfinite()) return Double.NaN

		num = MathFunctions.floor(MathFunctions.abs(num))

		if (num == 0.0) return Double.NaN
		if (ID < 1.0) return 0.0

		ID = MathFunctions.floor(ID)

		if (ID > Integer.MAX_VALUE) return 0.0

		val factors = primeFactors(num)
		val factorsDist = getDistValues(factors, false) ?: return 0.0

		return if (ID > factorsDist.size) 0.0 else factorsDist[(ID - 1).toInt()][1]
	}

}
