/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.math.parsertokens

import com.basmilius.math.MathParser

/**
 * Object Operator
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.parsertokens
 */
object Operator
{

	// Operator - token type id.
	val TYPE_ID = 1
	val TYPE_DESC = "Operator"

	// Operator - tokens id.
	val PLUS_ID = 1
	val MINUS_ID = 2
	val MULTIPLY_ID = 3
	val DIVIDE_ID = 4
	val POWER_ID = 5
	val FACT_ID = 6
	val MOD_ID = 7
	val PERC_ID = 8

	// Operator - tokens key words.
	val PLUS_STR = "+"
	val MINUS_STR = "-"
	val MULTIPLY_STR = "*"
	val DIVIDE_STR = "/"
	val POWER_STR = "^"
	val FACT_STR = "!"
	val MOD_STR = "#"
	val PERC_STR = "%"

	// Operator - syntax.
	val PLUS_SYN = "a + b"
	val MINUS_SYN = "a - b"
	val MULTIPLY_SYN = "a * b"
	val DIVIDE_SYN = "a / b"
	val POWER_SYN = "a^b"
	val FACT_SYN = "n!"
	val MOD_SYN = "a # b"
	val PERC_SYN = "n%"

	// Operator - tokens description.
	val PLUS_DESC = "Addition"
	val MINUS_DESC = "Subtraction"
	val MULTIPLY_DESC = "Nultiplication"
	val DIVIDE_DESC = "Division"
	val POWER_DESC = "Exponentiation"
	val FACT_DESC = "Factorial"
	val MOD_DESC = "Modulo function"
	val PERC_DESC = "Percentage"

	// Operator - since.
	val PLUS_SINCE = MathParser.NAMEv10
	val MINUS_SINCE = MathParser.NAMEv10
	val MULTIPLY_SINCE = MathParser.NAMEv10
	val DIVIDE_SINCE = MathParser.NAMEv10
	val POWER_SINCE = MathParser.NAMEv10
	val FACT_SINCE = MathParser.NAMEv10
	val MOD_SINCE = MathParser.NAMEv10
	val PERC_SINCE = MathParser.NAMEv41

}
