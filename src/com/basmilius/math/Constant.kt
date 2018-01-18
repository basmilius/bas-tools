package com.basmilius.math

import com.basmilius.math.miscellaneous.HeadEqBody
import com.basmilius.math.parsertokens.ParserSymbol

/**
 * Class Constant
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math
 */
class Constant: PrimitiveElement
{

	/**
	 * Companion Object Constant
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.math
	 */
	companion object
	{

		val TYPE_ID = 104
		val TYPE_DESC = "User defined constant"

		val NOT_FOUND = Expression.NOT_FOUND
		val NO_SYNTAX_ERRORS = Expression.NO_SYNTAX_ERRORS
		val SYNTAX_ERROR_OR_STATUS_UNKNOWN = Expression.SYNTAX_ERROR_OR_STATUS_UNKNOWN
		val NO_SYNTAX_ERROR_MSG = "Constant - no syntax errors."

	}

	var description = ""

	private var constantName = ""
	private var constantValue = 0.0
	private var relatedExpressionList: ArrayList<Expression> = arrayListOf()
	private var syntaxStatus = false
	private var errorMessage = ""

	/**
	 * Constant Constructor.
	 *
	 * @constructor
	 * @param constantName String
	 * @param constantValue Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor (constantName: String, constantValue: Double): super(TYPE_ID)
	{
		if (MathParser.regexMatch(constantName, ParserSymbol.nameOnlyTokenRegExp))
		{
			this.constantName = constantName
			this.constantValue = constantValue
			this.description = ""
			this.syntaxStatus = NO_SYNTAX_ERRORS
			this.errorMessage = NO_SYNTAX_ERROR_MSG
		}
		else
		{
			this.syntaxStatus = SYNTAX_ERROR_OR_STATUS_UNKNOWN
			this.errorMessage = "[$constantName] --> invalid constant name, pattern not matches: ${ParserSymbol.nameTokenRegExp}"
		}
	}

	/**
	 * Constant Constructor.
	 *
	 * @constructor
	 * @param constantName String
	 * @param constantValue Double
	 * @param description String
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor(constantName: String, constantValue: Double, description: String): this(constantName, constantValue)
	{
		this.description = description
	}

	/**
	 * Constant Constructor.
	 *
	 * @constructor
	 * @param constantDefinitionString String
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	constructor(constantDefinitionString: String, vararg elements: PrimitiveElement): super(TYPE_ID)
	{
		this.description = ""
		this.syntaxStatus = SYNTAX_ERROR_OR_STATUS_UNKNOWN

		if (MathParser.regexMatch(constantDefinitionString, ParserSymbol.constArgDefStrRegExp))
		{
			val headEqBody = HeadEqBody(constantDefinitionString)
			this.constantName = headEqBody.headTokens!![0].tokenStr

			val bodyExpression = Expression(headEqBody.bodyStr, *elements)
			this.constantValue = bodyExpression.calculate()
			this.syntaxStatus = bodyExpression.syntaxStatus
			this.errorMessage = bodyExpression.errorMessage
		}
		else
		{
			this.errorMessage = "[$constantDefinitionString] --> pattern not matches: ${ParserSymbol.constArgDefStrRegExp}"
		}
	}

	/**
	 * Gets the constant name.
	 *
	 * @return String
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getConstantName(): String
	{
		return this.constantName
	}

	/**
	 * Sets the constant name.
	 *
	 * @param constantName String
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun setConstantName(constantName: String)
	{
		if (MathParser.regexMatch(constantName, ParserSymbol.nameOnlyTokenRegExp))
		{
			this.constantName = constantName
			this.setExpressionModifiedFlags()
		}
		else
		{
			this.syntaxStatus = SYNTAX_ERROR_OR_STATUS_UNKNOWN
			this.errorMessage = "[$constantName] --> invalid constant name, pattern not matches: ${ParserSymbol.nameTokenRegExp}"
		}
	}

	/**
	 * Gets the constant value.
	 *
	 * @return Double
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getConstantValue(): Double
	{
		return this.constantValue
	}

	/**
	 * Gets error message.
	 *
	 * @return String
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getErrorMessage(): String
	{
		return this.errorMessage
	}

	/**
	 * Gets syntax status of the expression.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getSyntaxStatus(): Boolean
	{
		return this.syntaxStatus
	}

	/**
	 * Adds related expression.
	 *
	 * @param expression Expression?
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun addRelatedExpression(expression: Expression?)
	{
		if (expression != null && !this.relatedExpressionList.contains(expression))
			this.relatedExpressionList.add(expression)
	}

	/**
	 * Removes related expression.
	 *
	 * @param expression Expression?
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun removeRelatedExpression(expression: Expression?)
	{
		if (expression != null)
			this.relatedExpressionList.remove(expression)
	}

	/**
	 * Sets expression modified flag to each related expression.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun setExpressionModifiedFlags()
	{
		for (expression in this.relatedExpressionList)
			expression.setExpressionModifiedFlag()
	}

}
