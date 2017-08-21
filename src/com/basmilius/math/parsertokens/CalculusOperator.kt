package com.basmilius.math.parsertokens

import com.basmilius.math.MathParser

/**
 * Object CalculusOperator
 *
 * @author Bas Milius
 * @package com.basmilius.math.parsertokens
 */
object CalculusOperator
{

	// CalculusOperator - token type id.
	val TYPE_ID = 8
	val TYPE_DESC = "Calculus Operator"

	// CalculusOperator - tokens id.
	val SUM_ID = 1
	val PROD_ID = 3
	val INT_ID = 5
	val DER_ID = 6
	val DER_LEFT_ID = 7
	val DER_RIGHT_ID = 8
	val DERN_ID = 9
	val FORW_DIFF_ID = 10
	val BACKW_DIFF_ID = 11
	val AVG_ID = 12
	val VAR_ID = 13
	val STD_ID = 14
	val MIN_ID = 15
	val MAX_ID = 16
	val SOLVE_ID = 17

	// CalculusOperator - tokens key words.
	val SUM_STR = "sum"
	val PROD_STR = "prod"
	val INT_STR = "int"
	val DER_STR = "der"
	val DER_LEFT_STR = "der-"
	val DER_RIGHT_STR = "der+"
	val DERN_STR = "dern"
	val FORW_DIFF_STR = "diff"
	val BACKW_DIFF_STR = "difb"
	val AVG_STR = "avg"
	val VAR_STR = "vari"
	val STD_STR = "stdi"
	val MIN_STR = "mini"
	val MAX_STR = "maxi"
	val SOLVE_STR = "solve"

	// CalculusOperator - syntax.
	val SUM_SYN = "sum( i, from, to, expr , <by> )"
	val PROD_SYN = "prod( i, from, to, expr , <by> )"
	val INT_SYN = "int( expr, arg, from, to )"
	val DER_SYN = "der( expr, arg, <point> )"
	val DER_LEFT_SYN = "der-( expr, arg, <point> )"
	val DER_RIGHT_SYN = "der+( expr, arg, <point> )"
	val DERN_SYN = "dern( expr, n, arg )"
	val FORW_DIFF_SYN = "diff( expr, arg, <delta> )"
	val BACKW_DIFF_SYN = "difb( expr, arg, <delta> )"
	val AVG_SYN = "avg( i, from, to, expr , <by> )"
	val VAR_SYN = "vari( i, from, to, expr , <by> )"
	val STD_SYN = "stdi( i, from, to, expr , <by> )"
	val MIN_SYN = "mini( i, from, to, expr , <by> )"
	val MAX_SYN = "maxi( i, from, to, expr , <by> )"
	val SOLVE_SYN = "solve( expr, a, b )"

	// CalculusOperator - tokens description.
	val SUM_DESC = "Summation operator - SIGMA"
	val PROD_DESC = "Product operator - PI"
	val INT_DESC = "Definite integral operator"
	val DER_DESC = "Derivative operator"
	val DER_LEFT_DESC = "Left derivative operator"
	val DER_RIGHT_DESC = "Right derivative operator"
	val DERN_DESC = "n-th derivative operator"
	val FORW_DIFF_DESC = "Forward difference operator"
	val BACKW_DIFF_DESC = "Backward difference operator"
	val AVG_DESC = "Average operator"
	val VAR_DESC = "Bias-corrected sample variance operator"
	val STD_DESC = "Bias-corrected sample standard deviation operator"
	val MIN_DESC = "Minimum value"
	val MAX_DESC = "Maximum value"
	val SOLVE_DESC = "f(x) = 0 equation solving, function root finding"

	// CalculusOperator - since.
	val SUM_SINCE = MathParser.NAMEv10
	val PROD_SINCE = MathParser.NAMEv10
	val INT_SINCE = MathParser.NAMEv10
	val DER_SINCE = MathParser.NAMEv10
	val DER_LEFT_SINCE = MathParser.NAMEv10
	val DER_RIGHT_SINCE = MathParser.NAMEv10
	val DERN_SINCE = MathParser.NAMEv10
	val FORW_DIFF_SINCE = MathParser.NAMEv10
	val BACKW_DIFF_SINCE = MathParser.NAMEv10
	val AVG_SINCE = MathParser.NAMEv24
	val VAR_SINCE = MathParser.NAMEv24
	val STD_SINCE = MathParser.NAMEv24
	val MIN_SINCE = MathParser.NAMEv24
	val MAX_SINCE = MathParser.NAMEv24
	val SOLVE_SINCE = MathParser.NAMEv40

}
