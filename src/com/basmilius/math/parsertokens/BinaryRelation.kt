package com.basmilius.math.parsertokens

import com.basmilius.math.MathParser

/**
 * Object BinaryRelation
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser.parsertokens
 */
object BinaryRelation
{

	// BinaryRelation - token type id.
	val TYPE_ID = 3
	val TYPE_DESC = "Binary Relation"

	// BinaryRelation - tokens id.
	val EQ_ID = 1
	val NEQ_ID = 2
	val LT_ID = 3
	val GT_ID = 4
	val LEQ_ID = 5
	val GEQ_ID = 6

	// BinaryRelation - tokens key words.
	val EQ_STR = "="
	val EQ1_STR = "=="
	val NEQ_STR = "<>"
	val NEQ1_STR = "~="
	val NEQ2_STR = "!="
	val LT_STR = "<"
	val GT_STR = ">"
	val LEQ_STR = "<="
	val GEQ_STR = ">="

	// BinaryRelation - syntax.
	val EQ_SYN = "a = b"
	val EQ1_SYN = "a == b"
	val NEQ_SYN = "a <> b"
	val NEQ1_SYN = "a ~= b"
	val NEQ2_SYN = "a != b"
	val LT_SYN = "a < b"
	val GT_SYN = "a > b"
	val LEQ_SYN = "a <= b"
	val GEQ_SYN = "a >= b"

	// BinaryRelation - tokens description.
	val EQ_DESC = "Equality"
	val NEQ_DESC = "Inequation"
	val LT_DESC = "Lower than"
	val GT_DESC = "Greater than"
	val LEQ_DESC = "Lower or equal"
	val GEQ_DESC = "Greater or equal"

	// BinaryRelation - since.
	val EQ_SINCE = MathParser.NAMEv10
	val NEQ_SINCE = MathParser.NAMEv10
	val LT_SINCE = MathParser.NAMEv10
	val GT_SINCE = MathParser.NAMEv10
	val LEQ_SINCE = MathParser.NAMEv10
	val GEQ_SINCE = MathParser.NAMEv10

}
