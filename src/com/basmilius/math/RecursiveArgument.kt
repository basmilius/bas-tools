/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.math

import com.basmilius.math.parsertokens.ParserSymbol

/**
 * Class RecursiveArgument
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math
 */
class RecursiveArgument: Argument
{

	/**
	 * Companion Object RecursiveArgument
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.math
	 */
	companion object
	{

		val TYPE_ID_RECURSIVE = 102
		val TYPE_DESC_RECURSIVE = "User defined recursive argument"

	}

	private var baseValues = ArrayList<Double>()
	private var recursiveCounter = 0
	private var startingIndex = 0

	/**
	 * RecursiveArgument Constructor.
	 *
	 * @param argumentName String
	 * @param recursiveExpressionString String
	 * @param indexName String
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor(argumentName: String, recursiveExpressionString: String, indexName: String): super(argumentName, recursiveExpressionString)
	{
		if (argumentName != this.argumentName)
			return

		this.argumentType = RECURSIVE_ARGUMENT
		this.baseValues = ArrayList()
		this.n = Argument(indexName)

		this.argumentExpression?.addArguments(super.n!!)
		this.argumentExpression?.addArguments(this)
		this.argumentExpression?.description = this.argumentName

		this.recursiveCounter = -1
	}

	/**
	 * RecursiveArgument Constructor.
	 *
	 * @param argumentName String
	 * @param recursiveExpressionString String
	 * @param n Argument
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor(argumentName: String, recursiveExpressionString: String, n: Argument, vararg elements: PrimitiveElement): super(argumentName, recursiveExpressionString)
	{
		if (argumentName != this.argumentName)
			return

		this.argumentType = RECURSIVE_ARGUMENT
		this.baseValues = ArrayList()
		this.n = n

		this.argumentExpression?.addArguments(super.n!!)
		this.argumentExpression?.addArguments(this)
		this.argumentExpression?.addDefinitions(*elements)
		this.argumentExpression?.description = this.argumentName

		this.recursiveCounter = -1
	}

	/**
	 * RecursiveArgument Constructor.
	 *
	 * @param argumentDefinitionString String
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor(argumentDefinitionString: String, vararg elements: PrimitiveElement): super(argumentDefinitionString)
	{
		if (MathParser.regexMatch(argumentDefinitionString, ParserSymbol.function1ArgDefStrRegExp))
		{
			this.argumentType = RECURSIVE_ARGUMENT
			this.baseValues = ArrayList()
			this.recursiveCounter = -1

			this.argumentExpression?.addArguments(super.n!!)
			this.argumentExpression?.addArguments(this)
			this.argumentExpression?.addDefinitions(*elements)
			this.argumentExpression?.description = argumentDefinitionString
		}
		else
		{
			this.argumentExpression = Expression()
			this.argumentExpression?.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$argumentDefinitionString] Invalid argument definition (patterns: f(n) = f(n-1) ... ).")
		}
	}

	/**
	 * Adds base case.
	 *
	 * @param index Int
	 * @param value Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private fun addBaseCase(index: Int, value: Double)
	{
		val recSize = this.baseValues.size

		if (index > recSize - 1)
		{
			for (i in recSize until index)
				baseValues.add(Double.NaN)

			baseValues.add(value)
		}
		else
		{
			this.baseValues[index] = value
		}
	}

	/**
	 * Clears all based cases and stored calculated values.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun resetAllCases()
	{
		this.baseValues.clear()
		this.recursiveCounter = -1
	}

	/**
	 * Gets recursive argument value.
	 *
	 * @param index Double
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getArgumentValue(index: Double): Double
	{
		if (this.recursiveCounter == -1)
			this.startingIndex = Math.round(index).toInt()

		val recSize = this.baseValues.size
		val idx = Math.round(index).toInt()

		this.recursiveCounter++

		if (this.recursiveCounter <= this.startingIndex && idx <= this.startingIndex)
		{
			if (idx in 0..(recSize - 1) && !this.baseValues[idx].isNaN())
			{
				this.recursiveCounter--

				return this.baseValues[idx]
			}
			else if (idx >= 0)
			{
				this.n?.argumentValue = idx.toDouble()

				val newExp = Expression(super.argumentExpression!!.expressionString, super.argumentExpression!!.argumentsList, super.argumentExpression!!.functionsList, super.argumentExpression!!.constantsList)
				newExp.description = super.argumentName

				if (super.getVerboseMode())
					newExp.setVerboseMode()

				val value = newExp.calculate()
				addBaseCase(idx, value)
				recursiveCounter--

				return value
			}
			else
			{
				recursiveCounter--

				return Double.NaN
			}
		}
		else
		{
			recursiveCounter--

			return Double.NaN
		}
	}

}
