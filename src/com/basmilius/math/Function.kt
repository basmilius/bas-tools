package com.basmilius.math

import com.basmilius.math.miscellaneous.HeadEqBody
import com.basmilius.math.parsertokens.ParserSymbol

/**
 * Class Function
 *
 * @author Bas Milius
 * @package com.basmilius.math
 */
class Function: PrimitiveElement
{

	/**
	 * Companion Object Function
	 *
	 * @author Bas Milius
	 * @package com.basmilius.math
	 */
	companion object
	{

		val TYPE_ID = 103
		val TYPE_DESC = "User defined function"

		val NOT_FOUND = Expression.NOT_FOUND
		val NO_SYNTAX_ERRORS = Expression.NO_SYNTAX_ERRORS
		val SYNTAX_ERROR_OR_STATUS_UNKNOWN = Expression.SYNTAX_ERROR_OR_STATUS_UNKNOWN
		val BODY_RUNTIME = 1
		val BODY_EXTENDED = 2

	}

	var functionExpression: Expression?

	var functionBodyType: Int = BODY_RUNTIME
	var description: String = ""
	var parametersNumber: Int
	private var functionExtension: FunctionExtension? = null

	/**
	 * Gets or Sets the funciton name.
	 *
	 * @property
	 * @type String
	 *
	 * @author Bas Milius
	 */
	var functionName: String
		get() = this.pr_functionName
		set(value)
		{
			if (MathParser.regexMatch(value, ParserSymbol.nameOnlyTokenRegExp))
			{
				this.pr_functionName = value
				this.setExpressionModifiedFlags()
			}
			else
			{
				this.functionExpression!!.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$value] Invalid function name, pattern not matches: ${ParserSymbol.nameTokenRegExp}")
			}
		}

	private var pr_functionName = ""

	/**
	 * Function Constructor.
	 *
	 * @constructor
	 * @param functionName String
	 * @param functionExpressionString String
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	constructor(functionName: String, functionExpressionString: String, vararg elements: PrimitiveElement): super(TYPE_ID)
	{
		if (MathParser.regexMatch(functionName, ParserSymbol.nameOnlyTokenRegExp))
		{
			this.functionName = functionName
			this.functionExpression = Expression(functionExpressionString, *elements)
			this.functionExpression!!.description = this.functionName
			this.parametersNumber = 0
			this.description = ""
			this.functionBodyType = BODY_RUNTIME
			this.addFunctions(this)
		}
		else
		{
			this.parametersNumber = 0
			this.description = ""
			this.functionExpression = Expression("")
			this.functionExpression!!.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$functionName] Invalid function name, pattern not matches: ${ParserSymbol.nameTokenRegExp}")
		}
	}

	/**
	 * Function Constructor.
	 *
	 * @constructor
	 * @param functionName String
	 * @param functionExpressionString String
	 * @param argumentsNames String[]
	 *
	 * @author Bas Milius
	 */
	constructor(functionName: String, functionExpressionString: String, vararg argumentsNames: String): super(TYPE_ID)
	{
		if (MathParser.regexMatch(functionName, ParserSymbol.nameOnlyTokenRegExp))
		{
			this.functionName = functionName
			this.functionExpression = Expression(functionExpressionString)
			this.functionExpression!!.description = functionName

			for (argName in argumentsNames)
				this.functionExpression!!.addArguments(Argument(argName))

			this.parametersNumber = this.functionExpression!!.getArgumentsNumber() - this.countRecursiveArguments()
			this.description = ""
			this.functionBodyType = BODY_RUNTIME
			this.addFunctions(this)
		}
		else
		{
			this.parametersNumber = 0
			this.description = ""
			this.functionExpression = Expression("")
			this.functionExpression!!.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$functionName] Invalid function name, pattern not matches: ${ParserSymbol.nameTokenRegExp}")
		}
	}

	/**
	 * Function Constructor.
	 *
	 * @constructor
	 * @param functionDefinitionString String
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	constructor(functionDefinitionString: String, vararg elements: PrimitiveElement): super(TYPE_ID)
	{
		this.parametersNumber = 0

		if (MathParser.regexMatch(functionDefinitionString, ParserSymbol.functionDefStrRegExp))
		{
			val headEqBody = HeadEqBody(functionDefinitionString)
			this.functionName = headEqBody.headTokens!![0].tokenStr
			this.functionExpression = Expression(headEqBody.bodyStr, *elements)
			this.functionExpression!!.description = headEqBody.headStr

			if (headEqBody.headTokens!!.size > 1)
			{
				(1 until headEqBody.headTokens!!.size)
						.map { headEqBody.headTokens!![it] }
						.filter { it.tokenTypeId != ParserSymbol.TYPE_ID }
						.forEach { this.functionExpression!!.addArguments(Argument(it.tokenStr)) }
			}

			this.parametersNumber = this.functionExpression!!.getArgumentsNumber() - this.countRecursiveArguments()
			this.description = ""
			this.functionBodyType = BODY_RUNTIME
			this.addFunctions(this)
		}
		else
		{
			this.functionExpression = Expression()
			this.functionExpression!!.description = functionDefinitionString
			this.functionExpression!!.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$functionDefinitionString] --> pattern not matches: f(x1,...,xn) = ... reg exp: ${ParserSymbol.functionDefStrRegExp}")
		}
	}

	/**
	 * Function Constructor.
	 *
	 * @constructor
	 * @param functionName String
	 * @param functionExtension FunctionExtension
	 *
	 * @author Bas Milius
	 */
	constructor(functionName: String, functionExtension: FunctionExtension): super(TYPE_ID)
	{
		if (MathParser.regexMatch(functionName, ParserSymbol.nameOnlyTokenRegExp))
		{
			this.functionName = functionName
			this.functionExpression = Expression("{body-ext}")
			this.parametersNumber = functionExtension.getParametersNumber()
			this.description = ""
			this.functionExtension = functionExtension
			this.functionBodyType = BODY_EXTENDED
		}
		else
		{
			this.parametersNumber = 0
			this.description = ""
			this.functionExpression = Expression("")
			this.functionExpression!!.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$functionName] Invalid function name, pattern not matches: ${ParserSymbol.nameTokenRegExp}")
		}
	}

	/**
	 * Function Constructor.
	 *
	 * @constructor
	 * @param function Function
	 *
	 * @author Bas Milius
	 */
	constructor(function: Function): super(TYPE_ID)
	{
		this.functionName = function.functionName
		this.description = function.description
		this.parametersNumber = function.parametersNumber
		this.functionExpression = function.functionExpression
		this.functionBodyType = function.functionBodyType

		if (this.functionBodyType == BODY_EXTENDED)
			this.functionExtension = function.functionExtension!!.clone()
	}

	/**
	 * Gets the function expression string.
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getFunctionExpressionString(): String
	{
		if (this.functionExpression != null)
			return this.functionExpression!!.expressionString

		return ""
	}

	/**
	 * Sets value of function argument (function parameter).
	 *
	 * @param argumentIndex Int
	 * @param argumentValue Double
	 *
	 * @author Bas Milius
	 */
	fun setArgumentValue(argumentIndex: Int, argumentValue: Double)
	{
		if (this.functionBodyType == BODY_RUNTIME)
		{
			this.functionExpression!!.argumentsList[argumentIndex].argumentValue = argumentValue
		}
		else
		{
			this.functionExtension!!.setParameterValue(argumentIndex, argumentValue)
		}
	}

	/**
	 * Checks function syntax.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun checkSyntax(): Boolean
	{
		val syntaxStatus = NO_SYNTAX_ERRORS

		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression!!.checkSyntax()

		this.checkRecursiveMode()

		return syntaxStatus
	}

	/**
	 * Returns the error message after checking the syntax.
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getErrorMessage(): String
	{
		return this.functionExpression!!.errorMessage
	}

	/**
	 * Clone method.
	 *
	 * @return Function
	 *
	 * @author Bas Milius
	 */
	fun clone(): Function
	{
		return Function(this)
	}

	/**
	 * Calculates function value.
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun calculate(): Double
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.calculate() else this.functionExtension!!.calculate()
	}

	/**
	 * Calculates function value.
	 *
	 * @param params Double[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun calculate(vararg params: Double): Double
	{
		if (params.size == this.parametersNumber)
		{
			if (this.functionBodyType == BODY_RUNTIME)
			{
				for (p in params.indices)
					this.setArgumentValue(p, params[p])
			}
			else
			{
				for (p in params.indices)
					this.functionExtension!!.setParameterValue(p, params[p])
			}

			return calculate()
		}
		else
		{
			this.functionExpression!!.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$functionName] incorrect number of function parameters (expecting $parametersNumber, provided ${params.size})!")
		}

		return Double.NaN
	}

	/**
	 * Calculates function value.
	 *
	 * @param arguments Argument[]
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun calculate(vararg arguments: Argument): Double
	{
		if (arguments.size == this.parametersNumber)
		{
			if (this.functionBodyType == BODY_RUNTIME)
			{
				for (p in arguments.indices)
					this.setArgumentValue(p, arguments[p].argumentValue)
			}
			else
			{
				for (p in arguments.indices)
					this.functionExtension!!.setParameterValue(p, arguments[p].argumentValue)
			}

			return calculate()
		}
		else
		{
			this.functionExpression!!.setSyntaxStatus(SYNTAX_ERROR_OR_STATUS_UNKNOWN, "[$functionName] incorrect number of function parameters (expecting $parametersNumber, provided ${arguments.size})!")
		}

		return Double.NaN
	}

	/**
	 * Adds user defined elements (such as: Arguments, Constants, Functions) to the function expressions.
	 *
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	fun addDefinitions(vararg elements: PrimitiveElement)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression!!.addDefinitions(*elements)
	}

	/**
	 * Removes user defined elements (such as: Arguments, Constants, Functions) from the function expressions.
	 *
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	fun removeDefinitions(vararg elements: PrimitiveElement)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression!!.removeDefinitions(*elements)
	}

	/**
	 * Counts the recursive arguments.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	private fun countRecursiveArguments(): Int
	{
		var numOfRecursiveArguments = 0

		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression!!.argumentsList
					.filter { it.argumentType == Argument.RECURSIVE_ARGUMENT }
					.forEach { numOfRecursiveArguments++ }

		return numOfRecursiveArguments
	}

	/**
	 * Adds arguments (variadic) to the function expression definition.
	 *
	 * @param arguments Argument[]
	 *
	 * @author Bas Milius
	 */
	fun addArguments(vararg arguments: Argument)
	{
		if (this.functionBodyType == BODY_RUNTIME)
		{
			this.functionExpression!!.addArguments(*arguments)
			this.parametersNumber = this.functionExpression!!.getArgumentsNumber() - this.countRecursiveArguments()
		}
	}

	/**
	 * Enables to define the arguments (associated with the function expression) based on the given arguments names.
	 *
	 * @param argumentsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun defineArguments(vararg argumentsNames: String)
	{
		if (this.functionBodyType == BODY_RUNTIME)
		{
			this.functionExpression!!.defineArguments(*argumentsNames)
			this.parametersNumber = this.functionExpression!!.getArgumentsNumber() - this.countRecursiveArguments()
		}
	}

	/**
	 * Enables to define the argument (associated with the function expression) based on the argument name and the argument value.
	 *
	 * @param argumentName String
	 * @param argumentValue Double
	 *
	 * @author Bas Milius
	 */
	fun defineArgument(argumentName: String, argumentValue: Double)
	{
		if (this.functionBodyType == BODY_RUNTIME)
		{
			this.functionExpression!!.defineArgument(argumentName, argumentValue)
			this.parametersNumber = this.functionExpression!!.getArgumentsNumber() - this.countRecursiveArguments()
		}
	}

	/**
	 * Gets argument index from the function expression.
	 *
	 * @param argumentName String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getArgumentIndex(argumentName: String): Int
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getArgumentIndex(argumentName) else -1
	}

	/**
	 * Gets argument from function expression.
	 *
	 * @param argumentName String
	 *
	 * @return Argument?
	 *
	 * @author Bas Milius
	 */
	fun getArgument(argumentName: String): Argument?
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getArgument(argumentName) else null
	}

	/**
	 * Gets argument from the function expression.
	 *
	 * @param argumentIndex Int
	 *
	 * @return Argument?
	 *
	 * @author Bas Milius
	 */
	fun getArgument(argumentIndex: Int): Argument?
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getArgument(argumentIndex) else null
	}

	/**
	 * Gets user defined function parameter name.
	 *
	 * @param parameterIndex Int
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getParameterName(parameterIndex: Int): String
	{
		if (parameterIndex < 0 || parameterIndex >= this.parametersNumber) return ""

		if (this.functionBodyType == BODY_RUNTIME)
			return this.getArgument(parameterIndex)!!.argumentName

		return if (this.functionBodyType == BODY_EXTENDED) this.functionExtension!!.getParameterName(parameterIndex) else ""
	}

	/**
	 * Gets number of arguments associated with the function expression.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getArgumentsNumber(): Int
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getArgumentsNumber() else 0
	}

	/**
	 * Removes first occurrences of the arguments associated with the function expression.
	 *
	 * @param argumentsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun removeArguments(vararg argumentsNames: String)
	{
		if (this.functionBodyType == BODY_RUNTIME)
		{
			this.functionExpression!!.removeArguments(*argumentsNames)
			this.parametersNumber = this.functionExpression!!.getArgumentsNumber() - this.countRecursiveArguments()
		}
	}

	/**
	 * Removes first occurrences of the arguments associated with the function expression.
	 *
	 * @param arguments Argument[]
	 *
	 * @author Bas Milius
	 */
	fun removeArguments(vararg arguments: Argument)
	{
		if (this.functionBodyType == BODY_RUNTIME)
		{
			this.functionExpression!!.removeArguments(*arguments)
			this.parametersNumber = this.functionExpression!!.getArgumentsNumber() - this.countRecursiveArguments()
		}
	}

	/**
	 * Removes all arguments associated with the function expression.
	 *
	 * @author Bas Milius
	 */
	fun removeAllArguments()
	{
		if (this.functionBodyType == BODY_RUNTIME)
		{
			this.functionExpression!!.removeAllArguments()
			this.parametersNumber = 0
		}
	}

	/**
	 * Adds constants (variadic parameters) to the function expression definition.
	 *
	 * @param constants Constant[]
	 *
	 * @author Bas Milius
	 */
	fun addConstants(vararg constants: Constant)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression!!.addConstants(*constants)
	}

	/**
	 * Adds constants to the function expression definition.
	 *
	 * @param constantsList List<Constant>
	 *
	 * @author Bas Milius
	 */
	fun addConstants(constantsList: List<Constant>)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression!!.addConstants(constantsList)
	}

	/**
	 * Enables to define the constant (associated with the function expression) based on the constant name and constant value.
	 *
	 * @param constantName String
	 * @param constantValue Double
	 *
	 * @author Bas Milius
	 */
	fun defineConstant(constantName: String, constantValue: Double)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression!!.defineConstant(constantName, constantValue)
	}

	/**
	 * Gets constant index associated with the function expression.
	 *
	 * @param constantName String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getConstantIndex(constantName: String): Int
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getConstantIndex(constantName) else -1
	}

	/**
	 * Gets constant associated with the function expression.
	 *
	 * @param constantName String
	 *
	 * @return Constant?
	 *
	 * @author Bas Milius
	 */
	fun getConstant(constantName: String): Constant?
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getConstant(constantName) else null
	}

	/**
	 * Gets constant associated with the function expression.
	 *
	 * @param constantIndex Int
	 *
	 * @return Constant?
	 *
	 * @author Bas Milius
	 */
	fun getConstant(constantIndex: Int): Constant?
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getConstant(constantIndex) else null
	}

	/**
	 * Gets number of constants associated with the function expression.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getConstantsNumber(): Int
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getConstantsNumber() else 0
	}

	/**
	 * Removes first occurrences of the constants associated with the function expression.
	 *
	 * @param constantsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun removeConstants(vararg constantsNames: String)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression!!.removeConstants(*constantsNames)
	}

	/**
	 * Removes first occurrences of the constants associated with the function expression
	 *
	 * @param constants Constant[]
	 *
	 * @author Bas Milius
	 */
	fun removeConstants(vararg constants: Constant)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression!!.removeConstants(*constants)
	}

	/**
	 * Removes all constants associated with the function expression.
	 *
	 * @author Bas Milius
	 */
	fun removeAllConstants()
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression?.removeAllConstants()
	}

	/**
	 * Adds functions (variadic parameters) to the function expression definition.
	 *
	 * @param functions Function
	 *
	 * @author Bas Milius
	 */
	fun addFunctions(vararg functions: Function)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression?.addFunctions(*functions)
	}

	/**
	 * Enables to define the function (associated with the function expression) based on the function name, function expression string and arguments names (variadic parameters).
	 *
	 * @param functionName String
	 * @param functionExpressionString String
	 * @param argumentsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun defineFunction(functionName: String, functionExpressionString: String, vararg argumentsNames: String)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression?.defineFunction(functionName, functionExpressionString, *argumentsNames)
	}

	/**
	 * Gets index of function associated with the function expression.
	 *
	 * @param functionName String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getFunctionIndex(functionName: String): Int
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getFunctionIndex(functionName) else -1
	}

	/**
	 * Gets function associated with the function expression.
	 *
	 * @param functionName String
	 *
	 * @return Function?
	 *
	 * @author Bas Milius
	 */
	fun getFunction(functionName: String): Function?
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression?.getFunction(functionName) else null
	}

	/**
	 * Gets function associated with the function expression.
	 *
	 * @param functionIndex Int
	 *
	 * @return Function?
	 *
	 * @author Bas Milius
	 */
	fun getFunction(functionIndex: Int): Function?
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression?.getFunction(functionIndex) else null
	}

	/**
	 * Gets number of functions associated with the function expression.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getFunctionsNumber(): Int
	{
		return if (this.functionBodyType == BODY_RUNTIME) this.functionExpression!!.getFunctionsNumber() else 0
	}

	/**
	 * Removes first occurrences of the functions associated with the function expression.
	 *
	 * @param functionsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun removeFunctions(vararg functionsNames: String)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression?.removeFunctions(*functionsNames)
	}

	/**
	 * Removes first occurrences of the functions associated with the function expression.
	 *
	 * @param functions Function[]
	 *
	 * @author Bas Milius
	 */
	fun removeFunctions(vararg functions: Function)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression?.removeFunctions(*functions)
	}

	/**
	 * Removes all functions associated with the function expression.
	 *
	 * @author Bas Milius
	 */
	fun removeAllFunctions()
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression?.removeAllFunctions()
	}

	/**
	 * Enables verbose function mode.
	 *
	 * @author Bas Milius
	 */
	fun setVerboseMode()
	{
		this.functionExpression?.setVerboseMode()
	}

	/**
	 * Disables function verbose mode (Sets default silent mode).
	 *
	 * @author Bas Milius
	 */
	fun setSilentMode()
	{
		this.functionExpression?.setSilentMode()
	}

	/**
	 * Returns verbose mode status.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun getVerboseMode(): Boolean
	{
		return this.functionExpression?.verboseMode ?: false
	}

	/**
	 * Checks whether function name appears in function body, if yes the recursive mode is being set.
	 *
	 * @author Bas Milius
	 */
	fun checkRecursiveMode()
	{
		if (this.functionBodyType != BODY_RUNTIME)
			return

		val functionExpressionTokens = this.functionExpression?.initialTokens
		this.functionExpression?.disableRecursiveMode()

		if (functionExpressionTokens == null)
			return

		for (token in functionExpressionTokens)
			if (token.tokenStr == functionName)
			{
				this.functionExpression?.recursiveMode = true
				break
			}
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
		return this.functionExpression!!.recursiveMode
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
		return this.functionExpression!!.computingTime
	}

	/**
	 * Adds a related expression.
	 *
	 * @param expression Expression
	 *
	 * @author Bas Milius
	 */
	fun addRelatedExpression(expression: Expression)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression?.addRelatedExpression(expression)
	}

	/**
	 * Removes a related expression.
	 *
	 * @param expression Expression
	 *
	 * @author Bas Milius
	 */
	fun removeRelatedExpression(expression: Expression)
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression?.removeRelatedExpression(expression)
	}

	/**
	 * Sets expression modified flags in the related expression.
	 *
	 * @author Bas Milius
	 */
	fun setExpressionModifiedFlags()
	{
		if (this.functionBodyType == BODY_RUNTIME)
			this.functionExpression?.setExpressionModifiedFlag()
	}

}
