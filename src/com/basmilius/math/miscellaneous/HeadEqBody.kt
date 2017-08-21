package com.basmilius.math.miscellaneous

import com.basmilius.math.Expression
import com.basmilius.math.MathParser
import com.basmilius.math.parsertokens.Token

/**
 * Class HeadEqBody
 *
 * @author Bas Milius
 * @package com.basmilius.math.miscellaneous
 */
class HeadEqBody(definitionString: String)
{

	private val ONLY_PARSER_KEYWORDS = true
	var headStr: String
	var bodyStr: String
	var eqPos: Int = 0
	var headTokens: List<Token>? = null
	var definitionError: Boolean = false

	/**
	 * HeadEqBody Constructor.
	 *
	 * @constructor
	 * @author Bas Milius
	 */
	init
	{
		var c: Char
		eqPos = 0
		var matchStatus = MathParser.NOT_FOUND
		definitionError = false

		do
		{
			c = definitionString[eqPos]
			if (c == '=')
				matchStatus = MathParser.FOUND
			else
				eqPos++
		}
		while (eqPos < definitionString.length && matchStatus == MathParser.NOT_FOUND)

		if (matchStatus == MathParser.FOUND && eqPos > 0 && eqPos <= definitionString.length - 2)
		{
			headStr = definitionString.substring(0, eqPos)
			bodyStr = definitionString.substring(eqPos + 1)

			val headExpression = Expression(headStr, ONLY_PARSER_KEYWORDS)

			headTokens = headExpression.getCopyOfInitialTokens()
		}
		else
		{
			definitionError = true
			headStr = ""
			bodyStr = ""
			headTokens = null
			eqPos = -1
		}
	}

}
