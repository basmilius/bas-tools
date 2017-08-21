package com.basmilius.math.parsertokens

import com.basmilius.math.MathParser

/**
 * Object RandomVariable
 *
 * @author Bas Milius
 * @package com.basmilius.math.parsertokens
 */
object RandomVariable
{

	// RandomVariable - token type id.
	val TYPE_ID = 10
	val TYPE_DESC = "Random Variable"

	// RandomVariable - tokens id.
	val UNIFORM_ID = 1
	val INT_ID = 2
	val INT1_ID = 3
	val INT2_ID = 4
	val INT3_ID = 5
	val INT4_ID = 6
	val INT5_ID = 7
	val INT6_ID = 8
	val INT7_ID = 9
	val INT8_ID = 10
	val INT9_ID = 11
	val NAT0_ID = 12
	val NAT0_1_ID = 13
	val NAT0_2_ID = 14
	val NAT0_3_ID = 15
	val NAT0_4_ID = 16
	val NAT0_5_ID = 17
	val NAT0_6_ID = 18
	val NAT0_7_ID = 19
	val NAT0_8_ID = 20
	val NAT0_9_ID = 21
	val NAT1_ID = 22
	val NAT1_1_ID = 23
	val NAT1_2_ID = 24
	val NAT1_3_ID = 25
	val NAT1_4_ID = 26
	val NAT1_5_ID = 27
	val NAT1_6_ID = 28
	val NAT1_7_ID = 29
	val NAT1_8_ID = 30
	val NAT1_9_ID = 31
	val NOR_ID = 32

	// RandomVariable - tokens key words.
	val UNIFORM_STR = "[Uni]"
	val INT_STR = "[Int]"
	val INT1_STR = "[Int1]"
	val INT2_STR = "[Int2]"
	val INT3_STR = "[Int3]"
	val INT4_STR = "[Int4]"
	val INT5_STR = "[Int5]"
	val INT6_STR = "[Int6]"
	val INT7_STR = "[Int7]"
	val INT8_STR = "[Int8]"
	val INT9_STR = "[Int9]"
	val NAT0_STR = "[nat]"
	val NAT0_1_STR = "[nat1]"
	val NAT0_2_STR = "[nat2]"
	val NAT0_3_STR = "[nat3]"
	val NAT0_4_STR = "[nat4]"
	val NAT0_5_STR = "[nat5]"
	val NAT0_6_STR = "[nat6]"
	val NAT0_7_STR = "[nat7]"
	val NAT0_8_STR = "[nat8]"
	val NAT0_9_STR = "[nat9]"
	val NAT1_STR = "[Nat]"
	val NAT1_1_STR = "[Nat1]"
	val NAT1_2_STR = "[Nat2]"
	val NAT1_3_STR = "[Nat3]"
	val NAT1_4_STR = "[Nat4]"
	val NAT1_5_STR = "[Nat5]"
	val NAT1_6_STR = "[Nat6]"
	val NAT1_7_STR = "[Nat7]"
	val NAT1_8_STR = "[Nat8]"
	val NAT1_9_STR = "[Nat9]"
	val NOR_STR = "[Nor]"

	// RandomVariable - syntax.
	val UNIFORM_SYN = UNIFORM_STR
	val INT_SYN = INT_STR
	val INT1_SYN = INT1_STR
	val INT2_SYN = INT2_STR
	val INT3_SYN = INT3_STR
	val INT4_SYN = INT4_STR
	val INT5_SYN = INT5_STR
	val INT6_SYN = INT6_STR
	val INT7_SYN = INT7_STR
	val INT8_SYN = INT8_STR
	val INT9_SYN = INT9_STR
	val NAT0_SYN = NAT0_STR
	val NAT0_1_SYN = NAT0_1_STR
	val NAT0_2_SYN = NAT0_2_STR
	val NAT0_3_SYN = NAT0_3_STR
	val NAT0_4_SYN = NAT0_4_STR
	val NAT0_5_SYN = NAT0_5_STR
	val NAT0_6_SYN = NAT0_6_STR
	val NAT0_7_SYN = NAT0_7_STR
	val NAT0_8_SYN = NAT0_8_STR
	val NAT0_9_SYN = NAT0_9_STR
	val NAT1_SYN = NAT1_STR
	val NAT1_1_SYN = NAT1_1_STR
	val NAT1_2_SYN = NAT1_2_STR
	val NAT1_3_SYN = NAT1_3_STR
	val NAT1_4_SYN = NAT1_4_STR
	val NAT1_5_SYN = NAT1_5_STR
	val NAT1_6_SYN = NAT1_6_STR
	val NAT1_7_SYN = NAT1_7_STR
	val NAT1_8_SYN = NAT1_8_STR
	val NAT1_9_SYN = NAT1_9_STR
	val NOR_SYN = NOR_STR

	// RandomVariable - tokens description.
	val UNIFORM_DESC = "Random variable - Uniform continuous distribution U(0,1)"
	val INT_DESC = "Random variable - random integer"
	val INT1_DESC = "Random variable - random integer - Uniform discrete distribution U{-10^1, 10^1}"
	val INT2_DESC = "Random variable - random integer - Uniform discrete distribution U{-10^2, 10^2}"
	val INT3_DESC = "Random variable - random integer - Uniform discrete distribution U{-10^3, 10^3}"
	val INT4_DESC = "Random variable - random integer - Uniform discrete distribution U{-10^4, 10^4}"
	val INT5_DESC = "Random variable - random integer - Uniform discrete distribution U{-10^5, 10^5}"
	val INT6_DESC = "Random variable - random integer - Uniform discrete distribution U{-10^6, 10^6}"
	val INT7_DESC = "Random variable - random integer - Uniform discrete distribution U{-10^7, 10^7}"
	val INT8_DESC = "Random variable - random integer - Uniform discrete distribution U{-10^8, 10^8}"
	val INT9_DESC = "Random variable - random integer - Uniform discrete distribution U{-10^9, 10^9}"
	val NAT0_DESC = "Random variable - random natural number including 0"
	val NAT0_1_DESC = "Random variable - random natural number including 0 - Uniform discrete distribution U{0, 10^1}"
	val NAT0_2_DESC = "Random variable - random natural number including 0 - Uniform discrete distribution U{0, 10^2}"
	val NAT0_3_DESC = "Random variable - random natural number including 0 - Uniform discrete distribution U{0, 10^3}"
	val NAT0_4_DESC = "Random variable - random natural number including 0 - Uniform discrete distribution U{0, 10^4}"
	val NAT0_5_DESC = "Random variable - random natural number including 0 - Uniform discrete distribution U{0, 10^5}"
	val NAT0_6_DESC = "Random variable - random natural number including 0 - Uniform discrete distribution U{0, 10^6}"
	val NAT0_7_DESC = "Random variable - random natural number including 0 - Uniform discrete distribution U{0, 10^7}"
	val NAT0_8_DESC = "Random variable - random natural number including 0 - Uniform discrete distribution U{0, 10^8}"
	val NAT0_9_DESC = "Random variable - random natural number including 0 - Uniform discrete distribution U{0, 10^9}"
	val NAT1_DESC = "Random variable - random natural number"
	val NAT1_1_DESC = "Random variable - random natural number - Uniform discrete distribution U{1, 10^1}"
	val NAT1_2_DESC = "Random variable - random natural number - Uniform discrete distribution U{1, 10^2}"
	val NAT1_3_DESC = "Random variable - random natural number - Uniform discrete distribution U{1, 10^3}"
	val NAT1_4_DESC = "Random variable - random natural number - Uniform discrete distribution U{1, 10^4}"
	val NAT1_5_DESC = "Random variable - random natural number - Uniform discrete distribution U{1, 10^5}"
	val NAT1_6_DESC = "Random variable - random natural number - Uniform discrete distribution U{1, 10^6}"
	val NAT1_7_DESC = "Random variable - random natural number - Uniform discrete distribution U{1, 10^7}"
	val NAT1_8_DESC = "Random variable - random natural number - Uniform discrete distribution U{1, 10^8}"
	val NAT1_9_DESC = "Random variable - random natural number - Uniform discrete distribution U{1, 10^9}"
	val NOR_DESC = "Random variable - Normal distribution N(0,1)"

	// RandomVariable - since.
	val UNIFORM_SINCE = MathParser.NAMEv30
	val INT_SINCE = MathParser.NAMEv30
	val INT1_SINCE = MathParser.NAMEv30
	val INT2_SINCE = MathParser.NAMEv30
	val INT3_SINCE = MathParser.NAMEv30
	val INT4_SINCE = MathParser.NAMEv30
	val INT5_SINCE = MathParser.NAMEv30
	val INT6_SINCE = MathParser.NAMEv30
	val INT7_SINCE = MathParser.NAMEv30
	val INT8_SINCE = MathParser.NAMEv30
	val INT9_SINCE = MathParser.NAMEv30
	val NAT0_SINCE = MathParser.NAMEv30
	val NAT0_1_SINCE = MathParser.NAMEv30
	val NAT0_2_SINCE = MathParser.NAMEv30
	val NAT0_3_SINCE = MathParser.NAMEv30
	val NAT0_4_SINCE = MathParser.NAMEv30
	val NAT0_5_SINCE = MathParser.NAMEv30
	val NAT0_6_SINCE = MathParser.NAMEv30
	val NAT0_7_SINCE = MathParser.NAMEv30
	val NAT0_8_SINCE = MathParser.NAMEv30
	val NAT0_9_SINCE = MathParser.NAMEv30
	val NAT1_SINCE = MathParser.NAMEv30
	val NAT1_1_SINCE = MathParser.NAMEv30
	val NAT1_2_SINCE = MathParser.NAMEv30
	val NAT1_3_SINCE = MathParser.NAMEv30
	val NAT1_4_SINCE = MathParser.NAMEv30
	val NAT1_5_SINCE = MathParser.NAMEv30
	val NAT1_6_SINCE = MathParser.NAMEv30
	val NAT1_7_SINCE = MathParser.NAMEv30
	val NAT1_8_SINCE = MathParser.NAMEv30
	val NAT1_9_SINCE = MathParser.NAMEv30
	val NOR_SINCE = MathParser.NAMEv30

}
