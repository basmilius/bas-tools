package com.basmilius.math.parsertokens

import com.basmilius.math.MathParser

/**
 * Object BitwiseOperator
 *
 * @author Bas Milius
 * @package com.basmilius.math.parsertokens
 */
object BitwiseOperator
{

	// BitwiseOperator - token type id.
	val TYPE_ID = 11
	val TYPE_DESC = "Bitwise Operator"

	// BitwiseOperator - tokens id.
	val COMPL_ID = 1
	val AND_ID = 2
	val XOR_ID = 3
	val OR_ID = 4
	val LEFT_SHIFT_ID = 5
	val RIGHT_SHIFT_ID = 6

	// BitwiseOperator - tokens key words.
	val COMPL_STR = "@~"
	val AND_STR = "@&"
	val XOR_STR = "@^"
	val OR_STR = "@|"
	val LEFT_SHIFT_STR = "@<<"
	val RIGHT_SHIFT_STR = "@>>"

	// BitwiseOperator - syntax.
	val COMPL_SYN = "a @~ b"
	val AND_SYN = "a @& b"
	val XOR_SYN = "a @^ b"
	val OR_SYN = "a @| b"
	val LEFT_SHIFT_SYN = "a @<< b"
	val RIGHT_SHIFT_SYN = "a @>> b"

	// BitwiseOperator - tokens description.
	val COMPL_DESC = "Bitwise unary complement"
	val AND_DESC = "Bitwise AND"
	val XOR_DESC = "Bitwise exclusive OR"
	val OR_DESC = "Bitwise inclusive OR"
	val LEFT_SHIFT_DESC = "Signed left shift"
	val RIGHT_SHIFT_DESC = "Signed right shift"

	// BitwiseOperator - tokens since.
	val COMPL_SINCE = MathParser.NAMEv40
	val AND_SINCE = MathParser.NAMEv40
	val XOR_SINCE = MathParser.NAMEv40
	val OR_SINCE = MathParser.NAMEv40
	val LEFT_SHIFT_SINCE = MathParser.NAMEv40
	val RIGHT_SHIFT_SINCE = MathParser.NAMEv40

}
