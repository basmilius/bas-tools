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
 * Object FunctionVariadic
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.parsertokens
 */
object FunctionVariadic
{

	// FunctionVariadic - token type id.
	val TYPE_ID = 7
	val TYPE_DESC = "Variadic Function"

	// FunctionVariadic - tokens id.
	val IFF_ID = 1
	val MIN_ID = 2
	val MAX_ID = 3
	val CONT_FRAC_ID = 4
	val CONT_POL_ID = 5
	val GCD_ID = 6
	val LCM_ID = 7
	val SUM_ID = 8
	val PROD_ID = 9
	val AVG_ID = 10
	val VAR_ID = 11
	val STD_ID = 12
	val RND_LIST_ID = 13
	val COALESCE_ID = 14
	val OR_ID = 15
	val AND_ID = 16
	val XOR_ID = 17
	val ARGMIN_ID = 18
	val ARGMAX_ID = 19
	val MEDIAN_ID = 20
	val MODE_ID = 21
	val BASE_ID = 22
	val NDIST_ID = 23

	// FunctionVariadic - tokens key words.
	val IFF_STR = "iff"
	val MIN_STR = "min"
	val MAX_STR = "max"
	val CONT_FRAC_STR = "ConFrac"
	val CONT_POL_STR = "ConPol"
	val GCD_STR = "gcd"
	val LCM_STR = "lcm"
	val SUM_STR = "add"
	val PROD_STR = "multi"
	val AVG_STR = "mean"
	val VAR_STR = "var"
	val STD_STR = "std"
	val RND_LIST_STR = "rList"
	val COALESCE_STR = "coalesce"
	val OR_STR = "or"
	val AND_STR = "and"
	val XOR_STR = "xor"
	val ARGMIN_STR = "argmin"
	val ARGMAX_STR = "argmax"
	val MEDIAN_STR = "med"
	val MODE_STR = "mode"
	val BASE_STR = "base"
	val NDIST_STR = "ndist"

	// FunctionVariadic - syntax.
	val IFF_SYN = "iff( cond-1, expr-1; ... ; cond-n, expr-n )"
	val MIN_SYN = "min(a1, ..., an)"
	val MAX_SYN = "max(a1, ..., an)"
	val CONT_FRAC_SYN = "ConFrac(a1, ..., an)"
	val CONT_POL_SYN = "ConPol(a1, ..., an)"
	val GCD_SYN = "gcd(a1, ..., an)"
	val LCM_SYN = "lcm(a1, ..., an)"
	val SUM_SYN = "add(a1, ..., an)"
	val PROD_SYN = "multi(a1, ..., an)"
	val AVG_SYN = "mean(a1, ..., an)"
	val VAR_SYN = "var(a1, ..., an)"
	val STD_SYN = "std(a1, ..., an)"
	val RND_LIST_SYN = "rList(a1, ..., an)"
	val COALESCE_SYN = "coalesce(a1, ..., an)"
	val OR_SYN = "or(a1, ..., an)"
	val AND_SYN = "and(a1, ..., an)"
	val XOR_SYN = "xor(a1, ..., an)"
	val ARGMIN_SYN = "argmin(a1, ..., an)"
	val ARGMAX_SYN = "argmax(a1, ..., an)"
	val MEDIAN_SYN = "med(a1, ..., an)"
	val MODE_SYN = "mode(a1, ..., an)"
	val BASE_SYN = "base(b, d1, ..., dn)"
	val NDIST_SYN = "ndist(v1, ..., vn)"

	// FunctionVariadic - tokens description.
	val IFF_DESC = "If function"
	val MIN_DESC = "Minimum function"
	val MAX_DESC = "Maximum function"
	val CONT_FRAC_DESC = "Continued fraction"
	val CONT_POL_DESC = "Continued polynomial"
	val GCD_DESC = "Greatest common divisor"
	val LCM_DESC = "Least common multiple"
	val SUM_DESC = "Summation operator"
	val PROD_DESC = "Multiplication"
	val AVG_DESC = "Mean / average value"
	val VAR_DESC = "Bias-corrected sample variance"
	val STD_DESC = "Bias-corrected sample standard deviation"
	val RND_LIST_DESC = "Random number from given list of numbers"
	val COALESCE_DESC = "Returns the first non-NaN value"
	val OR_DESC = "Logical disjunction (OR) - variadic"
	val AND_DESC = "Logical conjunction (AND) - variadic"
	val XOR_DESC = "Exclusive or (XOR) - variadic"
	val ARGMIN_DESC = "Arguments / indices of the minima"
	val ARGMAX_DESC = "Arguments / indices of the maxima"
	val MEDIAN_DESC = "The sample median"
	val MODE_DESC = "Mode - the value that appears most often"
	val BASE_DESC = "Returns number in given numeral system base represented by list of digits"
	val NDIST_DESC = "Number of distinct values"

	// FunctionVariadic - since.
	val IFF_SINCE = MathParser.NAMEv10
	val MIN_SINCE = MathParser.NAMEv10
	val MAX_SINCE = MathParser.NAMEv10
	val CONT_FRAC_SINCE = MathParser.NAMEv10
	val CONT_POL_SINCE = MathParser.NAMEv10
	val GCD_SINCE = MathParser.NAMEv10
	val LCM_SINCE = MathParser.NAMEv10
	val SUM_SINCE = MathParser.NAMEv24
	val PROD_SINCE = MathParser.NAMEv24
	val AVG_SINCE = MathParser.NAMEv24
	val VAR_SINCE = MathParser.NAMEv24
	val STD_SINCE = MathParser.NAMEv24
	val RND_LIST_SINCE = MathParser.NAMEv30
	val COALESCE_SINCE = MathParser.NAMEv41
	val OR_SINCE = MathParser.NAMEv41
	val AND_SINCE = MathParser.NAMEv41
	val XOR_SINCE = MathParser.NAMEv41
	val ARGMIN_SINCE = MathParser.NAMEv41
	val ARGMAX_SINCE = MathParser.NAMEv41
	val MEDIAN_SINCE = MathParser.NAMEv41
	val MODE_SINCE = MathParser.NAMEv41
	val BASE_SINCE = MathParser.NAMEv41
	val NDIST_SINCE = MathParser.NAMEv41

}
