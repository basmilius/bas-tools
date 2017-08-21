package com.basmilius.math

import com.basmilius.math.miscellaneous.HeadEqBody
import com.basmilius.math.parsertokens.ParserSymbol

/**
 * Class Argument
 *
 * @author Bas Milius
 * @package com.basmilius.math
 */
open class Argument: PrimitiveElement
{

	/**
	 * Companion Object Argument
	 *
	 * @author Bas Milius
	 * @package com.basmilius.math
	 */
	companion object
	{

		val TYPE_ID = 101
		val TYPE_DESC = "User defined argument"

		val NOT_FOUND = Expression.NOT_FOUND
		val NO_SYNTAX_ERRORS = Expression.NO_SYNTAX_ERRORS
		val SYNTAX_ERROR_OR_STATUS_UNKNOWN = Expression.SYNTAX_ERROR_OR_STATUS_UNKNOWN
		val ARGUMENT_INITIAL_VALUE = Double.NaN
		val FREE_ARGUMENT = 1
		val DEPENDENT_ARGUMENT = 2
		val RECURSIVE_ARGUMENT = 3

	}

	var description = ""
	var argumentType = 0

	var argumentExpression: Expression? = null
		protected set

	protected var n: Argument? = null

	private var pr_argumentName: String = ""
	private var pr_argumentValue: Double = 0.0

	/**
	 * Gets or Sets the argument name.
	 *
	 * @property
	 * @type String
	 *
	 * @author Bas Milius
	 */
	var argumentName: String
		get() = this.pr_argumentName
		protected set(value)
		{
			if (MathParser.regexMatch(value, ParserSymbol.nameOnlyTokenRegExp))
			{
				this.pr_argumentName = value
				this.setExpressionModifiedFlags()
			}
			else if (this.argumentExpression != null)
			{
				this.argumentExpression?.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$argumentName] Invalid argument name, pattern not matches: ${ParserSymbol.nameOnlyTokenRegExp}")
			}
		}


	/**
	 * Gets or Sets the argument value.
	 */
	var argumentValue: Double
		get() = if (this.argumentType == FREE_ARGUMENT) this.pr_argumentValue else this.argumentExpression?.calculate() ?: 0.0
		set(value)
		{
			this.pr_argumentValue = value
		}

	/**
	 * Argument Constructor.
	 *
	 * @constructor
	 * @param argumentDefinitionString String
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	constructor(argumentDefinitionString: String, vararg elements: PrimitiveElement): super(TYPE_ID)
	{
		if (MathParser.regexMatch(argumentDefinitionString, ParserSymbol.nameOnlyTokenRegExp))
		{
			this.argumentName = argumentDefinitionString
			this.argumentValue = ARGUMENT_INITIAL_VALUE
			this.argumentType = FREE_ARGUMENT
			this.argumentExpression = Expression(*elements)
		}
		else if (MathParser.regexMatch(argumentDefinitionString, ParserSymbol.constArgDefStrRegExp))
		{
			val headEqBody = HeadEqBody(argumentDefinitionString)
			this.argumentName = headEqBody.headTokens!![0].tokenStr

			val bodyExpression = Expression(headEqBody.bodyStr)
			val bodyValue = bodyExpression.calculate()

			if (bodyExpression.syntaxStatus == Expression.NO_SYNTAX_ERRORS && !bodyValue.isNaN())
			{
				this.argumentExpression = Expression()
				this.argumentValue = bodyValue
				this.argumentType = FREE_ARGUMENT
			}
			else
			{
				this.argumentExpression = bodyExpression
				this.addDefinitions(*elements)
				this.argumentType = DEPENDENT_ARGUMENT
			}
		}
		else if (MathParser.regexMatch(argumentDefinitionString, ParserSymbol.functionDefStrRegExp))
		{
			val headEqBody = HeadEqBody(argumentDefinitionString)
			this.argumentName = headEqBody.headTokens!![0].tokenStr
			this.argumentExpression = Expression(headEqBody.bodyStr, *elements)
			this.argumentExpression?.description = headEqBody.headStr
			this.argumentValue = ARGUMENT_INITIAL_VALUE
			this.argumentType = DEPENDENT_ARGUMENT
			this.n = Argument(headEqBody.headTokens!![2].tokenStr)
		}
		else
		{
			this.argumentValue = ARGUMENT_INITIAL_VALUE
			this.argumentType = FREE_ARGUMENT
			this.argumentExpression = Expression()
			this.argumentExpression?.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$argumentDefinitionString] Invalid argument definition (patterns: 'x', 'x=5', 'x=5+3/2', 'z=2*y').")
		}

		this.setSilentMode()
		this.description = ""
	}

	/**
	 * Argument Constructor.
	 *
	 * @constructor
	 * @param argumentName String
	 * @param argumentValue Double
	 *
	 * @author Bas Milius
	 */
	constructor(argumentName: String, argumentValue: Double): super(TYPE_ID)
	{
		this.argumentExpression = Expression()

		if (MathParser.regexMatch(argumentName, ParserSymbol.nameOnlyTokenRegExp))
		{
			this.argumentName = argumentName
			this.argumentValue = argumentValue
			this.argumentType = FREE_ARGUMENT
		}
		else
		{
			this.argumentValue = ARGUMENT_INITIAL_VALUE
			this.argumentExpression?.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$argumentName] Invalid argument name, pattern not matches: ${ParserSymbol.nameOnlyTokenRegExp}")
		}

		this.setSilentMode()
		this.description = ""
	}

	/**
	 * Argument Constructor.
	 *
	 * @constructor
	 * @param argumentName String
	 * @param argumentExpressionString String
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	constructor(argumentName: String, argumentExpressionString: String, vararg elements: PrimitiveElement): super(TYPE_ID)
	{
		if (MathParser.regexMatch(argumentName, ParserSymbol.nameOnlyTokenRegExp))
		{
			this.argumentName = argumentName
			this.argumentValue = ARGUMENT_INITIAL_VALUE
			this.argumentExpression = Expression(argumentExpressionString, *elements)
			this.argumentExpression?.description = argumentName
			this.argumentType = DEPENDENT_ARGUMENT
		}
		else
		{
			this.argumentValue = ARGUMENT_INITIAL_VALUE
			this.argumentExpression = Expression()
			this.argumentExpression?.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$argumentName] Invalid argument name, pattern not matches: ${ParserSymbol.nameOnlyTokenRegExp}")
		}

		this.setSilentMode()
		this.description = ""
	}

	/**
	 * Enables argument verbose mode.
	 *
	 * @author Bas Milius
	 */
	fun setVerboseMode()
	{
		this.argumentExpression?.setVerboseMode()
	}

	/**
	 * Disables argument verbose mode (sets default silent mode).
	 *
	 * @author Bas Milius
	 */
	fun setSilentMode()
	{
		this.argumentExpression?.setSilentMode()
	}

	/**
	 * Gets verbose mode status.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun getVerboseMode(): Boolean
	{
		return this.argumentExpression?.verboseMode ?: false
	}

	/**
	 * Gets recursive mode status.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun getRecursiveMode(): Boolean
	{
		return this.argumentExpression?.recursiveMode ?: false
	}

	/**
	 * Gets computing time.
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun getComputingTime(): Double
	{
		return this.argumentExpression?.computingTime ?: 0.0
	}

	/**
	 * Gets argument expression string.
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getArgumentExpressionString(): String
	{
		return this.argumentExpression?.expressionString ?: ""
	}

	/**
	 * Sets argument expression string. Each expression / function / dependent argument associated with this argument will be marked as modified (requires new syntax checking).
	 *
	 * @param argumentExpressionString String
	 *
	 * @author Bas Milius
	 */
	fun setArgumentExpressionString(argumentExpressionString: String)
	{
		this.argumentExpression?.expressionString = argumentExpressionString

		if (this.argumentType == FREE_ARGUMENT)
		{
			this.argumentType = DEPENDENT_ARGUMENT
		}
	}

	/**
	 * Adds user defined elements (such as: Arguments, Constants, Functions) to the argument expressions.
	 *
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	fun addDefinitions(vararg elements: PrimitiveElement)
	{
		this.argumentExpression?.addDefinitions(*elements)
	}

	/**
	 * Removes user defined elements (such as: Arguments, Constants, Functions) from the argument expressions.
	 *
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	fun removeDefinitions(vararg elements: PrimitiveElement)
	{
		this.argumentExpression?.removeDefinitions(*elements)
	}

	/**
	 * Adds arguments (variadic) to the argument expression definition.
	 *
	 * @param arguments Argument[]
	 *
	 * @author Bas Milius
	 */
	fun addArguments(vararg arguments: Argument)
	{
		this.argumentExpression?.addArguments(*arguments)
	}

	/**
	 * Enables to define the arguments (associated with the argument expression) based on the given arguments names.
	 *
	 * @param argumentsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun defineArguments(vararg argumentsNames: String)
	{
		this.argumentExpression?.defineArguments(*argumentsNames)
	}

	/**
	 * Enables to define the argument (associated with the argument expression) based on the argument name and the argument value.
	 *
	 * @param argumentName String
	 * @param argumentValue Double
	 *
	 * @author Bas Milius
	 */
	fun defineArgument(argumentName: String, argumentValue: Double)
	{
		this.argumentExpression?.defineArgument(argumentName, argumentValue)
	}

	/**
	 * Gets argument index from the argument expression.
	 *
	 * @param argumentName String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getArgumentIndex(argumentName: String): Int
	{
		return this.argumentExpression?.getArgumentIndex(argumentName) ?: -1
	}

	/**
	 * Gets argument from the argument expression.
	 *
	 * @param argumentName String
	 *
	 * @return Argument?
	 *
	 * @author Bas Milius
	 */
	fun getArgument(argumentName: String): Argument?
	{
		return this.argumentExpression?.getArgument(argumentName)
	}

	/**
	 * Gets argument from the argument expression.
	 *
	 * @param argumentIndex Int
	 *
	 * @return Argument?
	 *
	 * @author Bas Milius
	 */
	fun getArgument(argumentIndex: Int): Argument?
	{
		return this.argumentExpression?.getArgument(argumentIndex)
	}

	/**
	 * Gets number of arguments associated with the argument expression.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getArgumentsNumber(): Int
	{
		return this.argumentExpression?.getArgumentsNumber() ?: -1
	}

	/**
	 * Removes first occurrences of the arguments associated with the argument expression.
	 *
	 * @param argumentsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun removeArguments(vararg argumentsNames: String)
	{
		this.argumentExpression?.removeArguments(*argumentsNames)
	}

	/**
	 * Removes first occurrences of the arguments associated with the argument expression.
	 *
	 * @param arguments Argument[]
	 *
	 * @author Bas Milius
	 */
	fun removeArguments(vararg arguments: Argument)
	{
		this.argumentExpression?.removeArguments(*arguments)
	}

	/**
	 * Removes all arguments associated with the argument expression.
	 *
	 * @author Bas Milius
	 */
	fun removeAllArguments()
	{
		this.argumentExpression?.removeAllArguments()
	}

	/**
	 * Adds constants (variadic parameters) to the argument expression definition.
	 *
	 * @param constants Constant[]
	 *
	 * @author Bas Milius
	 */
	fun addConstants(vararg constants: Constant)
	{
		this.argumentExpression?.addConstants(*constants)
	}

	/**
	 * Adds constants to the argument expression definition.
	 *
	 * @param constantsList List<Constant>
	 *
	 * @author Bas Milius
	 */
	fun addConstants(constantsList: List<Constant>)
	{
		this.argumentExpression?.addConstants(constantsList)
	}

	/**
	 * Enables to define the constant (associated with the argument expression) based on the constant name and constant value.
	 *
	 * @param constantName String
	 * @param constantValue Double
	 *
	 * @author Bas Milius
	 */
	fun defineConstant(constantName: String, constantValue: Double)
	{
		this.argumentExpression?.defineConstant(constantName, constantValue)
	}

	/**
	 * Gets constant index associated with the argument expression.
	 *
	 * @param constantName String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getConstantIndex(constantName: String): Int
	{
		return this.argumentExpression?.getConstantIndex(constantName) ?: -1
	}

	/**
	 * Gets constant associated with the argument expression.
	 *
	 * @param constantName String
	 *
	 * @return Constant?
	 *
	 * @author Bas Milius
	 */
	fun getConstant(constantName: String): Constant?
	{
		return this.argumentExpression?.getConstant(constantName)
	}

	/**
	 * Gets constant associated with the argument expression.
	 *
	 * @param constantIndex Int
	 *
	 * @return Constant?
	 *
	 * @author Bas Milius
	 */
	fun getConstant(constantIndex: Int): Constant?
	{
		return this.argumentExpression?.getConstant(constantIndex)
	}

	/**
	 * Gets number of constants associated with the argument expression.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getConstantsNumber(): Int
	{
		return this.argumentExpression?.getConstantsNumber() ?: -1
	}

	/**
	 * Removes first occurrences of the constants associated with the argument expression.
	 *
	 * @param constantsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun removeConstants(vararg constantsNames: String)
	{
		this.argumentExpression?.removeConstants(*constantsNames)
	}

	/**
	 * Removes first occurrences of the constants associated with the argument expression
	 *
	 * @param constants Constant[]
	 *
	 * @author Bas Milius
	 */
	fun removeConstants(vararg constants: Constant)
	{
		this.argumentExpression?.removeConstants(*constants)
	}

	/**
	 * Removes all constants associated with the argument expression
	 *
	 * @author Bas Milius
	 */
	fun removeAllConstants()
	{
		this.argumentExpression?.removeAllConstants()
	}

	/**
	 * Adds functions (variadic parameters) to the argument expression definition.
	 *
	 * @param functions Function[]
	 *
	 * @author Bas Milius
	 */
	fun addFunctions(vararg functions: Function)
	{
		this.argumentExpression?.addFunctions(*functions)
	}

	/**
	 * Enables to define the function (associated with the argument expression) based on the function name, function expression string and arguments names (variadic parameters).
	 *
	 * @param functionName String
	 * @param functionExpressionString String
	 * @param argumentsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun defineFunction(functionName: String, functionExpressionString: String, vararg argumentsNames: String)
	{
		this.argumentExpression?.defineFunction(functionName, functionExpressionString, *argumentsNames)
	}

	/**
	 * Gets index of function associated with the argument expression.
	 *
	 * @param functionName String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getFunctionIndex(functionName: String): Int
	{
		return this.argumentExpression?.getFunctionIndex(functionName) ?: -1
	}

	/**
	 * Gets function associated with the argument expression.
	 *
	 * @param functionName String
	 *
	 * @return Function?
	 *
	 * @author Bas Milius
	 */
	fun getFunction(functionName: String): Function?
	{
		return this.argumentExpression?.getFunction(functionName)
	}

	/**
	 * Gets function associated with the argument expression.
	 *
	 * @param functionIndex Int
	 *
	 * @return Function?
	 *
	 * @author Bas Milius
	 */
	fun getFunction(functionIndex: Int): Function?
	{
		return this.argumentExpression?.getFunction(functionIndex)
	}

	/**
	 * Gets number of functions associated with the argument expression.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getFunctionsNumber(): Int
	{
		return this.argumentExpression?.getFunctionsNumber() ?: -1
	}

	/**
	 * Removes first occurrences of the functions associated with the argument expression.
	 *
	 * @param functionsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun removeFunctions(vararg functionsNames: String)
	{
		this.argumentExpression?.removeFunctions(*functionsNames)
	}

	/**
	 * Removes first occurrences of the functions associated with the argument expression.
	 *
	 * @param functions Function[]
	 *
	 * @author Bas Milius
	 */
	fun removeFunctions(vararg functions: Function)
	{
		this.argumentExpression?.removeFunctions(*functions)
	}

	/**
	 * Removes all functions associated with the argument expression.
	 *
	 * @author Bas Milius
	 */
	fun removeAllFunctions()
	{
		this.argumentExpression?.removeAllFunctions()
	}

	/**
	 * Adds related expression form the argumentExpression.
	 *
	 * @param expression Expression
	 *
	 * @author Bas Milius
	 */
	fun addRelatedExpression(expression: Expression)
	{
		this.argumentExpression?.addRelatedExpression(expression)
	}

	/**
	 * Removes related expression form the argumentExpression.
	 *
	 * @param expression Expression
	 *
	 * @author Bas Milius
	 */
	fun removeRelatedExpression(expression: Expression)
	{
		this.argumentExpression?.removeRelatedExpression(expression)
	}

	/**
	 * Sets expression was modified flag to all related expressions to the argumentExpression.
	 *
	 * @author Bas Milius
	 */
	fun setExpressionModifiedFlags()
	{
		this.argumentExpression?.setExpressionModifiedFlag()
	}

	/**
	 * Creates cloned object of this argument.
	 *
	 * @return Argument
	 *
	 * @author Bas Milius
	 */
	fun clone(): Argument
	{
		val newArg = Argument(this.argumentName)
		newArg.argumentExpression = this.argumentExpression
		newArg.argumentType = this.argumentType
		newArg.argumentValue = this.argumentValue
		newArg.description = this.description
		newArg.n = this.n

		return newArg
	}

}
