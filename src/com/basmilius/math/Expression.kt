package com.basmilius.math

import com.basmilius.math.mathcollection.*
import com.basmilius.math.miscellaneous.*
import com.basmilius.math.parsertokens.*
import com.basmilius.math.parsertokens.Unit
import com.basmilius.math.syntaxchecker.SyntaxChecker
import java.io.ByteArrayInputStream
import java.util.*

/**
 * Class Expression
 *
 * @author Bas Milius
 * @package com.basmilius.math
 */
class Expression
{

	/**
	 * Companion Object Expression
	 *
	 * @author Bas Milius
	 * @package com.basmilius.math
	 */
	companion object
	{

		val NOT_FOUND = MathParser.NOT_FOUND
		val FOUND = MathParser.FOUND
		val INTERNAL = true
		val WITH_EXP_STR = true
		val NO_EXP_STR = false
		val NO_SYNTAX_ERRORS = true
		val SYNTAX_ERROR_OR_STATUS_UNKNOWN = false
		val DISABLE_ULP_ROUNDING = true
		val KEEP_ULP_ROUNDING_SETTINGS = false

		/**
		 * Text adjusting.
		 *
		 * @param maxStr String
		 * @param str String
		 *
		 * @return String
		 *
		 * @author Bas Milius
		 */
		private fun getLeftSpaces(maxStr: String, str: String): String
		{
			var spc = ""

			for (i in 0 until maxStr.length - str.length)
				spc += " "

			return spc + str
		}

		/**
		 * Text adjusting.
		 *
		 * @param maxStr String
		 * @param str String
		 *
		 * @return String
		 *
		 * @author Bas Milius
		 */
		private fun getRightSpaces(maxStr: String, str: String): String
		{
			var spc = ""

			for (i in 0..maxStr.length - str.length - 1)
				spc = " " + spc

			return str + spc
		}

		/**
		 * Show tokens.
		 *
		 * @param tokensList List<Token>?
		 *
		 * @author Bas Milius
		 */
		fun showTokens(tokensList: List<Token>?)
		{
			val maxStr = "TokenTypeId"

			MathParser.consolePrintln(" --------------------")
			MathParser.consolePrintln("| Expression tokens: |")
			MathParser.consolePrintln(" ---------------------------------------------------------------------------------------------------------------")
			MathParser.consolePrintln("|    TokenIdx |       Token |        KeyW |     TokenId | TokenTypeId |  TokenLevel |  TokenValue |   LooksLike |")
			MathParser.consolePrintln(" ---------------------------------------------------------------------------------------------------------------")

			if (tokensList == null)
			{
				MathParser.consolePrintln("NULL tokens list")
				return
			}

			val tokensNumber = tokensList.size

			for (tokenIndex in 0 until tokensNumber)
			{
				val tokenIndexStr = getLeftSpaces(maxStr, tokenIndex.toString())
				val tokenStr = getLeftSpaces(maxStr, tokensList[tokenIndex].tokenStr)
				val keyWordStr = getLeftSpaces(maxStr, tokensList[tokenIndex].keyWord)
				val tokenIdStr = getLeftSpaces(maxStr, tokensList[tokenIndex].tokenId.toString())
				val tokenTypeIdStr = getLeftSpaces(maxStr, tokensList[tokenIndex].tokenTypeId.toString())
				val tokenLevelStr = getLeftSpaces(maxStr, tokensList[tokenIndex].tokenLevel.toString())
				val tokenValueStr = getLeftSpaces(maxStr, tokensList[tokenIndex].tokenValue.toString())
				val tokenLooksLikeStr = getLeftSpaces(maxStr, tokensList[tokenIndex].looksLike)

				MathParser.consolePrintln("| $tokenIndexStr | $tokenStr | $keyWordStr | $tokenIdStr | $tokenTypeIdStr | $tokenLevelStr | $tokenValueStr | $tokenLooksLikeStr |")
			}

			MathParser.consolePrintln(" ---------------------------------------------------------------------------------------------------------------")
		}

	}

	private val FUNCTION = "function"
	private val ARGUMENT = "argument"
	private val ERROR = "error"

	var description: String = ""
	var argumentsList: ArrayList<Argument> = arrayListOf()
	var functionsList: ArrayList<Function> = arrayListOf()
	var constantsList: ArrayList<Constant> = arrayListOf()
	var relatedExpressionsList: ArrayList<Expression> = arrayListOf()
	var recursiveMode: Boolean = false
	var verboseMode: Boolean = false
	var disableUlpRounding: Boolean = false

	private var keyWordsList: ArrayList<KeyWord> = arrayListOf()
	private var tokensList: ArrayList<Token> = arrayListOf()
	private var expressionWasModified: Boolean = false
	private var recursionCallPending: Boolean = false
	private var recursionCallsCounter: Int = 0
	private var parserKeyWordsOnly: Boolean = false
	private var internalClone: Boolean = false
	private var optionsChangesetNumber: Int = -1

	private var pr_expressionString: String = ""

	var initialTokens: ArrayList<Token> = arrayListOf()
		private set

	/**
	 * Gets or Sets computing time.
	 *
	 * @property
	 * @type Double
	 *
	 * @author Bas Milius
	 */
	var computingTime: Double = 0.0
		private set

	/**
	 * Gets or Sets the error message.
	 *
	 * @property
	 * @type String
	 *
	 * @author Bas Milius
	 */
	var errorMessage: String = ""
		private set

	/**
	 * Gets or Sets the expression string.
	 *
	 * @property
	 * @type String
	 *
	 * @author Bas Milius
	 */
	var expressionString: String
		get() = this.pr_expressionString
		set(value)
		{
			this.pr_expressionString = value
			this.setExpressionModifiedFlag()
		}

	/**
	 * Gets or Sets the syntax status.
	 *
	 * @property
	 * @type Boolean
	 *
	 * @author Bas Milius
	 */
	var syntaxStatus: Boolean = false
		private set

	/**
	 * Expression Constructor.
	 *
	 * @constructor
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	constructor(vararg elements: PrimitiveElement)
	{
		this.expressionString = ""
		this.expressionInit()
		this.setExpressionModifiedFlag()
		this.addDefinitions(*elements)
	}

	/**
	 * Expression Constructor.
	 *
	 * @constructor
	 * @param expressionString String
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	constructor(expressionString: String, vararg elements: PrimitiveElement)
	{
		this.expressionInit()
		this.expressionString = expressionString
		this.setExpressionModifiedFlag()
		this.addDefinitions(*elements)
	}

	/**
	 * Expression Constructor.
	 *
	 * @constructor
	 * @param expressionString String
	 * @param parserKeyWordsOnly Boolean
	 *
	 * @author Bas Milius
	 */
	constructor(expressionString: String, parserKeyWordsOnly: Boolean)
	{
		this.expressionInit()
		this.expressionString = expressionString
		this.setExpressionModifiedFlag()
		this.parserKeyWordsOnly = parserKeyWordsOnly
	}

	/**
	 * Expression Constructor.
	 *
	 * @constructor
	 * @param expressionString String
	 * @param initialTokens ArrayList<Token>
	 * @param argumentsList ArrayList<Argument>
	 * @param functionsList ArrayList<Function>
	 * @param constantsList ArrayList<Constant>
	 * @param disableUlpRounding Boolean
	 *
	 * @author Bas Milius
	 */
	constructor(expressionString: String, initialTokens: ArrayList<Token>, argumentsList: ArrayList<Argument>, functionsList: ArrayList<Function>, constantsList: ArrayList<Constant>, disableUlpRounding: Boolean)
	{
		this.expressionString = expressionString
		this.initialTokens = initialTokens
		this.argumentsList = argumentsList
		this.functionsList = functionsList
		this.constantsList = constantsList
		this.relatedExpressionsList = arrayListOf()
		this.expressionWasModified = false
		this.syntaxStatus = NO_SYNTAX_ERRORS
		this.description = "_internal_"
		this.errorMessage = ""
		this.computingTime = 0.0
		this.recursionCallPending = false
		this.recursionCallsCounter = 0
		this.internalClone = false
		this.parserKeyWordsOnly = false
		this.disableUlpRounding = disableUlpRounding
		this.setSilentMode()
		this.disableRecursiveMode()
	}

	/**
	 * Expression Constructor.
	 *
	 * @constructor
	 * @param expressionString String
	 * @param argumentsList ArrayList<Argument>
	 * @param functionsList ArrayList<Function>
	 * @param constantsList ArrayList<Constant>
	 *
	 * @author Bas Milius
	 */
	constructor(expressionString: String, argumentsList: ArrayList<Argument>, functionsList: ArrayList<Function>, constantsList: ArrayList<Constant>)
	{
		this.expressionString = expressionString
		this.expressionInternalVarsInit()
		this.setSilentMode()
		this.disableRecursiveMode()
		this.argumentsList = argumentsList
		this.functionsList = functionsList
		this.constantsList = constantsList
		this.relatedExpressionsList = arrayListOf()
		this.setExpressionModifiedFlag()
	}

	/**
	 * Expression Constructor.
	 *
	 * @constructor
	 * @param expression Expression
	 *
	 * @author Bas Milius
	 */
	private constructor(expression: Expression)
	{
		this.expressionString = expression.expressionString
		this.description = expression.description
		this.argumentsList = expression.argumentsList
		this.functionsList = expression.functionsList
		this.constantsList = expression.constantsList
		this.keyWordsList = expression.keyWordsList
		this.relatedExpressionsList = expression.relatedExpressionsList
		this.computingTime = 0.0
		this.expressionWasModified = expression.expressionWasModified
		this.recursiveMode = expression.recursiveMode
		this.verboseMode = expression.verboseMode
		this.syntaxStatus = expression.syntaxStatus
		this.errorMessage = expression.errorMessage
		this.recursionCallPending = expression.recursionCallPending
		this.recursionCallsCounter = expression.recursionCallsCounter
		this.parserKeyWordsOnly = expression.parserKeyWordsOnly
		this.disableUlpRounding = expression.disableUlpRounding
		this.internalClone = true
	}

	/**
	 * Adds related expression.
	 *
	 * @param expression Expression
	 *
	 * @author Bas Milius
	 */
	fun addRelatedExpression(expression: Expression)
	{
		if (expression != this && !this.relatedExpressionsList.contains(expression))
		{
			this.relatedExpressionsList.add(expression)
		}
	}

	/**
	 * Removes related expression.
	 *
	 * @param expression Expression
	 *
	 * @author Bas Milius
	 */
	fun removeRelatedExpression(expression: Expression)
	{
		this.relatedExpressionsList.remove(expression)
	}

	/**
	 * Prints related expression list.
	 *
	 * @author Bas Milius
	 */
	fun showRelatedExpressions()
	{
		MathParser.consolePrintln()
		MathParser.consolePrintln("${this.description} = ${this.expressionString}:")
		for (expression in this.relatedExpressionsList)
			MathParser.consolePrintln("-> ${expression.description} = ${expression.expressionString}")
	}

	/**
	 * Sets information about errors indentified on constructor level.
	 *
	 * @param syntaxStatus Boolean
	 * @param errorMessage String
	 *
	 * @author Bas Milius
	 */
	fun setSyntaxStatus(syntaxStatus: Boolean, errorMessage: String)
	{
		this.syntaxStatus = syntaxStatus
		this.errorMessage = errorMessage
	}

	/**
	 * Sets expression status to modified.
	 *
	 * @author Bas Milius
	 */
	fun setExpressionModifiedFlag()
	{
		if (this.recursionCallPending)
			return

		this.recursionCallPending = true
		this.recursionCallsCounter = 0
		this.internalClone = false
		this.expressionWasModified = true
		this.syntaxStatus = SYNTAX_ERROR_OR_STATUS_UNKNOWN
		this.errorMessage = "Syntax status unknown."

		for (expression in this.relatedExpressionsList)
		{
			expression.setExpressionModifiedFlag()
		}

		this.recursionCallPending = false
	}

	/**
	 * Common variables while expression initializing.
	 *
	 * @author Bas Milius
	 */
	private fun expressionInternalVarsInit()
	{
		this.description = ""
		this.errorMessage = ""
		this.computingTime = 0.0
		this.recursionCallPending = false
		this.recursionCallsCounter = 0
		this.internalClone = false
		this.parserKeyWordsOnly = false
		this.disableUlpRounding = KEEP_ULP_ROUNDING_SETTINGS
	}

	/**
	 * Common variables while expression initializing.
	 *
	 * @author Bas Milius
	 */
	private fun expressionInit()
	{
		this.argumentsList = arrayListOf()
		this.functionsList = arrayListOf()
		this.constantsList = arrayListOf()

		this.setSilentMode()
		this.disableRecursiveMode()
		this.expressionInternalVarsInit()
	}

	/**
	 * Clears expression string.
	 *
	 * @author Bas Milius
	 */
	fun clearExpressionString()
	{
		this.expressionString = ""
		this.setExpressionModifiedFlag()
	}

	/**
	 * Clears expression description.
	 *
	 * @author Bas Milius
	 */
	fun clearDescription()
	{
		this.description = ""
	}

	/**
	 * Disables verbose mode (default silent mode).
	 *
	 * @author Bas Milius
	 */
	fun setSilentMode()
	{
		this.verboseMode = false
	}

	/**
	 * Enables verbose mode.
	 *
	 * @author Bas Milius
	 */
	fun setVerboseMode()
	{
		this.verboseMode = true
	}

	/**
	 * Disables recursive mode.
	 *
	 * @author Bas Milius
	 */
	fun disableRecursiveMode()
	{
		this.recursiveMode = false
	}

	/**
	 * Adds user defined elements (such as: Arguments, Constants, Functions) to the expression.
	 *
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	fun addDefinitions(vararg elements: PrimitiveElement)
	{
		for (element in elements)
		{
			when (element)
			{
				is Argument -> this.addArguments(element)
				is Constant -> this.addConstants(element)
				is Function -> this.addFunctions(element)
				is RecursiveArgument -> this.addArguments(element)
			}
		}
	}

	/**
	 * Removes user defined elements (such as: Arguments, Constants, Functions) to the expression.
	 *
	 * @param elements PrimitiveElement[]
	 *
	 * @author Bas Milius
	 */
	fun removeDefinitions(vararg elements: PrimitiveElement)
	{
		for (element in elements)
		{
			when (element)
			{
				is Argument -> this.removeArguments(element)
				is Constant -> this.removeConstants(element)
				is Function -> this.removeFunctions(element)
				is RecursiveArgument -> this.removeArguments(element)
			}
		}
	}

	/**
	 * Adds arguments (variadic) to the expression definition.
	 *
	 * @param arguments Argument[]
	 *
	 * @author Bas Milius
	 */
	fun addArguments(vararg arguments: Argument)
	{
		for (argument in arguments)
		{
			this.argumentsList.add(argument)
			argument.addRelatedExpression(this)
		}

		this.setExpressionModifiedFlag()
	}

	/**
	 * Defines an argument associated with the expression based on the argument name and value.
	 *
	 * @param argumentName String
	 * @param argumentValue Double
	 *
	 * @author Bas Milius
	 */
	fun defineArgument(argumentName: String, argumentValue: Double)
	{
		val argument = Argument(argumentName, argumentValue)
		argument.addRelatedExpression(this)
		this.argumentsList.add(argument)
		this.setExpressionModifiedFlag()
	}

	/**
	 * Defines arguments associated with the expression based on the given argument names.
	 *
	 * @param argumentNames String[]
	 *
	 * @author Bas Milius
	 */
	fun defineArguments(vararg argumentNames: String)
	{
		for (argumentName in argumentNames)
		{
			val argument = Argument(argumentName)
			argument.addRelatedExpression(this)
			this.argumentsList.add(argument)
		}

		this.setExpressionModifiedFlag()
	}

	/**
	 * Gets argument index from the expression.
	 *
	 * @param argumentName String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getArgumentIndex(argumentName: String): Int
	{
		if (this.argumentsList.size == 0)
			return NOT_FOUND

		var argumentIndex = 0
		var searchResult = NOT_FOUND

		while (argumentIndex < this.argumentsList.size && searchResult == NOT_FOUND)
		{
			if (this.argumentsList[argumentIndex].argumentName == argumentName)
				searchResult = FOUND
			else
				argumentIndex++
		}

		return if (searchResult == FOUND) argumentIndex else NOT_FOUND
	}

	/**
	 * Gets an argument from the expression.
	 *
	 * @param argumentName String
	 *
	 * @return Argument?
	 *
	 * @author Bas Milius
	 */
	fun getArgument(argumentName: String): Argument?
	{
		val argumentIndex = this.getArgumentIndex(argumentName)

		if (argumentIndex == NOT_FOUND)
			return null

		return this.argumentsList[argumentIndex]
	}

	/**
	 * Gets an argument from the expression.
	 *
	 * @param argumentIndex Int
	 *
	 * @return Argument?
	 *
	 * @author Bas Milius
	 */
	fun getArgument(argumentIndex: Int): Argument?
	{
		if (argumentIndex < 0 || argumentIndex >= this.argumentsList.size)
			return null

		return this.argumentsList[argumentIndex]
	}

	/**
	 * Gets number of arguments associated with the expression.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getArgumentsNumber(): Int
	{
		return this.argumentsList.size
	}

	/**
	 * Gets argument value.
	 *
	 * @param argumentName String
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun getArgumentValue(argumentName: String): Double
	{
		val argumentIndex = this.getArgumentIndex(argumentName)

		if (argumentIndex == NOT_FOUND)
			return Double.NaN

		return this.argumentsList[argumentIndex].argumentValue
	}

	/**
	 * Sets argument value.
	 *
	 * @param argumentName String
	 * @param argumentValue Double
	 *
	 * @author Bas Milius
	 */
	fun setArgumentValue(argumentName: String, argumentValue: Double)
	{
		val argumentIndex = this.getArgumentIndex(argumentName)

		if (argumentIndex != NOT_FOUND)
			this.argumentsList[argumentIndex].argumentValue = argumentValue
	}

	/**
	 * Removes first occurrences of the arguments associated with the expression.
	 *
	 * @param argumentNames String[]
	 *
	 * @author Bas Milius
	 */
	fun removeArguments(vararg argumentNames: String)
	{
		for (argumentName in argumentNames)
		{
			val argumentIndex = this.getArgumentIndex(argumentName)
			if (argumentIndex != NOT_FOUND)
			{
				val argument = this.argumentsList[argumentIndex]
				argument.removeRelatedExpression(this)
				this.argumentsList.remove(argument)
			}
		}

		this.setExpressionModifiedFlag()
	}

	/**
	 * Removes first occurrences of the arguments associated with the expression.
	 *
	 * @param arguments Argument[]
	 *
	 * @author Bas Milius
	 */
	fun removeArguments(vararg arguments: Argument)
	{
		for (argument in arguments)
		{
			this.argumentsList.remove(argument)
			argument.removeRelatedExpression(this)
		}

		this.setExpressionModifiedFlag()
	}

	/**
	 * Removes all arguments associated with the expression.
	 *
	 * @author Bas Milius
	 */
	fun removeAllArguments()
	{
		for (argument in this.argumentsList)
			argument.removeRelatedExpression(this)

		this.argumentsList.clear()
		this.setExpressionModifiedFlag()
	}

	/**
	 * Adds constants (variadic parameters) to the expression definition.
	 *
	 * @param constants Constant[]
	 *
	 * @author bas Milius
	 */
	fun addConstants(vararg constants: Constant)
	{
		for (constant in constants)
		{
			this.constantsList.add(constant)
			constant.addRelatedExpression(this)
		}

		this.setExpressionModifiedFlag()
	}

	/**
	 * Adds constants to the expression definition.
	 *
	 * @param constantsList List<Constant>
	 *
	 * @author Bas Milius
	 */
	fun addConstants(constantsList: List<Constant>)
	{
		this.constantsList.addAll(constantsList)

		for (constant in this.constantsList)
			constant.addRelatedExpression(this)

		this.setExpressionModifiedFlag()
	}

	/**
	 * Enables to define the constant (associated with the expression) based on the constant name and constant value.
	 *
	 * @param constantName String
	 * @param constantValue Double
	 *
	 * @author Bas Milius
	 */
	fun defineConstant(constantName: String, constantValue: Double)
	{
		val constant = Constant(constantName, constantValue)
		constant.addRelatedExpression(this)
		this.constantsList.add(constant)
		this.setExpressionModifiedFlag()
	}

	/**
	 * Gets constant index associated with the expression.
	 *
	 * @param constantName String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getConstantIndex(constantName: String): Int
	{
		if (this.constantsList.size == 0)
			return NOT_FOUND

		var constantIndex = 0
		var searchResult = NOT_FOUND

		while (constantIndex < this.constantsList.size && searchResult == NOT_FOUND)
		{
			if (this.constantsList[constantIndex].getConstantName() == constantName)
				searchResult = FOUND
			else
				constantIndex++
		}

		return if (searchResult == FOUND) constantIndex else NOT_FOUND
	}

	/**
	 * Gets constant associated with the expression.
	 *
	 * @param constantName String
	 *
	 * @return Constant?
	 *
	 * @author Bas Milius
	 */
	fun getConstant(constantName: String): Constant?
	{
		val constantIndex = this.getConstantIndex(constantName)

		return if (constantIndex == NOT_FOUND) null else this.constantsList[constantIndex]
	}

	/**
	 * Gets constant associated with the expression.
	 *
	 * @param constantIndex Int
	 *
	 * @return Constant?
	 *
	 * @author Bas Milius
	 */
	fun getConstant(constantIndex: Int): Constant?
	{
		return if (constantIndex < 0 || constantIndex >= this.constantsList.size) null else this.constantsList[constantIndex]
	}

	/**
	 * Gets number of constants associated with the expression.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getConstantsNumber(): Int
	{
		return this.constantsList.size
	}

	/**
	 * Removes first occurrences of the constants associated with the expression.
	 *
	 * @param constantsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun removeConstants(vararg constantsNames: String)
	{
		for (constantName in constantsNames)
		{
			val constantIndex = this.getConstantIndex(constantName)
			if (constantIndex != NOT_FOUND)
			{
				val constant = this.constantsList[constantIndex]
				constant.removeRelatedExpression(this)
				this.constantsList.removeAt(constantIndex)
			}
		}

		this.setExpressionModifiedFlag()
	}

	/**
	 * Removes first occurrences of the constants associated with the expression
	 *
	 * @param constants Constant[]
	 *
	 * @author Bas Milius
	 */
	fun removeConstants(vararg constants: Constant)
	{
		for (constant in constants)
		{
			this.constantsList.remove(constant)
			constant.removeRelatedExpression(this)
			this.setExpressionModifiedFlag()
		}
	}

	/**
	 * Removes all constants associated with the expression
	 *
	 * @author Bas Milius
	 */
	fun removeAllConstants()
	{
		for (constant in this.constantsList)
			constant.removeRelatedExpression(this)

		this.constantsList.clear()
		this.setExpressionModifiedFlag()
	}

	/**
	 * Adds functions (variadic parameters) to the expression definition.
	 *
	 * @param functions Function[]
	 *
	 * @author Bas Milius
	 */
	fun addFunctions(vararg functions: Function)
	{
		for (function in functions)
		{
			this.functionsList.add(function)

			if (function.functionBodyType == Function.BODY_RUNTIME)
				function.addRelatedExpression(this)
		}

		this.setExpressionModifiedFlag()
	}

	/**
	 * Enables to define the function (associated with the expression) based on the function name, function expression string and arguments names (variadic parameters).
	 *
	 * @param functionName String
	 * @param functionExpressionString String
	 * @param argumentsNames String
	 *
	 * @author Bas Milius
	 */
	fun defineFunction(functionName: String, functionExpressionString: String, vararg argumentsNames: String)
	{
		val function = Function(functionName, functionExpressionString, *argumentsNames)
		this.functionsList.add(function)
		function.addRelatedExpression(this)
		this.setExpressionModifiedFlag()
	}

	/**
	 * Gets index of function associated with the expression.
	 *
	 * @param functionName String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getFunctionIndex(functionName: String): Int
	{
		if (this.functionsList.size == 0)
			return NOT_FOUND

		var functionIndex = 0
		var searchResult = NOT_FOUND

		while (functionIndex < this.functionsList.size && searchResult == NOT_FOUND)
		{
			if (this.functionsList[functionIndex].functionName == functionName)
				searchResult = FOUND
			else
				functionIndex++
		}

		return if (searchResult == FOUND) functionIndex else NOT_FOUND
	}

	/**
	 * Gets function associated with the expression.
	 *
	 * @param functionName String
	 *
	 * @return Function?
	 *
	 * @author Bas Milius
	 */
	fun getFunction(functionName: String): Function?
	{
		val functionIndex = this.getFunctionIndex(functionName)

		return if (functionIndex == NOT_FOUND) null else this.functionsList[functionIndex]
	}

	/**
	 * Gets function associated with the expression.
	 *
	 * @param functionIndex Int
	 *
	 * @return Function?
	 *
	 * @author Bas Milius
	 */
	fun getFunction(functionIndex: Int): Function?
	{
		return if (functionIndex < 0 || functionIndex >= this.functionsList.size) null else this.functionsList[functionIndex]
	}

	/**
	 * Gets number of functions associated with the expression.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getFunctionsNumber(): Int
	{
		return this.functionsList.size
	}

	/**
	 * Removes first occurrences of the functions associated with the expression.
	 *
	 * @param functionsNames String[]
	 *
	 * @author Bas Milius
	 */
	fun removeFunctions(vararg functionsNames: String)
	{
		for (functionName in functionsNames)
		{
			val functionIndex = this.getFunctionIndex(functionName)
			if (functionIndex != NOT_FOUND)
			{
				val function = this.functionsList[functionIndex]
				function.removeRelatedExpression(this)
				this.functionsList.remove(function)
			}
		}

		this.setExpressionModifiedFlag()
	}

	/**
	 * Removes first occurrences of the functions associated with the expression.
	 *
	 * @param functions Function[]
	 *
	 * @author Bas Milius
	 */
	fun removeFunctions(vararg functions: Function)
	{
		for (function in functions)
		{
			function.removeRelatedExpression(this)
			this.functionsList.remove(function)
		}

		this.setExpressionModifiedFlag()
	}

	/**
	 * Removes all functions associated with the expression.
	 *
	 * @author Bas Milius
	 */
	fun removeAllFunctions()
	{
		for (function in this.functionsList)
			function.removeRelatedExpression(this)

		this.functionsList.clear()
		this.setExpressionModifiedFlag()
	}

	/**
	 * Sets given token to the number type / value. Method should be called only by the SetDecreaseRemove like methods
	 *
	 * @param pos Int
	 * @param number Double
	 * @param ulpRound Boolean
	 *
	 * @author Bas Milius
	 */
	private fun setToNumber(pos: Int, number: Double, ulpRound: Boolean)
	{
		val token = this.tokensList[pos]

		if (MathParser.ulpRounding && !this.disableUlpRounding && ulpRound)
		{
			if (number.isNaN() || number.isInfinite())
			{
				token.tokenValue = number
			}
			else
			{
				val precision = MathFunctions.ulpDecimalDigitsBefore(number)

				token.tokenValue = if (precision >= 0) MathFunctions.round(number, precision) else number
			}
		}
		else
		{
			token.tokenValue = number
		}

		token.tokenTypeId = ParserSymbol.NUMBER_TYPE_ID
		token.tokenId = ParserSymbol.NUMBER_ID
		token.keyWord = ParserSymbol.NUMBER_STR
	}

	/**
	 * Sets given token to the number type / value. Method should be called only by the SetDecreaseRemove like methods
	 *
	 * @param pos Int
	 * @param number Double
	 *
	 * @author Bas Milius
	 */
	private fun setToNumber(pos: Int, number: Double)
	{
		this.setToNumber(pos, number, false)
	}

	/**
	 * SetDecreaseRemove function for 1 arg functions.
	 *
	 * @param pos Int
	 * @param result Double
	 * @param ulpRound Boolean
	 *
	 * @author Bas Milius
	 */
	private fun f1SetDecreaseRemove(pos: Int, result: Double, ulpRound: Boolean)
	{
		this.setToNumber(pos, result, ulpRound)
		this.tokensList[pos].tokenLevel = this.tokensList[pos].tokenLevel - 1
		this.tokensList.removeAt(pos + 1)
	}

	/**
	 * SetDecreaseRemove function for 1 arg functions.
	 *
	 * @param pos Int
	 * @param result Double
	 *
	 * @author Bas Milius
	 */
	private fun f1SetDecreaseRemove(pos: Int, result: Double)
	{
		this.f1SetDecreaseRemove(pos, result, false)
	}

	/**
	 * SetDecreaseRemove function for 2-arg functions.
	 *
	 * @param pos Int
	 * @param result Double
	 * @param ulpRound Boolean
	 *
	 * @author Bas Milius
	 */
	private fun f2SetDecreaseRemove(pos: Int, result: Double, ulpRound: Boolean)
	{
		this.setToNumber(pos, result, ulpRound)
		this.tokensList[pos].tokenLevel = this.tokensList[pos].tokenLevel - 1
		this.tokensList.removeAt(pos + 2)
		this.tokensList.removeAt(pos + 1)
	}

	/**
	 * SetDecreaseRemove function for 2-arg functions.
	 *
	 * @param pos Int
	 * @param result Double
	 *
	 * @author Bas Milius
	 */
	private fun f2SetDecreaseRemove(pos: Int, result: Double)
	{
		this.f2SetDecreaseRemove(pos, result, false)
	}

	/**
	 * SetDecreaseRemove function for 3-arg functions.
	 *
	 * @param pos Int
	 * @param result Double
	 * @param ulpRound Boolean
	 *
	 * @author Bas Milius
	 */
	private fun f3SetDecreaseRemove(pos: Int, result: Double, ulpRound: Boolean)
	{
		this.setToNumber(pos, result, ulpRound)
		this.tokensList[pos].tokenLevel = this.tokensList[pos].tokenLevel - 1
		this.tokensList.removeAt(pos + 3)
		this.tokensList.removeAt(pos + 2)
		this.tokensList.removeAt(pos + 1)
	}

	/**
	 * SetDecreaseRemove function for 3-arg functions.
	 *
	 * @param pos Int
	 * @param result Double
	 *
	 * @author Bas Milius
	 */
	private fun f3SetDecreaseRemove(pos: Int, result: Double)
	{
		this.f3SetDecreaseRemove(pos, result, false)
	}

	/**
	 * SetDecreaseRemove function for operators.
	 *
	 * @param pos Int
	 * @param result Double
	 * @param ulpRound Boolean
	 *
	 * @author Bas Milius
	 */
	private fun opSetDecreaseRemove(pos: Int, result: Double, ulpRound: Boolean)
	{
		this.setToNumber(pos, result, ulpRound)
		this.tokensList.removeAt(pos + 1)
		this.tokensList.removeAt(pos - 1)
	}

	/**
	 * SetDecreaseRemove function for operators.
	 *
	 * @param pos Int
	 * @param result Double
	 *
	 * @author Bas Milius
	 */
	private fun opSetDecreaseRemove(pos: Int, result: Double)
	{
		this.opSetDecreaseRemove(pos, result, false)
	}

	/**
	 * SetDecreaseRemove function for calculus operators.
	 *
	 * @param pos Int
	 * @param result Double
	 * @param ulpRound Boolean
	 *
	 * @author Bas Milius
	 */
	private fun calcSetDecreaseRemove(pos: Int, result: Double, ulpRound: Boolean)
	{
		this.setToNumber(pos, result, ulpRound)
		this.tokensList[pos].tokenLevel = this.tokensList[pos].tokenLevel - 1

		val lPos = pos + 1
		var rPos = lPos + 1

		while (!(this.tokensList[rPos].tokenTypeId == ParserSymbol.TYPE_ID && this.tokensList[rPos].tokenId == ParserSymbol.RIGHT_PARENTHESES_ID && this.tokensList[rPos].tokenLevel == this.tokensList[lPos].tokenLevel))
			rPos++

		for (p in rPos downTo lPos)
			this.tokensList.removeAt(p)
	}

	/**
	 * SetDecreaseRemove function for calculus operators.
	 *
	 * @param pos Int
	 * @param result Double
	 *
	 * @author Bas Milius
	 */
	private fun calcSetDecreaseRemove(pos: Int, result: Double)
	{
		this.calcSetDecreaseRemove(pos, result, false)
	}

	/**
	 * SetDecreaseRemove function for special functions.
	 *
	 * @param pos Int
	 * @param value Double
	 * @param length Double
	 * @param ulpRound Boolean
	 *
	 * @author Bas Milius
	 */
	private fun variadicSetDecreaseRemove(pos: Int, value: Double, length: Int, ulpRound: Boolean)
	{
		this.setToNumber(pos, value, ulpRound)
		this.tokensList[pos].tokenLevel = this.tokensList[pos].tokenLevel - 1

		for (p in pos + length downTo pos + 1)
			this.tokensList.removeAt(p)
	}

	/**
	 * SetDecreaseRemove function for special functions.
	 *
	 * @param pos Int
	 * @param value Double
	 * @param length Double
	 *
	 * @author Bas Milius
	 */
	private fun variadicSetDecreaseRemove(pos: Int, value: Double, length: Int)
	{
		this.variadicSetDecreaseRemove(pos, value, length, false)
	}

	/**
	 * If set remove method for the if function.
	 *
	 * @param pos Int
	 * @param ifCondition Double
	 * @param ulpRound Boolean
	 *
	 * @author Bas Milius
	 */
	private fun ifSetRemove(pos: Int, ifCondition: Double, ulpRound: Boolean)
	{
		val lPos = pos + 1
		val ifLevel = this.tokensList[lPos].tokenLevel

		var c1Pos = lPos + 1
		while (!(this.tokensList[c1Pos].tokenTypeId == ParserSymbol.TYPE_ID && this.tokensList[c1Pos].tokenId == ParserSymbol.COMMA_ID && this.tokensList[c1Pos].tokenLevel == ifLevel))
			c1Pos++


		var c2Pos = c1Pos + 1
		while (!(this.tokensList[c2Pos].tokenTypeId == ParserSymbol.TYPE_ID && this.tokensList[c2Pos].tokenId == ParserSymbol.COMMA_ID && this.tokensList[c2Pos].tokenLevel == ifLevel))
			c2Pos++

		var rPos = c2Pos + 1
		while (!(this.tokensList[rPos].tokenTypeId == ParserSymbol.TYPE_ID && this.tokensList[rPos].tokenId == ParserSymbol.RIGHT_PARENTHESES_ID && this.tokensList[rPos].tokenLevel == ifLevel))
			rPos++

		if (!ifCondition.isNaN())
		{
			if (ifCondition != 0.0)
			{
				this.setToNumber(c2Pos + 1, Double.NaN)
				this.tokensList[c2Pos + 1].tokenLevel = ifLevel
				this.removeTokens(c2Pos + 2, rPos - 1)
			}
			else
			{
				this.setToNumber(c1Pos + 1, Double.NaN)
				this.tokensList[c1Pos + 1].tokenLevel = ifLevel
				this.removeTokens(c1Pos + 2, c2Pos - 1)
			}
		}
		else
		{
			this.setToNumber(c1Pos + 1, Double.NaN)
			this.setToNumber(c2Pos + 1, Double.NaN)
			this.tokensList[c1Pos + 1].tokenLevel = ifLevel
			this.tokensList[c2Pos + 1].tokenLevel = ifLevel
			this.removeTokens(c2Pos + 2, rPos - 1)
			this.removeTokens(c1Pos + 2, c2Pos - 1)
		}

		this.setToNumber(lPos + 1, ifCondition, ulpRound)
		this.tokensList[lPos + 1].tokenLevel = ifLevel
		this.removeTokens(lPos + 2, c1Pos - 1)
		this.tokensList[pos].tokenId = Function3Arg.IF_ID
	}

	/**
	 * If set remove method for the if function.
	 *
	 * @param pos Int
	 * @param ifCondition Double
	 *
	 * @author Bas Milius
	 */
	private fun ifSetRemove(pos: Int, ifCondition: Double)
	{
		this.ifSetRemove(pos, ifCondition, false)
	}

	/**
	 * Removes tokens.
	 *
	 * @param from Int
	 * @param to Int
	 *
	 * @author Bas Milius
	 */
	private fun removeTokens(from: Int, to: Int)
	{
		if (from < to)
		{
			for (p in to downTo from)
				this.tokensList.removeAt(p)
		}
		else if (from == to)
		{
			this.tokensList.removeAt(from)
		}
	}

	/**
	 * Creates string tokens list from the subexpression.
	 *
	 * @param startPos Int
	 * @param endPos Int
	 * @param tokensList List<Token>
	 *
	 * @return List<Token>
	 *
	 * @author Bas Milius
	 */
	private fun createInitialTokens(startPos: Int, endPos: Int, tokensList: List<Token>): ArrayList<Token>
	{
		val tokens = ArrayList<Token>()

		(startPos..endPos).mapTo(tokens) { tokensList[it].clone() }

		return tokens
	}

	/**
	 * Return number of functions parameters.
	 *
	 * @param pos Int
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	private fun getParametersNumber(pos: Int): Int
	{
		val lPpos = pos + 1
		if (lPpos == this.initialTokens.size)
			return -1

		if (this.initialTokens[lPpos].tokenTypeId == ParserSymbol.TYPE_ID && this.initialTokens[lPpos].tokenId == ParserSymbol.LEFT_PARENTHESES_ID)
		{
			val tokenLevel = this.initialTokens[lPpos].tokenLevel

			var endPos = lPpos + 1
			while (!(this.initialTokens[endPos].tokenTypeId == ParserSymbol.TYPE_ID && this.initialTokens[endPos].tokenId == ParserSymbol.RIGHT_PARENTHESES_ID && this.initialTokens[endPos].tokenLevel == tokenLevel))
				endPos++

			if (endPos == lPpos + 1)
				return 0

			val numberOfCommas = (lPpos until endPos)
					.map { this.initialTokens[it] }
					.count { it.tokenTypeId == ParserSymbol.TYPE_ID && it.tokenId == ParserSymbol.COMMA_ID && it.tokenLevel == tokenLevel }

			return numberOfCommas + 1
		}
		else
		{
			return -1
		}
	}

	/**
	 * Returns list of the functions parameters.
	 *
	 * @param pos Int
	 * @param tokensList List<Token>
	 *
	 * @return List<FunctionParameter>
	 *
	 * @author Bas Milius
	 */
	private fun getFunctionParameters(pos: Int, tokensList: List<Token>): List<FunctionParameter>
	{
		val functionParameters = ArrayList<FunctionParameter>()
		var cPos = pos + 2
		val tokenLevel = tokensList[pos + 1].tokenLevel
		var pPos = cPos
		var comma: Boolean
		var paren: Boolean
		var end = false
		var paramTkones = ArrayList<Token>()
		var paramStr = ""

		do
		{
			val t = tokensList[cPos]
			comma = false
			paren = false

			if (t.tokenLevel == tokenLevel && t.tokenTypeId == ParserSymbol.TYPE_ID)
			{
				if (t.tokenId == ParserSymbol.RIGHT_PARENTHESES_ID)
					paren = true
				else if (t.tokenId == ParserSymbol.COMMA_ID)
					comma = true
			}

			if ((paren || comma) && cPos > pos + 2)
			{
				functionParameters.add(FunctionParameter(paramTkones, paramStr, pPos, cPos - 1))
				paramTkones = ArrayList()
				paramStr = ""
				pPos = cPos + 1
			}
			else
			{
				paramTkones.add(t)
				paramStr += t.tokenStr
			}

			if (paren)
				end = true
			else
				cPos++
		}
		while (!end)

		return functionParameters
	}

	/**
	 * Gets / returns argument representing given argument name. If argument name exists on the list of known arguments the the initial status of the found argument is remembered, otherwise new argument will be created.
	 *
	 * @param argumentName String
	 * @return ArgumentParameter
	 *
	 * @author Bas Milius
	 */
	private fun getParamArgument(argumentName: String): ArgumentParameter
	{
		val argParam = ArgumentParameter()
		argParam.index = this.getArgumentIndex(argumentName)
		argParam.argument = this.getArgument(argParam.index)
		argParam.presence = FOUND

		if (argParam.argument == null)
		{
			argParam.argument = Argument(argumentName)
			this.argumentsList.add(argParam.argument!!)
			argParam.index = this.argumentsList.size - 1
			argParam.presence = NOT_FOUND
		}
		else
		{
			argParam.initialValue = argParam.argument!!.argumentValue
			argParam.initialType = argParam.argument!!.argumentType
			argParam.argument!!.argumentValue = argParam.argument!!.argumentValue
			argParam.argument!!.argumentType = Argument.FREE_ARGUMENT
		}

		return argParam
	}

	/**
	 * Clears argument parameter.
	 *
	 * @param argParam ArgumentParameter
	 *
	 * @author Bas Milius
	 */
	private fun clearParamArgument(argParam: ArgumentParameter)
	{
		if (argParam.presence == NOT_FOUND)
		{
			this.argumentsList.removeAt(argParam.index)
		}
		else
		{
			argParam.argument?.argumentValue = argParam.initialValue
			argParam.argument?.argumentType = argParam.initialType
		}
	}

	/**
	 * Creates ArraList<Double> containing function parameters.
	 *
	 * @param pos Int
	 *
	 * @return List<Double>
	 *
	 * @author Bas Milius
	 */
	private fun getNumbers(pos: Int): List<Double>
	{
		val numbers = ArrayList<Double>()
		var pn = pos
		val lastIndex = this.tokensList.size - 1
		var isNumber: Boolean
		var end = false

		do
		{
			pn++
			val t = this.tokensList[pn]
			isNumber = false

			if (t.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID && t.tokenId == ParserSymbol.NUMBER_ID)
			{
				isNumber = true
				numbers.add(t.tokenValue)
			}

			if (pn == lastIndex || !isNumber) end = true
		}
		while (!end)

		return numbers
	}

	/**
	 * Gets token value.
	 *
	 * @param tokenIndex Int
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	private fun getTokenValue(tokenIndex: Int): Double
	{
		return this.tokensList[tokenIndex].tokenValue
	}

	/**
	 * Updating missing tokens (i.e. indexes i sum operator). Used when creating internal expressions based on the sublist of tokens.
	 *
	 * @param tokens List<Token>
	 * @param keyWord String
	 * @param tokenId Int
	 * @param tokenTypeId Int
	 *
	 * @author Bas Milius
	 */
	private fun updateMissingTokens(tokens: List<Token>, keyWord: String, tokenId: Int, tokenTypeId: Int)
	{
		for (t in tokens)
			if (t.tokenTypeId == ConstantValue.NaN && t.tokenStr == keyWord)
			{
				t.keyWord = keyWord
				t.tokenId = tokenId
				t.tokenTypeId = tokenTypeId
			}
	}

	/**
	 * Update missing tokens in expression related to iterative operators.
	 *
	 * @param index ArgumentParameter
	 * @param iterParams IterativeOperatorParameters
	 *
	 * @author Bas Milius
	 */
	private fun updateMissingTokens(index: ArgumentParameter, iterParams: IterativeOperatorParameters)
	{
		if (index.presence == Argument.NOT_FOUND)
		{
			updateMissingTokens(iterParams.indexParam.tokens, iterParams.indexParam.paramStr, index.index, Argument.TYPE_ID)
			updateMissingTokens(iterParams.fromParam.tokens, iterParams.indexParam.paramStr, index.index, Argument.TYPE_ID)
			updateMissingTokens(iterParams.toParam.tokens, iterParams.indexParam.paramStr, index.index, Argument.TYPE_ID)
			updateMissingTokens(iterParams.funParam.tokens, iterParams.indexParam.paramStr, index.index, Argument.TYPE_ID)
		}
	}

	/**
	 * Evaluates ranges 'from', 'to', 'delta' for the iterative operator.
	 *
	 * @param index ArgumentParameter
	 * @param iterParams IterativeOperatorParameters
	 *
	 * @author Bas Milius
	 */
	private fun evalFromToDeltaParameters(index: ArgumentParameter, iterParams: IterativeOperatorParameters)
	{
		iterParams.fromExp = Expression(iterParams.fromParam.paramStr, iterParams.fromParam.tokens, this.argumentsList, this.functionsList, this.constantsList, KEEP_ULP_ROUNDING_SETTINGS)
		iterParams.toExp = Expression(iterParams.toParam.paramStr, iterParams.toParam.tokens, this.argumentsList, this.functionsList, this.constantsList, KEEP_ULP_ROUNDING_SETTINGS)
		iterParams.funExp = Expression(iterParams.funParam.paramStr, iterParams.funParam.tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
		iterParams.deltaExp = null

		if (verboseMode)
		{
			iterParams.fromExp?.setVerboseMode()
			iterParams.toExp?.setVerboseMode()
			iterParams.funExp?.setVerboseMode()
		}

		iterParams.from = iterParams.fromExp!!.calculate()
		iterParams.to = iterParams.toExp!!.calculate()
		iterParams.delta = 1.0

		if (iterParams.to < iterParams.from) iterParams.delta = -1.0

		if (iterParams.withDelta)
		{
			iterParams.deltaExp = Expression(iterParams.deltaParam!!.paramStr, iterParams.deltaParam!!.tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)

			if (index.presence == Argument.NOT_FOUND)
			{
				updateMissingTokens(iterParams.deltaParam!!.tokens, iterParams.indexParam.paramStr, index.index, Argument.TYPE_ID)
			}

			if (verboseMode) iterParams.deltaExp?.setVerboseMode()

			iterParams.delta = iterParams.deltaExp!!.calculate()
		}
	}

	/**
	 * Free Arguments handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun FREE_ARGUMENT(pos: Int)
	{
		val argument = this.argumentsList[this.tokensList[pos].tokenId]
		val argumentVerboseMode = argument.getVerboseMode()

		if (verboseMode) argument.setVerboseMode()

		this.setToNumber(pos, argument.argumentValue)

		if (!argumentVerboseMode) argument.setSilentMode()
	}

	/**
	 * Dependent Arguments handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun DEPENDENT_ARGUMENT(pos: Int)
	{
		val argument = this.argumentsList[this.tokensList[pos].tokenId]
		val argumentVerboseMode = argument.getVerboseMode()

		if (verboseMode) argument.setVerboseMode()

		val tokensListSizeBefore = this.tokensList.size
		val tokenBefore = this.tokensList[pos]
		val argumentValue = argument.argumentValue
		val tokensListSizeAfter = this.tokensList.size

		if (tokensListSizeBefore == tokensListSizeAfter)
		{
			val tokenAfter = this.tokensList[pos]
			if (tokenBefore.tokenTypeId == tokenAfter.tokenTypeId && tokenBefore.tokenId == tokenAfter.tokenId)
			{
				this.setToNumber(pos, argumentValue)
			}
		}

		if (argumentVerboseMode) argument.setSilentMode()
	}

	/**
	 * User functions handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun USER_FUNCTION(pos: Int)
	{
		val function: Function
		val `fun` = this.functionsList[this.tokensList[pos].tokenId]

		if (`fun`.getRecursiveMode())
		{
			function = `fun`.clone()
			function.functionExpression?.recursionCallsCounter = this.recursionCallsCounter
		}
		else
		{
			function = `fun`
		}

		val argsNumber = function.parametersNumber

		for (argIdx in 0 until argsNumber)
			function.setArgumentValue(argIdx, this.tokensList[pos + argIdx + 1].tokenValue)

		val functionVerboseMode = function.getVerboseMode()

		if (verboseMode) function.setVerboseMode()

		val tokensListSizeBefore = this.tokensList.size
		val tokenBefore = this.tokensList[pos]
		var value: Double

		try
		{
			value = function.calculate()
		}
		catch (soe: StackOverflowError)
		{
			value = Double.NaN
			errorMessage = soe.message!!
		}

		val tokensListSizeAfter = this.tokensList.size

		if (tokensListSizeBefore == tokensListSizeAfter)
		{
			val tokenAfter = this.tokensList[pos]

			if (tokenBefore.tokenTypeId == tokenAfter.tokenTypeId && tokenBefore.tokenId == tokenAfter.tokenId)
			{
				this.setToNumber(pos, value)
				this.tokensList[pos].tokenLevel = this.tokensList[pos].tokenLevel - 1

				for (argIdx in argsNumber downTo 1)
					this.tokensList.removeAt(pos + argIdx)
			}
		}

		if (!functionVerboseMode)
			function.setSilentMode()
	}

	/**
	 * User constants handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun USER_CONSTANT(pos: Int)
	{
		val constant = this.constantsList[this.tokensList[pos].tokenId]
		this.setToNumber(pos, constant.getConstantValue())
	}

	/**
	 * Recursive arguments handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun RECURSIVE_ARGUMENT(pos: Int)
	{
		val index = this.tokensList[pos + 1].tokenValue
		val argument = this.argumentsList[this.tokensList[pos].tokenId] as RecursiveArgument
		val argumentVerboseMode = argument.getVerboseMode()

		if (verboseMode) argument.setVerboseMode()

		val result = argument.getArgumentValue(index)
		this.f1SetDecreaseRemove(pos, result)

		if (!argumentVerboseMode) argument.setSilentMode()
	}

	/**
	 * Constants handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CONSTANT(pos: Int)
	{
		var constValue = Double.NaN

		when (tokensList[pos].tokenId)
		{
			ConstantValue.PI_ID -> constValue = MathConstants.PI
			ConstantValue.EULER_ID -> constValue = MathConstants.E
			ConstantValue.EULER_MASCHERONI_ID -> constValue = MathConstants.EULER_MASCHERONI
			ConstantValue.GOLDEN_RATIO_ID -> constValue = MathConstants.GOLDEN_RATIO
			ConstantValue.PLASTIC_ID -> constValue = MathConstants.PLASTIC
			ConstantValue.EMBREE_TREFETHEN_ID -> constValue = MathConstants.EMBREE_TREFETHEN
			ConstantValue.FEIGENBAUM_DELTA_ID -> constValue = MathConstants.FEIGENBAUM_DELTA
			ConstantValue.FEIGENBAUM_ALFA_ID -> constValue = MathConstants.FEIGENBAUM_ALFA
			ConstantValue.TWIN_PRIME_ID -> constValue = MathConstants.TWIN_PRIME
			ConstantValue.MEISSEL_MERTEENS_ID -> constValue = MathConstants.MEISSEL_MERTEENS
			ConstantValue.BRAUN_TWIN_PRIME_ID -> constValue = MathConstants.BRAUN_TWIN_PRIME
			ConstantValue.BRAUN_PRIME_QUADR_ID -> constValue = MathConstants.BRAUN_PRIME_QUADR
			ConstantValue.BRUIJN_NEWMAN_ID -> constValue = MathConstants.BRUIJN_NEWMAN
			ConstantValue.CATALAN_ID -> constValue = MathConstants.CATALAN
			ConstantValue.LANDAU_RAMANUJAN_ID -> constValue = MathConstants.LANDAU_RAMANUJAN
			ConstantValue.VISWANATH_ID -> constValue = MathConstants.VISWANATH
			ConstantValue.LEGENDRE_ID -> constValue = MathConstants.LEGENDRE
			ConstantValue.RAMANUJAN_SOLDNER_ID -> constValue = MathConstants.RAMANUJAN_SOLDNER
			ConstantValue.ERDOS_BORWEIN_ID -> constValue = MathConstants.ERDOS_BORWEIN
			ConstantValue.BERNSTEIN_ID -> constValue = MathConstants.BERNSTEIN
			ConstantValue.GAUSS_KUZMIN_WIRSING_ID -> constValue = MathConstants.GAUSS_KUZMIN_WIRSING
			ConstantValue.HAFNER_SARNAK_MCCURLEY_ID -> constValue = MathConstants.HAFNER_SARNAK_MCCURLEY
			ConstantValue.GOLOMB_DICKMAN_ID -> constValue = MathConstants.GOLOMB_DICKMAN
			ConstantValue.CAHEN_ID -> constValue = MathConstants.CAHEN
			ConstantValue.LAPLACE_LIMIT_ID -> constValue = MathConstants.LAPLACE_LIMIT
			ConstantValue.ALLADI_GRINSTEAD_ID -> constValue = MathConstants.ALLADI_GRINSTEAD
			ConstantValue.LENGYEL_ID -> constValue = MathConstants.LENGYEL
			ConstantValue.LEVY_ID -> constValue = MathConstants.LEVY
			ConstantValue.APERY_ID -> constValue = MathConstants.APERY
			ConstantValue.MILLS_ID -> constValue = MathConstants.MILLS
			ConstantValue.BACKHOUSE_ID -> constValue = MathConstants.BACKHOUSE
			ConstantValue.PORTER_ID -> constValue = MathConstants.PORTER
			ConstantValue.LIEB_QUARE_ICE_ID -> constValue = MathConstants.LIEB_QUARE_ICE
			ConstantValue.NIVEN_ID -> constValue = MathConstants.NIVEN
			ConstantValue.SIERPINSKI_ID -> constValue = MathConstants.SIERPINSKI
			ConstantValue.KHINCHIN_ID -> constValue = MathConstants.KHINCHIN
			ConstantValue.FRANSEN_ROBINSON_ID -> constValue = MathConstants.FRANSEN_ROBINSON
			ConstantValue.LANDAU_ID -> constValue = MathConstants.LANDAU
			ConstantValue.PARABOLIC_ID -> constValue = MathConstants.PARABOLIC
			ConstantValue.OMEGA_ID -> constValue = MathConstants.OMEGA
			ConstantValue.MRB_ID -> constValue = MathConstants.MRB
			ConstantValue.LI2_ID -> constValue = MathConstants.LI2
			ConstantValue.GOMPERTZ_ID -> constValue = MathConstants.GOMPERTZ
			ConstantValue.LIGHT_SPEED_ID -> constValue = PhysicalConstants.LIGHT_SPEED
			ConstantValue.GRAVITATIONAL_CONSTANT_ID -> constValue = PhysicalConstants.GRAVITATIONAL_CONSTANT
			ConstantValue.GRAVIT_ACC_EARTH_ID -> constValue = PhysicalConstants.GRAVIT_ACC_EARTH
			ConstantValue.PLANCK_CONSTANT_ID -> constValue = PhysicalConstants.PLANCK_CONSTANT
			ConstantValue.PLANCK_CONSTANT_REDUCED_ID -> constValue = PhysicalConstants.PLANCK_CONSTANT_REDUCED
			ConstantValue.PLANCK_LENGTH_ID -> constValue = PhysicalConstants.PLANCK_LENGTH
			ConstantValue.PLANCK_MASS_ID -> constValue = PhysicalConstants.PLANCK_MASS
			ConstantValue.PLANCK_TIME_ID -> constValue = PhysicalConstants.PLANCK_TIME
			ConstantValue.LIGHT_YEAR_ID -> constValue = AstronomicalConstants.LIGHT_YEAR
			ConstantValue.ASTRONOMICAL_UNIT_ID -> constValue = AstronomicalConstants.ASTRONOMICAL_UNIT
			ConstantValue.PARSEC_ID -> constValue = AstronomicalConstants.PARSEC
			ConstantValue.KILOPARSEC_ID -> constValue = AstronomicalConstants.KILOPARSEC
			ConstantValue.EARTH_RADIUS_EQUATORIAL_ID -> constValue = AstronomicalConstants.EARTH_RADIUS_EQUATORIAL
			ConstantValue.EARTH_RADIUS_POLAR_ID -> constValue = AstronomicalConstants.EARTH_RADIUS_POLAR
			ConstantValue.EARTH_RADIUS_MEAN_ID -> constValue = AstronomicalConstants.EARTH_RADIUS_MEAN
			ConstantValue.EARTH_MASS_ID -> constValue = AstronomicalConstants.EARTH_MASS
			ConstantValue.EARTH_SEMI_MAJOR_AXIS_ID -> constValue = AstronomicalConstants.EARTH_SEMI_MAJOR_AXIS
			ConstantValue.MOON_RADIUS_MEAN_ID -> constValue = AstronomicalConstants.MOON_RADIUS_MEAN
			ConstantValue.MOON_MASS_ID -> constValue = AstronomicalConstants.MOON_MASS
			ConstantValue.MONN_SEMI_MAJOR_AXIS_ID -> constValue = AstronomicalConstants.MONN_SEMI_MAJOR_AXIS
			ConstantValue.SOLAR_RADIUS_ID -> constValue = AstronomicalConstants.SOLAR_RADIUS
			ConstantValue.SOLAR_MASS_ID -> constValue = AstronomicalConstants.SOLAR_MASS
			ConstantValue.MERCURY_RADIUS_MEAN_ID -> constValue = AstronomicalConstants.MERCURY_RADIUS_MEAN
			ConstantValue.MERCURY_MASS_ID -> constValue = AstronomicalConstants.MERCURY_MASS
			ConstantValue.MERCURY_SEMI_MAJOR_AXIS_ID -> constValue = AstronomicalConstants.MERCURY_SEMI_MAJOR_AXIS
			ConstantValue.VENUS_RADIUS_MEAN_ID -> constValue = AstronomicalConstants.VENUS_RADIUS_MEAN
			ConstantValue.VENUS_MASS_ID -> constValue = AstronomicalConstants.VENUS_MASS
			ConstantValue.VENUS_SEMI_MAJOR_AXIS_ID -> constValue = AstronomicalConstants.VENUS_SEMI_MAJOR_AXIS
			ConstantValue.MARS_RADIUS_MEAN_ID -> constValue = AstronomicalConstants.MARS_RADIUS_MEAN
			ConstantValue.MARS_MASS_ID -> constValue = AstronomicalConstants.MARS_MASS
			ConstantValue.MARS_SEMI_MAJOR_AXIS_ID -> constValue = AstronomicalConstants.MARS_SEMI_MAJOR_AXIS
			ConstantValue.JUPITER_RADIUS_MEAN_ID -> constValue = AstronomicalConstants.JUPITER_RADIUS_MEAN
			ConstantValue.JUPITER_MASS_ID -> constValue = AstronomicalConstants.JUPITER_MASS
			ConstantValue.JUPITER_SEMI_MAJOR_AXIS_ID -> constValue = AstronomicalConstants.JUPITER_SEMI_MAJOR_AXIS
			ConstantValue.SATURN_RADIUS_MEAN_ID -> constValue = AstronomicalConstants.SATURN_RADIUS_MEAN
			ConstantValue.SATURN_MASS_ID -> constValue = AstronomicalConstants.SATURN_MASS
			ConstantValue.SATURN_SEMI_MAJOR_AXIS_ID -> constValue = AstronomicalConstants.SATURN_SEMI_MAJOR_AXIS
			ConstantValue.URANUS_RADIUS_MEAN_ID -> constValue = AstronomicalConstants.URANUS_RADIUS_MEAN
			ConstantValue.URANUS_MASS_ID -> constValue = AstronomicalConstants.URANUS_MASS
			ConstantValue.URANUS_SEMI_MAJOR_AXIS_ID -> constValue = AstronomicalConstants.URANUS_SEMI_MAJOR_AXIS
			ConstantValue.NEPTUNE_RADIUS_MEAN_ID -> constValue = AstronomicalConstants.NEPTUNE_RADIUS_MEAN
			ConstantValue.NEPTUNE_MASS_ID -> constValue = AstronomicalConstants.NEPTUNE_MASS
			ConstantValue.NEPTUNE_SEMI_MAJOR_AXIS_ID -> constValue = AstronomicalConstants.NEPTUNE_SEMI_MAJOR_AXIS
			ConstantValue.TRUE_ID -> constValue = BooleanAlgebra.TRUE.toDouble()
			ConstantValue.FALSE_ID -> constValue = BooleanAlgebra.FALSE.toDouble()
			ConstantValue.NAN_ID -> constValue = MathConstants.NOT_A_NUMBER
		}

		this.setToNumber(pos, constValue)
	}

	/**
	 * Constants handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun UNIT(pos: Int)
	{
		var unitValue = Double.NaN

		when (tokensList[pos].tokenId)
		{
			Unit.PERC_ID -> unitValue = Units.PERC
			Unit.PROMIL_ID -> unitValue = Units.PROMIL
			Unit.YOTTA_ID -> unitValue = Units.YOTTA
			Unit.ZETTA_ID -> unitValue = Units.ZETTA
			Unit.EXA_ID -> unitValue = Units.EXA
			Unit.PETA_ID -> unitValue = Units.PETA
			Unit.TERA_ID -> unitValue = Units.TERA
			Unit.GIGA_ID -> unitValue = Units.GIGA
			Unit.MEGA_ID -> unitValue = Units.MEGA
			Unit.KILO_ID -> unitValue = Units.KILO
			Unit.HECTO_ID -> unitValue = Units.HECTO
			Unit.DECA_ID -> unitValue = Units.DECA
			Unit.DECI_ID -> unitValue = Units.DECI
			Unit.CENTI_ID -> unitValue = Units.CENTI
			Unit.MILLI_ID -> unitValue = Units.MILLI
			Unit.MICRO_ID -> unitValue = Units.MICRO
			Unit.NANO_ID -> unitValue = Units.NANO
			Unit.PICO_ID -> unitValue = Units.PICO
			Unit.FEMTO_ID -> unitValue = Units.FEMTO
			Unit.ATTO_ID -> unitValue = Units.ATTO
			Unit.ZEPTO_ID -> unitValue = Units.ZEPTO
			Unit.YOCTO_ID -> unitValue = Units.YOCTO
			Unit.METRE_ID -> unitValue = Units.METRE
			Unit.KILOMETRE_ID -> unitValue = Units.KILOMETRE
			Unit.CENTIMETRE_ID -> unitValue = Units.CENTIMETRE
			Unit.MILLIMETRE_ID -> unitValue = Units.MILLIMETRE
			Unit.INCH_ID -> unitValue = Units.INCH
			Unit.YARD_ID -> unitValue = Units.YARD
			Unit.FEET_ID -> unitValue = Units.FEET
			Unit.MILE_ID -> unitValue = Units.MILE
			Unit.NAUTICAL_MILE_ID -> unitValue = Units.NAUTICAL_MILE
			Unit.METRE2_ID -> unitValue = Units.METRE2
			Unit.CENTIMETRE2_ID -> unitValue = Units.CENTIMETRE2
			Unit.MILLIMETRE2_ID -> unitValue = Units.MILLIMETRE2
			Unit.ARE_ID -> unitValue = Units.ARE
			Unit.HECTARE_ID -> unitValue = Units.HECTARE
			Unit.ACRE_ID -> unitValue = Units.ACRE
			Unit.KILOMETRE2_ID -> unitValue = Units.KILOMETRE2
			Unit.MILLIMETRE3_ID -> unitValue = Units.MILLIMETRE3
			Unit.CENTIMETRE3_ID -> unitValue = Units.CENTIMETRE3
			Unit.METRE3_ID -> unitValue = Units.METRE3
			Unit.KILOMETRE3_ID -> unitValue = Units.KILOMETRE3
			Unit.MILLILITRE_ID -> unitValue = Units.MILLILITRE
			Unit.LITRE_ID -> unitValue = Units.LITRE
			Unit.GALLON_ID -> unitValue = Units.GALLON
			Unit.PINT_ID -> unitValue = Units.PINT
			Unit.SECOND_ID -> unitValue = Units.SECOND
			Unit.MILLISECOND_ID -> unitValue = Units.MILLISECOND
			Unit.MINUTE_ID -> unitValue = Units.MINUTE
			Unit.HOUR_ID -> unitValue = Units.HOUR
			Unit.DAY_ID -> unitValue = Units.DAY
			Unit.WEEK_ID -> unitValue = Units.WEEK
			Unit.JULIAN_YEAR_ID -> unitValue = Units.JULIAN_YEAR
			Unit.KILOGRAM_ID -> unitValue = Units.KILOGRAM
			Unit.GRAM_ID -> unitValue = Units.GRAM
			Unit.MILLIGRAM_ID -> unitValue = Units.MILLIGRAM
			Unit.DECAGRAM_ID -> unitValue = Units.DECAGRAM
			Unit.TONNE_ID -> unitValue = Units.TONNE
			Unit.OUNCE_ID -> unitValue = Units.OUNCE
			Unit.POUND_ID -> unitValue = Units.POUND
			Unit.BIT_ID -> unitValue = Units.BIT
			Unit.KILOBIT_ID -> unitValue = Units.KILOBIT
			Unit.MEGABIT_ID -> unitValue = Units.MEGABIT
			Unit.GIGABIT_ID -> unitValue = Units.GIGABIT
			Unit.TERABIT_ID -> unitValue = Units.TERABIT
			Unit.PETABIT_ID -> unitValue = Units.PETABIT
			Unit.EXABIT_ID -> unitValue = Units.EXABIT
			Unit.ZETTABIT_ID -> unitValue = Units.ZETTABIT
			Unit.YOTTABIT_ID -> unitValue = Units.YOTTABIT
			Unit.BYTE_ID -> unitValue = Units.BYTE
			Unit.KILOBYTE_ID -> unitValue = Units.KILOBYTE
			Unit.MEGABYTE_ID -> unitValue = Units.MEGABYTE
			Unit.GIGABYTE_ID -> unitValue = Units.GIGABYTE
			Unit.TERABYTE_ID -> unitValue = Units.TERABYTE
			Unit.PETABYTE_ID -> unitValue = Units.PETABYTE
			Unit.EXABYTE_ID -> unitValue = Units.EXABYTE
			Unit.ZETTABYTE_ID -> unitValue = Units.ZETTABYTE
			Unit.YOTTABYTE_ID -> unitValue = Units.YOTTABYTE
			Unit.JOULE_ID -> unitValue = Units.JOULE
			Unit.ELECTRONO_VOLT_ID -> unitValue = Units.ELECTRONO_VOLT
			Unit.KILO_ELECTRONO_VOLT_ID -> unitValue = Units.KILO_ELECTRONO_VOLT
			Unit.MEGA_ELECTRONO_VOLT_ID -> unitValue = Units.MEGA_ELECTRONO_VOLT
			Unit.GIGA_ELECTRONO_VOLT_ID -> unitValue = Units.GIGA_ELECTRONO_VOLT
			Unit.TERA_ELECTRONO_VOLT_ID -> unitValue = Units.TERA_ELECTRONO_VOLT
			Unit.METRE_PER_SECOND_ID -> unitValue = Units.METRE_PER_SECOND
			Unit.KILOMETRE_PER_HOUR_ID -> unitValue = Units.KILOMETRE_PER_HOUR
			Unit.MILE_PER_HOUR_ID -> unitValue = Units.MILE_PER_HOUR
			Unit.KNOT_ID -> unitValue = Units.KNOT
			Unit.METRE_PER_SECOND2_ID -> unitValue = Units.METRE_PER_SECOND2
			Unit.KILOMETRE_PER_HOUR2_ID -> unitValue = Units.KILOMETRE_PER_HOUR2
			Unit.MILE_PER_HOUR2_ID -> unitValue = Units.MILE_PER_HOUR2
			Unit.RADIAN_ARC_ID -> unitValue = Units.RADIAN_ARC
			Unit.DEGREE_ARC_ID -> unitValue = Units.DEGREE_ARC
			Unit.MINUTE_ARC_ID -> unitValue = Units.MINUTE_ARC
			Unit.SECOND_ARC_ID -> unitValue = Units.SECOND_ARC
		}

		setToNumber(pos, unitValue)
	}

	/**
	 * Random Variables handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun RANDOM_VARIABLE(pos: Int)
	{
		var rndVar = Double.NaN

		when (tokensList[pos].tokenId)
		{
			RandomVariable.UNIFORM_ID -> rndVar = ProbabilityDistributions.rndUniformContinuous(ProbabilityDistributions.randomGenerator)
			RandomVariable.INT_ID -> rndVar = ProbabilityDistributions.rndInteger(ProbabilityDistributions.randomGenerator).toDouble()
			RandomVariable.INT1_ID -> rndVar = ProbabilityDistributions.rndInteger(-10, 10, ProbabilityDistributions.randomGenerator)
			RandomVariable.INT2_ID -> rndVar = ProbabilityDistributions.rndInteger(-100, 100, ProbabilityDistributions.randomGenerator)
			RandomVariable.INT3_ID -> rndVar = ProbabilityDistributions.rndInteger(-1000, 1000, ProbabilityDistributions.randomGenerator)
			RandomVariable.INT4_ID -> rndVar = ProbabilityDistributions.rndInteger(-10000, 10000, ProbabilityDistributions.randomGenerator)
			RandomVariable.INT5_ID -> rndVar = ProbabilityDistributions.rndInteger(-100000, 100000, ProbabilityDistributions.randomGenerator)
			RandomVariable.INT6_ID -> rndVar = ProbabilityDistributions.rndInteger(-1000000, 1000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.INT7_ID -> rndVar = ProbabilityDistributions.rndInteger(-10000000, 10000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.INT8_ID -> rndVar = ProbabilityDistributions.rndInteger(-100000000, 100000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.INT9_ID -> rndVar = ProbabilityDistributions.rndInteger(-1000000000, 1000000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 2147483646, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_1_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 10, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_2_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 100, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_3_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 1000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_4_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 10000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_5_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 100000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_6_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 1000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_7_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 10000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_8_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 100000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT0_9_ID -> rndVar = ProbabilityDistributions.rndInteger(0, 1000000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 2147483646, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_1_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 10, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_2_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 100, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_3_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 1000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_4_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 10000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_5_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 100000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_6_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 1000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_7_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 10000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_8_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 100000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NAT1_9_ID -> rndVar = ProbabilityDistributions.rndInteger(1, 1000000000, ProbabilityDistributions.randomGenerator)
			RandomVariable.NOR_ID -> rndVar = ProbabilityDistributions.rndNormal(0.0, 1.0, ProbabilityDistributions.randomGenerator)
		}

		this.setToNumber(pos, rndVar)
	}

	/**
	 * Power handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun POWER(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, MathFunctions.power(a, b), true)
	}

	/**
	 * Modulo handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MODULO(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, MathFunctions.mod(a, b))
	}

	/**
	 * Division handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun DIVIDE(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, MathFunctions.div(a, b), true)
	}

	/**
	 * Multiplication handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MULTIPLY(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, a * b, true)
	}

	/**
	 * Addition handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun PLUS(pos: Int)
	{
		val b = this.tokensList[pos + 1]

		if (pos > 0)
		{
			val a = this.tokensList[pos - 1]

			if (a.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID && b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID)
			{
				this.opSetDecreaseRemove(pos, a.tokenValue + b.tokenValue, true)
			}
			else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID)
			{
				this.setToNumber(pos, b.tokenValue)
				this.tokensList.removeAt(pos + 1)
			}
		}
		else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID)
		{
			this.setToNumber(pos, b.tokenValue)
			this.tokensList.removeAt(pos + 1)
		}
	}

	/**
	 * Subtraction handling.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MINUS(pos: Int)
	{
		val b = this.tokensList[pos + 1]

		if (pos > 0)
		{
			val a = this.tokensList[pos - 1]

			if (a.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID && b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID)
			{
				this.opSetDecreaseRemove(pos, a.tokenValue - b.tokenValue, true)
			}
			else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID)
			{
				this.setToNumber(pos, -b.tokenValue)
				this.tokensList.removeAt(pos + 1)
			}
		}
		else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID)
		{
			this.setToNumber(pos, -b.tokenValue)
			this.tokensList.removeAt(pos + 1)
		}
	}

	/**
	 * Logical AND.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun AND(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.and(a, b))
	}

	/**
	 * Logical OR.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun OR(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.or(a, b))
	}

	/**
	 * Logical NAND.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NAND(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.nand(a, b))
	}

	/**
	 * Logical NOR.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NOR(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.nor(a, b))
	}

	/**
	 * Logical XOR.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun XOR(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.xor(a, b))
	}

	/**
	 * Logical IMP.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun IMP(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.imp(a, b))
	}

	/**
	 * Logical CIMP.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CIMP(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.cimp(a, b))
	}

	/**
	 * Logical NIMP.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NIMP(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.nimp(a, b))
	}

	/**
	 * Logical CNIMP.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CNIMP(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.cnimp(a, b))
	}

	/**
	 * Logical EQV.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun EQV(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BooleanAlgebra.eqv(a, b))
	}

	/**
	 * Logical negation.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NEG(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.setToNumber(pos, BooleanAlgebra.not(a))
		this.tokensList.removeAt(pos + 1)
	}

	/**
	 * Equality relation.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun EQ(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BinaryRelations.eq(a, b))
	}

	/**
	 * Not equals.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NEQ(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BinaryRelations.neq(a, b))
	}

	/**
	 * Lower than.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun LT(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BinaryRelations.lt(a, b))
	}

	/**
	 * Greater than.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun GT(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BinaryRelations.gt(a, b))
	}

	/**
	 * Lower or equal.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun LEQ(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BinaryRelations.leq(a, b))
	}

	/**
	 * Greater or equal.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun GEQ(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		val b = this.getTokenValue(pos + 1)
		this.opSetDecreaseRemove(pos, BinaryRelations.geq(a, b))
	}

	/**
	 * Bitwise COMPL.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BITWISE_COMPL(pos: Int)
	{
		val a = this.getTokenValue(pos + 1).toLong()
		this.setToNumber(pos, a.inv().toDouble())
		this.tokensList.removeAt(pos + 1)
	}

	/**
	 * Bitwise AND.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BITWISE_AND(pos: Int)
	{
		val a = this.getTokenValue(pos - 1).toLong()
		val b = this.getTokenValue(pos + 1).toLong()
		this.opSetDecreaseRemove(pos, (a and b).toDouble())
	}

	/**
	 * Bitwise OR.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BITWISE_OR(pos: Int)
	{
		val a = this.getTokenValue(pos - 1).toLong()
		val b = this.getTokenValue(pos + 1).toLong()
		this.opSetDecreaseRemove(pos, (a or b).toDouble())
	}

	/**
	 * Bitwise XOR.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BITWISE_XOR(pos: Int)
	{
		val a = this.getTokenValue(pos - 1).toLong()
		val b = this.getTokenValue(pos + 1).toLong()
		this.opSetDecreaseRemove(pos, (a xor b).toDouble())
	}

	/**
	 * Bitwise LEFT SHIFT.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BITWISE_LEFT_SHIFT(pos: Int)
	{
		val a = this.getTokenValue(pos - 1).toLong()
		val b = this.getTokenValue(pos + 1).toInt()
		this.opSetDecreaseRemove(pos, (a shl b).toDouble())
	}

	/**
	 * Bitwise RIGHT SHIFT.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BITWISE_RIGHT_SHIFT(pos: Int)
	{
		val a = this.getTokenValue(pos - 1).toLong()
		val b = this.getTokenValue(pos + 1).toInt()
		this.opSetDecreaseRemove(pos, (a shr b).toDouble())
	}

	/**
	 * Sine function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SIN(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.sin(a))
	}

	/**
	 * Cosine / Trigonometric functions. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun COS(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.cos(a))
	}

	/**
	 * Tangent / Trigonometric functions. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun TAN(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.tan(a))
	}

	/**
	 * Cotangent / Trigonometric functions. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CTAN(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.ctan(a))
	}

	/**
	 * Secant / Trigonometric functions. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SEC(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.sec(a))
	}

	/**
	 * Cosecant / Trigonometric functions. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun COSEC(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.cosec(a))
	}

	/**
	 * Arcus sine / Inverse trigonometric functions. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ASIN(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.asin(a))
	}

	/**
	 * Arcus cosine / Inverse trigonometric functions. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ACOS(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.acos(a))
	}

	/**
	 * Arcus tangent / Inverse trigonometric functions. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ATAN(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.atan(a))
	}

	/**
	 * Arcus cotangent / Inverse trigonometric functions. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ACTAN(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.actan(a))
	}

	/**
	 * Natural logarithm (base e). Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun LN(pos: Int)
	{
		val a = getTokenValue(pos + 1)
		f1SetDecreaseRemove(pos, MathFunctions.ln(a))
	}

	/**
	 * Logarithm - base 2. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun LOG2(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.log2(a))
	}

	/**
	 * Logarithm - base 10. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun LOG10(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.log10(a))
	}

	/**
	 * Converts degrees to radius. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun RAD(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.rad(a))
	}

	/**
	 * Exponential function. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun EXP(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.exp(a))
	}

	/**
	 * Square root. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SQRT(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.sqrt(a))
	}

	/**
	 * Hyperbolic sine. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SINH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.sinh(a))
	}

	/**
	 * Hyperbolic cosine. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun COSH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.cosh(a))
	}

	/**
	 * Hyperbolic tangent. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun TANH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.tanh(a))
	}

	/**
	 * Hyperbolic cotangent. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun COTH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.coth(a))
	}

	/**
	 * Hyperbolic secant. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SECH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.sech(a))
	}

	/**
	 * Hyperbolic cosecant. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CSCH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.csch(a))
	}

	/**
	 * Converts radius to degrees. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun DEG(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.deg(a))
	}

	/**
	 * Absolut value. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ABS(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.abs(a))
	}

	/**
	 * Signum function. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SGN(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.sgn(a))
	}

	/**
	 * Floor function. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun FLOOR(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.floor(a))
	}

	/**
	 * Ceil function. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CEIL(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.ceil(a))
	}

	/**
	 * Arcus hyperbolic sine. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARSINH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.arsinh(a))
	}

	/**
	 * Arcus hyperbolic cosine. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARCOSH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.arcosh(a))
	}

	/**
	 * Arcus hyperbolic tangent. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARTANH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.artanh(a))
	}

	/**
	 * Arcus hyperbolic cotangent. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARCOTH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.arcoth(a))
	}

	/**
	 * Arcus hyperbolic secant. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARSECH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.arsech(a))
	}

	/**
	 * Arcus hyperbolic cosecant. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARCSCH(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.arcsch(a))
	}

	/**
	 * SA / sinc normalized.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SA(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.sa(a))
	}

	/**
	 * Sinc unnormalized.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SINC(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.sinc(a))
	}

	/**
	 * Bell numbers.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BELL_NUMBER(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.bellNumber(n))
	}

	/**
	 * Lucas numbers.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun LUCAS_NUMBER(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.lucasNumber(n))
	}

	/**
	 * Fibonacci numbers.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun FIBONACCI_NUMBER(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.fibonacciNumber(n))
	}

	/**
	 * Harmonic numbers.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun HARMONIC_NUMBER(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.harmonicNumber(n))
	}

	/**
	 * Prime test.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun IS_PRIME(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, NumberTheory.primeTest(n))
	}

	/**
	 * Prime counting.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun PRIME_COUNT(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, NumberTheory.primeCount(n))
	}

	/**
	 * Exponential integral function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun EXP_INT(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, SpecialFunctions.exponentialIntegralEi(x))
	}

	/**
	 * Logarithmic exponential integral function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun LOG_INT(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, SpecialFunctions.logarithmicIntegralLi(x))
	}

	/**
	 * Offset logarithmic exponential integral function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun OFF_LOG_INT(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, SpecialFunctions.offsetLogarithmicIntegralLi(x))
	}

	/**
	 * Factorilal function. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun FACT(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		this.setToNumber(pos, MathFunctions.factorial(a))
		this.tokensList.removeAt(pos - 1)
	}

	/**
	 * Percentage. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun PERC(pos: Int)
	{
		val a = this.getTokenValue(pos - 1)
		this.setToNumber(pos, a * Units.PERC)
		this.tokensList.removeAt(pos - 1)
	}

	/**
	 * Negation. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NOT(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, BooleanAlgebra.not(a))
	}

	/**
	 * Gauss error function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun GAUSS_ERF(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, SpecialFunctions.erf(x))
	}

	/**
	 * Gauss complementary error function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun GAUSS_ERFC(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, SpecialFunctions.erfc(x))
	}

	/**
	 * Inverse of Gauss error function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun GAUSS_ERF_INV(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, SpecialFunctions.erfInv(x))
	}

	/**
	 * Inverse of Gauss complementary error function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun GAUSS_ERFC_INV(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, SpecialFunctions.erfcInv(x))
	}

	/**
	 * Unit in The Last Place. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ULP(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.ulp(x))
	}

	/**
	 * Is Not-a-Number. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ISNAN(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, if (x.isNaN()) BooleanAlgebra.TRUE.toDouble() else BooleanAlgebra.FALSE.toDouble())
	}

	/**
	 * Number of digits in base 10. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NDIG10(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, NumberTheory.numberOfDigits(x))
	}

	/**
	 * Number of prime factors - distinct. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NFACT(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, NumberTheory.numberOfPrimeFactors(n))
	}

	/**
	 * Arcuus secant. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARCSEC(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.asec(x))
	}

	/**
	 * Arcuus cosecant. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARCCSC(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		this.f1SetDecreaseRemove(pos, MathFunctions.acosec(x))
	}

	/**
	 * Logarithm. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun LOG(pos: Int)
	{
		val b = this.getTokenValue(pos + 1)
		val a = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.log(a, b))
	}

	/**
	 * Modulo. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MOD(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		val b = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.mod(a, b))
	}

	/**
	 * Binomial Coefficient.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BINOM_COEFF(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		val b = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.binomCoeff(a, b))
	}

	/**
	 * Bernoulli Number.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BERNOULLI_NUMBER(pos: Int)
	{
		val m = this.getTokenValue(pos + 1)
		val n = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.bernoulliNumber(m, n))
	}

	/**
	 * Stirling number of the first kind.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun STIRLING1_NUMBER(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		val k = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.Stirling1Number(n, k))
	}

	/**
	 * Stirling number of the second kind.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun STIRLING2_NUMBER(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		val k = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.Stirling2Number(n, k))
	}

	/**
	 * Worpitzky number.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun WORPITZKY_NUMBER(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		val k = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.worpitzkyNumber(n, k))
	}

	/**
	 * Euler number.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun EULER_NUMBER(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		val k = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.eulerNumber(n, k))
	}

	/**
	 * Kronecker delta.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun KRONECKER_DELTA(pos: Int)
	{
		val i = this.getTokenValue(pos + 1)
		val j = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.kroneckerDelta(i, j))
	}

	/**
	 * Euler polynomial.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun EULER_POLYNOMIAL(pos: Int)
	{
		val m = this.getTokenValue(pos + 1)
		val x = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.eulerPolynomial(m, x))
	}

	/**
	 * Harmonic numbers.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun HARMONIC2_NUMBER(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		val n = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.harmonicNumber(x, n))
	}

	/**
	 * Decimal rounding.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ROUND(pos: Int)
	{
		val value = this.getTokenValue(pos + 1)
		val places = this.getTokenValue(pos + 2).toInt()
		this.f2SetDecreaseRemove(pos, MathFunctions.round(value, places.toDouble()))
	}

	/**
	 * Random number - Uniform Continuous distribution.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun RND_VAR_UNIFORM_CONT(pos: Int)
	{
		val a = this.getTokenValue(pos + 1)
		val b = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, ProbabilityDistributions.rndUniformContinuous(a, b, ProbabilityDistributions.randomGenerator))
	}

	/**
	 * Random number - Uniform Discrete distribution.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun RND_VAR_UNIFORM_DISCR(pos: Int)
	{
		val a = this.getTokenValue(pos + 1).toInt()
		val b = this.getTokenValue(pos + 2).toInt()
		this.f2SetDecreaseRemove(pos, ProbabilityDistributions.rndInteger(a, b, ProbabilityDistributions.randomGenerator))
	}

	/**
	 * Random number - Normal distribution.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun RND_NORMAL(pos: Int)
	{
		val mean = this.getTokenValue(pos + 1)
		val stddev = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, ProbabilityDistributions.rndNormal(mean, stddev, ProbabilityDistributions.randomGenerator))
	}

	/**
	 * Number of digits in given numeral system.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NDIG(pos: Int)
	{
		val number = this.getTokenValue(pos + 1)
		val numeralSystemBase = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, NumberTheory.numberOfDigits(number, numeralSystemBase))
	}

	/**
	 * Digit at position - base 10 numeral system.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun DIGIT10(pos: Int)
	{
		val number = this.getTokenValue(pos + 1)
		val position = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, NumberTheory.digitAtPosition(number, position))
	}

	/**
	 * Prime factor value.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun FACTVAL(pos: Int)
	{
		val number = this.getTokenValue(pos + 1)
		val id = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, NumberTheory.primeFactorValue(number, id))
	}

	/**
	 * Prime factor value exponent.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun FACTEXP(pos: Int)
	{
		val number = this.getTokenValue(pos + 1)
		val id = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, NumberTheory.primeFactorExponent(number, id))
	}

	/**
	 * Nth order root.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ROOT(pos: Int)
	{
		val n = this.getTokenValue(pos + 1)
		val x = this.getTokenValue(pos + 2)
		this.f2SetDecreaseRemove(pos, MathFunctions.root(n, x))
	}

	/**
	 * IF function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun IF_CONDITION(pos: Int)
	{
		val ifParams = this.getFunctionParameters(pos, this.tokensList)
		val (tokens, paramStr) = ifParams[0]
		val ifExp = Expression(paramStr, tokens, this.argumentsList, this.functionsList, this.constantsList, KEEP_ULP_ROUNDING_SETTINGS)

		if (verboseMode) ifExp.setVerboseMode()

		this.ifSetRemove(pos, ifExp.calculate())
	}

	/**
	 * IFF function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun IFF(pos: Int)
	{
		val iffParams = getFunctionParameters(pos, this.tokensList)
		var iffParam = iffParams[0]
		val parametersNumber = iffParams.size
		val trueParamNumber: Int
		var paramNumber = 1
		var iffExp: Expression
		var iffCon: Boolean

		do
		{
			iffExp = Expression(iffParam.paramStr, iffParam.tokens, this.argumentsList, this.functionsList, this.constantsList, KEEP_ULP_ROUNDING_SETTINGS)

			if (verboseMode) iffExp.setVerboseMode()

			iffCon = true
			val iffValue = iffExp.calculate()

			if (iffValue == 0.0 || iffValue.isNaN())
			{
				paramNumber += 2
				iffCon = false

				if (paramNumber < parametersNumber)
					iffParam = iffParams[paramNumber - 1]
			}
		}
		while (!iffCon && paramNumber < parametersNumber)

		var from: Int
		var to: Int
		var p: Int

		if (iffCon)
		{
			trueParamNumber = paramNumber + 1
			from = pos + 1
			to = iffParams[parametersNumber - 1].toIndex + 1
			this.tokensList[from].tokenLevel--
			this.tokensList[to].tokenLevel--

			if (trueParamNumber < parametersNumber)
			{
				to = iffParams[parametersNumber - 1].toIndex
				from = iffParams[trueParamNumber].fromIndex - 1
				p = to

				while (p >= from)
				{
					this.tokensList.removeAt(p)
					p--
				}
			}

			from = iffParams[trueParamNumber - 1].fromIndex
			to = iffParams[trueParamNumber - 1].toIndex
			p = from

			while (p <= to)
			{
				this.tokensList[p].tokenLevel--
				p++
			}

			to = from - 1
			from = pos
			p = to

			while (p >= from)
			{
				if (p != pos + 1)
					this.tokensList.removeAt(p)

				p--
			}
		}
		else
		{
			to = iffParams[parametersNumber - 1].toIndex + 1
			from = pos + 1
			p = to

			while (p >= from)
			{
				this.tokensList.removeAt(p)
				p--
			}

			this.setToNumber(pos, Double.NaN)
			this.tokensList[pos].tokenLevel--
		}
	}

	/**
	 * IF. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun IF(pos: Int)
	{
		val ifCondition = this.tokensList[pos + 1].tokenValue
		val ifTrue = this.tokensList[pos + 2].tokenValue
		val ifFalse = this.tokensList[pos + 3].tokenValue
		var result = ifFalse

		if (ifCondition != 0.0)
			result = ifTrue

		if (ifCondition.isNaN())
			result = Double.NaN

		this.f3SetDecreaseRemove(pos, result)
	}

	/**
	 * Characteristic function (a,b).
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CHI(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		val a = this.getTokenValue(pos + 2)
		val b = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, MathFunctions.chi(x, a, b))
	}

	/**
	 * Characteristic function [a,b].
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CHI_LR(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		val a = this.getTokenValue(pos + 2)
		val b = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, MathFunctions.chi_LR(x, a, b))
	}

	/**
	 * Characteristic function [a,b).
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CHI_L(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		val a = this.getTokenValue(pos + 2)
		val b = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, MathFunctions.chi_L(x, a, b))
	}

	/**
	 * Characteristic function (a,b].
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CHI_R(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		val a = this.getTokenValue(pos + 2)
		val b = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, MathFunctions.chi_R(x, a, b))
	}

	/**
	 * Probability Distribution Function - Uniform Continuous distribution.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun PDF_UNIFORM_CONT(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		val a = this.getTokenValue(pos + 2)
		val b = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, ProbabilityDistributions.pdfUniformContinuous(x, a, b))
	}

	/**
	 * Cumulative Distribution Function - Uniform Continuous distribution.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CDF_UNIFORM_CONT(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		val a = this.getTokenValue(pos + 2)
		val b = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, ProbabilityDistributions.cdfUniformContinuous(x, a, b))
	}

	/**
	 * Quantile Function - Uniform Continuous distribution.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun QNT_UNIFORM_CONT(pos: Int)
	{
		val q = this.getTokenValue(pos + 1)
		val a = this.getTokenValue(pos + 2)
		val b = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, ProbabilityDistributions.qntUniformContinuous(q, a, b))
	}

	/**
	 * Probability Distribution Function - Normal distribution.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun PDF_NORMAL(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		val mean = this.getTokenValue(pos + 2)
		val stddev = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, ProbabilityDistributions.pdfNormal(x, mean, stddev))
	}

	/**
	 * Cumulative Distribution Function - Normal distribution.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CDF_NORMAL(pos: Int)
	{
		val x = this.getTokenValue(pos + 1)
		val mean = this.getTokenValue(pos + 2)
		val stddev = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, ProbabilityDistributions.cdfNormal(x, mean, stddev))
	}

	/**
	 * Quantile Function - Normal distribution.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun QNT_NORMAL(pos: Int)
	{
		val q = this.getTokenValue(pos + 1)
		val mean = this.getTokenValue(pos + 2)
		val stddev = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, ProbabilityDistributions.qntNormal(q, mean, stddev))
	}

	/**
	 * Digit at position - numeral system with given base.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun DIGIT(pos: Int)
	{
		val number = this.getTokenValue(pos + 1)
		val position = this.getTokenValue(pos + 2)
		val numeralSystemBase = this.getTokenValue(pos + 3)
		this.f3SetDecreaseRemove(pos, NumberTheory.digitAtPosition(number, position, numeralSystemBase))
	}

	/**
	 * Summation operator (SIGMA by).
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SUM(pos: Int)
	{
		val iterParams = IterativeOperatorParameters(getFunctionParameters(pos, this.tokensList))
		val index = getParamArgument(iterParams.indexParam.paramStr)
		this.updateMissingTokens(index, iterParams)
		this.evalFromToDeltaParameters(index, iterParams)
		val sigma = NumberTheory.sigmaSummation(iterParams.funExp!!, index.argument!!, iterParams.from, iterParams.to, iterParams.delta)
		this.clearParamArgument(index)
		this.calcSetDecreaseRemove(pos, sigma, true)
	}

	/**
	 * Product operator (SIGMA by).
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun PROD(pos: Int)
	{
		val iterParams = IterativeOperatorParameters(getFunctionParameters(pos, this.tokensList))
		val index = getParamArgument(iterParams.indexParam.paramStr)
		this.updateMissingTokens(index, iterParams)
		this.evalFromToDeltaParameters(index, iterParams)
		val product = NumberTheory.piProduct(iterParams.funExp!!, index.argument!!, iterParams.from, iterParams.to, iterParams.delta)
		this.clearParamArgument(index)
		this.calcSetDecreaseRemove(pos, product, true)
	}

	/**
	 * Minimum value - iterative operator.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MIN(pos: Int)
	{
		val iterParams = IterativeOperatorParameters(getFunctionParameters(pos, this.tokensList))
		val index = getParamArgument(iterParams.indexParam.paramStr)
		this.updateMissingTokens(index, iterParams)
		this.evalFromToDeltaParameters(index, iterParams)
		val min = NumberTheory.min(iterParams.funExp!!, index.argument!!, iterParams.from, iterParams.to, iterParams.delta)
		this.clearParamArgument(index)
		this.calcSetDecreaseRemove(pos, min)
	}

	/**
	 * Maximum value - iterative operator.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MAX(pos: Int)
	{
		val iterParams = IterativeOperatorParameters(getFunctionParameters(pos, this.tokensList))
		val index = getParamArgument(iterParams.indexParam.paramStr)
		this.updateMissingTokens(index, iterParams)
		this.evalFromToDeltaParameters(index, iterParams)
		val max = NumberTheory.max(iterParams.funExp!!, index.argument!!, iterParams.from, iterParams.to, iterParams.delta)
		this.clearParamArgument(index)
		this.calcSetDecreaseRemove(pos, max)
	}

	/**
	 * Average function value - iterative operator.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun AVG(pos: Int)
	{
		val iterParams = IterativeOperatorParameters(getFunctionParameters(pos, this.tokensList))
		val index = getParamArgument(iterParams.indexParam.paramStr)
		this.updateMissingTokens(index, iterParams)
		this.evalFromToDeltaParameters(index, iterParams)
		val avg = Statistics.avg(iterParams.funExp!!, index.argument!!, iterParams.from, iterParams.to, iterParams.delta)
		this.clearParamArgument(index)
		this.calcSetDecreaseRemove(pos, avg, true)
	}

	/**
	 * Variance from sample function values - iterative operator.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun VAR(pos: Int)
	{
		val iterParams = IterativeOperatorParameters(getFunctionParameters(pos, this.tokensList))
		val index = getParamArgument(iterParams.indexParam.paramStr)
		this.updateMissingTokens(index, iterParams)
		this.evalFromToDeltaParameters(index, iterParams)
		val `var` = Statistics.`var`(iterParams.funExp!!, index.argument!!, iterParams.from, iterParams.to, iterParams.delta)
		this.clearParamArgument(index)
		this.calcSetDecreaseRemove(pos, `var`, true)
	}

	/**
	 * Standard deviation from sample function values - iterative operator.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun STD(pos: Int)
	{
		val iterParams = IterativeOperatorParameters(getFunctionParameters(pos, this.tokensList))
		val index = getParamArgument(iterParams.indexParam.paramStr)
		this.updateMissingTokens(index, iterParams)
		this.evalFromToDeltaParameters(index, iterParams)
		val std = Statistics.std(iterParams.funExp!!, index.argument!!, iterParams.from, iterParams.to, iterParams.delta)
		this.clearParamArgument(index)
		this.calcSetDecreaseRemove(pos, std, true)
	}

	/**
	 * Function derivative.
	 *
	 * @param pos Int
	 * @param derivativeType Int
	 *
	 * @author Bas Milius
	 */
	private fun DERIVATIVE(pos: Int, derivativeType: Int)
	{
		val derParams = getFunctionParameters(pos, this.tokensList)

		val DEF_EPS = 1E-8
		val DEF_MAX_STEPS = 20

		val (tokens, paramStr) = derParams[0]
		val (tokens1, paramStr1) = derParams[1]

		val x = this.getParamArgument(paramStr1)

		if (x.presence == Argument.NOT_FOUND)
		{
			this.updateMissingTokens(tokens1, paramStr1, x.index, Argument.TYPE_ID)
			this.updateMissingTokens(tokens, paramStr1, x.index, Argument.TYPE_ID)
		}

		val funExp = Expression(paramStr, tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
		var x0 = Double.NaN

		if (derParams.size == 2 || derParams.size == 4)
			x0 = x.argument!!.argumentValue

		if (derParams.size == 3 || derParams.size == 5)
		{
			val (tokens2, paramStr2) = derParams[2]

			if (x.presence == Argument.NOT_FOUND)
				updateMissingTokens(tokens2, paramStr1, x.index, Argument.TYPE_ID)

			val x0Expr = Expression(paramStr2, tokens2, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
			x0 = x0Expr.calculate()
		}

		var eps = DEF_EPS
		var maxSteps = DEF_MAX_STEPS

		if (derParams.size == 4 || derParams.size == 5)
		{
			val epsParam: FunctionParameter
			val maxStepsParam: FunctionParameter

			if (derParams.size == 4)
			{
				epsParam = derParams[2]
				maxStepsParam = derParams[3]
			}
			else
			{
				epsParam = derParams[3]
				maxStepsParam = derParams[4]
			}

			if (x.presence == Argument.NOT_FOUND)
			{
				this.updateMissingTokens(epsParam.tokens, paramStr1, x.index, Argument.TYPE_ID)
				this.updateMissingTokens(maxStepsParam.tokens, paramStr1, x.index, Argument.TYPE_ID)
			}

			val epsExpr = Expression(epsParam.paramStr, epsParam.tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
			val maxStepsExp = Expression(maxStepsParam.paramStr, maxStepsParam.tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)

			eps = epsExpr.calculate()
			maxSteps = Math.round(maxStepsExp.calculate()).toInt()
		}

		when (derivativeType)
		{
			Calculus.GENERAL_DERIVATIVE ->
			{
				val general = Calculus.derivative(funExp, x.argument!!, x0, Calculus.GENERAL_DERIVATIVE, eps, maxSteps)
				this.calcSetDecreaseRemove(pos, general)
			}
			Calculus.LEFT_DERIVATIVE ->
			{
				val left = Calculus.derivative(funExp, x.argument!!, x0, Calculus.LEFT_DERIVATIVE, eps, maxSteps)
				this.calcSetDecreaseRemove(pos, left)
			}
			else ->
			{
				val right = Calculus.derivative(funExp, x.argument!!, x0, Calculus.RIGHT_DERIVATIVE, eps, maxSteps)
				this.calcSetDecreaseRemove(pos, right)
			}
		}

		this.clearParamArgument(x)
	}

	/**
	 * Function derivative.
	 *
	 * @param pos Int
	 * @param derivativeType Int
	 *
	 * @author Bas Milius
	 */
	private fun DERIVATIVE_NTH(pos: Int, derivativeType: Int)
	{
		val derParams = getFunctionParameters(pos, this.tokensList)

		val DEF_EPS = 1E-6
		val DEF_MAX_STEPS = 20

		val (tokens, paramStr) = derParams[0]
		val (tokens1, paramStr1) = derParams[1]
		val (tokens2, paramStr2) = derParams[2]

		val x = this.getParamArgument(paramStr2)

		if (x.presence == Argument.NOT_FOUND)
		{
			this.updateMissingTokens(tokens2, paramStr2, x.index, Argument.TYPE_ID)
			this.updateMissingTokens(tokens, paramStr2, x.index, Argument.TYPE_ID)
			this.updateMissingTokens(tokens1, paramStr2, x.index, Argument.TYPE_ID)
		}

		val funExp = Expression(paramStr, tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
		val nExp = Expression(paramStr1, tokens1, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
		val n = nExp.calculate()
		val x0 = x.argument!!.argumentValue
		var eps = DEF_EPS
		var maxSteps = DEF_MAX_STEPS

		if (derParams.size == 5)
		{
			val (tokens3, paramStr3) = derParams[3]
			val (tokens4, paramStr4) = derParams[4]

			if (x.presence == Argument.NOT_FOUND)
			{
				this.updateMissingTokens(tokens3, paramStr2, x.index, Argument.TYPE_ID)
				this.updateMissingTokens(tokens4, paramStr2, x.index, Argument.TYPE_ID)
			}

			val epsExpr = Expression(paramStr3, tokens3, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
			val maxStepsExp = Expression(paramStr4, tokens4, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
			eps = epsExpr.calculate()
			maxSteps = Math.round(maxStepsExp.calculate()).toInt()
		}

		when (derivativeType)
		{
			Calculus.GENERAL_DERIVATIVE ->
			{
				val left = Calculus.derivativeNth(funExp, n, x.argument!!, x0, Calculus.LEFT_DERIVATIVE, eps, maxSteps)
				val right = Calculus.derivativeNth(funExp, n, x.argument!!, x0, Calculus.RIGHT_DERIVATIVE, eps, maxSteps)
				this.calcSetDecreaseRemove(pos, (left + right) / 2.0)
			}
			Calculus.LEFT_DERIVATIVE ->
			{
				val left = Calculus.derivativeNth(funExp, n, x.argument!!, x0, Calculus.LEFT_DERIVATIVE, eps, maxSteps)
				this.calcSetDecreaseRemove(pos, left)
			}
			else ->
			{
				val right = Calculus.derivativeNth(funExp, n, x.argument!!, x0, Calculus.RIGHT_DERIVATIVE, eps, maxSteps)
				this.calcSetDecreaseRemove(pos, right)
			}
		}

		this.clearParamArgument(x)
	}

	/**
	 * Function integral.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun INTEGRAL(pos: Int)
	{
		val intParams = getFunctionParameters(pos, this.tokensList)

		val DEF_EPS = 1E-6
		val DEF_MAX_STEPS = 20

		val (tokens, paramStr) = intParams[0]
		val (tokens1, paramStr1) = intParams[1]
		val (tokens2, paramStr2) = intParams[2]
		val (tokens3, paramStr3) = intParams[3]

		val x = this.getParamArgument(paramStr1)

		if (x.presence == Argument.NOT_FOUND)
		{
			this.updateMissingTokens(tokens1, paramStr1, x.index, Argument.TYPE_ID)
			this.updateMissingTokens(tokens, paramStr1, x.index, Argument.TYPE_ID)
			this.updateMissingTokens(tokens2, paramStr1, x.index, Argument.TYPE_ID)
			this.updateMissingTokens(tokens3, paramStr1, x.index, Argument.TYPE_ID)
		}

		val funExp = Expression(paramStr, tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
		val aExp = Expression(paramStr2, tokens2, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
		val bExp = Expression(paramStr3, tokens3, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)

		this.calcSetDecreaseRemove(pos, Calculus.integralTrapezoid(funExp, x.argument!!, aExp.calculate(), bExp.calculate(), DEF_EPS, DEF_MAX_STEPS))
		this.clearParamArgument(x)
	}

	/**
	 * Function SOLVE.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SOLVE(pos: Int)
	{
		val intParams = getFunctionParameters(pos, this.tokensList)

		val DEF_EPS = 1E-9
		val DEF_MAX_STEPS = 100

		val (tokens, paramStr) = intParams[0]
		val (tokens1, paramStr1) = intParams[1]
		val (tokens2, paramStr2) = intParams[2]
		val (tokens3, paramStr3) = intParams[3]

		val x = this.getParamArgument(paramStr1)

		if (x.presence == Argument.NOT_FOUND)
		{
			this.updateMissingTokens(tokens1, paramStr1, x.index, Argument.TYPE_ID)
			this.updateMissingTokens(tokens, paramStr1, x.index, Argument.TYPE_ID)
			this.updateMissingTokens(tokens2, paramStr1, x.index, Argument.TYPE_ID)
			this.updateMissingTokens(tokens3, paramStr1, x.index, Argument.TYPE_ID)
		}

		val funExp = Expression(paramStr, tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
		val aExp = Expression(paramStr2, tokens2, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)
		val bExp = Expression(paramStr3, tokens3, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)

		this.calcSetDecreaseRemove(pos, Calculus.solveBrent(funExp, x.argument!!, aExp.calculate(), bExp.calculate(), DEF_EPS, DEF_MAX_STEPS.toDouble()))
		this.clearParamArgument(x)
	}

	/**
	 * Forward difference operator.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun FORWARD_DIFFERENCE(pos: Int)
	{
		val params = this.getFunctionParameters(pos, this.tokensList)
		val (tokens, paramStr) = params[0]
		val (_, paramStr1) = params[1]
		val x = this.getParamArgument(paramStr1)
		val funExp = Expression(paramStr, tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)

		if (verboseMode) funExp.setVerboseMode()

		var h = 1.0

		if (params.size == 3)
		{
			val (tokens1, paramStr2) = params[2]
			val hExp = Expression(paramStr2, tokens1, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)

			if (verboseMode) hExp.setVerboseMode()

			h = hExp.calculate()
		}

		this.calcSetDecreaseRemove(pos, Calculus.forwardDifference(funExp, h, x.argument!!))
		this.clearParamArgument(x)
	}

	/**
	 * Backward diffrence operator.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BACKWARD_DIFFERENCE(pos: Int)
	{
		val params = this.getFunctionParameters(pos, this.tokensList)
		val (tokens, paramStr) = params[0]
		val (_, paramStr1) = params[1]
		val x = this.getParamArgument(paramStr1)
		val funExp = Expression(paramStr, tokens, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)

		if (verboseMode) funExp.setVerboseMode()

		var h = 1.0

		if (params.size == 3)
		{
			val (tokens1, paramStr2) = params[2]
			val hExp = Expression(paramStr2, tokens1, this.argumentsList, this.functionsList, this.constantsList, DISABLE_ULP_ROUNDING)

			if (verboseMode) hExp.setVerboseMode()

			h = hExp.calculate()
		}

		this.calcSetDecreaseRemove(pos, Calculus.backwardDifference(funExp, h, x.argument!!))
		this.clearParamArgument(x)
	}

	/**
	 * Minimum variadic. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MIN_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.min(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * Maximum variadic. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MAX_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.max(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * Sum variadic. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun SUM_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.sum(*MathParser.arrayList2double(numbers)), numbers.size, true)
	}

	/**
	 * Sum variadic. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun PROD_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.prod(*MathParser.arrayList2double(numbers)), numbers.size, true)
	}

	/**
	 * Average variadic. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun AVG_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, Statistics.avg(*MathParser.arrayList2double(numbers)), numbers.size, true)
	}

	/**
	 * Variance variadic. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun VAR_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, Statistics.`var`(*MathParser.arrayList2double(numbers)), numbers.size, true)
	}

	/**
	 * Standard deviation variadic. Sets tokens to number token.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun STD_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, Statistics.std(*MathParser.arrayList2double(numbers)), numbers.size, true)
	}

	/**
	 * Continued fraction.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CONTINUED_FRACTION(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, MathFunctions.continuedFraction(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * Continued polynomial.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun CONTINUED_POLYNOMIAL(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, MathFunctions.continuedPolynomial(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * Greates Common Divisor.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun GCD(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.gcd(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * Lowest Common Multiply.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun LCM(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.lcm(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * Random number from list.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun RND_LIST(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		val n = numbers.size
		val i = ProbabilityDistributions.rndIndex(n, ProbabilityDistributions.randomGenerator)
		this.variadicSetDecreaseRemove(pos, numbers[i], numbers.size)
	}

	/**
	 * Coalesce.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun COALESCE(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, MathFunctions.coalesce(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * OR_VARIADIC.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun OR_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, BooleanAlgebra.orVariadic(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * AND_VARIADIC.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun AND_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, BooleanAlgebra.andVariadic(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * XOR_VARIADIC.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun XOR_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, BooleanAlgebra.xorVariadic(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * ARGMIN_VARIADIC.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARGMIN_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.argmin(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * ARGMAX_VARIADIC.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun ARGMAX_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.argmax(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * MEDIAN_VARIADIC.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MEDIAN_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, Statistics.median(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * MODE_VARIADIC.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun MODE_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, Statistics.mode(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * BASE_VARIADIC.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun BASE_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.convOthBase2Decimal(MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * NDIST_VARIADIC.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun NDIST_VARIADIC(pos: Int)
	{
		val numbers = this.getNumbers(pos)
		this.variadicSetDecreaseRemove(pos, NumberTheory.numberOfDistValues(*MathParser.arrayList2double(numbers)), numbers.size)
	}

	/**
	 * Parser symbols. Removes comma.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun COMMA(pos: Int)
	{
		this.tokensList.removeAt(pos)
	}

	/**
	 * Parser symbols. Removes parenthesis.
	 *
	 * @param lPos Int
	 * @param rPos Int
	 *
	 * @author Bas Milius
	 */
	private fun PARENTHESES(lPos: Int, rPos: Int)
	{
		for (p in lPos..rPos)
			this.tokensList[p].tokenLevel--

		this.tokensList.removeAt(rPos)
		this.tokensList.removeAt(lPos)
	}

	/**
	 * Checks syntax of the expression string.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun checkLexSyntax(): Boolean
	{
		this.recursionCallsCounter = 0
		var syntax = NO_SYNTAX_ERRORS

		val syn = SyntaxChecker(ByteArrayInputStream(this.expressionString.toByteArray()))

		try
		{
			syn.checkSyntax()
		}
		catch (e: Exception)
		{
			syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
			this.errorMessage = "lexical error \n\n" + e.message + "\n"
		}

		return syntax
	}

	/**
	 * Checks syntax of the expression string.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun checkSyntax(): Boolean
	{
		return checkSyntax("[$expressionString] ", false)
	}

	/**
	 * Checks syntax of the calculus parameter
	 *
	 * @param param String
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	private fun checkCalculusParameter(param: String): Int
	{
		return this.keyWordsList.count { it.wordTypeId != Argument.TYPE_ID && param == it.wordString }
	}

	/**
	 * Checks if argument given in the function parameter is known in the expression.
	 *
	 * @param param FunctionParameter
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	private fun checkIfKnownArgument(param: FunctionParameter): Boolean
	{
		if (param.tokens.size > 1)
			return false

		return param.tokens[0].tokenTypeId == Argument.TYPE_ID
	}

	/**
	 * Checks if token is uknown.
	 *
	 * @param param FunctionParameter
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	private fun checkIfUnknownToken(param: FunctionParameter): Boolean
	{
		if (param.tokens.size > 1)
			return false

		return param.tokens[0].tokenTypeId == ConstantValue.NaN
	}

	/**
	 * Checking the syntax (recursively).
	 *
	 * @param level String
	 * @param functionWithBodyExt Boolean
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	private fun checkSyntax(level: String, functionWithBodyExt: Boolean): Boolean
	{
		if (!this.expressionWasModified && this.syntaxStatus == NO_SYNTAX_ERRORS && this.optionsChangesetNumber == MathParser.optionsChangesetNumber)
		{
			this.errorMessage = level + "already checked - no errors!\n"
			this.recursionCallPending = false

			return NO_SYNTAX_ERRORS
		}

		this.optionsChangesetNumber = MathParser.optionsChangesetNumber

		if (functionWithBodyExt)
		{
			this.syntaxStatus = NO_SYNTAX_ERRORS
			this.recursionCallPending = false
			this.expressionWasModified = false
			this.errorMessage = this.errorMessage + level + "function with extended body - assuming no errors.\n"

			return NO_SYNTAX_ERRORS
		}

		this.recursionCallPending = true
		this.errorMessage = level + "checking ...\n"

		var syntax = NO_SYNTAX_ERRORS
		val syn = SyntaxChecker(ByteArrayInputStream(this.expressionString.toByteArray()))

		try
		{
			syn.checkSyntax()
			this.tokenizeExpressionString()

			var kw1: String
			var kw2: String

			Collections.sort(this.keyWordsList, KwStrComparator())

			for (kwId in 1 until this.keyWordsList.size)
			{
				kw1 = this.keyWordsList[kwId - 1].wordString
				kw2 = this.keyWordsList[kwId].wordString

				if (kw1 == kw2)
				{
					syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
					this.errorMessage = "${this.errorMessage}$level($kw1) Duplicated <KEYWORD>.\n"
				}
			}

			val tokensNumber = this.initialTokens.size
			val syntaxStack = Stack<SyntaxStackElement>()
			var stackElement: SyntaxStackElement

			for (tokenIndex in 0 until tokensNumber)
			{
				val t = this.initialTokens[tokenIndex]
				val tokenStr = "(" + t.tokenStr + ", " + tokenIndex + ") "

				if (t.tokenTypeId == Argument.TYPE_ID)
				{
					val arg = this.getArgument(t.tokenId)
					if (arg!!.argumentType == Argument.DEPENDENT_ARGUMENT)
					{
						if (this.getParametersNumber(tokenIndex) >= 0)
						{
							syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
							this.errorMessage = this.errorMessage + level + tokenStr + "<ARGUMENT> was expected.\n"
						}
						else if (arg.argumentExpression !== this && !arg.argumentExpression!!.recursionCallPending)
						{
							val syntaxRec = arg.argumentExpression!!.checkSyntax(level + "-> " + "[" + t.tokenStr + "] = [" + arg.argumentExpression!!.expressionString + "] ", false)
							syntax = syntax && syntaxRec
							this.errorMessage = this.errorMessage + level + tokenStr + "checking dependent argument ...\n" + arg.argumentExpression!!.errorMessage
						}
					}
				}

				if (t.tokenTypeId == RecursiveArgument.TYPE_ID_RECURSIVE)
				{
					val arg = getArgument(t.tokenId)

					if (this.getParametersNumber(tokenIndex) != 1)
					{
						syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
						this.errorMessage = this.errorMessage + level + tokenStr + "<RECURSIVE_ARGUMENT> expecting 1 parameter.\n"
					}
					else if (arg!!.argumentExpression !== this && !arg!!.argumentExpression!!.recursionCallPending)
					{
						val syntaxRec = arg.argumentExpression!!.checkSyntax(level + "-> " + "[" + t.tokenStr + "] = [" + arg.argumentExpression!!.expressionString + "] ", false)
						syntax = syntax && syntaxRec
						this.errorMessage = this.errorMessage + level + tokenStr + "checking recursive argument ...\n" + arg.argumentExpression!!.errorMessage
					}
				}

				if (t.tokenTypeId == Token.NOT_MATCHED)
				{
					if (!syntaxStack.any { it.tokenStr == t.tokenStr })
					{
						syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
						this.errorMessage = this.errorMessage + level + tokenStr + "invalid <TOKEN>.\n"
					}
				}

				if (t.tokenTypeId == Function.TYPE_ID)
				{
					val `fun` = getFunction(t.tokenId)
					`fun`!!.checkRecursiveMode()

					if (`fun`.parametersNumber != getParametersNumber(tokenIndex))
					{
						syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
						this.errorMessage = this.errorMessage + level + tokenStr + "<USER_DEFINED_FUNCTION> expecting " + `fun`.parametersNumber + " arguments.\n"
					}
					else if (`fun`.functionExpression !== this && !`fun`.functionExpression!!.recursionCallPending)
					{
						val syntaxRec = if (`fun`.functionBodyType == Function.BODY_RUNTIME)
							`fun`.functionExpression!!.checkSyntax(level + "-> " + "[" + t.tokenStr + "] = [" + `fun`.functionExpression!!.expressionString + "] ", false)
						else
							`fun`.functionExpression!!.checkSyntax(level + "-> " + "[" + t.tokenStr + "] = [" + `fun`.functionExpression!!.expressionString + "] ", true)

						syntax = syntax && syntaxRec
						this.errorMessage = this.errorMessage + level + tokenStr + "checking user defined function ...\n" + `fun`.functionExpression!!.errorMessage
					}
				}

				if (t.tokenTypeId == ConstantValue.TYPE_ID && this.getParametersNumber(tokenIndex) >= 0)
				{
					syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
					this.errorMessage = this.errorMessage + level + tokenStr + "<CONSTANT> was expected.\n"
				}

				if (t.tokenTypeId == Constant.TYPE_ID && this.getParametersNumber(tokenIndex) >= 0)
				{
					syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
					this.errorMessage = this.errorMessage + level + tokenStr + "<USER_DEFINED_CONSTANT> was expected.\n"
				}

				if (t.tokenTypeId == Function1Arg.TYPE_ID && this.getParametersNumber(tokenIndex) != 1)
				{
					syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
					this.errorMessage = this.errorMessage + level + tokenStr + "<FUNCTION> expecting 1 argument.\n"
				}

				if (t.tokenTypeId == Function2Arg.TYPE_ID && this.getParametersNumber(tokenIndex) != 2)
				{
					syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
					this.errorMessage = this.errorMessage + level + tokenStr + "<FUNCTION> expecting 2 arguments.\n"
				}

				if (t.tokenTypeId == Function3Arg.TYPE_ID && this.getParametersNumber(tokenIndex) != 3)
				{
					syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
					this.errorMessage = this.errorMessage + level + tokenStr + "<FUNCTION> expecting 3 arguments.\n"
				}

				if (t.tokenTypeId == CalculusOperator.TYPE_ID)
				{
					val paramsNumber = this.getParametersNumber(tokenIndex)
					var funParams: List<FunctionParameter>? = null

					if (paramsNumber > 0)
						funParams = this.getFunctionParameters(tokenIndex, this.initialTokens)

					if (t.tokenId == CalculusOperator.DER_ID || t.tokenId == CalculusOperator.DER_LEFT_ID || t.tokenId == CalculusOperator.DER_RIGHT_ID)
					{
						if (paramsNumber < 2 || paramsNumber > 5)
						{
							syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
							this.errorMessage = this.errorMessage + level + tokenStr + "<DERIVATIVE> expecting 2 or 3 or 4 or 5 calculus parameters.\n"
						}
						else
						{
							if (paramsNumber == 2 || paramsNumber == 4)
							{
								val argParam = funParams!![1]
								if (!this.checkIfKnownArgument(argParam))
								{
									syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
									this.errorMessage = this.errorMessage + level + tokenStr + "<DERIVATIVE> argument was expected.\n"
								}
							}
							else
							{
								val argParam = funParams!![1]
								stackElement = SyntaxStackElement(argParam.paramStr, t.tokenLevel + 1)
								syntaxStack.push(stackElement)
								val errors = this.checkCalculusParameter(stackElement.tokenStr)

								if (errors > 0)
								{
									syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
									this.errorMessage = this.errorMessage + level + tokenStr + "<DERIVATIVE> Found duplicated key words for calculus parameter " + "(" + stackElement.tokenStr + ", " + errors + ").\n"
								}

								if (!this.checkIfKnownArgument(argParam) && !this.checkIfUnknownToken(argParam))
								{
									syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
									this.errorMessage = this.errorMessage + level + tokenStr + "<DERIVATIVE> One token (argument or unknown) was expected.\n"
								}
							}
						}
					}

					if (t.tokenId == CalculusOperator.DERN_ID)
					{
						if (paramsNumber != 3 && paramsNumber != 5)
						{
							syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
							this.errorMessage = this.errorMessage + level + tokenStr + "<NTH_DERIVATIVE> expecting 3 or 5 calculus arguments.\n"
						}
						else
						{
							val argParam = funParams!![2]

							if (!this.checkIfKnownArgument(argParam))
							{
								syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
								this.errorMessage = this.errorMessage + level + tokenStr + "<DERIVATIVE> argument was expected.\n"
							}
						}
					}

					if (t.tokenId == CalculusOperator.INT_ID || t.tokenId == CalculusOperator.SOLVE_ID)
					{
						if (paramsNumber != 4)
						{
							syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
							this.errorMessage = this.errorMessage + level + tokenStr + "<INTEGRAL/SOLVE> expecting 4 calculus arguments.\n"
						}
						else
						{
							val argParam = funParams!![1]
							stackElement = SyntaxStackElement(argParam.paramStr, t.tokenLevel + 1)
							syntaxStack.push(stackElement)
							val errors = this.checkCalculusParameter(stackElement.tokenStr)

							if (errors > 0)
							{
								syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
								this.errorMessage = this.errorMessage + level + tokenStr + "Found duplicated key words for calculus parameter " + "(" + stackElement.tokenStr + ", " + errors + ").\n"
							}

							if (!this.checkIfKnownArgument(argParam) && !this.checkIfUnknownToken(argParam))
							{
								syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
								this.errorMessage = this.errorMessage + level + tokenStr + "One token (argument or unknown) was expected.\n"
							}
						}
					}

					if (t.tokenId == CalculusOperator.PROD_ID || t.tokenId == CalculusOperator.SUM_ID || t.tokenId == CalculusOperator.MIN_ID || t.tokenId == CalculusOperator.MAX_ID || t.tokenId == CalculusOperator.AVG_ID || t.tokenId == CalculusOperator.VAR_ID || t.tokenId == CalculusOperator.STD_ID)
					{
						if (paramsNumber != 4 && paramsNumber != 5)
						{
							syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
							this.errorMessage = this.errorMessage + level + tokenStr + "<ITER_OPERATOR> expecting 4 or 5 calculus arguments.\n"
						}
						else
						{
							val indexParam = funParams!![0]
							stackElement = SyntaxStackElement(indexParam.paramStr, t.tokenLevel + 1)
							syntaxStack.push(stackElement)
							val errors = this.checkCalculusParameter(stackElement.tokenStr)

							if (errors > 0)
							{
								syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
								this.errorMessage = this.errorMessage + level + tokenStr + "Found duplicated key words for calculus parameter " + "(" + stackElement.tokenStr + ", " + errors + ").\n"
							}

							if (!this.checkIfKnownArgument(indexParam) && !this.checkIfUnknownToken(indexParam))
							{
								syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
								this.errorMessage = this.errorMessage + level + tokenStr + "One token (argument or unknown) was expected.\n"
							}
						}
					}

					if (t.tokenId == CalculusOperator.FORW_DIFF_ID || t.tokenId == CalculusOperator.BACKW_DIFF_ID)
					{
						if (paramsNumber != 2 && paramsNumber != 3)
						{
							syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
							this.errorMessage = this.errorMessage + level + tokenStr + "<DIFF> expecting 2 or 3 arguments.\n"
						}
						else
						{
							val xParam = funParams!![1]
							if (!this.checkIfKnownArgument(xParam))
							{
								syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
								this.errorMessage = this.errorMessage + level + tokenStr + "<DIFF> argument was expected.\n"
							}
						}
					}
				}

				if (t.tokenTypeId == FunctionVariadic.TYPE_ID)
				{
					val paramsNumber = this.getParametersNumber(tokenIndex)

					if (paramsNumber < 1)
					{
						syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
						this.errorMessage = this.errorMessage + level + tokenStr + "At least one argument was expected.\n"
					}

					if (t.tokenId == FunctionVariadic.IFF_ID)
					{
						if (paramsNumber % 2 != 0 || paramsNumber < 2)
						{
							syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
							this.errorMessage = this.errorMessage + level + tokenStr + "Expecting parity number of arguments.\n"
						}
					}
				}

				if (t.tokenTypeId == ParserSymbol.TYPE_ID && t.tokenId == ParserSymbol.RIGHT_PARENTHESES_ID)
				{
					if (syntaxStack.size > 0)
						if (t.tokenLevel == syntaxStack.lastElement().tokenLevel)
							syntaxStack.pop()
				}
			}
		}
		catch (e: Exception)
		{
			syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN
			this.errorMessage = this.errorMessage + level + "lexical error \n\n" + e.message + "\n"
		}

		if (syntax == NO_SYNTAX_ERRORS)
		{
			this.errorMessage = this.errorMessage + level + "no errors.\n"
			this.expressionWasModified = false
		}
		else
		{
			this.errorMessage = this.errorMessage + level + "errors were found.\n"
			this.expressionWasModified = true
		}

		this.syntaxStatus = syntax
		this.recursionCallPending = false

		return syntax
	}

	/**
	 * Calculates the expression value.
	 *
	 * @return Double
	 *
	 * @author Bas Milius
	 */
	fun calculate(): Double
	{
		this.computingTime = 0.0
		val startTime = System.currentTimeMillis()

		if (this.verboseMode)
		{
			this.printSystemInfo("\n", NO_EXP_STR)
			this.printSystemInfo("\n", WITH_EXP_STR)
			this.printSystemInfo("Starting ...\n", WITH_EXP_STR)
			this.showArguments()
		}

		if (this.expressionWasModified || this.syntaxStatus != NO_SYNTAX_ERRORS)
			this.syntaxStatus = this.checkSyntax()

		if (this.syntaxStatus == SYNTAX_ERROR_OR_STATUS_UNKNOWN)
		{
			this.recursionCallsCounter = 0
			return Double.NaN
		}

		if (this.recursionCallsCounter == 0 || this.internalClone)
			this.copyInitialTokens()

		if (this.tokensList.size == 0)
		{
			this.recursionCallsCounter = 0
			return Double.NaN
		}

		if (this.recursionCallsCounter >= MathParser.MAX_RECURSION_CALLS)
		{
			this.recursionCallsCounter = 0
			this.errorMessage = "${this.errorMessage}\n[${this.description}][${this.expressionString}] Maximum recursion calls reached.\n"
			return Double.NaN
		}

		this.recursionCallsCounter++

		var calculusPos: Int
		var ifPos: Int
		var iffPos: Int
		var variadicFunPos: Int
		var depArgPos: Int
		var recArgPos: Int
		var f3ArgPos: Int
		var f2ArgPos: Int
		var f1ArgPos: Int
		var userFunPos: Int
		var plusPos: Int
		var minusPos: Int
		var multiplyPos: Int
		var dividePos: Int
		var powerPos: Int
		var powerNum: Int
		var factPos: Int
		var modPos: Int
		var percPos: Int
		var negPos: Int
		var bolPos: Int
		var eqPos: Int
		var neqPos: Int
		var ltPos: Int
		var gtPos: Int
		var leqPos: Int
		var geqPos: Int
		var commaPos: Int
		var lParPos: Int
		var rParPos: Int
		var bitwisePos: Int
		var bitwiseComplPos: Int
		var token: Token
		var tokenL: Token
		var tokenR: Token
		var argument: Argument
		var tokensNumber: Int
		var maxPartLevel: Int
		var lPos: Int
		var rPos: Int
		var tokenIndex: Int
		var pos: Int
		var p: Int
		var commas: MutableList<Int>? = null

		do
		{
			tokensNumber = this.tokensList.size
			maxPartLevel = -1
			lPos = -1

			calculusPos = -1
			ifPos = -1
			iffPos = -1
			variadicFunPos = -1
			recArgPos = -1
			depArgPos = -1
			f3ArgPos = -1
			f2ArgPos = -1
			f1ArgPos = -1
			userFunPos = -1
			plusPos = -1
			minusPos = -1
			multiplyPos = -1
			dividePos = -1
			powerPos = -1
			factPos = -1
			modPos = -1
			percPos = -1
			powerNum = 0
			negPos = -1
			bolPos = -1
			eqPos = -1
			neqPos = -1
			ltPos = -1
			gtPos = -1
			leqPos = -1
			geqPos = -1
			commaPos = -1
			lParPos = -1
			rParPos = -1
			bitwisePos = -1
			bitwiseComplPos = -1
			p = -1

			do
			{
				p++
				token = this.tokensList[p]

				if (token.tokenTypeId == CalculusOperator.TYPE_ID)
					calculusPos = p
			}
			while (p < tokensNumber - 1 && calculusPos < 0)

			if (calculusPos < 0)
			{
				p = -1

				do
				{
					p++
					token = this.tokensList[p]

					if (token.tokenTypeId == Function3Arg.TYPE_ID && token.tokenId == Function3Arg.IF_CONDITION_ID)
						ifPos = p
				}
				while (p < tokensNumber - 1 && ifPos < 0)
			}

			if (calculusPos < 0 && ifPos < 0)
			{
				p = -1

				do
				{
					p++
					token = this.tokensList[p]

					if (token.tokenTypeId == FunctionVariadic.TYPE_ID && token.tokenId == FunctionVariadic.IFF_ID)
						iffPos = p
				}
				while (p < tokensNumber - 1 && iffPos < 0)
			}

			if (calculusPos < 0 && ifPos < 0 && iffPos < 0)
			{
				tokenIndex = 0

				while (tokenIndex < tokensNumber)
				{
					token = this.tokensList[tokenIndex]

					if (token.tokenLevel > maxPartLevel)
					{
						maxPartLevel = this.tokensList[tokenIndex].tokenLevel
						lPos = tokenIndex
					}

					if (token.tokenTypeId == Argument.TYPE_ID)
					{
						argument = argumentsList[this.tokensList[tokenIndex].tokenId]

						if (argument.argumentType == Argument.FREE_ARGUMENT)
						{
							FREE_ARGUMENT(tokenIndex)
						}
						else
						{
							depArgPos = tokenIndex
						}
					}
					else if (token.tokenTypeId == ConstantValue.TYPE_ID)
					{
						CONSTANT(tokenIndex)
					}
					else if (token.tokenTypeId == Unit.TYPE_ID)
					{
						UNIT(tokenIndex)
					}
					else if (token.tokenTypeId == Constant.TYPE_ID)
					{
						USER_CONSTANT(tokenIndex)
					}
					else if (token.tokenTypeId == RandomVariable.TYPE_ID)
					{
						RANDOM_VARIABLE(tokenIndex)
					}

					tokenIndex++
				}

				if (depArgPos >= 0)
				{
					do
					{
						var depArgFound = false
						val currentTokensNumber = this.tokensList.size
						tokenIndex = 0

						while (tokenIndex < currentTokensNumber)
						{
							token = this.tokensList[tokenIndex]

							if (token.tokenTypeId == Argument.TYPE_ID)
							{
								argument = this.argumentsList[this.tokensList[tokenIndex].tokenId]
								if (argument.argumentType == Argument.DEPENDENT_ARGUMENT)
								{
									DEPENDENT_ARGUMENT(tokenIndex)
									depArgFound = true
									break
								}
							}

							tokenIndex++
						}
					}
					while (depArgFound)
				}
				else
				{
					tokenIndex = lPos

					while (tokenIndex < tokensNumber && maxPartLevel == this.tokensList[tokenIndex].tokenLevel)
					{
						tokenIndex++
					}

					rPos = tokenIndex - 1

					if (verboseMode)
					{
						this.printSystemInfo("Parsing ($lPos, $rPos) ", WITH_EXP_STR)
						this.showParsing(lPos, rPos)
					}

					var leftIsNUmber: Boolean
					var rigthIsNUmber: Boolean

					pos = lPos

					while (pos <= rPos)
					{
						leftIsNUmber = false
						rigthIsNUmber = false
						token = this.tokensList[pos]

						if (pos - 1 >= 0)
						{
							tokenL = this.tokensList[pos - 1]
							if (tokenL.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID)
								leftIsNUmber = true
						}

						if (pos + 1 < tokensNumber)
						{
							tokenR = this.tokensList[pos + 1]
							if (tokenR.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID)
								rigthIsNUmber = true
						}

						if (token.tokenTypeId == RecursiveArgument.TYPE_ID_RECURSIVE && recArgPos < 0)
						{
							recArgPos = pos
						}
						else if (token.tokenTypeId == FunctionVariadic.TYPE_ID && variadicFunPos < 0)
						{
							variadicFunPos = pos
						}
						else if (token.tokenTypeId == Function3Arg.TYPE_ID && f3ArgPos < 0)
						{
							f3ArgPos = pos
						}
						else if (token.tokenTypeId == Function2Arg.TYPE_ID && f2ArgPos < 0)
						{
							f2ArgPos = pos
						}
						else if (token.tokenTypeId == Function1Arg.TYPE_ID && f1ArgPos < 0)
						{
							f1ArgPos = pos
						}
						else if (token.tokenTypeId == Function.TYPE_ID && userFunPos < 0)
						{
							userFunPos = pos
						}
						else if (token.tokenTypeId == Operator.TYPE_ID)
						{
							if (token.tokenId == Operator.POWER_ID && leftIsNUmber && rigthIsNUmber)
							{
								powerPos = pos
								powerNum++
							}
							else if (token.tokenId == Operator.FACT_ID && factPos < 0 && leftIsNUmber)
							{
								factPos = pos
							}
							else if (token.tokenId == Operator.PERC_ID && percPos < 0 && leftIsNUmber)
							{
								percPos = pos
							}
							else if (token.tokenId == Operator.MOD_ID && modPos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								modPos = pos
							}
							else if (token.tokenId == Operator.PLUS_ID && plusPos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								plusPos = pos
							}
							else if (token.tokenId == Operator.MINUS_ID && minusPos < 0 && rigthIsNUmber)
							{
								minusPos = pos
							}
							else if (token.tokenId == Operator.MULTIPLY_ID && multiplyPos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								multiplyPos = pos
							}
							else if (token.tokenId == Operator.DIVIDE_ID && dividePos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								dividePos = pos
							}
						}
						else if (token.tokenTypeId == BooleanOperator.TYPE_ID && token.tokenId == BooleanOperator.NEG_ID && negPos < 0 && rigthIsNUmber)
						{
							negPos = pos
						}
						else if (token.tokenTypeId == BooleanOperator.TYPE_ID && bolPos < 0 && leftIsNUmber && rigthIsNUmber)
						{
							bolPos = pos
						}
						else if (token.tokenTypeId == BinaryRelation.TYPE_ID)
						{
							if (token.tokenId == BinaryRelation.EQ_ID && eqPos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								eqPos = pos
							}
							else if (token.tokenId == BinaryRelation.NEQ_ID && neqPos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								neqPos = pos
							}
							else if (token.tokenId == BinaryRelation.LT_ID && ltPos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								ltPos = pos
							}
							else if (token.tokenId == BinaryRelation.GT_ID && gtPos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								gtPos = pos
							}
							else if (token.tokenId == BinaryRelation.LEQ_ID && leqPos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								leqPos = pos
							}
							else if (token.tokenId == BinaryRelation.GEQ_ID && geqPos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								geqPos = pos
							}
						}
						else if (token.tokenTypeId == BitwiseOperator.TYPE_ID)
						{
							if (token.tokenId == BitwiseOperator.COMPL_ID && bitwiseComplPos < 0 && rigthIsNUmber)
							{
								bitwiseComplPos = pos
							}
							else if (bitwisePos < 0 && leftIsNUmber && rigthIsNUmber)
							{
								bitwisePos = pos
							}
						}
						else if (token.tokenTypeId == ParserSymbol.TYPE_ID)
						{
							if (token.tokenId == ParserSymbol.COMMA_ID)
							{
								if (commaPos < 0)
									commas = ArrayList()
								commas!!.add(pos)
								commaPos = pos
							}
							else if (token.tokenId == ParserSymbol.LEFT_PARENTHESES_ID && lParPos < 0)
							{
								lParPos = pos
							}
							else if (token.tokenId == ParserSymbol.RIGHT_PARENTHESES_ID && rParPos < 0)
							{
								rParPos = pos
							}
						}

						pos++
					}

					if (powerNum > 1)
					{
						powerPos = -1
						p = rPos + 1

						do
						{
							p--
							token = this.tokensList[p]

							if (token.tokenTypeId == Operator.TYPE_ID && token.tokenId == Operator.POWER_ID)
								powerPos = p
						}
						while (p > lPos && powerPos == -1)
					}
				}
			}

			if (calculusPos >= 0)
			{
				this.calculusCalc(calculusPos)
			}
			else if (ifPos >= 0)
			{
				this.IF_CONDITION(ifPos)
			}
			else if (iffPos >= 0)
			{
				this.IFF(iffPos)
			}
			else
			{
				if (recArgPos >= 0)
				{
					this.RECURSIVE_ARGUMENT(recArgPos)
				}
				else
				{
					if (variadicFunPos >= 0)
					{
						this.variadicFunCalc(variadicFunPos)
					}
					else
					{
						if (f3ArgPos >= 0)
						{
							this.f3ArgCalc(f3ArgPos)
						}
						else
						{
							if (f2ArgPos >= 0)
							{
								this.f2ArgCalc(f2ArgPos)
							}
							else
							{
								if (f1ArgPos >= 0)
								{
									this.f1ArgCalc(f1ArgPos)
								}
								else
								{
									if (userFunPos >= 0)
									{
										this.USER_FUNCTION(userFunPos)
									}
									else
									{
										if (powerPos >= 0)
										{
											this.POWER(powerPos)
										}
										else if (factPos >= 0)
										{
											this.FACT(factPos)
										}
										else if (percPos >= 0)
										{
											this.PERC(percPos)
										}
										else if (modPos >= 0)
										{
											this.MODULO(modPos)
										}
										else if (negPos >= 0)
										{
											this.NEG(negPos)
										}
										else if (bitwiseComplPos >= 0)
										{
											this.BITWISE_COMPL(bitwiseComplPos)
										}
										else
										{
											if (multiplyPos >= 0 || dividePos >= 0)
											{
												if (multiplyPos >= 0 && dividePos >= 0)
												{
													if (multiplyPos <= dividePos)
													{
														this.MULTIPLY(multiplyPos)
													}
													else
													{
														this.DIVIDE(dividePos)
													}
												}
												else if (multiplyPos >= 0)
												{
													this.MULTIPLY(multiplyPos)
												}
												else
												{
													this.DIVIDE(dividePos)
												}
											}
											else if (minusPos >= 0 || plusPos >= 0)
											{
												if (minusPos >= 0 && plusPos >= 0)
												{
													if (minusPos <= plusPos)
													{
														this.MINUS(minusPos)
													}
													else
													{
														this.PLUS(plusPos)
													}
												}
												else if (minusPos >= 0)
												{
													this.MINUS(minusPos)
												}
												else
												{
													this.PLUS(plusPos)
												}
											}
											else if (neqPos >= 0)
											{
												this.NEQ(neqPos)
											}
											else
											{
												if (eqPos >= 0)
												{
													this.EQ(eqPos)
												}
												else if (ltPos >= 0)
												{
													this.LT(ltPos)
												}
												else if (gtPos >= 0)
												{
													this.GT(gtPos)
												}
												else if (leqPos >= 0)
												{
													this.LEQ(leqPos)
												}
												else if (geqPos >= 0)
												{
													this.GEQ(geqPos)
												}
												else if (commaPos >= 0)
												{
													for (i in commas!!.indices.reversed())
													{
														this.COMMA(commas[i])
													}
												}
												else
												{
													if (bolPos >= 0)
													{
														this.bolCalc(bolPos)
													}
													else
													{
														if (bitwisePos >= 0)
														{
															this.bitwiseCalc(bitwisePos)
														}
														else if (lParPos in 0..(rParPos - 1))
														{
															this.PARENTHESES(lParPos, rParPos)
														}
														else if (this.tokensList.size > 1)
														{
															this.errorMessage = "${this.errorMessage}\n[${this.description}][${this.expressionString}] Fatal error - not know what to do with tokens while calculate().\n"
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

			if (verboseMode)
			{
				this.showParsing(0, this.tokensList.size - 1)
				this.printSystemInfo(" done\n", NO_EXP_STR)
			}
		}
		while (this.tokensList.size > 1)

		if (this.verboseMode)
		{
			this.printSystemInfo("Calculated value: " + this.tokensList[0].tokenValue + "\n", WITH_EXP_STR)
			this.printSystemInfo("Exiting\n", WITH_EXP_STR)
			this.printSystemInfo("\n", NO_EXP_STR)
		}

		val endTime = System.currentTimeMillis()

		this.computingTime = (endTime - startTime) / 1000.0
		this.recursionCallsCounter = 0

		var result = this.tokensList[0].tokenValue
		val resultint = Math.round(result).toDouble()

		if (MathFunctions.abs(result - resultint) <= BinaryRelations.epsilon)
			result = resultint

		return result
	}

	/**
	 * Calculates unary function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun f1ArgCalc(pos: Int)
	{
		when (this.tokensList[pos].tokenId)
		{
			Function1Arg.SIN_ID -> this.SIN(pos)
			Function1Arg.COS_ID -> this.COS(pos)
			Function1Arg.TAN_ID -> this.TAN(pos)
			Function1Arg.CTAN_ID -> this.CTAN(pos)
			Function1Arg.SEC_ID -> this.SEC(pos)
			Function1Arg.COSEC_ID -> this.COSEC(pos)
			Function1Arg.ASIN_ID -> this.ASIN(pos)
			Function1Arg.ACOS_ID -> this.ACOS(pos)
			Function1Arg.ATAN_ID -> this.ATAN(pos)
			Function1Arg.ACTAN_ID -> this.ACTAN(pos)
			Function1Arg.LN_ID -> this.LN(pos)
			Function1Arg.LOG2_ID -> this.LOG2(pos)
			Function1Arg.LOG10_ID -> this.LOG10(pos)
			Function1Arg.RAD_ID -> this.RAD(pos)
			Function1Arg.EXP_ID -> this.EXP(pos)
			Function1Arg.SQRT_ID -> this.SQRT(pos)
			Function1Arg.SINH_ID -> this.SINH(pos)
			Function1Arg.COSH_ID -> this.COSH(pos)
			Function1Arg.TANH_ID -> this.TANH(pos)
			Function1Arg.COTH_ID -> this.COTH(pos)
			Function1Arg.SECH_ID -> this.SECH(pos)
			Function1Arg.CSCH_ID -> this.CSCH(pos)
			Function1Arg.DEG_ID -> this.DEG(pos)
			Function1Arg.ABS_ID -> this.ABS(pos)
			Function1Arg.SGN_ID -> this.SGN(pos)
			Function1Arg.FLOOR_ID -> this.FLOOR(pos)
			Function1Arg.CEIL_ID -> this.CEIL(pos)
			Function1Arg.NOT_ID -> this.NOT(pos)
			Function1Arg.ARSINH_ID -> this.ARSINH(pos)
			Function1Arg.ARCOSH_ID -> this.ARCOSH(pos)
			Function1Arg.ARTANH_ID -> this.ARTANH(pos)
			Function1Arg.ARCOTH_ID -> this.ARCOTH(pos)
			Function1Arg.ARSECH_ID -> this.ARSECH(pos)
			Function1Arg.ARCSCH_ID -> this.ARCSCH(pos)
			Function1Arg.SA_ID -> this.SA(pos)
			Function1Arg.SINC_ID -> this.SINC(pos)
			Function1Arg.BELL_NUMBER_ID -> this.BELL_NUMBER(pos)
			Function1Arg.LUCAS_NUMBER_ID -> this.LUCAS_NUMBER(pos)
			Function1Arg.FIBONACCI_NUMBER_ID -> this.FIBONACCI_NUMBER(pos)
			Function1Arg.HARMONIC_NUMBER_ID -> this.HARMONIC_NUMBER(pos)
			Function1Arg.IS_PRIME_ID -> this.IS_PRIME(pos)
			Function1Arg.PRIME_COUNT_ID -> this.PRIME_COUNT(pos)
			Function1Arg.EXP_INT_ID -> this.EXP_INT(pos)
			Function1Arg.LOG_INT_ID -> this.LOG_INT(pos)
			Function1Arg.OFF_LOG_INT_ID -> this.OFF_LOG_INT(pos)
			Function1Arg.GAUSS_ERF_ID -> this.GAUSS_ERF(pos)
			Function1Arg.GAUSS_ERFC_ID -> this.GAUSS_ERFC(pos)
			Function1Arg.GAUSS_ERF_INV_ID -> this.GAUSS_ERF_INV(pos)
			Function1Arg.GAUSS_ERFC_INV_ID -> this.GAUSS_ERFC_INV(pos)
			Function1Arg.ULP_ID -> this.ULP(pos)
			Function1Arg.ISNAN_ID -> this.ISNAN(pos)
			Function1Arg.NDIG10_ID -> this.NDIG10(pos)
			Function1Arg.NFACT_ID -> this.NFACT(pos)
			Function1Arg.ARCSEC_ID -> this.ARCSEC(pos)
			Function1Arg.ARCCSC_ID -> this.ARCCSC(pos)
		}
	}

	/**
	 * Calculates binary function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun f2ArgCalc(pos: Int)
	{
		when (this.tokensList[pos].tokenId)
		{
			Function2Arg.LOG_ID -> this.LOG(pos)
			Function2Arg.MOD_ID -> this.MOD(pos)
			Function2Arg.BINOM_COEFF_ID -> this.BINOM_COEFF(pos)
			Function2Arg.BERNOULLI_NUMBER_ID -> this.BERNOULLI_NUMBER(pos)
			Function2Arg.STIRLING1_NUMBER_ID -> this.STIRLING1_NUMBER(pos)
			Function2Arg.STIRLING2_NUMBER_ID -> this.STIRLING2_NUMBER(pos)
			Function2Arg.WORPITZKY_NUMBER_ID -> this.WORPITZKY_NUMBER(pos)
			Function2Arg.EULER_NUMBER_ID -> this.EULER_NUMBER(pos)
			Function2Arg.KRONECKER_DELTA_ID -> this.KRONECKER_DELTA(pos)
			Function2Arg.EULER_POLYNOMIAL_ID -> this.EULER_POLYNOMIAL(pos)
			Function2Arg.HARMONIC_NUMBER_ID -> this.HARMONIC2_NUMBER(pos)
			Function2Arg.RND_UNIFORM_CONT_ID -> this.RND_VAR_UNIFORM_CONT(pos)
			Function2Arg.RND_UNIFORM_DISCR_ID -> this.RND_VAR_UNIFORM_DISCR(pos)
			Function2Arg.ROUND_ID -> this.ROUND(pos)
			Function2Arg.RND_NORMAL_ID -> this.RND_NORMAL(pos)
			Function2Arg.NDIG_ID -> this.NDIG(pos)
			Function2Arg.DIGIT10_ID -> this.DIGIT10(pos)
			Function2Arg.FACTVAL_ID -> this.FACTVAL(pos)
			Function2Arg.FACTEXP_ID -> this.FACTEXP(pos)
			Function2Arg.ROOT_ID -> this.ROOT(pos)
		}
	}

	/**
	 * Calculates function with 3 arguments.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun f3ArgCalc(pos: Int)
	{
		when (this.tokensList[pos].tokenId)
		{
			Function3Arg.IF_ID -> this.IF(pos)
			Function3Arg.CHI_ID -> this.CHI(pos)
			Function3Arg.CHI_LR_ID -> this.CHI_LR(pos)
			Function3Arg.CHI_L_ID -> this.CHI_L(pos)
			Function3Arg.CHI_R_ID -> this.CHI_R(pos)
			Function3Arg.PDF_UNIFORM_CONT_ID -> this.PDF_UNIFORM_CONT(pos)
			Function3Arg.CDF_UNIFORM_CONT_ID -> this.CDF_UNIFORM_CONT(pos)
			Function3Arg.QNT_UNIFORM_CONT_ID -> this.QNT_UNIFORM_CONT(pos)
			Function3Arg.PDF_NORMAL_ID -> this.PDF_NORMAL(pos)
			Function3Arg.CDF_NORMAL_ID -> this.CDF_NORMAL(pos)
			Function3Arg.QNT_NORMAL_ID -> this.QNT_NORMAL(pos)
			Function3Arg.DIGIT_ID -> this.DIGIT(pos)
		}
	}

	/**
	 * Calculates Variadic function.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun variadicFunCalc(pos: Int)
	{
		when (this.tokensList[pos].tokenId)
		{
			FunctionVariadic.IFF_ID -> this.IFF(pos)
			FunctionVariadic.MIN_ID -> this.MIN_VARIADIC(pos)
			FunctionVariadic.MAX_ID -> this.MAX_VARIADIC(pos)
			FunctionVariadic.SUM_ID -> this.SUM_VARIADIC(pos)
			FunctionVariadic.PROD_ID -> this.PROD_VARIADIC(pos)
			FunctionVariadic.AVG_ID -> this.AVG_VARIADIC(pos)
			FunctionVariadic.VAR_ID -> this.VAR_VARIADIC(pos)
			FunctionVariadic.STD_ID -> this.STD_VARIADIC(pos)
			FunctionVariadic.CONT_FRAC_ID -> this.CONTINUED_FRACTION(pos)
			FunctionVariadic.CONT_POL_ID -> this.CONTINUED_POLYNOMIAL(pos)
			FunctionVariadic.GCD_ID -> this.GCD(pos)
			FunctionVariadic.LCM_ID -> this.LCM(pos)
			FunctionVariadic.RND_LIST_ID -> this.RND_LIST(pos)
			FunctionVariadic.COALESCE_ID -> this.COALESCE(pos)
			FunctionVariadic.OR_ID -> this.OR_VARIADIC(pos)
			FunctionVariadic.AND_ID -> this.AND_VARIADIC(pos)
			FunctionVariadic.XOR_ID -> this.XOR_VARIADIC(pos)
			FunctionVariadic.ARGMIN_ID -> this.ARGMIN_VARIADIC(pos)
			FunctionVariadic.ARGMAX_ID -> this.ARGMAX_VARIADIC(pos)
			FunctionVariadic.MEDIAN_ID -> this.MEDIAN_VARIADIC(pos)
			FunctionVariadic.MODE_ID -> this.MODE_VARIADIC(pos)
			FunctionVariadic.BASE_ID -> this.BASE_VARIADIC(pos)
			FunctionVariadic.NDIST_ID -> this.NDIST_VARIADIC(pos)
		}
	}

	/**
	 * Calculates calculus operators.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun calculusCalc(pos: Int)
	{
		when (this.tokensList[pos].tokenId)
		{
			CalculusOperator.SUM_ID -> this.SUM(pos)
			CalculusOperator.PROD_ID -> this.PROD(pos)
			CalculusOperator.MIN_ID -> this.MIN(pos)
			CalculusOperator.MAX_ID -> this.MAX(pos)
			CalculusOperator.AVG_ID -> this.AVG(pos)
			CalculusOperator.VAR_ID -> this.VAR(pos)
			CalculusOperator.STD_ID -> this.STD(pos)
			CalculusOperator.INT_ID -> this.INTEGRAL(pos)
			CalculusOperator.SOLVE_ID -> this.SOLVE(pos)
			CalculusOperator.DER_ID -> this.DERIVATIVE(pos, Calculus.GENERAL_DERIVATIVE)
			CalculusOperator.DER_LEFT_ID -> this.DERIVATIVE(pos, Calculus.LEFT_DERIVATIVE)
			CalculusOperator.DER_RIGHT_ID -> this.DERIVATIVE(pos, Calculus.RIGHT_DERIVATIVE)
			CalculusOperator.DERN_ID -> this.DERIVATIVE_NTH(pos, Calculus.GENERAL_DERIVATIVE)
			CalculusOperator.FORW_DIFF_ID -> this.FORWARD_DIFFERENCE(pos)
			CalculusOperator.BACKW_DIFF_ID -> this.BACKWARD_DIFFERENCE(pos)
		}
	}

	/**
	 * Calculates boolean operators.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun bolCalc(pos: Int)
	{
		when (this.tokensList[pos].tokenId)
		{
			BooleanOperator.AND_ID -> this.AND(pos)
			BooleanOperator.CIMP_ID -> this.CIMP(pos)
			BooleanOperator.CNIMP_ID -> this.CNIMP(pos)
			BooleanOperator.EQV_ID -> this.EQV(pos)
			BooleanOperator.IMP_ID -> this.IMP(pos)
			BooleanOperator.NAND_ID -> this.NAND(pos)
			BooleanOperator.NIMP_ID -> this.NIMP(pos)
			BooleanOperator.NOR_ID -> this.NOR(pos)
			BooleanOperator.OR_ID -> this.OR(pos)
			BooleanOperator.XOR_ID -> this.XOR(pos)
		}
	}

	/**
	 * Calculates Bitwise operators.
	 *
	 * @param pos Int
	 *
	 * @author Bas Milius
	 */
	private fun bitwiseCalc(pos: Int)
	{
		when (this.tokensList[pos].tokenId)
		{
			BitwiseOperator.AND_ID -> this.BITWISE_AND(pos)
			BitwiseOperator.OR_ID -> this.BITWISE_OR(pos)
			BitwiseOperator.XOR_ID -> this.BITWISE_XOR(pos)
			BitwiseOperator.LEFT_SHIFT_ID -> this.BITWISE_LEFT_SHIFT(pos)
			BitwiseOperator.RIGHT_SHIFT_ID -> this.BITWISE_RIGHT_SHIFT(pos)
		}
	}

	/**
	 * Creates parser key words list.
	 *
	 * @author Bas Milius
	 */
	private fun addParserKeyWords()
	{
		this.addKeyWord(Operator.PLUS_STR, Operator.PLUS_DESC, Operator.PLUS_ID, Operator.PLUS_SYN, Operator.PLUS_SINCE, Operator.TYPE_ID)
		this.addKeyWord(Operator.MINUS_STR, Operator.MINUS_DESC, Operator.MINUS_ID, Operator.MINUS_SYN, Operator.MINUS_SINCE, Operator.TYPE_ID)
		this.addKeyWord(Operator.MULTIPLY_STR, Operator.MULTIPLY_DESC, Operator.MULTIPLY_ID, Operator.MULTIPLY_SYN, Operator.MULTIPLY_SINCE, Operator.TYPE_ID)
		this.addKeyWord(Operator.DIVIDE_STR, Operator.DIVIDE_DESC, Operator.DIVIDE_ID, Operator.DIVIDE_SYN, Operator.DIVIDE_SINCE, Operator.TYPE_ID)
		this.addKeyWord(Operator.POWER_STR, Operator.POWER_DESC, Operator.POWER_ID, Operator.POWER_SYN, Operator.POWER_SINCE, Operator.TYPE_ID)
		this.addKeyWord(Operator.FACT_STR, Operator.FACT_DESC, Operator.FACT_ID, Operator.FACT_SYN, Operator.FACT_SINCE, Operator.TYPE_ID)
		this.addKeyWord(Operator.MOD_STR, Operator.MOD_DESC, Operator.MOD_ID, Operator.MOD_SYN, Operator.MOD_SINCE, Operator.TYPE_ID)
		this.addKeyWord(Operator.PERC_STR, Operator.PERC_DESC, Operator.PERC_ID, Operator.PERC_SYN, Operator.PERC_SINCE, Operator.TYPE_ID)

		this.addKeyWord(BooleanOperator.NEG_STR, BooleanOperator.NEG_DESC, BooleanOperator.NEG_ID, BooleanOperator.NEG_SYN, BooleanOperator.NEG_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.AND_STR, BooleanOperator.AND_DESC, BooleanOperator.AND_ID, BooleanOperator.AND_SYN, BooleanOperator.AND_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.AND1_STR, BooleanOperator.AND_DESC, BooleanOperator.AND_ID, BooleanOperator.AND1_SYN, BooleanOperator.AND_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.AND2_STR, BooleanOperator.AND_DESC, BooleanOperator.AND_ID, BooleanOperator.AND2_SYN, BooleanOperator.AND_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.NAND_STR, BooleanOperator.NAND_DESC, BooleanOperator.NAND_ID, BooleanOperator.NAND_SYN, BooleanOperator.NAND_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.NAND1_STR, BooleanOperator.NAND_DESC, BooleanOperator.NAND_ID, BooleanOperator.NAND1_SYN, BooleanOperator.NAND_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.NAND2_STR, BooleanOperator.NAND_DESC, BooleanOperator.NAND_ID, BooleanOperator.NAND2_SYN, BooleanOperator.NAND_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.OR_STR, BooleanOperator.OR_DESC, BooleanOperator.OR_ID, BooleanOperator.OR_SYN, BooleanOperator.OR_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.OR1_STR, BooleanOperator.OR_DESC, BooleanOperator.OR_ID, BooleanOperator.OR1_SYN, BooleanOperator.OR_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.OR2_STR, BooleanOperator.OR_DESC, BooleanOperator.OR_ID, BooleanOperator.OR2_SYN, BooleanOperator.OR_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.NOR_STR, BooleanOperator.NOR_DESC, BooleanOperator.NOR_ID, BooleanOperator.NOR_SYN, BooleanOperator.NOR_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.NOR1_STR, BooleanOperator.NOR_DESC, BooleanOperator.NOR_ID, BooleanOperator.NOR1_SYN, BooleanOperator.NOR_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.NOR2_STR, BooleanOperator.NOR_DESC, BooleanOperator.NOR_ID, BooleanOperator.NOR2_SYN, BooleanOperator.NOR_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.XOR_STR, BooleanOperator.XOR_DESC, BooleanOperator.XOR_ID, BooleanOperator.XOR_SYN, BooleanOperator.XOR_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.IMP_STR, BooleanOperator.IMP_DESC, BooleanOperator.IMP_ID, BooleanOperator.IMP_SYN, BooleanOperator.IMP_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.NIMP_STR, BooleanOperator.NIMP_DESC, BooleanOperator.NIMP_ID, BooleanOperator.NIMP_SYN, BooleanOperator.NIMP_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.CIMP_STR, BooleanOperator.CIMP_DESC, BooleanOperator.CIMP_ID, BooleanOperator.CIMP_SYN, BooleanOperator.CIMP_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.CNIMP_STR, BooleanOperator.CNIMP_DESC, BooleanOperator.CNIMP_ID, BooleanOperator.CNIMP_SYN, BooleanOperator.CNIMP_SINCE, BooleanOperator.TYPE_ID)
		this.addKeyWord(BooleanOperator.EQV_STR, BooleanOperator.EQV_DESC, BooleanOperator.EQV_ID, BooleanOperator.EQV_SYN, BooleanOperator.EQV_SINCE, BooleanOperator.TYPE_ID)

		this.addKeyWord(BinaryRelation.EQ_STR, BinaryRelation.EQ_DESC, BinaryRelation.EQ_ID, BinaryRelation.EQ_SYN, BinaryRelation.EQ_SINCE, BinaryRelation.TYPE_ID)
		this.addKeyWord(BinaryRelation.EQ1_STR, BinaryRelation.EQ_DESC, BinaryRelation.EQ_ID, BinaryRelation.EQ1_SYN, BinaryRelation.EQ_SINCE, BinaryRelation.TYPE_ID)
		this.addKeyWord(BinaryRelation.NEQ_STR, BinaryRelation.NEQ_DESC, BinaryRelation.NEQ_ID, BinaryRelation.NEQ_SYN, BinaryRelation.NEQ_SINCE, BinaryRelation.TYPE_ID)
		this.addKeyWord(BinaryRelation.NEQ1_STR, BinaryRelation.NEQ_DESC, BinaryRelation.NEQ_ID, BinaryRelation.NEQ1_SYN, BinaryRelation.NEQ_SINCE, BinaryRelation.TYPE_ID)
		this.addKeyWord(BinaryRelation.NEQ2_STR, BinaryRelation.NEQ_DESC, BinaryRelation.NEQ_ID, BinaryRelation.NEQ2_SYN, BinaryRelation.NEQ_SINCE, BinaryRelation.TYPE_ID)
		this.addKeyWord(BinaryRelation.LT_STR, BinaryRelation.LT_DESC, BinaryRelation.LT_ID, BinaryRelation.LT_SYN, BinaryRelation.LT_SINCE, BinaryRelation.TYPE_ID)
		this.addKeyWord(BinaryRelation.GT_STR, BinaryRelation.GT_DESC, BinaryRelation.GT_ID, BinaryRelation.GT_SYN, BinaryRelation.GT_SINCE, BinaryRelation.TYPE_ID)
		this.addKeyWord(BinaryRelation.LEQ_STR, BinaryRelation.LEQ_DESC, BinaryRelation.LEQ_ID, BinaryRelation.LEQ_SYN, BinaryRelation.LEQ_SINCE, BinaryRelation.TYPE_ID)
		this.addKeyWord(BinaryRelation.GEQ_STR, BinaryRelation.GEQ_DESC, BinaryRelation.GEQ_ID, BinaryRelation.GEQ_SYN, BinaryRelation.GEQ_SINCE, BinaryRelation.TYPE_ID)

		if (!this.parserKeyWordsOnly)
		{
			this.addKeyWord(Function1Arg.SIN_STR, Function1Arg.SIN_DESC, Function1Arg.SIN_ID, Function1Arg.SIN_SYN, Function1Arg.SIN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.COS_STR, Function1Arg.COS_DESC, Function1Arg.COS_ID, Function1Arg.COS_SYN, Function1Arg.COS_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.TAN_STR, Function1Arg.TAN_DESC, Function1Arg.TAN_ID, Function1Arg.TAN_SYN, Function1Arg.TAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.TG_STR, Function1Arg.TAN_DESC, Function1Arg.TAN_ID, Function1Arg.TG_SYN, Function1Arg.TAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.CTAN_STR, Function1Arg.CTAN_DESC, Function1Arg.CTAN_ID, Function1Arg.CTAN_SYN, Function1Arg.CTAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.CTG_STR, Function1Arg.CTAN_DESC, Function1Arg.CTAN_ID, Function1Arg.CTG_SYN, Function1Arg.CTAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.COT_STR, Function1Arg.CTAN_DESC, Function1Arg.CTAN_ID, Function1Arg.COT_SYN, Function1Arg.CTAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.SEC_STR, Function1Arg.SEC_DESC, Function1Arg.SEC_ID, Function1Arg.SEC_SYN, Function1Arg.SEC_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.COSEC_STR, Function1Arg.COSEC_DESC, Function1Arg.COSEC_ID, Function1Arg.COSEC_SYN, Function1Arg.COSEC_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.CSC_STR, Function1Arg.COSEC_DESC, Function1Arg.COSEC_ID, Function1Arg.CSC_SYN, Function1Arg.COSEC_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ASIN_STR, Function1Arg.ASIN_DESC, Function1Arg.ASIN_ID, Function1Arg.ASIN_SYN, Function1Arg.ASIN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARSIN_STR, Function1Arg.ASIN_DESC, Function1Arg.ASIN_ID, Function1Arg.ARSIN_SYN, Function1Arg.ASIN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCSIN_STR, Function1Arg.ASIN_DESC, Function1Arg.ASIN_ID, Function1Arg.ARCSIN_SYN, Function1Arg.ASIN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACOS_STR, Function1Arg.ACOS_DESC, Function1Arg.ACOS_ID, Function1Arg.ACOS_SYN, Function1Arg.ACOS_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCOS_STR, Function1Arg.ACOS_DESC, Function1Arg.ACOS_ID, Function1Arg.ARCOS_SYN, Function1Arg.ACOS_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCOS_STR, Function1Arg.ACOS_DESC, Function1Arg.ACOS_ID, Function1Arg.ARCCOS_SYN, Function1Arg.ACOS_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ATAN_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.ATAN_SYN, Function1Arg.ATAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCTAN_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.ARCTAN_SYN, Function1Arg.ATAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ATG_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.ATG_SYN, Function1Arg.ATAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCTG_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.ARCTG_SYN, Function1Arg.ATAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACTAN_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.ACTAN_SYN, Function1Arg.ACTAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCTAN_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.ARCCTAN_SYN, Function1Arg.ACTAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACTG_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.ACTG_SYN, Function1Arg.ACTAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCTG_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.ARCCTG_SYN, Function1Arg.ACTAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACOT_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.ACOT_SYN, Function1Arg.ACTAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCOT_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.ARCCOT_SYN, Function1Arg.ACTAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.LN_STR, Function1Arg.LN_DESC, Function1Arg.LN_ID, Function1Arg.LN_SYN, Function1Arg.LN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.LOG2_STR, Function1Arg.LOG2_DESC, Function1Arg.LOG2_ID, Function1Arg.LOG2_SYN, Function1Arg.LOG2_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.LOG10_STR, Function1Arg.LOG10_DESC, Function1Arg.LOG10_ID, Function1Arg.LOG10_SYN, Function1Arg.LOG10_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.RAD_STR, Function1Arg.RAD_DESC, Function1Arg.RAD_ID, Function1Arg.RAD_SYN, Function1Arg.RAD_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.EXP_STR, Function1Arg.EXP_DESC, Function1Arg.EXP_ID, Function1Arg.EXP_SYN, Function1Arg.EXP_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.SQRT_STR, Function1Arg.SQRT_DESC, Function1Arg.SQRT_ID, Function1Arg.SQRT_SYN, Function1Arg.SQRT_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.SINH_STR, Function1Arg.SINH_DESC, Function1Arg.SINH_ID, Function1Arg.SINH_SYN, Function1Arg.SINH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.COSH_STR, Function1Arg.COSH_DESC, Function1Arg.COSH_ID, Function1Arg.COSH_SYN, Function1Arg.COSH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.TANH_STR, Function1Arg.TANH_DESC, Function1Arg.TANH_ID, Function1Arg.TANH_SYN, Function1Arg.TANH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.TGH_STR, Function1Arg.TANH_DESC, Function1Arg.TANH_ID, Function1Arg.TGH_SYN, Function1Arg.TANH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.CTANH_STR, Function1Arg.COTH_DESC, Function1Arg.COTH_ID, Function1Arg.CTANH_SYN, Function1Arg.COTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.COTH_STR, Function1Arg.COTH_DESC, Function1Arg.COTH_ID, Function1Arg.COTH_SYN, Function1Arg.COTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.CTGH_STR, Function1Arg.COTH_DESC, Function1Arg.COTH_ID, Function1Arg.CTGH_SYN, Function1Arg.COTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.SECH_STR, Function1Arg.SECH_DESC, Function1Arg.SECH_ID, Function1Arg.SECH_SYN, Function1Arg.SECH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.CSCH_STR, Function1Arg.CSCH_DESC, Function1Arg.CSCH_ID, Function1Arg.CSCH_SYN, Function1Arg.CSCH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.COSECH_STR, Function1Arg.CSCH_DESC, Function1Arg.CSCH_ID, Function1Arg.COSECH_SYN, Function1Arg.CSCH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.DEG_STR, Function1Arg.DEG_DESC, Function1Arg.DEG_ID, Function1Arg.DEG_SYN, Function1Arg.DEG_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ABS_STR, Function1Arg.ABS_DESC, Function1Arg.ABS_ID, Function1Arg.ABS_SYN, Function1Arg.ABS_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.SGN_STR, Function1Arg.SGN_DESC, Function1Arg.SGN_ID, Function1Arg.SGN_SYN, Function1Arg.SGN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.FLOOR_STR, Function1Arg.FLOOR_DESC, Function1Arg.FLOOR_ID, Function1Arg.FLOOR_SYN, Function1Arg.FLOOR_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.CEIL_STR, Function1Arg.CEIL_DESC, Function1Arg.CEIL_ID, Function1Arg.CEIL_SYN, Function1Arg.CEIL_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.NOT_STR, Function1Arg.NOT_DESC, Function1Arg.NOT_ID, Function1Arg.NOT_SYN, Function1Arg.NOT_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ASINH_STR, Function1Arg.ARSINH_DESC, Function1Arg.ARSINH_ID, Function1Arg.ASINH_SYN, Function1Arg.ARSINH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARSINH_STR, Function1Arg.ARSINH_DESC, Function1Arg.ARSINH_ID, Function1Arg.ARSINH_SYN, Function1Arg.ARSINH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCSINH_STR, Function1Arg.ARSINH_DESC, Function1Arg.ARSINH_ID, Function1Arg.ARCSINH_SYN, Function1Arg.ARSINH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACOSH_STR, Function1Arg.ARCOSH_DESC, Function1Arg.ARCOSH_ID, Function1Arg.ACOSH_SYN, Function1Arg.ARCOSH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCOSH_STR, Function1Arg.ARCOSH_DESC, Function1Arg.ARCOSH_ID, Function1Arg.ARCOSH_SYN, Function1Arg.ARCOSH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCOSH_STR, Function1Arg.ARCOSH_DESC, Function1Arg.ARCOSH_ID, Function1Arg.ARCCOSH_SYN, Function1Arg.ARCOSH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ATANH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.ATANH_SYN, Function1Arg.ARTANH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCTANH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.ARCTANH_SYN, Function1Arg.ARTANH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ATGH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.ATGH_SYN, Function1Arg.ARTANH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCTGH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.ARCTGH_SYN, Function1Arg.ARTANH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACTANH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.ACTANH_SYN, Function1Arg.ARCOTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCTANH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.ARCCTANH_SYN, Function1Arg.ARCOTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACOTH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.ACOTH_SYN, Function1Arg.ARCOTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCOTH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.ARCOTH_SYN, Function1Arg.ARCOTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCOTH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.ARCCOTH_SYN, Function1Arg.ARCOTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACTGH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.ACTGH_SYN, Function1Arg.ARCOTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCTGH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.ARCCTGH_SYN, Function1Arg.ARCOTH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ASECH_STR, Function1Arg.ARSECH_DESC, Function1Arg.ARSECH_ID, Function1Arg.ASECH_SYN, Function1Arg.ARSECH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARSECH_STR, Function1Arg.ARSECH_DESC, Function1Arg.ARSECH_ID, Function1Arg.ARSECH_SYN, Function1Arg.ARSECH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCSECH_STR, Function1Arg.ARSECH_DESC, Function1Arg.ARSECH_ID, Function1Arg.ARCSECH_SYN, Function1Arg.ARSECH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACSCH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.ACSCH_SYN, Function1Arg.ARCSCH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCSCH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.ARCSCH_SYN, Function1Arg.ARCSCH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCSCH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.ARCCSCH_SYN, Function1Arg.ARCSCH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ACOSECH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.ACOSECH_SYN, Function1Arg.ARCSCH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCOSECH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.ARCOSECH_SYN, Function1Arg.ARCSCH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCOSECH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.ARCCOSECH_SYN, Function1Arg.ARCSCH_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.SA_STR, Function1Arg.SA_DESC, Function1Arg.SA_ID, Function1Arg.SA_SYN, Function1Arg.SA_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.SA1_STR, Function1Arg.SA_DESC, Function1Arg.SA_ID, Function1Arg.SA1_SYN, Function1Arg.SA_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.SINC_STR, Function1Arg.SINC_DESC, Function1Arg.SINC_ID, Function1Arg.SINC_SYN, Function1Arg.SINC_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.BELL_NUMBER_STR, Function1Arg.BELL_NUMBER_DESC, Function1Arg.BELL_NUMBER_ID, Function1Arg.BELL_NUMBER_SYN, Function1Arg.BELL_NUMBER_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.FIBONACCI_NUMBER_STR, Function1Arg.FIBONACCI_NUMBER_DESC, Function1Arg.FIBONACCI_NUMBER_ID, Function1Arg.FIBONACCI_NUMBER_SYN, Function1Arg.FIBONACCI_NUMBER_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.LUCAS_NUMBER_STR, Function1Arg.LUCAS_NUMBER_DESC, Function1Arg.LUCAS_NUMBER_ID, Function1Arg.LUCAS_NUMBER_SYN, Function1Arg.LUCAS_NUMBER_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.HARMONIC_NUMBER_STR, Function1Arg.HARMONIC_NUMBER_DESC, Function1Arg.HARMONIC_NUMBER_ID, Function1Arg.HARMONIC_NUMBER_SYN, Function1Arg.HARMONIC_NUMBER_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.IS_PRIME_STR, Function1Arg.IS_PRIME_DESC, Function1Arg.IS_PRIME_ID, Function1Arg.IS_PRIME_SYN, Function1Arg.IS_PRIME_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.PRIME_COUNT_STR, Function1Arg.PRIME_COUNT_DESC, Function1Arg.PRIME_COUNT_ID, Function1Arg.PRIME_COUNT_SYN, Function1Arg.PRIME_COUNT_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.EXP_INT_STR, Function1Arg.EXP_INT_DESC, Function1Arg.EXP_INT_ID, Function1Arg.EXP_INT_SYN, Function1Arg.EXP_INT_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.LOG_INT_STR, Function1Arg.LOG_INT_DESC, Function1Arg.LOG_INT_ID, Function1Arg.LOG_INT_SYN, Function1Arg.LOG_INT_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.OFF_LOG_INT_STR, Function1Arg.OFF_LOG_INT_DESC, Function1Arg.OFF_LOG_INT_ID, Function1Arg.OFF_LOG_INT_SYN, Function1Arg.OFF_LOG_INT_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.GAUSS_ERF_STR, Function1Arg.GAUSS_ERF_DESC, Function1Arg.GAUSS_ERF_ID, Function1Arg.GAUSS_ERF_SYN, Function1Arg.GAUSS_ERF_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.GAUSS_ERFC_STR, Function1Arg.GAUSS_ERFC_DESC, Function1Arg.GAUSS_ERFC_ID, Function1Arg.GAUSS_ERFC_SYN, Function1Arg.GAUSS_ERFC_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.GAUSS_ERF_INV_STR, Function1Arg.GAUSS_ERF_INV_DESC, Function1Arg.GAUSS_ERF_INV_ID, Function1Arg.GAUSS_ERF_INV_SYN, Function1Arg.GAUSS_ERF_INV_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.GAUSS_ERFC_INV_STR, Function1Arg.GAUSS_ERFC_INV_DESC, Function1Arg.GAUSS_ERFC_INV_ID, Function1Arg.GAUSS_ERFC_INV_SYN, Function1Arg.GAUSS_ERFC_INV_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ULP_STR, Function1Arg.ULP_DESC, Function1Arg.ULP_ID, Function1Arg.ULP_SYN, Function1Arg.ULP_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ISNAN_STR, Function1Arg.ISNAN_DESC, Function1Arg.ISNAN_ID, Function1Arg.ISNAN_SYN, Function1Arg.ISNAN_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.NDIG10_STR, Function1Arg.NDIG10_DESC, Function1Arg.NDIG10_ID, Function1Arg.NDIG10_SYN, Function1Arg.NDIG10_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.NFACT_STR, Function1Arg.NFACT_DESC, Function1Arg.NFACT_ID, Function1Arg.NFACT_SYN, Function1Arg.NFACT_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCSEC_STR, Function1Arg.ARCSEC_DESC, Function1Arg.ARCSEC_ID, Function1Arg.ARCSEC_SYN, Function1Arg.ARCSEC_SINCE, Function1Arg.TYPE_ID)
			this.addKeyWord(Function1Arg.ARCCSC_STR, Function1Arg.ARCCSC_DESC, Function1Arg.ARCCSC_ID, Function1Arg.ARCCSC_SYN, Function1Arg.ARCCSC_SINCE, Function1Arg.TYPE_ID)

			this.addKeyWord(Function2Arg.LOG_STR, Function2Arg.LOG_DESC, Function2Arg.LOG_ID, Function2Arg.LOG_SYN, Function2Arg.LOG_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.MOD_STR, Function2Arg.MOD_DESC, Function2Arg.MOD_ID, Function2Arg.MOD_SYN, Function2Arg.MOD_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.BINOM_COEFF_STR, Function2Arg.BINOM_COEFF_DESC, Function2Arg.BINOM_COEFF_ID, Function2Arg.BINOM_COEFF_SYN, Function2Arg.BINOM_COEFF_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.BERNOULLI_NUMBER_STR, Function2Arg.BERNOULLI_NUMBER_DESC, Function2Arg.BERNOULLI_NUMBER_ID, Function2Arg.BERNOULLI_NUMBER_SYN, Function2Arg.BERNOULLI_NUMBER_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.STIRLING1_NUMBER_STR, Function2Arg.STIRLING1_NUMBER_DESC, Function2Arg.STIRLING1_NUMBER_ID, Function2Arg.STIRLING1_NUMBER_SYN, Function2Arg.STIRLING1_NUMBER_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.STIRLING2_NUMBER_STR, Function2Arg.STIRLING2_NUMBER_DESC, Function2Arg.STIRLING2_NUMBER_ID, Function2Arg.STIRLING2_NUMBER_SYN, Function2Arg.STIRLING2_NUMBER_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.WORPITZKY_NUMBER_STR, Function2Arg.WORPITZKY_NUMBER_DESC, Function2Arg.WORPITZKY_NUMBER_ID, Function2Arg.WORPITZKY_NUMBER_SYN, Function2Arg.WORPITZKY_NUMBER_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.EULER_NUMBER_STR, Function2Arg.EULER_NUMBER_DESC, Function2Arg.EULER_NUMBER_ID, Function2Arg.EULER_NUMBER_SYN, Function2Arg.EULER_NUMBER_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.KRONECKER_DELTA_STR, Function2Arg.KRONECKER_DELTA_DESC, Function2Arg.KRONECKER_DELTA_ID, Function2Arg.KRONECKER_DELTA_SYN, Function2Arg.KRONECKER_DELTA_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.EULER_POLYNOMIAL_STR, Function2Arg.EULER_POLYNOMIAL_DESC, Function2Arg.EULER_POLYNOMIAL_ID, Function2Arg.EULER_POLYNOMIAL_SYN, Function2Arg.EULER_POLYNOMIAL_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.HARMONIC_NUMBER_STR, Function2Arg.HARMONIC_NUMBER_DESC, Function2Arg.HARMONIC_NUMBER_ID, Function2Arg.HARMONIC_NUMBER_SYN, Function2Arg.HARMONIC_NUMBER_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.RND_UNIFORM_CONT_STR, Function2Arg.RND_UNIFORM_CONT_DESC, Function2Arg.RND_UNIFORM_CONT_ID, Function2Arg.RND_UNIFORM_CONT_SYN, Function2Arg.RND_UNIFORM_CONT_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.RND_UNIFORM_DISCR_STR, Function2Arg.RND_UNIFORM_DISCR_DESC, Function2Arg.RND_UNIFORM_DISCR_ID, Function2Arg.RND_UNIFORM_DISCR_SYN, Function2Arg.RND_UNIFORM_DISCR_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.ROUND_STR, Function2Arg.ROUND_DESC, Function2Arg.ROUND_ID, Function2Arg.ROUND_SYN, Function2Arg.ROUND_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.RND_NORMAL_STR, Function2Arg.RND_NORMAL_DESC, Function2Arg.RND_NORMAL_ID, Function2Arg.RND_NORMAL_SYN, Function2Arg.RND_NORMAL_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.NDIG_STR, Function2Arg.NDIG_DESC, Function2Arg.NDIG_ID, Function2Arg.NDIG_SYN, Function2Arg.NDIG_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.DIGIT10_STR, Function2Arg.DIGIT10_DESC, Function2Arg.DIGIT10_ID, Function2Arg.DIGIT10_SYN, Function2Arg.DIGIT10_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.FACTVAL_STR, Function2Arg.FACTVAL_DESC, Function2Arg.FACTVAL_ID, Function2Arg.FACTVAL_SYN, Function2Arg.FACTVAL_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.FACTEXP_STR, Function2Arg.FACTEXP_DESC, Function2Arg.FACTEXP_ID, Function2Arg.FACTEXP_SYN, Function2Arg.FACTEXP_SINCE, Function2Arg.TYPE_ID)
			this.addKeyWord(Function2Arg.ROOT_STR, Function2Arg.ROOT_DESC, Function2Arg.ROOT_ID, Function2Arg.ROOT_SYN, Function2Arg.ROOT_SINCE, Function2Arg.TYPE_ID)

			this.addKeyWord(Function3Arg.IF_STR, Function3Arg.IF_DESC, Function3Arg.IF_CONDITION_ID, Function3Arg.IF_SYN, Function3Arg.IF_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.CHI_STR, Function3Arg.CHI_DESC, Function3Arg.CHI_ID, Function3Arg.CHI_SYN, Function3Arg.CHI_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.CHI_LR_STR, Function3Arg.CHI_LR_DESC, Function3Arg.CHI_LR_ID, Function3Arg.CHI_LR_SYN, Function3Arg.CHI_LR_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.CHI_L_STR, Function3Arg.CHI_L_DESC, Function3Arg.CHI_L_ID, Function3Arg.CHI_L_SYN, Function3Arg.CHI_L_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.CHI_R_STR, Function3Arg.CHI_R_DESC, Function3Arg.CHI_R_ID, Function3Arg.CHI_R_SYN, Function3Arg.CHI_R_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.PDF_UNIFORM_CONT_STR, Function3Arg.PDF_UNIFORM_CONT_DESC, Function3Arg.PDF_UNIFORM_CONT_ID, Function3Arg.PDF_UNIFORM_CONT_SYN, Function3Arg.PDF_UNIFORM_CONT_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.CDF_UNIFORM_CONT_STR, Function3Arg.CDF_UNIFORM_CONT_DESC, Function3Arg.CDF_UNIFORM_CONT_ID, Function3Arg.CDF_UNIFORM_CONT_SYN, Function3Arg.CDF_UNIFORM_CONT_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.QNT_UNIFORM_CONT_STR, Function3Arg.QNT_UNIFORM_CONT_DESC, Function3Arg.QNT_UNIFORM_CONT_ID, Function3Arg.QNT_UNIFORM_CONT_SYN, Function3Arg.QNT_UNIFORM_CONT_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.PDF_NORMAL_STR, Function3Arg.PDF_NORMAL_DESC, Function3Arg.PDF_NORMAL_ID, Function3Arg.PDF_NORMAL_SYN, Function3Arg.PDF_NORMAL_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.CDF_NORMAL_STR, Function3Arg.CDF_NORMAL_DESC, Function3Arg.CDF_NORMAL_ID, Function3Arg.CDF_NORMAL_SYN, Function3Arg.CDF_NORMAL_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.QNT_NORMAL_STR, Function3Arg.QNT_NORMAL_DESC, Function3Arg.QNT_NORMAL_ID, Function3Arg.QNT_NORMAL_SYN, Function3Arg.QNT_NORMAL_SINCE, Function3Arg.TYPE_ID)
			this.addKeyWord(Function3Arg.DIGIT_STR, Function3Arg.DIGIT_DESC, Function3Arg.DIGIT_ID, Function3Arg.DIGIT_SYN, Function3Arg.DIGIT_SINCE, Function3Arg.TYPE_ID)

			this.addKeyWord(FunctionVariadic.IFF_STR, FunctionVariadic.IFF_DESC, FunctionVariadic.IFF_ID, FunctionVariadic.IFF_SYN, FunctionVariadic.IFF_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.MIN_STR, FunctionVariadic.MIN_DESC, FunctionVariadic.MIN_ID, FunctionVariadic.MIN_SYN, FunctionVariadic.MIN_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.MAX_STR, FunctionVariadic.MAX_DESC, FunctionVariadic.MAX_ID, FunctionVariadic.MAX_SYN, FunctionVariadic.MAX_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.CONT_FRAC_STR, FunctionVariadic.CONT_FRAC_DESC, FunctionVariadic.CONT_FRAC_ID, FunctionVariadic.CONT_FRAC_SYN, FunctionVariadic.CONT_FRAC_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.CONT_POL_STR, FunctionVariadic.CONT_POL_DESC, FunctionVariadic.CONT_POL_ID, FunctionVariadic.CONT_POL_SYN, FunctionVariadic.CONT_POL_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.GCD_STR, FunctionVariadic.GCD_DESC, FunctionVariadic.GCD_ID, FunctionVariadic.GCD_SYN, FunctionVariadic.GCD_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.LCM_STR, FunctionVariadic.LCM_DESC, FunctionVariadic.LCM_ID, FunctionVariadic.LCM_SYN, FunctionVariadic.LCM_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.SUM_STR, FunctionVariadic.SUM_DESC, FunctionVariadic.SUM_ID, FunctionVariadic.SUM_SYN, FunctionVariadic.SUM_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.PROD_STR, FunctionVariadic.PROD_DESC, FunctionVariadic.PROD_ID, FunctionVariadic.PROD_SYN, FunctionVariadic.PROD_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.AVG_STR, FunctionVariadic.AVG_DESC, FunctionVariadic.AVG_ID, FunctionVariadic.AVG_SYN, FunctionVariadic.AVG_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.VAR_STR, FunctionVariadic.VAR_DESC, FunctionVariadic.VAR_ID, FunctionVariadic.VAR_SYN, FunctionVariadic.VAR_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.STD_STR, FunctionVariadic.STD_DESC, FunctionVariadic.STD_ID, FunctionVariadic.STD_SYN, FunctionVariadic.STD_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.RND_LIST_STR, FunctionVariadic.RND_LIST_DESC, FunctionVariadic.RND_LIST_ID, FunctionVariadic.RND_LIST_SYN, FunctionVariadic.RND_LIST_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.COALESCE_STR, FunctionVariadic.COALESCE_DESC, FunctionVariadic.COALESCE_ID, FunctionVariadic.COALESCE_SYN, FunctionVariadic.COALESCE_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.OR_STR, FunctionVariadic.OR_DESC, FunctionVariadic.OR_ID, FunctionVariadic.OR_SYN, FunctionVariadic.OR_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.AND_STR, FunctionVariadic.AND_DESC, FunctionVariadic.AND_ID, FunctionVariadic.AND_SYN, FunctionVariadic.AND_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.XOR_STR, FunctionVariadic.XOR_DESC, FunctionVariadic.XOR_ID, FunctionVariadic.XOR_SYN, FunctionVariadic.XOR_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.ARGMIN_STR, FunctionVariadic.ARGMIN_DESC, FunctionVariadic.ARGMIN_ID, FunctionVariadic.ARGMIN_SYN, FunctionVariadic.ARGMIN_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.ARGMAX_STR, FunctionVariadic.ARGMAX_DESC, FunctionVariadic.ARGMAX_ID, FunctionVariadic.ARGMAX_SYN, FunctionVariadic.ARGMAX_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.MEDIAN_STR, FunctionVariadic.MEDIAN_DESC, FunctionVariadic.MEDIAN_ID, FunctionVariadic.MEDIAN_SYN, FunctionVariadic.MEDIAN_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.MODE_STR, FunctionVariadic.MODE_DESC, FunctionVariadic.MODE_ID, FunctionVariadic.MODE_SYN, FunctionVariadic.MODE_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.BASE_STR, FunctionVariadic.BASE_DESC, FunctionVariadic.BASE_ID, FunctionVariadic.BASE_SYN, FunctionVariadic.BASE_SINCE, FunctionVariadic.TYPE_ID)
			this.addKeyWord(FunctionVariadic.NDIST_STR, FunctionVariadic.NDIST_DESC, FunctionVariadic.NDIST_ID, FunctionVariadic.NDIST_SYN, FunctionVariadic.NDIST_SINCE, FunctionVariadic.TYPE_ID)

			this.addKeyWord(CalculusOperator.SUM_STR, CalculusOperator.SUM_DESC, CalculusOperator.SUM_ID, CalculusOperator.SUM_SYN, CalculusOperator.SUM_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.PROD_STR, CalculusOperator.PROD_DESC, CalculusOperator.PROD_ID, CalculusOperator.PROD_SYN, CalculusOperator.PROD_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.INT_STR, CalculusOperator.INT_DESC, CalculusOperator.INT_ID, CalculusOperator.INT_SYN, CalculusOperator.INT_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.DER_STR, CalculusOperator.DER_DESC, CalculusOperator.DER_ID, CalculusOperator.DER_SYN, CalculusOperator.DER_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.DER_LEFT_STR, CalculusOperator.DER_LEFT_DESC, CalculusOperator.DER_LEFT_ID, CalculusOperator.DER_LEFT_SYN, CalculusOperator.DER_LEFT_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.DER_RIGHT_STR, CalculusOperator.DER_RIGHT_DESC, CalculusOperator.DER_RIGHT_ID, CalculusOperator.DER_RIGHT_SYN, CalculusOperator.DER_RIGHT_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.DERN_STR, CalculusOperator.DERN_DESC, CalculusOperator.DERN_ID, CalculusOperator.DERN_SYN, CalculusOperator.DERN_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.FORW_DIFF_STR, CalculusOperator.FORW_DIFF_DESC, CalculusOperator.FORW_DIFF_ID, CalculusOperator.FORW_DIFF_SYN, CalculusOperator.FORW_DIFF_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.BACKW_DIFF_STR, CalculusOperator.BACKW_DIFF_DESC, CalculusOperator.BACKW_DIFF_ID, CalculusOperator.BACKW_DIFF_SYN, CalculusOperator.BACKW_DIFF_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.AVG_STR, CalculusOperator.AVG_DESC, CalculusOperator.AVG_ID, CalculusOperator.AVG_SYN, CalculusOperator.AVG_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.VAR_STR, CalculusOperator.VAR_DESC, CalculusOperator.VAR_ID, CalculusOperator.VAR_SYN, CalculusOperator.VAR_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.STD_STR, CalculusOperator.STD_DESC, CalculusOperator.STD_ID, CalculusOperator.STD_SYN, CalculusOperator.STD_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.MIN_STR, CalculusOperator.MIN_DESC, CalculusOperator.MIN_ID, CalculusOperator.MIN_SYN, CalculusOperator.MIN_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.MAX_STR, CalculusOperator.MAX_DESC, CalculusOperator.MAX_ID, CalculusOperator.MAX_SYN, CalculusOperator.MAX_SINCE, CalculusOperator.TYPE_ID)
			this.addKeyWord(CalculusOperator.SOLVE_STR, CalculusOperator.SOLVE_DESC, CalculusOperator.SOLVE_ID, CalculusOperator.SOLVE_SYN, CalculusOperator.SOLVE_SINCE, CalculusOperator.TYPE_ID)

			this.addKeyWord(ConstantValue.PI_STR, ConstantValue.PI_DESC, ConstantValue.PI_ID, ConstantValue.PI_SYN, ConstantValue.PI_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.EULER_STR, ConstantValue.EULER_DESC, ConstantValue.EULER_ID, ConstantValue.EULER_SYN, ConstantValue.EULER_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.EULER_MASCHERONI_STR, ConstantValue.EULER_MASCHERONI_DESC, ConstantValue.EULER_MASCHERONI_ID, ConstantValue.EULER_MASCHERONI_SYN, ConstantValue.EULER_MASCHERONI_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.GOLDEN_RATIO_STR, ConstantValue.GOLDEN_RATIO_DESC, ConstantValue.GOLDEN_RATIO_ID, ConstantValue.GOLDEN_RATIO_SYN, ConstantValue.GOLDEN_RATIO_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.PLASTIC_STR, ConstantValue.PLASTIC_DESC, ConstantValue.PLASTIC_ID, ConstantValue.PLASTIC_SYN, ConstantValue.PLASTIC_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.EMBREE_TREFETHEN_STR, ConstantValue.EMBREE_TREFETHEN_DESC, ConstantValue.EMBREE_TREFETHEN_ID, ConstantValue.EMBREE_TREFETHEN_SYN, ConstantValue.EMBREE_TREFETHEN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.FEIGENBAUM_DELTA_STR, ConstantValue.FEIGENBAUM_DELTA_DESC, ConstantValue.FEIGENBAUM_DELTA_ID, ConstantValue.FEIGENBAUM_DELTA_SYN, ConstantValue.FEIGENBAUM_DELTA_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.FEIGENBAUM_ALFA_STR, ConstantValue.FEIGENBAUM_ALFA_DESC, ConstantValue.FEIGENBAUM_ALFA_ID, ConstantValue.FEIGENBAUM_ALFA_SYN, ConstantValue.FEIGENBAUM_ALFA_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.TWIN_PRIME_STR, ConstantValue.TWIN_PRIME_DESC, ConstantValue.TWIN_PRIME_ID, ConstantValue.TWIN_PRIME_SYN, ConstantValue.TWIN_PRIME_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MEISSEL_MERTEENS_STR, ConstantValue.MEISSEL_MERTEENS_DESC, ConstantValue.MEISSEL_MERTEENS_ID, ConstantValue.MEISSEL_MERTEENS_SYN, ConstantValue.MEISSEL_MERTEENS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.BRAUN_TWIN_PRIME_STR, ConstantValue.BRAUN_TWIN_PRIME_DESC, ConstantValue.BRAUN_TWIN_PRIME_ID, ConstantValue.BRAUN_TWIN_PRIME_SYN, ConstantValue.BRAUN_TWIN_PRIME_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.BRAUN_PRIME_QUADR_STR, ConstantValue.BRAUN_PRIME_QUADR_DESC, ConstantValue.BRAUN_PRIME_QUADR_ID, ConstantValue.BRAUN_PRIME_QUADR_SYN, ConstantValue.BRAUN_PRIME_QUADR_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.BRUIJN_NEWMAN_STR, ConstantValue.BRUIJN_NEWMAN_DESC, ConstantValue.BRUIJN_NEWMAN_ID, ConstantValue.BRUIJN_NEWMAN_SYN, ConstantValue.BRUIJN_NEWMAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.CATALAN_STR, ConstantValue.CATALAN_DESC, ConstantValue.CATALAN_ID, ConstantValue.CATALAN_SYN, ConstantValue.CATALAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.LANDAU_RAMANUJAN_STR, ConstantValue.LANDAU_RAMANUJAN_DESC, ConstantValue.LANDAU_RAMANUJAN_ID, ConstantValue.LANDAU_RAMANUJAN_SYN, ConstantValue.LANDAU_RAMANUJAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.VISWANATH_STR, ConstantValue.VISWANATH_DESC, ConstantValue.VISWANATH_ID, ConstantValue.VISWANATH_SYN, ConstantValue.VISWANATH_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.LEGENDRE_STR, ConstantValue.LEGENDRE_DESC, ConstantValue.LEGENDRE_ID, ConstantValue.LEGENDRE_SYN, ConstantValue.LEGENDRE_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.RAMANUJAN_SOLDNER_STR, ConstantValue.RAMANUJAN_SOLDNER_DESC, ConstantValue.RAMANUJAN_SOLDNER_ID, ConstantValue.RAMANUJAN_SOLDNER_SYN, ConstantValue.RAMANUJAN_SOLDNER_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.ERDOS_BORWEIN_STR, ConstantValue.ERDOS_BORWEIN_DESC, ConstantValue.ERDOS_BORWEIN_ID, ConstantValue.ERDOS_BORWEIN_SYN, ConstantValue.ERDOS_BORWEIN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.BERNSTEIN_STR, ConstantValue.BERNSTEIN_DESC, ConstantValue.BERNSTEIN_ID, ConstantValue.BERNSTEIN_SYN, ConstantValue.BERNSTEIN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.GAUSS_KUZMIN_WIRSING_STR, ConstantValue.GAUSS_KUZMIN_WIRSING_DESC, ConstantValue.GAUSS_KUZMIN_WIRSING_ID, ConstantValue.GAUSS_KUZMIN_WIRSING_SYN, ConstantValue.GAUSS_KUZMIN_WIRSING_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.HAFNER_SARNAK_MCCURLEY_STR, ConstantValue.HAFNER_SARNAK_MCCURLEY_DESC, ConstantValue.HAFNER_SARNAK_MCCURLEY_ID, ConstantValue.HAFNER_SARNAK_MCCURLEY_SYN, ConstantValue.HAFNER_SARNAK_MCCURLEY_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.GOLOMB_DICKMAN_STR, ConstantValue.GOLOMB_DICKMAN_DESC, ConstantValue.GOLOMB_DICKMAN_ID, ConstantValue.GOLOMB_DICKMAN_SYN, ConstantValue.GOLOMB_DICKMAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.CAHEN_STR, ConstantValue.CAHEN_DESC, ConstantValue.CAHEN_ID, ConstantValue.CAHEN_SYN, ConstantValue.CAHEN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.LAPLACE_LIMIT_STR, ConstantValue.LAPLACE_LIMIT_DESC, ConstantValue.LAPLACE_LIMIT_ID, ConstantValue.LAPLACE_LIMIT_SYN, ConstantValue.LAPLACE_LIMIT_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.ALLADI_GRINSTEAD_STR, ConstantValue.ALLADI_GRINSTEAD_DESC, ConstantValue.ALLADI_GRINSTEAD_ID, ConstantValue.ALLADI_GRINSTEAD_SYN, ConstantValue.ALLADI_GRINSTEAD_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.LENGYEL_STR, ConstantValue.LENGYEL_DESC, ConstantValue.LENGYEL_ID, ConstantValue.LENGYEL_SYN, ConstantValue.LENGYEL_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.LEVY_STR, ConstantValue.LEVY_DESC, ConstantValue.LEVY_ID, ConstantValue.LEVY_SYN, ConstantValue.LEVY_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.APERY_STR, ConstantValue.APERY_DESC, ConstantValue.APERY_ID, ConstantValue.APERY_SYN, ConstantValue.APERY_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MILLS_STR, ConstantValue.MILLS_DESC, ConstantValue.MILLS_ID, ConstantValue.MILLS_SYN, ConstantValue.MILLS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.BACKHOUSE_STR, ConstantValue.BACKHOUSE_DESC, ConstantValue.BACKHOUSE_ID, ConstantValue.BACKHOUSE_SYN, ConstantValue.BACKHOUSE_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.PORTER_STR, ConstantValue.PORTER_DESC, ConstantValue.PORTER_ID, ConstantValue.PORTER_SYN, ConstantValue.PORTER_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.LIEB_QUARE_ICE_STR, ConstantValue.LIEB_QUARE_ICE_DESC, ConstantValue.LIEB_QUARE_ICE_ID, ConstantValue.LIEB_QUARE_ICE_SYN, ConstantValue.LIEB_QUARE_ICE_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.NIVEN_STR, ConstantValue.NIVEN_DESC, ConstantValue.NIVEN_ID, ConstantValue.NIVEN_SYN, ConstantValue.NIVEN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.SIERPINSKI_STR, ConstantValue.SIERPINSKI_DESC, ConstantValue.SIERPINSKI_ID, ConstantValue.SIERPINSKI_SYN, ConstantValue.SIERPINSKI_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.KHINCHIN_STR, ConstantValue.KHINCHIN_DESC, ConstantValue.KHINCHIN_ID, ConstantValue.KHINCHIN_SYN, ConstantValue.KHINCHIN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.FRANSEN_ROBINSON_STR, ConstantValue.FRANSEN_ROBINSON_DESC, ConstantValue.FRANSEN_ROBINSON_ID, ConstantValue.FRANSEN_ROBINSON_SYN, ConstantValue.FRANSEN_ROBINSON_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.LANDAU_STR, ConstantValue.LANDAU_DESC, ConstantValue.LANDAU_ID, ConstantValue.LANDAU_SYN, ConstantValue.LANDAU_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.PARABOLIC_STR, ConstantValue.PARABOLIC_DESC, ConstantValue.PARABOLIC_ID, ConstantValue.PARABOLIC_SYN, ConstantValue.PARABOLIC_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.OMEGA_STR, ConstantValue.OMEGA_DESC, ConstantValue.OMEGA_ID, ConstantValue.OMEGA_SYN, ConstantValue.OMEGA_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MRB_STR, ConstantValue.MRB_DESC, ConstantValue.MRB_ID, ConstantValue.MRB_SYN, ConstantValue.MRB_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.LI2_STR, ConstantValue.LI2_DESC, ConstantValue.LI2_ID, ConstantValue.LI2_SYN, ConstantValue.LI2_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.GOMPERTZ_STR, ConstantValue.GOMPERTZ_DESC, ConstantValue.GOMPERTZ_ID, ConstantValue.GOMPERTZ_SYN, ConstantValue.GOMPERTZ_SINCE, ConstantValue.TYPE_ID)

			this.addKeyWord(ConstantValue.LIGHT_SPEED_STR, ConstantValue.LIGHT_SPEED_DESC, ConstantValue.LIGHT_SPEED_ID, ConstantValue.LIGHT_SPEED_SYN, ConstantValue.LIGHT_SPEED_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.GRAVITATIONAL_CONSTANT_STR, ConstantValue.GRAVITATIONAL_CONSTANT_DESC, ConstantValue.GRAVITATIONAL_CONSTANT_ID, ConstantValue.GRAVITATIONAL_CONSTANT_SYN, ConstantValue.GRAVITATIONAL_CONSTANT_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.GRAVIT_ACC_EARTH_STR, ConstantValue.GRAVIT_ACC_EARTH_DESC, ConstantValue.GRAVIT_ACC_EARTH_ID, ConstantValue.GRAVIT_ACC_EARTH_SYN, ConstantValue.GRAVIT_ACC_EARTH_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.PLANCK_CONSTANT_STR, ConstantValue.PLANCK_CONSTANT_DESC, ConstantValue.PLANCK_CONSTANT_ID, ConstantValue.PLANCK_CONSTANT_SYN, ConstantValue.PLANCK_CONSTANT_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.PLANCK_CONSTANT_REDUCED_STR, ConstantValue.PLANCK_CONSTANT_REDUCED_DESC, ConstantValue.PLANCK_CONSTANT_REDUCED_ID, ConstantValue.PLANCK_CONSTANT_REDUCED_SYN, ConstantValue.PLANCK_CONSTANT_REDUCED_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.PLANCK_LENGTH_STR, ConstantValue.PLANCK_LENGTH_DESC, ConstantValue.PLANCK_LENGTH_ID, ConstantValue.PLANCK_LENGTH_SYN, ConstantValue.PLANCK_LENGTH_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.PLANCK_MASS_STR, ConstantValue.PLANCK_MASS_DESC, ConstantValue.PLANCK_MASS_ID, ConstantValue.PLANCK_MASS_SYN, ConstantValue.PLANCK_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.PLANCK_TIME_STR, ConstantValue.PLANCK_TIME_DESC, ConstantValue.PLANCK_TIME_ID, ConstantValue.PLANCK_TIME_SYN, ConstantValue.PLANCK_TIME_SINCE, ConstantValue.TYPE_ID)

			this.addKeyWord(ConstantValue.LIGHT_YEAR_STR, ConstantValue.LIGHT_YEAR_DESC, ConstantValue.LIGHT_YEAR_ID, ConstantValue.LIGHT_YEAR_SYN, ConstantValue.LIGHT_YEAR_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.ASTRONOMICAL_UNIT_STR, ConstantValue.ASTRONOMICAL_UNIT_DESC, ConstantValue.ASTRONOMICAL_UNIT_ID, ConstantValue.ASTRONOMICAL_UNIT_SYN, ConstantValue.ASTRONOMICAL_UNIT_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.PARSEC_STR, ConstantValue.PARSEC_DESC, ConstantValue.PARSEC_ID, ConstantValue.PARSEC_SYN, ConstantValue.PARSEC_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.KILOPARSEC_STR, ConstantValue.KILOPARSEC_DESC, ConstantValue.KILOPARSEC_ID, ConstantValue.KILOPARSEC_SYN, ConstantValue.KILOPARSEC_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.EARTH_RADIUS_EQUATORIAL_STR, ConstantValue.EARTH_RADIUS_EQUATORIAL_DESC, ConstantValue.EARTH_RADIUS_EQUATORIAL_ID, ConstantValue.EARTH_RADIUS_EQUATORIAL_SYN, ConstantValue.EARTH_RADIUS_EQUATORIAL_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.EARTH_RADIUS_POLAR_STR, ConstantValue.EARTH_RADIUS_POLAR_DESC, ConstantValue.EARTH_RADIUS_POLAR_ID, ConstantValue.EARTH_RADIUS_POLAR_SYN, ConstantValue.EARTH_RADIUS_POLAR_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.EARTH_RADIUS_MEAN_STR, ConstantValue.EARTH_RADIUS_MEAN_DESC, ConstantValue.EARTH_RADIUS_MEAN_ID, ConstantValue.EARTH_RADIUS_MEAN_SYN, ConstantValue.EARTH_RADIUS_MEAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.EARTH_MASS_STR, ConstantValue.EARTH_MASS_DESC, ConstantValue.EARTH_MASS_ID, ConstantValue.EARTH_MASS_SYN, ConstantValue.EARTH_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.EARTH_SEMI_MAJOR_AXIS_STR, ConstantValue.EARTH_SEMI_MAJOR_AXIS_DESC, ConstantValue.EARTH_SEMI_MAJOR_AXIS_ID, ConstantValue.EARTH_SEMI_MAJOR_AXIS_SYN, ConstantValue.EARTH_SEMI_MAJOR_AXIS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MOON_RADIUS_MEAN_STR, ConstantValue.MOON_RADIUS_MEAN_DESC, ConstantValue.MOON_RADIUS_MEAN_ID, ConstantValue.MOON_RADIUS_MEAN_SYN, ConstantValue.MOON_RADIUS_MEAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MOON_MASS_STR, ConstantValue.MOON_MASS_DESC, ConstantValue.MOON_MASS_ID, ConstantValue.MOON_MASS_SYN, ConstantValue.MOON_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MONN_SEMI_MAJOR_AXIS_STR, ConstantValue.MONN_SEMI_MAJOR_AXIS_DESC, ConstantValue.MONN_SEMI_MAJOR_AXIS_ID, ConstantValue.MONN_SEMI_MAJOR_AXIS_SYN, ConstantValue.MONN_SEMI_MAJOR_AXIS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.SOLAR_RADIUS_STR, ConstantValue.SOLAR_RADIUS_DESC, ConstantValue.SOLAR_RADIUS_ID, ConstantValue.SOLAR_RADIUS_SYN, ConstantValue.SOLAR_RADIUS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.SOLAR_MASS_STR, ConstantValue.SOLAR_MASS_DESC, ConstantValue.SOLAR_MASS_ID, ConstantValue.SOLAR_MASS_SYN, ConstantValue.SOLAR_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MERCURY_RADIUS_MEAN_STR, ConstantValue.MERCURY_RADIUS_MEAN_DESC, ConstantValue.MERCURY_RADIUS_MEAN_ID, ConstantValue.MERCURY_RADIUS_MEAN_SYN, ConstantValue.MERCURY_RADIUS_MEAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MERCURY_MASS_STR, ConstantValue.MERCURY_MASS_DESC, ConstantValue.MERCURY_MASS_ID, ConstantValue.MERCURY_MASS_SYN, ConstantValue.MERCURY_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MERCURY_SEMI_MAJOR_AXIS_STR, ConstantValue.MERCURY_SEMI_MAJOR_AXIS_DESC, ConstantValue.MERCURY_SEMI_MAJOR_AXIS_ID, ConstantValue.MERCURY_SEMI_MAJOR_AXIS_SYN, ConstantValue.MERCURY_SEMI_MAJOR_AXIS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.VENUS_RADIUS_MEAN_STR, ConstantValue.VENUS_RADIUS_MEAN_DESC, ConstantValue.VENUS_RADIUS_MEAN_ID, ConstantValue.VENUS_RADIUS_MEAN_SYN, ConstantValue.VENUS_RADIUS_MEAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.VENUS_MASS_STR, ConstantValue.VENUS_MASS_DESC, ConstantValue.VENUS_MASS_ID, ConstantValue.VENUS_MASS_SYN, ConstantValue.VENUS_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.VENUS_SEMI_MAJOR_AXIS_STR, ConstantValue.VENUS_SEMI_MAJOR_AXIS_DESC, ConstantValue.VENUS_SEMI_MAJOR_AXIS_ID, ConstantValue.VENUS_SEMI_MAJOR_AXIS_SYN, ConstantValue.VENUS_SEMI_MAJOR_AXIS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MARS_RADIUS_MEAN_STR, ConstantValue.MARS_RADIUS_MEAN_DESC, ConstantValue.MARS_RADIUS_MEAN_ID, ConstantValue.MARS_RADIUS_MEAN_SYN, ConstantValue.MARS_RADIUS_MEAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MARS_MASS_STR, ConstantValue.MARS_MASS_DESC, ConstantValue.MARS_MASS_ID, ConstantValue.MARS_MASS_SYN, ConstantValue.MARS_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.MARS_SEMI_MAJOR_AXIS_STR, ConstantValue.MARS_SEMI_MAJOR_AXIS_DESC, ConstantValue.MARS_SEMI_MAJOR_AXIS_ID, ConstantValue.MARS_SEMI_MAJOR_AXIS_SYN, ConstantValue.MARS_SEMI_MAJOR_AXIS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.JUPITER_RADIUS_MEAN_STR, ConstantValue.JUPITER_RADIUS_MEAN_DESC, ConstantValue.JUPITER_RADIUS_MEAN_ID, ConstantValue.JUPITER_RADIUS_MEAN_SYN, ConstantValue.JUPITER_RADIUS_MEAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.JUPITER_MASS_STR, ConstantValue.JUPITER_MASS_DESC, ConstantValue.JUPITER_MASS_ID, ConstantValue.JUPITER_MASS_SYN, ConstantValue.JUPITER_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.JUPITER_SEMI_MAJOR_AXIS_STR, ConstantValue.JUPITER_SEMI_MAJOR_AXIS_DESC, ConstantValue.JUPITER_SEMI_MAJOR_AXIS_ID, ConstantValue.JUPITER_SEMI_MAJOR_AXIS_SYN, ConstantValue.JUPITER_SEMI_MAJOR_AXIS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.SATURN_RADIUS_MEAN_STR, ConstantValue.SATURN_RADIUS_MEAN_DESC, ConstantValue.SATURN_RADIUS_MEAN_ID, ConstantValue.SATURN_RADIUS_MEAN_SYN, ConstantValue.SATURN_RADIUS_MEAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.SATURN_MASS_STR, ConstantValue.SATURN_MASS_DESC, ConstantValue.SATURN_MASS_ID, ConstantValue.SATURN_MASS_SYN, ConstantValue.SATURN_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.SATURN_SEMI_MAJOR_AXIS_STR, ConstantValue.SATURN_SEMI_MAJOR_AXIS_DESC, ConstantValue.SATURN_SEMI_MAJOR_AXIS_ID, ConstantValue.SATURN_SEMI_MAJOR_AXIS_SYN, ConstantValue.SATURN_SEMI_MAJOR_AXIS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.URANUS_RADIUS_MEAN_STR, ConstantValue.URANUS_RADIUS_MEAN_DESC, ConstantValue.URANUS_RADIUS_MEAN_ID, ConstantValue.URANUS_RADIUS_MEAN_SYN, ConstantValue.URANUS_RADIUS_MEAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.URANUS_MASS_STR, ConstantValue.URANUS_MASS_DESC, ConstantValue.URANUS_MASS_ID, ConstantValue.URANUS_MASS_SYN, ConstantValue.URANUS_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.URANUS_SEMI_MAJOR_AXIS_STR, ConstantValue.URANUS_SEMI_MAJOR_AXIS_DESC, ConstantValue.URANUS_SEMI_MAJOR_AXIS_ID, ConstantValue.URANUS_SEMI_MAJOR_AXIS_SYN, ConstantValue.URANUS_SEMI_MAJOR_AXIS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.NEPTUNE_RADIUS_MEAN_STR, ConstantValue.NEPTUNE_RADIUS_MEAN_DESC, ConstantValue.NEPTUNE_RADIUS_MEAN_ID, ConstantValue.NEPTUNE_RADIUS_MEAN_SYN, ConstantValue.NEPTUNE_RADIUS_MEAN_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.NEPTUNE_MASS_STR, ConstantValue.NEPTUNE_MASS_DESC, ConstantValue.NEPTUNE_MASS_ID, ConstantValue.NEPTUNE_MASS_SYN, ConstantValue.NEPTUNE_MASS_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.NEPTUNE_SEMI_MAJOR_AXIS_STR, ConstantValue.NEPTUNE_SEMI_MAJOR_AXIS_DESC, ConstantValue.NEPTUNE_SEMI_MAJOR_AXIS_ID, ConstantValue.NEPTUNE_SEMI_MAJOR_AXIS_SYN, ConstantValue.NEPTUNE_SEMI_MAJOR_AXIS_SINCE, ConstantValue.TYPE_ID)

			this.addKeyWord(ConstantValue.TRUE_STR, ConstantValue.TRUE_DESC, ConstantValue.TRUE_ID, ConstantValue.TRUE_SYN, ConstantValue.TRUE_SINCE, ConstantValue.TYPE_ID)
			this.addKeyWord(ConstantValue.FALSE_STR, ConstantValue.FALSE_DESC, ConstantValue.FALSE_ID, ConstantValue.FALSE_SYN, ConstantValue.FALSE_SINCE, ConstantValue.TYPE_ID)

			this.addKeyWord(ConstantValue.NAN_STR, ConstantValue.NAN_DESC, ConstantValue.NAN_ID, ConstantValue.NAN_SYN, ConstantValue.NAN_SINCE, ConstantValue.TYPE_ID)

			this.addKeyWord(RandomVariable.UNIFORM_STR, RandomVariable.UNIFORM_DESC, RandomVariable.UNIFORM_ID, RandomVariable.UNIFORM_SYN, RandomVariable.UNIFORM_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT_STR, RandomVariable.INT_DESC, RandomVariable.INT_ID, RandomVariable.INT_SYN, RandomVariable.INT_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT1_STR, RandomVariable.INT1_DESC, RandomVariable.INT1_ID, RandomVariable.INT1_SYN, RandomVariable.INT1_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT2_STR, RandomVariable.INT2_DESC, RandomVariable.INT2_ID, RandomVariable.INT2_SYN, RandomVariable.INT2_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT3_STR, RandomVariable.INT3_DESC, RandomVariable.INT3_ID, RandomVariable.INT3_SYN, RandomVariable.INT3_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT4_STR, RandomVariable.INT4_DESC, RandomVariable.INT4_ID, RandomVariable.INT4_SYN, RandomVariable.INT4_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT5_STR, RandomVariable.INT5_DESC, RandomVariable.INT5_ID, RandomVariable.INT5_SYN, RandomVariable.INT5_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT6_STR, RandomVariable.INT6_DESC, RandomVariable.INT6_ID, RandomVariable.INT6_SYN, RandomVariable.INT6_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT7_STR, RandomVariable.INT7_DESC, RandomVariable.INT7_ID, RandomVariable.INT7_SYN, RandomVariable.INT7_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT8_STR, RandomVariable.INT8_DESC, RandomVariable.INT8_ID, RandomVariable.INT8_SYN, RandomVariable.INT8_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.INT9_STR, RandomVariable.INT9_DESC, RandomVariable.INT9_ID, RandomVariable.INT9_SYN, RandomVariable.INT9_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_STR, RandomVariable.NAT0_DESC, RandomVariable.NAT0_ID, RandomVariable.NAT0_SYN, RandomVariable.NAT0_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_1_STR, RandomVariable.NAT0_1_DESC, RandomVariable.NAT0_1_ID, RandomVariable.NAT0_1_SYN, RandomVariable.NAT0_1_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_2_STR, RandomVariable.NAT0_2_DESC, RandomVariable.NAT0_2_ID, RandomVariable.NAT0_2_SYN, RandomVariable.NAT0_2_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_3_STR, RandomVariable.NAT0_3_DESC, RandomVariable.NAT0_3_ID, RandomVariable.NAT0_3_SYN, RandomVariable.NAT0_3_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_4_STR, RandomVariable.NAT0_4_DESC, RandomVariable.NAT0_4_ID, RandomVariable.NAT0_4_SYN, RandomVariable.NAT0_4_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_5_STR, RandomVariable.NAT0_5_DESC, RandomVariable.NAT0_5_ID, RandomVariable.NAT0_5_SYN, RandomVariable.NAT0_5_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_6_STR, RandomVariable.NAT0_6_DESC, RandomVariable.NAT0_6_ID, RandomVariable.NAT0_6_SYN, RandomVariable.NAT0_6_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_7_STR, RandomVariable.NAT0_7_DESC, RandomVariable.NAT0_7_ID, RandomVariable.NAT0_7_SYN, RandomVariable.NAT0_7_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_8_STR, RandomVariable.NAT0_8_DESC, RandomVariable.NAT0_8_ID, RandomVariable.NAT0_8_SYN, RandomVariable.NAT0_8_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT0_9_STR, RandomVariable.NAT0_9_DESC, RandomVariable.NAT0_9_ID, RandomVariable.NAT0_9_SYN, RandomVariable.NAT0_9_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_STR, RandomVariable.NAT1_DESC, RandomVariable.NAT1_ID, RandomVariable.NAT1_SYN, RandomVariable.NAT1_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_1_STR, RandomVariable.NAT1_1_DESC, RandomVariable.NAT1_1_ID, RandomVariable.NAT1_1_SYN, RandomVariable.NAT1_1_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_2_STR, RandomVariable.NAT1_2_DESC, RandomVariable.NAT1_2_ID, RandomVariable.NAT1_2_SYN, RandomVariable.NAT1_2_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_3_STR, RandomVariable.NAT1_3_DESC, RandomVariable.NAT1_3_ID, RandomVariable.NAT1_3_SYN, RandomVariable.NAT1_3_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_4_STR, RandomVariable.NAT1_4_DESC, RandomVariable.NAT1_4_ID, RandomVariable.NAT1_4_SYN, RandomVariable.NAT1_4_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_5_STR, RandomVariable.NAT1_5_DESC, RandomVariable.NAT1_5_ID, RandomVariable.NAT1_5_SYN, RandomVariable.NAT1_5_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_6_STR, RandomVariable.NAT1_6_DESC, RandomVariable.NAT1_6_ID, RandomVariable.NAT1_6_SYN, RandomVariable.NAT1_6_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_7_STR, RandomVariable.NAT1_7_DESC, RandomVariable.NAT1_7_ID, RandomVariable.NAT1_7_SYN, RandomVariable.NAT1_7_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_8_STR, RandomVariable.NAT1_8_DESC, RandomVariable.NAT1_8_ID, RandomVariable.NAT1_8_SYN, RandomVariable.NAT1_8_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NAT1_9_STR, RandomVariable.NAT1_9_DESC, RandomVariable.NAT1_9_ID, RandomVariable.NAT1_9_SYN, RandomVariable.NAT1_9_SINCE, RandomVariable.TYPE_ID)
			this.addKeyWord(RandomVariable.NOR_STR, RandomVariable.NOR_DESC, RandomVariable.NOR_ID, RandomVariable.NOR_SYN, RandomVariable.NOR_SINCE, RandomVariable.TYPE_ID)

			this.addKeyWord(BitwiseOperator.COMPL_STR, BitwiseOperator.COMPL_DESC, BitwiseOperator.COMPL_ID, BitwiseOperator.COMPL_SYN, BitwiseOperator.COMPL_SINCE, BitwiseOperator.TYPE_ID)
			this.addKeyWord(BitwiseOperator.AND_STR, BitwiseOperator.AND_DESC, BitwiseOperator.AND_ID, BitwiseOperator.AND_SYN, BitwiseOperator.AND_SINCE, BitwiseOperator.TYPE_ID)
			this.addKeyWord(BitwiseOperator.XOR_STR, BitwiseOperator.XOR_DESC, BitwiseOperator.XOR_ID, BitwiseOperator.XOR_SYN, BitwiseOperator.XOR_SINCE, BitwiseOperator.TYPE_ID)
			this.addKeyWord(BitwiseOperator.OR_STR, BitwiseOperator.OR_DESC, BitwiseOperator.OR_ID, BitwiseOperator.OR_SYN, BitwiseOperator.OR_SINCE, BitwiseOperator.TYPE_ID)
			this.addKeyWord(BitwiseOperator.LEFT_SHIFT_STR, BitwiseOperator.LEFT_SHIFT_DESC, BitwiseOperator.LEFT_SHIFT_ID, BitwiseOperator.LEFT_SHIFT_SYN, BitwiseOperator.LEFT_SHIFT_SINCE, BitwiseOperator.TYPE_ID)
			this.addKeyWord(BitwiseOperator.RIGHT_SHIFT_STR, BitwiseOperator.RIGHT_SHIFT_DESC, BitwiseOperator.RIGHT_SHIFT_ID, BitwiseOperator.RIGHT_SHIFT_SYN, BitwiseOperator.RIGHT_SHIFT_SINCE, BitwiseOperator.TYPE_ID)

			this.addKeyWord(Unit.PERC_STR, Unit.PERC_DESC, Unit.PERC_ID, Unit.PERC_SYN, Unit.PERC_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.PROMIL_STR, Unit.PROMIL_DESC, Unit.PROMIL_ID, Unit.PROMIL_SYN, Unit.PROMIL_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.YOTTA_STR, Unit.YOTTA_DESC, Unit.YOTTA_ID, Unit.YOTTA_SYN, Unit.YOTTA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.YOTTA_SEPT_STR, Unit.YOTTA_DESC, Unit.YOTTA_ID, Unit.YOTTA_SEPT_SYN, Unit.YOTTA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.ZETTA_STR, Unit.ZETTA_DESC, Unit.ZETTA_ID, Unit.ZETTA_SYN, Unit.ZETTA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.ZETTA_SEXT_STR, Unit.ZETTA_DESC, Unit.ZETTA_ID, Unit.ZETTA_SEXT_SYN, Unit.ZETTA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.EXA_STR, Unit.EXA_DESC, Unit.EXA_ID, Unit.EXA_SYN, Unit.EXA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.EXA_QUINT_STR, Unit.EXA_DESC, Unit.EXA_ID, Unit.EXA_QUINT_SYN, Unit.EXA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.PETA_STR, Unit.PETA_DESC, Unit.PETA_ID, Unit.PETA_SYN, Unit.PETA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.PETA_QUAD_STR, Unit.PETA_DESC, Unit.PETA_ID, Unit.PETA_QUAD_SYN, Unit.PETA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.TERA_STR, Unit.TERA_DESC, Unit.TERA_ID, Unit.TERA_SYN, Unit.TERA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.TERA_TRIL_STR, Unit.TERA_DESC, Unit.TERA_ID, Unit.TERA_TRIL_SYN, Unit.TERA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.GIGA_STR, Unit.GIGA_DESC, Unit.GIGA_ID, Unit.GIGA_SYN, Unit.GIGA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.GIGA_BIL_STR, Unit.GIGA_DESC, Unit.GIGA_ID, Unit.GIGA_BIL_SYN, Unit.GIGA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MEGA_STR, Unit.MEGA_DESC, Unit.MEGA_ID, Unit.MEGA_SYN, Unit.MEGA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MEGA_MIL_STR, Unit.MEGA_DESC, Unit.MEGA_ID, Unit.MEGA_MIL_SYN, Unit.MEGA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILO_STR, Unit.KILO_DESC, Unit.KILO_ID, Unit.KILO_SYN, Unit.KILO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILO_TH_STR, Unit.KILO_DESC, Unit.KILO_ID, Unit.KILO_TH_SYN, Unit.KILO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.HECTO_STR, Unit.HECTO_DESC, Unit.HECTO_ID, Unit.HECTO_SYN, Unit.HECTO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.HECTO_HUND_STR, Unit.HECTO_DESC, Unit.HECTO_ID, Unit.HECTO_HUND_SYN, Unit.HECTO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.DECA_STR, Unit.DECA_DESC, Unit.DECA_ID, Unit.DECA_SYN, Unit.DECA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.DECA_TEN_STR, Unit.DECA_DESC, Unit.DECA_ID, Unit.DECA_TEN_SYN, Unit.DECA_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.DECI_STR, Unit.DECI_DESC, Unit.DECI_ID, Unit.DECI_SYN, Unit.DECI_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.CENTI_STR, Unit.CENTI_DESC, Unit.CENTI_ID, Unit.CENTI_SYN, Unit.CENTI_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MILLI_STR, Unit.MILLI_DESC, Unit.MILLI_ID, Unit.MILLI_SYN, Unit.MILLI_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MICRO_STR, Unit.MICRO_DESC, Unit.MICRO_ID, Unit.MICRO_SYN, Unit.MICRO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.NANO_STR, Unit.NANO_DESC, Unit.NANO_ID, Unit.NANO_SYN, Unit.NANO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.PICO_STR, Unit.PICO_DESC, Unit.PICO_ID, Unit.PICO_SYN, Unit.PICO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.FEMTO_STR, Unit.FEMTO_DESC, Unit.FEMTO_ID, Unit.FEMTO_SYN, Unit.FEMTO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.ATTO_STR, Unit.ATTO_DESC, Unit.ATTO_ID, Unit.ATTO_SYN, Unit.ATTO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.ZEPTO_STR, Unit.ZEPTO_DESC, Unit.ZEPTO_ID, Unit.ZEPTO_SYN, Unit.ZEPTO_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.YOCTO_STR, Unit.YOCTO_DESC, Unit.YOCTO_ID, Unit.YOCTO_SYN, Unit.YOCTO_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.METRE_STR, Unit.METRE_DESC, Unit.METRE_ID, Unit.METRE_SYN, Unit.METRE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILOMETRE_STR, Unit.KILOMETRE_DESC, Unit.KILOMETRE_ID, Unit.KILOMETRE_SYN, Unit.KILOMETRE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.CENTIMETRE_STR, Unit.CENTIMETRE_DESC, Unit.CENTIMETRE_ID, Unit.CENTIMETRE_SYN, Unit.CENTIMETRE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MILLIMETRE_STR, Unit.MILLIMETRE_DESC, Unit.MILLIMETRE_ID, Unit.MILLIMETRE_SYN, Unit.MILLIMETRE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.INCH_STR, Unit.INCH_DESC, Unit.INCH_ID, Unit.INCH_SYN, Unit.INCH_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.YARD_STR, Unit.YARD_DESC, Unit.YARD_ID, Unit.YARD_SYN, Unit.YARD_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.FEET_STR, Unit.FEET_DESC, Unit.FEET_ID, Unit.FEET_SYN, Unit.FEET_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MILE_STR, Unit.MILE_DESC, Unit.MILE_ID, Unit.MILE_SYN, Unit.MILE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.NAUTICAL_MILE_STR, Unit.NAUTICAL_MILE_DESC, Unit.NAUTICAL_MILE_ID, Unit.NAUTICAL_MILE_SYN, Unit.NAUTICAL_MILE_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.METRE2_STR, Unit.METRE2_DESC, Unit.METRE2_ID, Unit.METRE2_SYN, Unit.METRE2_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.CENTIMETRE2_STR, Unit.CENTIMETRE2_DESC, Unit.CENTIMETRE2_ID, Unit.CENTIMETRE2_SYN, Unit.CENTIMETRE2_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MILLIMETRE2_STR, Unit.MILLIMETRE2_DESC, Unit.MILLIMETRE2_ID, Unit.MILLIMETRE2_SYN, Unit.MILLIMETRE2_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.ARE_STR, Unit.ARE_DESC, Unit.ARE_ID, Unit.ARE_SYN, Unit.ARE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.HECTARE_STR, Unit.HECTARE_DESC, Unit.HECTARE_ID, Unit.HECTARE_SYN, Unit.HECTARE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.ACRE_STR, Unit.ACRE_DESC, Unit.ACRE_ID, Unit.ACRE_SYN, Unit.ACRE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILOMETRE2_STR, Unit.KILOMETRE2_DESC, Unit.KILOMETRE2_ID, Unit.KILOMETRE2_SYN, Unit.KILOMETRE2_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.MILLIMETRE3_STR, Unit.MILLIMETRE3_DESC, Unit.MILLIMETRE3_ID, Unit.MILLIMETRE3_SYN, Unit.MILLIMETRE3_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.CENTIMETRE3_STR, Unit.CENTIMETRE3_DESC, Unit.CENTIMETRE3_ID, Unit.CENTIMETRE3_SYN, Unit.CENTIMETRE3_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.METRE3_STR, Unit.METRE3_DESC, Unit.METRE3_ID, Unit.METRE3_SYN, Unit.METRE3_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILOMETRE3_STR, Unit.KILOMETRE3_DESC, Unit.KILOMETRE3_ID, Unit.KILOMETRE3_SYN, Unit.KILOMETRE3_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MILLILITRE_STR, Unit.MILLILITRE_DESC, Unit.MILLILITRE_ID, Unit.MILLILITRE_SYN, Unit.MILLILITRE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.LITRE_STR, Unit.LITRE_DESC, Unit.LITRE_ID, Unit.LITRE_SYN, Unit.LITRE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.GALLON_STR, Unit.GALLON_DESC, Unit.GALLON_ID, Unit.GALLON_SYN, Unit.GALLON_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.PINT_STR, Unit.PINT_DESC, Unit.PINT_ID, Unit.PINT_SYN, Unit.PINT_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.SECOND_STR, Unit.SECOND_DESC, Unit.SECOND_ID, Unit.SECOND_SYN, Unit.SECOND_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MILLISECOND_STR, Unit.MILLISECOND_DESC, Unit.MILLISECOND_ID, Unit.MILLISECOND_SYN, Unit.MILLISECOND_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MINUTE_STR, Unit.MINUTE_DESC, Unit.MINUTE_ID, Unit.MINUTE_SYN, Unit.MINUTE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.HOUR_STR, Unit.HOUR_DESC, Unit.HOUR_ID, Unit.HOUR_SYN, Unit.HOUR_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.DAY_STR, Unit.DAY_DESC, Unit.DAY_ID, Unit.DAY_SYN, Unit.DAY_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.WEEK_STR, Unit.WEEK_DESC, Unit.WEEK_ID, Unit.WEEK_SYN, Unit.WEEK_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.JULIAN_YEAR_STR, Unit.JULIAN_YEAR_DESC, Unit.JULIAN_YEAR_ID, Unit.JULIAN_YEAR_SYN, Unit.JULIAN_YEAR_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.KILOGRAM_STR, Unit.KILOGRAM_DESC, Unit.KILOGRAM_ID, Unit.KILOGRAM_SYN, Unit.KILOGRAM_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.GRAM_STR, Unit.GRAM_DESC, Unit.GRAM_ID, Unit.GRAM_SYN, Unit.GRAM_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MILLIGRAM_STR, Unit.MILLIGRAM_DESC, Unit.MILLIGRAM_ID, Unit.MILLIGRAM_SYN, Unit.MILLIGRAM_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.DECAGRAM_STR, Unit.DECAGRAM_DESC, Unit.DECAGRAM_ID, Unit.DECAGRAM_SYN, Unit.DECAGRAM_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.TONNE_STR, Unit.TONNE_DESC, Unit.TONNE_ID, Unit.TONNE_SYN, Unit.TONNE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.OUNCE_STR, Unit.OUNCE_DESC, Unit.OUNCE_ID, Unit.OUNCE_SYN, Unit.OUNCE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.POUND_STR, Unit.POUND_DESC, Unit.POUND_ID, Unit.POUND_SYN, Unit.POUND_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.BIT_STR, Unit.BIT_DESC, Unit.BIT_ID, Unit.BIT_SYN, Unit.BIT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILOBIT_STR, Unit.KILOBIT_DESC, Unit.KILOBIT_ID, Unit.KILOBIT_SYN, Unit.KILOBIT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MEGABIT_STR, Unit.MEGABIT_DESC, Unit.MEGABIT_ID, Unit.MEGABIT_SYN, Unit.MEGABIT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.GIGABIT_STR, Unit.GIGABIT_DESC, Unit.GIGABIT_ID, Unit.GIGABIT_SYN, Unit.GIGABIT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.TERABIT_STR, Unit.TERABIT_DESC, Unit.TERABIT_ID, Unit.TERABIT_SYN, Unit.TERABIT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.PETABIT_STR, Unit.PETABIT_DESC, Unit.PETABIT_ID, Unit.PETABIT_SYN, Unit.PETABIT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.EXABIT_STR, Unit.EXABIT_DESC, Unit.EXABIT_ID, Unit.EXABIT_SYN, Unit.EXABIT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.ZETTABIT_STR, Unit.ZETTABIT_DESC, Unit.ZETTABIT_ID, Unit.ZETTABIT_SYN, Unit.ZETTABIT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.YOTTABIT_STR, Unit.YOTTABIT_DESC, Unit.YOTTABIT_ID, Unit.YOTTABIT_SYN, Unit.YOTTABIT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.BYTE_STR, Unit.BYTE_DESC, Unit.BYTE_ID, Unit.BYTE_SYN, Unit.BYTE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILOBYTE_STR, Unit.KILOBYTE_DESC, Unit.KILOBYTE_ID, Unit.KILOBYTE_SYN, Unit.KILOBYTE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MEGABYTE_STR, Unit.MEGABYTE_DESC, Unit.MEGABYTE_ID, Unit.MEGABYTE_SYN, Unit.MEGABYTE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.GIGABYTE_STR, Unit.GIGABYTE_DESC, Unit.GIGABYTE_ID, Unit.GIGABYTE_SYN, Unit.GIGABYTE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.TERABYTE_STR, Unit.TERABYTE_DESC, Unit.TERABYTE_ID, Unit.TERABYTE_SYN, Unit.TERABYTE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.PETABYTE_STR, Unit.PETABYTE_DESC, Unit.PETABYTE_ID, Unit.PETABYTE_SYN, Unit.PETABYTE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.EXABYTE_STR, Unit.EXABYTE_DESC, Unit.EXABYTE_ID, Unit.EXABYTE_SYN, Unit.EXABYTE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.ZETTABYTE_STR, Unit.ZETTABYTE_DESC, Unit.ZETTABYTE_ID, Unit.ZETTABYTE_SYN, Unit.ZETTABYTE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.YOTTABYTE_STR, Unit.YOTTABYTE_DESC, Unit.YOTTABYTE_ID, Unit.YOTTABYTE_SYN, Unit.YOTTABYTE_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.JOULE_STR, Unit.JOULE_DESC, Unit.JOULE_ID, Unit.JOULE_SYN, Unit.JOULE_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.ELECTRONO_VOLT_STR, Unit.ELECTRONO_VOLT_DESC, Unit.ELECTRONO_VOLT_ID, Unit.ELECTRONO_VOLT_SYN, Unit.ELECTRONO_VOLT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILO_ELECTRONO_VOLT_STR, Unit.KILO_ELECTRONO_VOLT_DESC, Unit.KILO_ELECTRONO_VOLT_ID, Unit.KILO_ELECTRONO_VOLT_SYN, Unit.KILO_ELECTRONO_VOLT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MEGA_ELECTRONO_VOLT_STR, Unit.MEGA_ELECTRONO_VOLT_DESC, Unit.MEGA_ELECTRONO_VOLT_ID, Unit.MEGA_ELECTRONO_VOLT_SYN, Unit.MEGA_ELECTRONO_VOLT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.GIGA_ELECTRONO_VOLT_STR, Unit.GIGA_ELECTRONO_VOLT_DESC, Unit.GIGA_ELECTRONO_VOLT_ID, Unit.GIGA_ELECTRONO_VOLT_SYN, Unit.GIGA_ELECTRONO_VOLT_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.TERA_ELECTRONO_VOLT_STR, Unit.TERA_ELECTRONO_VOLT_DESC, Unit.TERA_ELECTRONO_VOLT_ID, Unit.TERA_ELECTRONO_VOLT_SYN, Unit.TERA_ELECTRONO_VOLT_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.METRE_PER_SECOND_STR, Unit.METRE_PER_SECOND_DESC, Unit.METRE_PER_SECOND_ID, Unit.METRE_PER_SECOND_SYN, Unit.METRE_PER_SECOND_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILOMETRE_PER_HOUR_STR, Unit.KILOMETRE_PER_HOUR_DESC, Unit.KILOMETRE_PER_HOUR_ID, Unit.KILOMETRE_PER_HOUR_SYN, Unit.KILOMETRE_PER_HOUR_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MILE_PER_HOUR_STR, Unit.MILE_PER_HOUR_DESC, Unit.MILE_PER_HOUR_ID, Unit.MILE_PER_HOUR_SYN, Unit.MILE_PER_HOUR_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KNOT_STR, Unit.KNOT_DESC, Unit.KNOT_ID, Unit.KNOT_SYN, Unit.KNOT_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.METRE_PER_SECOND2_STR, Unit.METRE_PER_SECOND2_DESC, Unit.METRE_PER_SECOND2_ID, Unit.METRE_PER_SECOND2_SYN, Unit.METRE_PER_SECOND2_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.KILOMETRE_PER_HOUR2_STR, Unit.KILOMETRE_PER_HOUR2_DESC, Unit.KILOMETRE_PER_HOUR2_ID, Unit.KILOMETRE_PER_HOUR2_SYN, Unit.KILOMETRE_PER_HOUR2_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MILE_PER_HOUR2_STR, Unit.MILE_PER_HOUR2_DESC, Unit.MILE_PER_HOUR2_ID, Unit.MILE_PER_HOUR2_SYN, Unit.MILE_PER_HOUR2_SINCE, Unit.TYPE_ID)

			this.addKeyWord(Unit.RADIAN_ARC_STR, Unit.RADIAN_ARC_DESC, Unit.RADIAN_ARC_ID, Unit.RADIAN_ARC_SYN, Unit.RADIAN_ARC_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.DEGREE_ARC_STR, Unit.DEGREE_ARC_DESC, Unit.DEGREE_ARC_ID, Unit.DEGREE_ARC_SYN, Unit.DEGREE_ARC_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.MINUTE_ARC_STR, Unit.MINUTE_ARC_DESC, Unit.MINUTE_ARC_ID, Unit.MINUTE_ARC_SYN, Unit.MINUTE_ARC_SINCE, Unit.TYPE_ID)
			this.addKeyWord(Unit.SECOND_ARC_STR, Unit.SECOND_ARC_DESC, Unit.SECOND_ARC_ID, Unit.SECOND_ARC_SYN, Unit.SECOND_ARC_SINCE, Unit.TYPE_ID)
		}

		this.addKeyWord(ParserSymbol.LEFT_PARENTHESES_STR, ParserSymbol.LEFT_PARENTHESES_DESC, ParserSymbol.LEFT_PARENTHESES_ID, ParserSymbol.LEFT_PARENTHESES_SYN, ParserSymbol.LEFT_PARENTHESES_SINCE, ParserSymbol.TYPE_ID)
		this.addKeyWord(ParserSymbol.RIGHT_PARENTHESES_STR, ParserSymbol.RIGHT_PARENTHESES_DESC, ParserSymbol.RIGHT_PARENTHESES_ID, ParserSymbol.RIGHT_PARENTHESES_SYN, ParserSymbol.RIGHT_PARENTHESES_SINCE, ParserSymbol.TYPE_ID)
		this.addKeyWord(ParserSymbol.COMMA_STR, ParserSymbol.COMMA_DESC, ParserSymbol.COMMA_ID, ParserSymbol.COMMA_SYN, ParserSymbol.COMMA_SINCE, ParserSymbol.TYPE_ID)
		this.addKeyWord(ParserSymbol.SEMI_STR, ParserSymbol.SEMI_DESC, ParserSymbol.COMMA_ID, ParserSymbol.SEMI_SYN, ParserSymbol.COMMA_SINCE, ParserSymbol.TYPE_ID)
		this.addKeyWord(ParserSymbol.DECIMAL_REG_EXP, ParserSymbol.NUMBER_REG_DESC, ParserSymbol.NUMBER_ID, ParserSymbol.NUMBER_SYN, ParserSymbol.NUMBER_SINCE, ParserSymbol.NUMBER_TYPE_ID)
	}

	/**
	 * Adds arguments key words to the keywords list.
	 *
	 * @author Bas Milius
	 */
	private fun addArgumentsKeyWords()
	{
		val argumentsNumber = argumentsList.size

		for (argumentIndex in 0 until argumentsNumber)
		{
			val arg = argumentsList[argumentIndex]

			if (arg.argumentType != Argument.RECURSIVE_ARGUMENT)
				this.addKeyWord(arg.argumentName, arg.description, argumentIndex, arg.argumentName, "", Argument.TYPE_ID)
			else
				this.addKeyWord(arg.argumentName, arg.description, argumentIndex, arg.argumentName + "(n)", "", RecursiveArgument.TYPE_ID_RECURSIVE)
		}
	}

	/**
	 * Adds functions key words to the keywords list.
	 *
	 * @author Bas Milius
	 */
	private fun addFunctionsKeyWords()
	{
		val functionsNumber = functionsList.size

		for (functionIndex in 0 until functionsNumber)
		{
			val `fun` = functionsList[functionIndex]
			var syntax = `fun`.functionName + "("
			val paramsNum = `fun`.parametersNumber

			for (i in 0 until paramsNum)
			{
				syntax += `fun`.getParameterName(i)

				if (paramsNum > 1 && i < paramsNum - 1)
					syntax += ","
			}

			syntax += ")"

			this.addKeyWord(`fun`.functionName, `fun`.description, functionIndex, syntax, "", Function.TYPE_ID)
		}
	}

	/**
	 * Adds constants key words to the keywords list.
	 *
	 * @author Bas Milius
	 */
	private fun addConstantsKeyWords()
	{
		val constantsNumber = constantsList.size

		for (constantIndex in 0 until constantsNumber)
		{
			val c = constantsList[constantIndex]
			this.addKeyWord(c.getConstantName(), c.description, constantIndex, c.getConstantName(), "", Constant.TYPE_ID)
		}
	}

	/**
	 * Final validation of key words.
	 *
	 * @author Bas Milius
	 */
	private fun validateParserKeyWords()
	{
		if (MathParser.overrideBuiltinTokens)
		{
			val userDefinedTokens = argumentsList.mapTo(ArrayList()) { it.argumentName }
			this.functionsList.mapTo(userDefinedTokens) { it.functionName }
			this.constantsList.mapTo(userDefinedTokens) { it.getConstantName() }

			if (userDefinedTokens.isEmpty()) return

			val keyWordsToOverride = keyWordsList.filter { userDefinedTokens.contains(it.wordString) }

			if (keyWordsToOverride.isEmpty()) return

			for (kw in keyWordsToOverride)
				keyWordsList.remove(kw)
		}
	}

	/**
	 * Adds key word to the keyWords list
	 *
	 * @param wordString String
	 * @param wordDescription String
	 * @param wordId Int
	 * @param wordSyntax String
	 * @param wordSince String
	 * @param wordTypeId Int
	 *
	 * @author Bas Milius
	 */
	private fun addKeyWord(wordString: String, wordDescription: String, wordId: Int, wordSyntax: String, wordSince: String, wordTypeId: Int)
	{
		var wordStringImp = wordString
		var wordDescriptionImp = wordDescription
		var wordSyntaxImp = wordSyntax

		if (MathParser.tokensToRemove.size > 0 || MathParser.tokensToModify.size > 0 && (wordTypeId == Function1Arg.TYPE_ID || wordTypeId == Function2Arg.TYPE_ID || wordTypeId == Function3Arg.TYPE_ID || wordTypeId == FunctionVariadic.TYPE_ID || wordTypeId == CalculusOperator.TYPE_ID || wordTypeId == ConstantValue.TYPE_ID || wordTypeId == RandomVariable.TYPE_ID || wordTypeId == Unit.TYPE_ID))
		{
			if (MathParser.tokensToRemove.size > 0 && MathParser.tokensToRemove.contains(wordStringImp))
				return

			if (MathParser.tokensToModify.size > 0)
			{
				for ((currentToken, newToken, newTokenDescription) in MathParser.tokensToModify)
					if (currentToken == wordStringImp)
					{
						wordStringImp = newToken
						wordDescriptionImp = newTokenDescription
						wordSyntaxImp = wordSyntaxImp.replace(currentToken, newToken)
					}
			}
		}

		this.keyWordsList.add(KeyWord(wordStringImp, wordDescriptionImp, wordId, wordSyntaxImp, wordSince, wordTypeId))
	}

	/**
	 * Checks whether unknown token represents number literal provided in different numeral base system, where base is between 1 and 36.
	 *
	 * @param token Token
	 *
	 * @author Bas Milius
	 */
	private fun checkOtherNumberBases(token: Token)
	{
		var dotPos = 0
		val tokenStrLength = token.tokenStr.length

		if (tokenStrLength >= 2 && token.tokenStr[1] == '.')
			dotPos = 1

		if (dotPos == 0 && tokenStrLength >= 3 && token.tokenStr[2] == '.')
			dotPos = 2

		if (dotPos == 0 && tokenStrLength >= 4 && token.tokenStr[3] == '.')
			dotPos = 3

		if (dotPos == 0)
			return

		val baseInd = token.tokenStr.substring(0, dotPos).toLowerCase()
		val numberLiteral = if (tokenStrLength > dotPos + 1) token.tokenStr.substring(dotPos + 1) else ""
		val numeralSystemBase = when (baseInd)
		{
			"b" -> 2
			"o" -> 8
			"h" -> 16
			"b1" -> 1
			"b2" -> 2
			"b3" -> 3
			"b4" -> 4
			"b5" -> 5
			"b6" -> 6
			"b7" -> 7
			"b8" -> 8
			"b9" -> 9
			"b10" -> 10
			"b11" -> 11
			"b12" -> 12
			"b13" -> 13
			"b14" -> 14
			"b15" -> 15
			"b16" -> 16
			"b17" -> 17
			"b18" -> 18
			"b19" -> 19
			"b20" -> 20
			"b21" -> 21
			"b22" -> 22
			"b23" -> 23
			"b24" -> 24
			"b25" -> 25
			"b26" -> 26
			"b27" -> 27
			"b28" -> 28
			"b29" -> 29
			"b30" -> 30
			"b31" -> 31
			"b32" -> 32
			"b33" -> 33
			"b34" -> 34
			"b35" -> 35
			"b36" -> 36
			else -> 0
		}

		if (numeralSystemBase in 1..36)
		{
			token.tokenTypeId = ParserSymbol.NUMBER_TYPE_ID
			token.tokenId = ParserSymbol.NUMBER_ID
			token.tokenValue = NumberTheory.convOthBase2Decimal(numberLiteral, numeralSystemBase)
		}
	}

	/**
	 * Adds expression token. Method is called by the tokenExpressionString() while parsing string expression
	 *
	 * @param tokenStr String
	 * @param keyWord KeyWord
	 *
	 * @author Bas Milius
	 */
	private fun addToken(tokenStr: String, keyWord: KeyWord)
	{
		val token = Token()
		token.tokenStr = tokenStr
		token.keyWord = keyWord.wordString
		token.tokenTypeId = keyWord.wordTypeId
		token.tokenId = keyWord.wordId
		this.initialTokens.add(token)

		when
		{
			token.tokenTypeId == Argument.TYPE_ID -> token.tokenValue = argumentsList[token.tokenId].argumentValue
			token.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID ->
			{
				token.tokenValue = token.tokenStr.toDouble()
				token.keyWord = ParserSymbol.NUMBER_STR
			}
			token.tokenTypeId == Token.NOT_MATCHED -> checkOtherNumberBases(token)
		}
	}

	/**
	 * Tokenizing expression string.
	 *
	 * @author Bas Milius
	 */
	private fun tokenizeExpressionString()
	{
		this.keyWordsList = ArrayList()
		this.addParserKeyWords()
		this.validateParserKeyWords()

		if (!this.parserKeyWordsOnly)
		{
			this.addArgumentsKeyWords()
			this.addFunctionsKeyWords()
			this.addConstantsKeyWords()
		}

		Collections.sort(this.keyWordsList, DescKwLenComparator())

		var numberKwId = ConstantValue.NaN
		var plusKwId = ConstantValue.NaN
		var minusKwId = ConstantValue.NaN

		for (kwId in this.keyWordsList.indices)
		{
			if (this.keyWordsList[kwId].wordTypeId == ParserSymbol.NUMBER_TYPE_ID)
				numberKwId = kwId

			if (this.keyWordsList[kwId].wordTypeId == Operator.TYPE_ID)
			{
				if (this.keyWordsList[kwId].wordId == Operator.PLUS_ID)
					plusKwId = kwId

				if (this.keyWordsList[kwId].wordId == Operator.MINUS_ID)
					minusKwId = kwId
			}
		}

		var newExpressionString = ""
		var c: Char

		if (this.expressionString.isNotEmpty())
		{
			for (i in 0 until this.expressionString.length)
			{
				c = this.expressionString[i]

				if (c != ' ' && c != '\n' && c != '\r' && c != '\t' && c != '\u000C')
					newExpressionString += c
			}
		}

		this.initialTokens = ArrayList()

		var lastPos = 0
		var pos = 0
		var tokenStr: String
		var matchStatusPrev = NOT_FOUND
		var matchStatus: Int
		var kw: KeyWord?
		var sub: String
		var kwStr: String
		var precedingChar: Char
		var followingChar: Char
		var firstChar: Char

		do
		{
			var numEnd = -1

			firstChar = newExpressionString[pos]

			if (firstChar == '+' || firstChar == '-' || firstChar == '0' || firstChar == '1' || firstChar == '2' || firstChar == '3' || firstChar == '4' || firstChar == '5' || firstChar == '6' || firstChar == '7' || firstChar == '8' || firstChar == '9')
			{
				for (i in pos until newExpressionString.length)
				{
					if (i > pos)
					{
						c = newExpressionString[i]

						if (c != '+' && c != '-' && c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9' && c != '.' && c != 'e' && c != 'E')
							break
					}

					val str = newExpressionString.substring(pos, i + 1)

					if (MathParser.regexMatch(str, ParserSymbol.DECIMAL_REG_EXP))
						numEnd = i
				}
			}

			if (numEnd >= 0 && pos > 0)
			{
				precedingChar = newExpressionString[pos - 1]

				if (precedingChar != ',' && precedingChar != ';' && precedingChar != '|' && precedingChar != '&' && precedingChar != '+' && precedingChar != '-' && precedingChar != '*' && precedingChar != '\\' && precedingChar != '/' && precedingChar != '(' && precedingChar != ')' && precedingChar != '=' && precedingChar != '>' && precedingChar != '<' && precedingChar != '~' && precedingChar != '^' && precedingChar != '#' && precedingChar != '%' && precedingChar != '@' && precedingChar != '!')
					numEnd = -1
			}

			if (numEnd >= 0 && numEnd < newExpressionString.length - 1)
			{
				followingChar = newExpressionString[numEnd + 1]

				if (followingChar != ',' && followingChar != ';' && followingChar != '|' && followingChar != '&' && followingChar != '+' && followingChar != '-' && followingChar != '*' && followingChar != '\\' && followingChar != '/' && followingChar != '(' && followingChar != ')' && followingChar != '=' && followingChar != '>' && followingChar != '<' && followingChar != '~' && followingChar != '^' && followingChar != '#' && followingChar != '%' && followingChar != '@' && followingChar != '!')
					numEnd = -1
			}

			if (numEnd >= 0)
			{
				if (matchStatusPrev == NOT_FOUND && pos > 0)
				{
					tokenStr = newExpressionString.substring(lastPos, pos)
					this.addToken(tokenStr, KeyWord())
				}

				firstChar = newExpressionString[pos]
				var leadingOp: Boolean

				if (firstChar == '-' || firstChar == '+')
				{
					leadingOp = if (initialTokens.size > 0)
					{
						val lastToken = initialTokens[initialTokens.size - 1]

						!(lastToken.tokenTypeId == Operator.TYPE_ID && lastToken.tokenId != Operator.FACT_ID && lastToken.tokenId != Operator.PERC_ID || lastToken.tokenTypeId == BinaryRelation.TYPE_ID || lastToken.tokenTypeId == BooleanOperator.TYPE_ID || lastToken.tokenTypeId == BitwiseOperator.TYPE_ID || lastToken.tokenTypeId == ParserSymbol.TYPE_ID && lastToken.tokenId == ParserSymbol.LEFT_PARENTHESES_ID)
					}
					else
					{
						false
					}
				}
				else
				{
					leadingOp = false
				}

				if (leadingOp)
				{
					if (firstChar == '-')
						this.addToken("-", this.keyWordsList[minusKwId])

					if (firstChar == '+')
						this.addToken("+", this.keyWordsList[plusKwId])

					pos++
				}

				tokenStr = newExpressionString.substring(pos, numEnd + 1)
				this.addToken(tokenStr, this.keyWordsList[numberKwId])

				pos = numEnd + 1
				lastPos = pos

				matchStatus = FOUND
				matchStatusPrev = FOUND
			}
			else
			{
				var kwId = -1
				matchStatus = NOT_FOUND

				do
				{
					kwId++
					kw = this.keyWordsList[kwId]
					kwStr = kw.wordString

					if (pos + kwStr.length <= newExpressionString.length)
					{
						sub = newExpressionString.substring(pos, pos + kwStr.length)

						if (sub == kwStr)
							matchStatus = FOUND

						if (matchStatus == FOUND && (kw.wordTypeId == Argument.TYPE_ID || kw.wordTypeId == RecursiveArgument.TYPE_ID_RECURSIVE || kw.wordTypeId == Function1Arg.TYPE_ID || kw.wordTypeId == Function2Arg.TYPE_ID || kw.wordTypeId == Function3Arg.TYPE_ID || kw.wordTypeId == FunctionVariadic.TYPE_ID || kw.wordTypeId == ConstantValue.TYPE_ID || kw.wordTypeId == Constant.TYPE_ID || kw.wordTypeId == RandomVariable.TYPE_ID || kw.wordTypeId == Unit.TYPE_ID || kw.wordTypeId == Function.TYPE_ID || kw.wordTypeId == CalculusOperator.TYPE_ID))
						{
							if (pos > 0)
							{
								precedingChar = newExpressionString[pos - 1]

								if (precedingChar != ',' && precedingChar != ';' && precedingChar != '|' && precedingChar != '&' && precedingChar != '+' && precedingChar != '-' && precedingChar != '*' && precedingChar != '\\' && precedingChar != '/' && precedingChar != '(' && precedingChar != ')' && precedingChar != '=' && precedingChar != '>' && precedingChar != '<' && precedingChar != '~' && precedingChar != '^' && precedingChar != '#' && precedingChar != '%' && precedingChar != '@' && precedingChar != '!')
									matchStatus = NOT_FOUND
							}

							if (matchStatus == FOUND && pos + kwStr.length < newExpressionString.length)
							{
								followingChar = newExpressionString[pos + kwStr.length]

								if (followingChar != ',' && followingChar != ';' && followingChar != '|' && followingChar != '&' && followingChar != '+' && followingChar != '-' && followingChar != '*' && followingChar != '\\' && followingChar != '/' && followingChar != '(' && followingChar != ')' && followingChar != '=' && followingChar != '>' && followingChar != '<' && followingChar != '~' && followingChar != '^' && followingChar != '#' && followingChar != '%' && followingChar != '@' && followingChar != '!')
									matchStatus = NOT_FOUND
							}
						}
					}
				}
				while (kwId < this.keyWordsList.size - 1 && matchStatus == NOT_FOUND)

				if (matchStatus == FOUND)
				{
					if (matchStatusPrev == NOT_FOUND && pos > 0)
					{
						tokenStr = newExpressionString.substring(lastPos, pos)
						this.addToken(tokenStr, KeyWord())
					}

					matchStatusPrev = FOUND
					tokenStr = newExpressionString.substring(pos, pos + kwStr.length)

					this.addToken(tokenStr, kw!!)

					lastPos = pos + kwStr.length
					pos += kwStr.length
				}
				else
				{
					matchStatusPrev = NOT_FOUND

					if (pos < newExpressionString.length)
						pos++
				}
			}
		}
		while (pos < newExpressionString.length)

		if (matchStatus == NOT_FOUND)
		{
			tokenStr = newExpressionString.substring(lastPos, pos)
			this.addToken(tokenStr, KeyWord())
		}

		this.evaluateTokensLevels()
	}

	/**
	 * Evaluates tokens levels.
	 *
	 * @author Bas Milius
	 */
	private fun evaluateTokensLevels()
	{
		var tokenLevel = 0
		val tokenStack = Stack<TokenStackElement>()
		var precedingFunction = false

		if (this.initialTokens.size > 0)
		{
			for (tokenIndex in this.initialTokens.indices)
			{
				val token = this.initialTokens[tokenIndex]

				precedingFunction = if (token.tokenTypeId == Function1Arg.TYPE_ID || token.tokenTypeId == Function2Arg.TYPE_ID || token.tokenTypeId == Function3Arg.TYPE_ID || token.tokenTypeId == Function.TYPE_ID || token.tokenTypeId == CalculusOperator.TYPE_ID || token.tokenTypeId == RecursiveArgument.TYPE_ID_RECURSIVE || token.tokenTypeId == FunctionVariadic.TYPE_ID)
				{
					tokenLevel++
					true
				}
				else if (token.tokenTypeId == ParserSymbol.TYPE_ID && token.tokenId == ParserSymbol.LEFT_PARENTHESES_ID)
				{
					tokenLevel++
					tokenStack.push(TokenStackElement(tokenIndex, token.tokenId, token.tokenTypeId, token.tokenLevel, precedingFunction))
					false
				}
				else
				{
					false
				}

				token.tokenLevel = tokenLevel

				if (token.tokenTypeId == ParserSymbol.TYPE_ID && token.tokenId == ParserSymbol.RIGHT_PARENTHESES_ID)
				{
					tokenLevel--

					if (!tokenStack.isEmpty())
					{
						val (_, _, _, _, precedingFunction1) = tokenStack.pop()

						if (precedingFunction1)
							tokenLevel--
					}
				}
			}
		}
	}

	/**
	 * copy initial tokens lito to tokens list.
	 *
	 * @author Bas Milius
	 */
	private fun copyInitialTokens()
	{
		this.tokensList = ArrayList()

		for (token in this.initialTokens)
		{
			this.tokensList.add(token.clone())
		}
	}

	/**
	 * Tokenizes expression string and returns tokens list, including: string, type, level.
	 *
	 * @return List<Token>
	 *
	 * @author Bas Milius
	 */
	fun getCopyOfInitialTokens(): List<Token>
	{
		this.tokenizeExpressionString()
		val tokensListCopy = ArrayList<Token>()
		var token: Token

		for (i in this.initialTokens.indices)
		{
			token = this.initialTokens[i]

			if (token.tokenTypeId == Token.NOT_MATCHED)
			{
				if (MathParser.regexMatch(token.tokenStr, ParserSymbol.nameOnlyTokenRegExp))
				{
					token.looksLike = ARGUMENT
					if (i < this.initialTokens.size - 1)
					{
						val tokenNext = this.initialTokens[i + 1]

						if (tokenNext.tokenTypeId == ParserSymbol.TYPE_ID && tokenNext.tokenId == ParserSymbol.LEFT_PARENTHESES_ID)
							token.looksLike = FUNCTION
					}
				}
				else
				{
					token.looksLike = ERROR
				}
			}

			tokensListCopy.add(token.clone())
		}

		return tokensListCopy
	}

	/**
	 * Returns missing user defined arguments names, i.e. sin(x) + cos(y) where x and y are not defined function will return x and y.
	 *
	 * @return Array<String?>
	 *
	 * @author Bas Milius
	 */
	fun getMissingUserDefinedArguments(): Array<String?>
	{
		val tokens = getCopyOfInitialTokens()
		val missingArguments = ArrayList<String>()

		tokens
				.filter { it.looksLike == ARGUMENT && !missingArguments.contains(it.tokenStr) }
				.forEach { missingArguments.add(it.tokenStr) }

		val n = missingArguments.size
		val missArgs = arrayOfNulls<String>(n)

		for (i in 0 until n)
			missArgs[i] = missingArguments[i]

		return missArgs
	}

	/**
	 * Returns missing user defined functions names, i.e. sin(x) + fun(x,y) where fun is not defined function will return fun.
	 *
	 * @return Array<String?>
	 *
	 * @author Bas Milius
	 */
	fun getMissingUserDefinedFunctions(): Array<String?>
	{
		val tokens = getCopyOfInitialTokens()
		val missingFunctions = ArrayList<String>()

		tokens
				.filter { it.looksLike == FUNCTION && !missingFunctions.contains(it.tokenStr) }
				.forEach { missingFunctions.add(it.tokenStr) }

		val n = missingFunctions.size
		val missFun = arrayOfNulls<String>(n)

		for (i in 0 until n)
			missFun[i] = missingFunctions[i]

		return missFun
	}

	/**
	 * Shows parsing (verbose mode purposes).
	 *
	 * @param lPos Int
	 * @param rPos Int
	 *
	 * @author Bas Milius
	 */
	private fun showParsing(lPos: Int, rPos: Int)
	{
		MathParser.consolePrint(" ---> ")

		(lPos..rPos)
				.map { tokensList[it] }
				.forEach {
					MathParser.consolePrint(if (it.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) it.tokenValue.toString() + " " else it.tokenStr + " ")
				}

		MathParser.consolePrint(" ... ")
	}

	/**
	 * shows known keywords.
	 *
	 * @author Bas Milius
	 */
	internal fun showKeyWords()
	{
		val keyWordsNumber = keyWordsList.size
		val maxStr = "KEY_WORD"
		MathParser.consolePrintln("KEY WORDS:")
		MathParser.consolePrintln(" -------------------------------------------")
		MathParser.consolePrintln("|      IDX | KEY_WORD |       ID |  TYPE_ID |")
		MathParser.consolePrintln(" -------------------------------------------")
		for (keyWordIndex in 0..keyWordsNumber - 1)
		{
			val keyWord = keyWordsList[keyWordIndex]
			val idxStr = getLeftSpaces(maxStr, Integer.toString(keyWordIndex))
			val wordStr = getLeftSpaces(maxStr, keyWord.wordString)
			val idStr = getLeftSpaces(maxStr, Integer.toString(keyWord.wordId))
			val typeIdStr = getLeftSpaces(maxStr, Integer.toString(keyWord.wordTypeId))
			MathParser.consolePrintln("| $idxStr | $wordStr | $idStr | $typeIdStr |")
		}
		MathParser.consolePrintln(" -------------------------------------------")
	}

	/**
	 * Gets help content.
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getHelp(): String
	{
		return this.getHelp("")
	}

	/**
	 * Searching help content.
	 *
	 * @param word String
	 *
	 * @return String
	 *
	 * @author Bas Milius
	 */
	fun getHelp(word: String): String
	{
		this.keyWordsList = ArrayList()
		var helpStr = "Help content: \n\n"

		this.addParserKeyWords()
		this.validateParserKeyWords()

		if (!this.parserKeyWordsOnly)
		{
			this.addArgumentsKeyWords()
			this.addFunctionsKeyWords()
			this.addConstantsKeyWords()
		}

		helpStr = helpStr + getLeftSpaces("12345", "#") + "  " + getRightSpaces("01234567890123456789", "key word") + getRightSpaces("                        ", "type") + getRightSpaces("0123456789012345678901234567890123456789012345", "syntax") + getRightSpaces("012345", "since") + "description" + "\n"
		helpStr = helpStr + getLeftSpaces("12345", "-") + "  " + getRightSpaces("01234567890123456789", "--------") + getRightSpaces("                        ", "----") + getRightSpaces("0123456789012345678901234567890123456789012345", "------") + getRightSpaces("012345", "-----") + "-----------" + "\n"

		Collections.sort(this.keyWordsList, KwTypeComparator())

		val keyWordsNumber = this.keyWordsList.size
		var type: String
		var kw: String
		var line: String

		for (keyWordIndex in 0 until keyWordsNumber)
		{
			val keyWord = this.keyWordsList[keyWordIndex]
			type = ""
			kw = keyWord.wordString

			when (keyWord.wordTypeId)
			{
				ParserSymbol.TYPE_ID -> type = ParserSymbol.TYPE_DESC
				ParserSymbol.NUMBER_TYPE_ID ->
				{
					type = "number"
					kw = "_number_"
				}
				Operator.TYPE_ID -> type = Operator.TYPE_DESC
				BooleanOperator.TYPE_ID -> type = BooleanOperator.TYPE_DESC
				BinaryRelation.TYPE_ID -> type = BinaryRelation.TYPE_DESC
				Function1Arg.TYPE_ID -> type = Function1Arg.TYPE_DESC
				Function2Arg.TYPE_ID -> type = Function2Arg.TYPE_DESC
				Function3Arg.TYPE_ID -> type = Function3Arg.TYPE_DESC
				FunctionVariadic.TYPE_ID -> type = FunctionVariadic.TYPE_DESC
				CalculusOperator.TYPE_ID -> type = CalculusOperator.TYPE_DESC
				RandomVariable.TYPE_ID -> type = RandomVariable.TYPE_DESC
				ConstantValue.TYPE_ID -> type = ConstantValue.TYPE_DESC
				Argument.TYPE_ID -> type = Argument.TYPE_DESC
				RecursiveArgument.TYPE_ID_RECURSIVE -> type = RecursiveArgument.TYPE_DESC_RECURSIVE
				Function.TYPE_ID -> type = Function.TYPE_DESC
				Constant.TYPE_ID -> type = Constant.TYPE_DESC
				Unit.TYPE_ID -> type = Unit.TYPE_DESC
				BitwiseOperator.TYPE_ID -> type = BitwiseOperator.TYPE_DESC
			}

			line = getLeftSpaces("12345", Integer.toString(keyWordIndex + 1)) + ". " + getRightSpaces("01234567890123456789", kw) + getRightSpaces("                        ", "<$type>") + getRightSpaces("0123456789012345678901234567890123456789012345", keyWord.syntax) + getRightSpaces("012345", keyWord.since) + keyWord.description + "\n"

			if (line.toLowerCase().indexOf(word.toLowerCase()) >= 0)
			{
				helpStr += line
			}
		}

		return helpStr
	}

	/**
	 * Returns list of key words known to the parser
	 *
	 * @return List<KeyWord>
	 *
	 * @author Bas Milius
	 */
	fun getKeyWords(): List<KeyWord>
	{
		return this.getKeyWords("")
	}

	/**
	 * Returns list of key words known to the parser
	 *
	 * @param query String
	 *
	 * @return List<KeyWord>
	 *
	 * @author Bas Milius
	 */
	fun getKeyWords(query: String): List<KeyWord>
	{
		this.keyWordsList = ArrayList()
		val kwyWordsToReturn = ArrayList<KeyWord>()

		this.addParserKeyWords()
		this.validateParserKeyWords()

		if (!this.parserKeyWordsOnly)
		{
			this.addArgumentsKeyWords()
			this.addFunctionsKeyWords()
			this.addConstantsKeyWords()
		}

		Collections.sort(this.keyWordsList, KwTypeComparator())

		var line: String

		for (kw in this.keyWordsList)
		{
			line = "str=" + kw.wordString + " " + "desc=" + kw.description + " " + "syn=" + kw.syntax + " " + "sin=" + kw.since + " " + "wid=" + kw.wordId + " " + "tid=" + kw.wordTypeId

			if (line.toLowerCase().indexOf(query.toLowerCase()) >= 0)
				kwyWordsToReturn.add(kw)
		}

		return kwyWordsToReturn
	}

	/**
	 * Show tokens.
	 *
	 * @author Bas Milius
	 */
	private fun showTokens()
	{
		showTokens(this.tokensList)
	}

	/**
	 * Shows initial tokens.
	 *
	 * @author Bas Milius
	 */
	internal fun showInitialTokens()
	{
		showTokens(this.initialTokens)
	}

	/**
	 * Shows arguments.
	 *
	 * @author Bas Milius
	 */
	private fun showArguments()
	{
		for (a in this.argumentsList)
		{
			val vMode = a.getVerboseMode()
			a.setSilentMode()
			this.printSystemInfo(a.argumentName + " = " + a.argumentValue + "\n", WITH_EXP_STR)

			if (vMode)
				a.setVerboseMode()
		}
	}

	/**
	 * Prints system info.
	 *
	 * @param info String
	 * @param withExpressionString Boolean
	 *
	 * @author Bas Milius
	 */
	private fun printSystemInfo(info: String, withExpressionString: Boolean)
	{
		if (withExpressionString)
		{
			MathParser.consolePrint("[$description][$expressionString] $info")
		}
		else
		{
			MathParser.consolePrint(info)
		}
	}

	/**
	 * Expression cloning.
	 *
	 * @return Expression
	 *
	 * @author Bas Milius
	 */
	private fun clone(): Expression
	{
		val newExp = Expression(this)

		if (this.initialTokens.isNotEmpty())
			newExp.initialTokens = this.createInitialTokens(0, this.initialTokens.size - 1, this.initialTokens)

		return newExp
	}

}
