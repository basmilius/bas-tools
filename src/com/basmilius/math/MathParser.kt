package com.basmilius.math

import com.basmilius.math.mathcollection.BinaryRelations
import com.basmilius.math.mathcollection.NumberTheory
import com.basmilius.math.mathcollection.PrimesCache
import com.basmilius.math.mathcollection.ProbabilityDistributions
import com.basmilius.math.miscellaneous.TokenModification
import com.basmilius.math.parsertokens.*
import com.basmilius.math.parsertokens.Unit
import com.sun.jna.StringArray
import java.util.*
import java.util.regex.Pattern

/**
 * Object MathParser
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser
 */
object MathParser
{

	val NOT_FOUND = -1
	val FOUND = 0

	private val VERSION = "1.0.0"
	private var CONSOLE_OUTPUT = ""
	private var CONSOLE_PREFIX = "[MathParser-v${VERSION}] "
	private var CONSOLE_OUTPUT_PREFIX = CONSOLE_PREFIX
	private var CONSOLE_ROW_NUMBER = 1

	var primesCache: PrimesCache? = null

	private val PRIMES_CACHE_NOT_INITIALIZED = -1
	private var THREADS_NUMBER = Runtime.getRuntime().availableProcessors()
	private val mathParserExpression = Expression()

	var ulpRounding = true
	var MAX_RECURSION_CALLS = 200
	val tokensToRemove = ArrayList<String>()
	val tokensToModify = ArrayList<TokenModification>()
	var overrideBuiltinTokens = false
	var optionsChangesetNumber = 0

	val NAMEv10 = "1.0"
	val NAMEv20 = "2.0"
	val NAMEv23 = "2.3"
	val NAMEv24 = "2.4"
	val NAMEv30 = "3.0"
	val NAMEv40 = "4.0"
	val NAMEv41 = "4.1"

	/**
	 * Initialization of prime numbers cache.
	 *
	 * @param maximumNumbersInCache Int
	 *
	 * @author Bas Milius
	 */
	fun initPrimesCache(maximumNumbersInCache: Int = PrimesCache.DEFAULT_MAX_NUM_IN_CACHE)
	{
		primesCache = PrimesCache(maximumNumbersInCache)
	}

	/**
	 * Initialization of prime numbers cache.
	 *
	 * @param primesCache PrimesCache
	 *
	 * @author Bas Milius
	 */
	fun initPrimesCache(primesCache: PrimesCache)
	{
		MathParser.primesCache = primesCache
	}

	/**
	 * Sets primes cache to null.
	 *
	 * @author Bas Milius
	 */
	fun setNoPrimesCache()
	{
		primesCache = null
	}

	/**
	 * Returns maximum integer number in primes cache.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getMaxNumInPrimesCache(): Int
	{
		return primesCache?.maxNumInCache ?: PRIMES_CACHE_NOT_INITIALIZED
	}

	/**
	 * Gets maximum threads number.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getThreadsNumber(): Int
	{
		return THREADS_NUMBER
	}

	/**
	 * Sets default threads number.
	 *
	 * @author Bas Milius
	 */
	fun setDefaultThreadsNumber()
	{
		THREADS_NUMBER = Runtime.getRuntime().availableProcessors()
	}

	/**
	 * Sets threads number.
	 *
	 * @param threadsNumber Int
	 *
	 * @author Bas Milius
	 */
	fun setThreadsNumber(threadsNumber: Int)
	{
		if (threadsNumber > 0)
			THREADS_NUMBER = threadsNumber
	}

	/**
	 * Calculates function f(x0) (given as expression) assigning Argument x = x0.
	 *
	 * @param f Expression
	 * @param x Argument
	 * @param x0 Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun getFunctionValue(f: Expression, x: Argument, x0: Double): Double
	{
		x.argumentValue = x0
		return f.calculate()
	}

	/**
	 * Converts a list with doubles to an array with doubles.
	 *
	 * @param numbers List<Double>
	 *
	 * @return DoubleArray?
	 *
	 * @author Bas Milius
	 */
	fun arrayList2double(numbers: List<Double>?): DoubleArray
	{
		if (numbers == null)
			return DoubleArray(0)

		val size = numbers.size
		val newNumbers = DoubleArray(size)

		for (i in 0 until size)
			newNumbers[i] = numbers[i]

		return newNumbers
	}

	/**
	 * Returns array of double values of the function f(i) calculated on the range: i = from to i = to by step = delta.
	 *
	 * @param f Expression
	 * @param index Argument
	 * @param from Double
	 * @param to Double
	 * @param delta Double
	 *
	 * @return DoubleArray
	 *
	 * @author Bas Milius
	 */
	fun getFunctionValues(f: Expression, index: Argument, from: Double, to: Double, delta: Double): DoubleArray
	{
		if (from.isNaN() || to.isNaN() || delta.isNaN() || delta == 0.0)
			return DoubleArray(0)

		var n = 0
		val values: DoubleArray?

		if (to >= from && delta > 0)
		{
			run {
				var i = from
				while (i < to)
				{
					n++
					i += delta
				}
			}

			n++
			values = DoubleArray(n)

			var j = 0
			var i = from

			while (i < to)
			{
				values[j] = getFunctionValue(f, index, i)
				j++
				i += delta
			}

			values[j] = getFunctionValue(f, index, to)
		}
		else if (to <= from && delta < 0)
		{
			run {
				var i = from
				while (i > to)
				{
					n++
					i += delta
				}
			}

			n++
			values = DoubleArray(n)

			var j = 0
			var i = from

			while (i > to)
			{
				values[j] = getFunctionValue(f, index, i)
				j++
				i += delta
			}

			values[j] = getFunctionValue(f, index, to)
		}
		else if (from == to)
		{
			n = 1
			values = DoubleArray(n)
			values[0] = getFunctionValue(f, index, from)
		}
		else
		{
			values = DoubleArray(0)
		}

		return values
	}

	/**
	 * Modifies random generator used by the ProbabilityDistributions class.
	 *
	 * @param randomGenerator Random
	 *
	 * @author Bas Milius
	 */
	fun setRandomGenerator(randomGenerator: Random)
	{
		ProbabilityDistributions.randomGenerator = randomGenerator
	}

	/**
	 * Sets comparison mode to EXACT.
	 *
	 * @author Bas Milius
	 */
	fun setExactComparison()
	{
		BinaryRelations.setExactComparison()
	}

	/**
	 * Sets comparison mode to EPSILON.
	 *
	 * @author Bas Milius
	 */
	fun setEpsilonComparison()
	{
		BinaryRelations.setEpsilonComparison()
	}

	/**
	 * Gets current epsilon value.
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun getEpsilon(): Double
	{
		return BinaryRelations.epsilon
	}

	/**
	 * Sets epsilon value.
	 *
	 * @param epsilon Double
	 *
	 * @author Bas Milius
	 */
	fun setEpsilon(epsilon: Double)
	{
		BinaryRelations.epsilon = epsilon
	}

	/**
	 * Sets default epsilon value.
	 *
	 * @author Bas Milius
	 */
	fun setDefaultEpsilon()
	{
		BinaryRelations.setDefaultEpsilon()
	}

	/**
	 * Checks if epsilon mode is active.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun checkIfEpsilonMode(): Boolean
	{
		return BinaryRelations.checkIfEpsilonMode()
	}

	/**
	 * Checks if exact mode is active.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun checkIfExactMode(): Boolean
	{
		return BinaryRelations.checkIfExactMode()
	}

	/**
	 * Double floating-point precision arithmetic causes rounding problems.
	 *
	 * @author Bas Milius
	 */
	fun enableUlpRounding()
	{
		ulpRounding = true
	}

	/**
	 * Double floating-point precision arithmetic causes rounding problems.
	 *
	 * @author Bas Milius
	 */
	fun disableUlpRounding()
	{
		ulpRounding = false
	}

	/**
	 * Double floating-point precision arithmetic causes rounding problems.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun checkIfUlpRounding(): Boolean
	{
		return ulpRounding
	}

	/**
	 * Gets max allowed recursion depth.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getMaxAllowedRecursionDepth(): Int
	{
		return MAX_RECURSION_CALLS
	}

	/**
	 * Sets max allowed recursion depth.
	 *
	 * @param maxAllowedRecursionDepth Int
	 *
	 * @author Bas Milius
	 */
	fun setMaxAllowedRecursionDepth(maxAllowedRecursionDepth: Int)
	{
		MAX_RECURSION_CALLS = maxAllowedRecursionDepth
	}

	/**
	 * Removes built-in tokens from the list of tokens recognized by the parsers. Precedure affects only tokens classified to built-in functions, built-in constants, built-in units, built-in random variables.
	 *
	 * @param tokens String[]
	 *
	 * @author Bas Milius
	 */
	fun removeBuiltinTokens(vararg tokens: String)
	{
		tokens
				.filter { it.isNotEmpty() && !tokensToRemove.contains(it) }
				.forEach { tokensToRemove.add(it) }

		optionsChangesetNumber++
	}

	/**
	 * Un-marks tokens previously marked to be removed.
	 *
	 * @param tokens String[]
	 *
	 * @author Bas Milius
	 */
	fun unremoveBuiltinTokens(vararg tokens: String)
	{
		if (tokens.isEmpty()) return
		if (tokensToRemove.isEmpty()) return

		for (token in tokens)
			tokensToRemove.remove(token)

		optionsChangesetNumber++
	}

	/**
	 * Un-marks all tokens previously marked to be removed.
	 *
	 * @author Bas Milius
	 */
	fun unremoveAllBuiltinTokens()
	{
		tokensToRemove.clear()
		optionsChangesetNumber++
	}

	/**
	 * Returns current list of tokens marked to be removed.
	 *
	 * @return StringArray
	 *
	 * @author Bas Milius
	 */
	fun getBuiltinTokensToRemove(): StringArray
	{
		val tokensToRemoveArray = arrayOfNulls<String>(tokensToRemove.size)

		for (i in 0 until tokensToRemove.size)
			tokensToRemoveArray[i] = tokensToRemove[i]

		return StringArray(tokensToRemoveArray.requireNoNulls())
	}

	/**
	 * Method to change definition of built-in token.
	 *
	 * @param currentToken String
	 * @param newToken String
	 * @param newTokenDescription String
	 *
	 * @author Bas Milius
	 */
	fun modifyBuiltinToken(currentToken: String, newToken: String, newTokenDescription: String = "")
	{
		if (currentToken.isEmpty()) return
		if (newToken.isEmpty()) return

		tokensToModify
				.filter { it.currentToken == currentToken }
				.forEach { return }

		tokensToModify.add(TokenModification(currentToken, newToken, newTokenDescription))
		optionsChangesetNumber++
	}

	/**
	 * Un-marks tokens previously marked to be modified.
	 *
	 * @param currentOrNewTokens String[]
	 *
	 * @author Bas Milius
	 */
	fun unmodifyBuiltinTokens(vararg currentOrNewTokens: String)
	{
		if (currentOrNewTokens.isEmpty()) return
		if (tokensToModify.isEmpty()) return

		val toRemove = ArrayList<TokenModification>()

		currentOrNewTokens
				.filter { it.isNotEmpty() }
				.forEach { token -> tokensToModify.filterTo(toRemove) { token == it.currentToken || token == it.newToken } }

		for (tm in toRemove)
			tokensToModify.remove(tm)

		optionsChangesetNumber++
	}

	/**
	 * Un-marks all tokens previously marked to be modified.
	 *
	 * @author Bas Milius
	 */
	fun unmodifyAllBuiltinTokens()
	{
		tokensToModify.clear()
		optionsChangesetNumber++
	}

	/**
	 * Return details on tokens marked to be modified.
	 *
	 * @return Array<Array<String?>>
	 *
	 * @author Bas Milius
	 */
	fun getBuiltinTokensToModify(): Array<Array<String?>>
	{
		val tokensNum = tokensToModify.size
		val tokensToModifyArray = Array<Array<String?>>(tokensNum) { arrayOfNulls(3) }

		for (i in 0 until tokensNum)
		{
			val (currentToken, newToken, newTokenDescription) = tokensToModify[i]
			tokensToModifyArray[i][0] = currentToken
			tokensToModifyArray[i][1] = newToken
			tokensToModifyArray[i][2] = newTokenDescription
		}

		return tokensToModifyArray
	}

	/**
	 * Sets MathParser to override built-in tokens.
	 *
	 * @author Bas Milius
	 */
	fun setToOverrideBuiltinTokens()
	{
		overrideBuiltinTokens = true
		optionsChangesetNumber++
	}

	/**
	 * Sets MathParser not to override built-in tokens.
	 *
	 * @author Bas Milius
	 */
	fun setNotToOverrideBuiltinTokens()
	{
		overrideBuiltinTokens = false
		optionsChangesetNumber++
	}

	/**
	 * Checks whether MathParser is set to override built-in tokens.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun checkIfsetToUserrideBuiltinTokens(): Boolean
	{
		return overrideBuiltinTokens
	}

	/**
	 * Returns token type description.
	 *
	 * @param tokenTypeId Int
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getTokenTypeDescription(tokenTypeId: Int): String
	{
		return when (tokenTypeId)
		{
			ParserSymbol.TYPE_ID -> ParserSymbol.TYPE_DESC
			ParserSymbol.NUMBER_TYPE_ID -> "number"
			Operator.TYPE_ID -> Operator.TYPE_DESC
			BooleanOperator.TYPE_ID -> BooleanOperator.TYPE_DESC
			BinaryRelation.TYPE_ID -> BinaryRelation.TYPE_DESC
			Function1Arg.TYPE_ID -> Function1Arg.TYPE_DESC
			Function2Arg.TYPE_ID -> Function2Arg.TYPE_DESC
			Function3Arg.TYPE_ID -> Function3Arg.TYPE_DESC
			FunctionVariadic.TYPE_ID -> FunctionVariadic.TYPE_DESC
			CalculusOperator.TYPE_ID -> CalculusOperator.TYPE_DESC
			RandomVariable.TYPE_ID -> RandomVariable.TYPE_DESC
			ConstantValue.TYPE_ID -> ConstantValue.TYPE_DESC
			Argument.TYPE_ID -> Argument.TYPE_DESC
			RecursiveArgument.TYPE_ID_RECURSIVE -> RecursiveArgument.TYPE_DESC_RECURSIVE
			Function.TYPE_ID -> Function.TYPE_DESC
			Constant.TYPE_ID -> Constant.TYPE_DESC
			Unit.TYPE_ID -> Unit.TYPE_DESC
			BitwiseOperator.TYPE_ID -> BitwiseOperator.TYPE_DESC
			else -> ""
		}
	}

	/**
	 * Converts integer number to hex string (plain text).
	 *
	 * @param number Int
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun numberToHexString(number: Int): String
	{
		return Integer.toHexString(number)
	}

	/**
	 * Converts long number to hex string (plain text).
	 *
	 * @param number Long
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun numberToHexString(number: Long): String
	{
		// ewwww java D:
		return java.lang.Long.toHexString(number)
	}

	/**
	 * Converts (long)double number to hex string (plain text).
	 *
	 * @param number Double
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun numberToHexString(number: Double): String
	{
		return numberToHexString(number.toLong())
	}

	/**
	 * Converts hex string into ASCII string, where each letter is represented by two hex digits (byte) from the hex string.
	 *
	 * @param hexString String
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun hexString2AsciiString(hexString: String): String
	{
		var hexByteStr: String
		var hexByteInt: Int
		var asciiString = ""
		var i = 0

		while (i < hexString.length)
		{
			hexByteStr = hexString.substring(i, i + 2)
			hexByteInt = Integer.parseInt(hexByteStr, 16)
			asciiString += hexByteInt.toChar()
			i += 2
		}

		return asciiString
	}

	/**
	 * Converts number into ASCII string, where each letter is represented by two hex digits (byte) from the hex representation of the original number
	 *
	 * @param number Int
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun numberToAsciiString(number: Int): String
	{
		return hexString2AsciiString(numberToHexString(number))
	}

	/**
	 * Converts number into ASCII string, where each letter is represented by two hex digits (byte) from the hex representation of the original number
	 *
	 * @param number Long
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun numberToAsciiString(number: Long): String
	{
		return hexString2AsciiString(numberToHexString(number))
	}

	/**
	 * Converts (long)double number into ASCII string, where each letter is represented by two hex digits (byte) from the hex representation of the original number casted to long type.
	 *
	 * @param number Double
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun numberToAsciiString(number: Double): String
	{
		return hexString2AsciiString(numberToHexString(number))
	}

	/**
	 * Other base (base between 1 and 36) number literal conversion to decimal number.
	 *
	 * @param numberLiteral String
	 * @param numeralSystemBase Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(numberLiteral: String, numeralSystemBase: Int): Double
	{
		return NumberTheory.convOthBase2Decimal(numberLiteral, numeralSystemBase)
	}

	/**
	 * Other base (base between 1 and 36) number literal conversion to decimal number. Base specification included in number literal.
	 *
	 * @param numberLiteral String
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(numberLiteral: String): Double
	{
		return NumberTheory.convOthBase2Decimal(numberLiteral)
	}

	/**
	 * Other base to decimal conversion.
	 *
	 * @param numeralSystemBase Int
	 * @param digits Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(numeralSystemBase: Int, vararg digits: Int): Double
	{
		return NumberTheory.convOthBase2Decimal(numeralSystemBase, *digits)
	}

	/**
	 * Other base to decimal conversion.
	 *
	 * @param numeralSystemBase Double
	 * @param digits Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun convOthBase2Decimal(numeralSystemBase: Double, vararg digits: Double): Double
	{
		return NumberTheory.convOthBase2Decimal(numeralSystemBase, *digits)
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
		return NumberTheory.convDecimal2OthBase(decimalNumber, numeralSystemBase)
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
		return NumberTheory.convDecimal2OthBase(decimalNumber, numeralSystemBase, format)
	}

	/**
	 * Prints object.toString to the Console + new line.
	 *
	 * @param o Any
	 *
	 * @author Bas Milius
	 */
	fun consolePrintln(o: Any)
	{
		if (CONSOLE_ROW_NUMBER == 1 && CONSOLE_OUTPUT == "")
		{
			print(CONSOLE_PREFIX)
			CONSOLE_OUTPUT = CONSOLE_PREFIX
		}

		println(o)
		CONSOLE_ROW_NUMBER++
		print(CONSOLE_PREFIX)
		CONSOLE_OUTPUT = CONSOLE_OUTPUT + o + "\n" + CONSOLE_OUTPUT_PREFIX
	}

	/**
	 * Prints array of strings.
	 *
	 * @param stringArray Array<String>?
	 *
	 * @author Bas Milius
	 */
	fun consolePrintln(stringArray: Array<String>?)
	{
		if (stringArray == null)
		{
			consolePrintln("null")
			return
		}

		for (s in stringArray)
			consolePrintln(s)
	}

	/**
	 * Prints new line to the Console, no new line.
	 *
	 * @author Bas Milius
	 */
	fun consolePrintln()
	{
		if (CONSOLE_ROW_NUMBER == 1 && CONSOLE_OUTPUT == "")
		{
			print(CONSOLE_PREFIX)
			CONSOLE_OUTPUT = CONSOLE_PREFIX
		}

		println()
		CONSOLE_ROW_NUMBER++
		print(CONSOLE_PREFIX)
		CONSOLE_OUTPUT = CONSOLE_OUTPUT + "\n" + CONSOLE_OUTPUT_PREFIX
	}

	/**
	 * Prints object.toString to the Console.
	 *
	 * @param o Any
	 *
	 * @author Bas Milius
	 */
	fun consolePrint(o: Any)
	{
		if (CONSOLE_ROW_NUMBER == 1 && CONSOLE_OUTPUT == "")
		{
			print(CONSOLE_PREFIX)
			CONSOLE_OUTPUT = CONSOLE_PREFIX
		}

		print(o)
		CONSOLE_OUTPUT += o
	}

	/**
	 * Resets console output string, console output string is being built by consolePrintln(), consolePrint().
	 *
	 * @author Bas Milius
	 */
	fun resetConsoleOutput()
	{
		CONSOLE_OUTPUT = ""
		CONSOLE_ROW_NUMBER = 1
	}

	/**
	 * Sets default console prefix.
	 *
	 * @author Bas Milius
	 */
	fun setDefaultConsolePrefix()
	{
		CONSOLE_PREFIX = "[MathParser-v.${VERSION}] "
	}

	/**
	 * Sets default console output string prefix.
	 *
	 * @author Bas Milius
	 */
	fun setDefaultConsoleOutputPrefix()
	{
		CONSOLE_OUTPUT_PREFIX = "[MathParser-v${VERSION}] "
	}

	/**
	 * Sets console prefix.
	 *
	 * @param consolePrefix String
	 *
	 * @author Bas Milius
	 */
	fun setConsolePrefix(consolePrefix: String)
	{
		CONSOLE_PREFIX = consolePrefix
	}

	/**
	 * Sets console output string prefix.
	 *
	 * @param consoleOutputPrefix String
	 *
	 * @author Bas Milius
	 */
	fun setConsoleOutputPrefix(consoleOutputPrefix: String)
	{
		CONSOLE_OUTPUT_PREFIX = consoleOutputPrefix
	}

	/**
	 * General MathParser expression help.
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getHelp(): String
	{
		return mathParserExpression.getHelp()
	}

	/**
	 * General MathParser expression help - in-line key word searching.
	 *
	 * @param word String
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getHelp(word: String): String
	{
		return mathParserExpression.getHelp(word)
	}

	/**
	 * Prints all help content.
	 *
	 * @author Bas Milius
	 */
	fun consolePrintHelp()
	{
		println(getHelp())
	}

	/**
	 * Prints filtered help content.
	 *
	 * @param word String
	 *
	 * @author Bas Milius
	 */
	fun consolePrintHelp(word: String)
	{
		println(getHelp(word))
	}

	/**
	 * Returns list of key words known to the parser.
	 *
	 * @return List<KeyWord>
	 *
	 * @author Bas Milius
	 */
	fun getKeyWords(): List<KeyWord>
	{
		return mathParserExpression.getKeyWords()
	}

	/**
	 * Returns list of key words known to the parser.
	 *
	 * @param query String
	 *
	 * @return List<KeyWord>
	 *
	 * @author Bas Milius
	 */
	fun getKeyWords(query: String): List<KeyWord>
	{
		return mathParserExpression.getKeyWords(query)
	}

	/**
	 * Function used to introduce some compatibility between JAVA and C# while regexp matching.
	 *
	 * @param str String
	 * @param pattern String
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun regexMatch(str: String, pattern: String): Boolean
	{
		return Pattern.matches(pattern, str)
	}

	/**
	 * Prints tokens to the console.
	 *
	 * @param tokens List<Token>
	 *
	 * @author Bas Milius
	 */
	fun consolePrintTokens(tokens: List<Token>)
	{
		Expression.showTokens(tokens)
	}

}
