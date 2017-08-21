package com.basmilius.math.parsertokens

import com.basmilius.math.MathParser

/**
 * Object Function3Arg
 *
 * @author Bas Milius
 * @package com.basmilius.math.parsertokens
 */
object Function3Arg
{

	// 3-args Function - token type id.
	val TYPE_ID = 6
	val TYPE_DESC = "3-args Function"

	// 3-args Function - tokens id.
	val IF_CONDITION_ID = 1
	val IF_ID = 2
	val CHI_ID = 3
	val CHI_LR_ID = 4
	val CHI_L_ID = 5
	val CHI_R_ID = 6
	val PDF_UNIFORM_CONT_ID = 7
	val CDF_UNIFORM_CONT_ID = 8
	val QNT_UNIFORM_CONT_ID = 9
	val PDF_NORMAL_ID = 10
	val CDF_NORMAL_ID = 11
	val QNT_NORMAL_ID = 12
	val DIGIT_ID = 13

	// 3-args Function - tokens key words.
	val IF_STR = "if"
	val CHI_STR = "chi"
	val CHI_LR_STR = "CHi"
	val CHI_L_STR = "Chi"
	val CHI_R_STR = "cHi"
	val PDF_UNIFORM_CONT_STR = "pUni"
	val CDF_UNIFORM_CONT_STR = "cUni"
	val QNT_UNIFORM_CONT_STR = "qUni"
	val PDF_NORMAL_STR = "pNor"
	val CDF_NORMAL_STR = "cNor"
	val QNT_NORMAL_STR = "qNor"
	val DIGIT_STR = "dig"

	// 3-args Function - syntax.
	val IF_SYN = "if( cond, expr-if-true, expr-if-false )"
	val CHI_SYN = "chi(x, a, b)"
	val CHI_LR_SYN = "CHi(x, a, b)"
	val CHI_L_SYN = "Chi(x, a, b)"
	val CHI_R_SYN = "cHi(x, a, b)"
	val PDF_UNIFORM_CONT_SYN = "pUni(x, a, b)"
	val CDF_UNIFORM_CONT_SYN = "cUni(x, a, b)"
	val QNT_UNIFORM_CONT_SYN = "qUni(q, a, b)"
	val PDF_NORMAL_SYN = "pNor(x, mean, stdv)"
	val CDF_NORMAL_SYN = "cNor(x, mean, stdv)"
	val QNT_NORMAL_SYN = "qNor(q, mean, stdv)"
	val DIGIT_SYN = "dig(num, pos, base)"

	// 3-args Function - tokens description.
	val IF_DESC = "If function"
	val CHI_DESC = "Characteristic function for x in (a,b)"
	val CHI_LR_DESC = "Characteristic function for x in [a,b]"
	val CHI_L_DESC = "Characteristic function for x in [a,b)"
	val CHI_R_DESC = "Characteristic function for x in (a,b]"
	val PDF_UNIFORM_CONT_DESC = "Probability distribution function - Uniform continuous distribution U(a,b)"
	val CDF_UNIFORM_CONT_DESC = "Cumulative distribution function - Uniform continuous distribution U(a,b)"
	val QNT_UNIFORM_CONT_DESC = "Quantile function (inverse cumulative distribution function) - Uniform continuous distribution U(a,b)"
	val PDF_NORMAL_DESC = "Probability distribution function - Normal distribution N(m,s)"
	val CDF_NORMAL_DESC = "Cumulative distribution function - Normal distribution N(m,s)"
	val QNT_NORMAL_DESC = "Quantile function (inverse cumulative distribution function)"
	val DIGIT_DESC = "Digit at position 1 ... n (left -> right) or 0 ... -(n-1) (right -> left) - numeral system with given base"

	// 3-args Function - since.
	val IF_SINCE = MathParser.NAMEv10
	val CHI_SINCE = MathParser.NAMEv10
	val CHI_LR_SINCE = MathParser.NAMEv10
	val CHI_L_SINCE = MathParser.NAMEv10
	val CHI_R_SINCE = MathParser.NAMEv10
	val PDF_UNIFORM_CONT_SINCE = MathParser.NAMEv30
	val CDF_UNIFORM_CONT_SINCE = MathParser.NAMEv30
	val QNT_UNIFORM_CONT_SINCE = MathParser.NAMEv30
	val PDF_NORMAL_SINCE = MathParser.NAMEv30
	val CDF_NORMAL_SINCE = MathParser.NAMEv30
	val QNT_NORMAL_SINCE = MathParser.NAMEv30
	val DIGIT_SINCE = MathParser.NAMEv41

}
