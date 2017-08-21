package com.basmilius.math.parsertokens

import com.basmilius.math.MathParser

/**
 * Object Function2Arg
 *
 * @author Bas Milius
 * @package com.basmilius.math.mxparser.parsertokens
 */
object Function2Arg
{

	// BinaryFunction - token type id.
	val TYPE_ID = 5
	val TYPE_DESC = "Binary Function"

	// BinaryFunction - tokens id.
	val LOG_ID = 1
	val MOD_ID = 2
	val BINOM_COEFF_ID = 3
	val BERNOULLI_NUMBER_ID = 4
	val STIRLING1_NUMBER_ID = 5
	val STIRLING2_NUMBER_ID = 6
	val WORPITZKY_NUMBER_ID = 7
	val EULER_NUMBER_ID = 8
	val KRONECKER_DELTA_ID = 9
	val EULER_POLYNOMIAL_ID = 10
	val HARMONIC_NUMBER_ID = 11
	val RND_UNIFORM_CONT_ID = 12
	val RND_UNIFORM_DISCR_ID = 13
	val ROUND_ID = 14
	val RND_NORMAL_ID = 15
	val NDIG_ID = 16
	val DIGIT10_ID = 17
	val FACTVAL_ID = 18
	val FACTEXP_ID = 19
	val ROOT_ID = 20

	// BinaryFunction - tokens key words.
	val LOG_STR = "log"
	val MOD_STR = "mod"
	val BINOM_COEFF_STR = "C"
	val BERNOULLI_NUMBER_STR = "Bern"
	val STIRLING1_NUMBER_STR = "Stirl1"
	val STIRLING2_NUMBER_STR = "Stirl2"
	val WORPITZKY_NUMBER_STR = "Worp"
	val EULER_NUMBER_STR = "Euler"
	val KRONECKER_DELTA_STR = "KDelta"
	val EULER_POLYNOMIAL_STR = "EulerPol"
	val HARMONIC_NUMBER_STR = "Harm"
	val RND_UNIFORM_CONT_STR = "rUni"
	val RND_UNIFORM_DISCR_STR = "rUnid"
	val ROUND_STR = "round"
	val RND_NORMAL_STR = "rNor"
	val NDIG_STR = "ndig"
	val DIGIT10_STR = "dig10"
	val FACTVAL_STR = "factval"
	val FACTEXP_STR = "factexp"
	val ROOT_STR = "root"

	// BinaryFunction - syntax.
	val LOG_SYN = "log(a, b)"
	val MOD_SYN = "mod(a, b)"
	val BINOM_COEFF_SYN = "C(n, k)"
	val BERNOULLI_NUMBER_SYN = "Bern(m, n)"
	val STIRLING1_NUMBER_SYN = "Stirl1(n, k)"
	val STIRLING2_NUMBER_SYN = "Stirl2(n, k)"
	val WORPITZKY_NUMBER_SYN = "Worp(n, k)"
	val EULER_NUMBER_SYN = "Euler(n, k)"
	val KRONECKER_DELTA_SYN = "KDelta(i, j)"
	val EULER_POLYNOMIAL_SYN = "EulerPol"
	val HARMONIC_NUMBER_SYN = "Harm(x, n)"
	val RND_UNIFORM_CONT_SYN = "rUni(a, b)"
	val RND_UNIFORM_DISCR_SYN = "rUnid(a, b)"
	val ROUND_SYN = "round(x, n)"
	val RND_NORMAL_SYN = "rNor(mean, stdv)"
	val NDIG_SYN = "ndig(number, base)"
	val DIGIT10_SYN = "dig10(num, pos)"
	val FACTVAL_SYN = "factval(number, factorid)"
	val FACTEXP_SYN = "factexp(number, factorid)"
	val ROOT_SYN = "root(rootorder, number)"

	// BinaryFunction - tokens description.
	val LOG_DESC = "Logarithm function"
	val MOD_DESC = "Modulo function"
	val BINOM_COEFF_DESC = "Binomial coefficient function"
	val BERNOULLI_NUMBER_DESC = "Bernoulli numbers"
	val STIRLING1_NUMBER_DESC = "Stirling numbers of the first kind"
	val STIRLING2_NUMBER_DESC = "Stirling numbers of the second kind"
	val WORPITZKY_NUMBER_DESC = "Worpitzky number"
	val EULER_NUMBER_DESC = "Euler number"
	val KRONECKER_DELTA_DESC = "Kronecker delta"
	val EULER_POLYNOMIAL_DESC = "EulerPol"
	val HARMONIC_NUMBER_DESC = "Harmonic number"
	val RND_UNIFORM_CONT_DESC = "Random variable - Uniform continuous distribution U(a,b), usage example: 2*rUni(2,10)"
	val RND_UNIFORM_DISCR_DESC = "Random variable - Uniform discrete distribution U{a,b}, usage example: 2*rUnid(2,100)"
	val ROUND_DESC = "Half-up rounding, usage examples: round(2.2, 0) = 2, round(2.6, 0) = 3, round(2.66,1) = 2.7"
	val RND_NORMAL_DESC = "Random variable - Normal distribution N(m,s) m - mean, s - stddev, usage example: 3*rNor(0,1)"
	val NDIG_DESC = "Number of digits representing the number in numeral system with given base"
	val DIGIT10_DESC = "Digit at position 1 ... n (left -> right) or 0 ... -(n-1) (right -> left) - base 10 numeral system"
	val FACTVAL_DESC = "Prime decomposition - factor value at position between 1 ... nfact(n) - ascending order by factor value"
	val FACTEXP_DESC = "Prime decomposition - factor exponent / multiplicity at position between 1 ... nfact(n) - ascending order by factor value"
	val ROOT_DESC = "N-th order root of a number"

	// BinaryFunction - since.
	val LOG_SINCE = MathParser.NAMEv10
	val MOD_SINCE = MathParser.NAMEv10
	val BINOM_COEFF_SINCE = MathParser.NAMEv10
	val BERNOULLI_NUMBER_SINCE = MathParser.NAMEv10
	val STIRLING1_NUMBER_SINCE = MathParser.NAMEv10
	val STIRLING2_NUMBER_SINCE = MathParser.NAMEv10
	val WORPITZKY_NUMBER_SINCE = MathParser.NAMEv10
	val EULER_NUMBER_SINCE = MathParser.NAMEv10
	val KRONECKER_DELTA_SINCE = MathParser.NAMEv10
	val EULER_POLYNOMIAL_SINCE = MathParser.NAMEv10
	val HARMONIC_NUMBER_SINCE = MathParser.NAMEv10
	val RND_UNIFORM_CONT_SINCE = MathParser.NAMEv30
	val RND_UNIFORM_DISCR_SINCE = MathParser.NAMEv30
	val ROUND_SINCE = MathParser.NAMEv30
	val RND_NORMAL_SINCE = MathParser.NAMEv30
	val NDIG_SINCE = MathParser.NAMEv41
	val DIGIT10_SINCE = MathParser.NAMEv41
	val FACTVAL_SINCE = MathParser.NAMEv41
	val FACTEXP_SINCE = MathParser.NAMEv41
	val ROOT_SINCE = MathParser.NAMEv41

}
