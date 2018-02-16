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
 * Object ParserSymbol
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.parsertokens
 */
object ParserSymbol
{

	// ParserSymbol - reg exp patterns.
	val DIGIT = "[0-9]"
	val DIGIT_B1 = "1"
	val DIGIT_B2 = "[01]"
	val DIGIT_B3 = "[0-2]"
	val DIGIT_B4 = "[0-3]"
	val DIGIT_B5 = "[0-4]"
	val DIGIT_B6 = "[0-5]"
	val DIGIT_B7 = "[0-6]"
	val DIGIT_B8 = "[0-7]"
	val DIGIT_B9 = "[0-8]"
	val DIGIT_B10 = "[0-9]"
	val DIGIT_B11 = "[0-9aA]"
	val DIGIT_B12 = "[0-9a-bA-B]"
	val DIGIT_B13 = "[0-9a-cA-C]"
	val DIGIT_B14 = "[0-9a-dA-D]"
	val DIGIT_B15 = "[0-9a-eA-E]"
	val DIGIT_B16 = "[0-9a-fA-F]"
	val DIGIT_B17 = "[0-9a-gA-G]"
	val DIGIT_B18 = "[0-9a-hA-H]"
	val DIGIT_B19 = "[0-9a-iA-I]"
	val DIGIT_B20 = "[0-9a-jA-J]"
	val DIGIT_B21 = "[0-9a-kA-K]"
	val DIGIT_B22 = "[0-9a-lA-L]"
	val DIGIT_B23 = "[0-9a-mA-M]"
	val DIGIT_B24 = "[0-9a-nA-N]"
	val DIGIT_B25 = "[0-9a-oA-O]"
	val DIGIT_B26 = "[0-9a-pA-P]"
	val DIGIT_B27 = "[0-9a-qA-Q]"
	val DIGIT_B28 = "[0-9a-rA-R]"
	val DIGIT_B29 = "[0-9a-sA-S]"
	val DIGIT_B30 = "[0-9a-tA-T]"
	val DIGIT_B31 = "[0-9a-uA-U]"
	val DIGIT_B32 = "[0-9a-vA-V]"
	val DIGIT_B33 = "[0-9a-wA-W]"
	val DIGIT_B34 = "[0-9a-xA-X]"
	val DIGIT_B35 = "[0-9a-yA-Y]"
	val DIGIT_B36 = "[0-9a-zA-Z]"
	val INTEGER = "${DIGIT}(${DIGIT})*"
	val DEC_FRACT = INTEGER + "\\." + INTEGER
	val DEC_FRACT_OR_INT = "(${DEC_FRACT}|${INTEGER})"
	val DECIMAL_REG_EXP = "[+-]?${DEC_FRACT_OR_INT}([eE][+-]?${INTEGER})?"
	val BASE1_REG_EXP = "[+-]?[bB]1\\.(${DIGIT_B1})*"
	val BASE2_REG_EXP = "[+-]?[bB][2]?\\.${DIGIT_B2}(${DIGIT_B2})*"
	val BASE3_REG_EXP = "[+-]?[bB]3\\.${DIGIT_B3}(${DIGIT_B3})*"
	val BASE4_REG_EXP = "[+-]?[bB]4\\.${DIGIT_B4}(${DIGIT_B4})*"
	val BASE5_REG_EXP = "[+-]?[bB]5\\.${DIGIT_B5}(${DIGIT_B5})*"
	val BASE6_REG_EXP = "[+-]?[bB]6\\.${DIGIT_B6}(${DIGIT_B6})*"
	val BASE7_REG_EXP = "[+-]?[bB]7\\.${DIGIT_B7}(${DIGIT_B7})*"
	val BASE8_REG_EXP = "[+-]?([bB]8|[oO])\\.${DIGIT_B8}(${DIGIT_B8})*"
	val BASE9_REG_EXP = "[+-]?[bB]9\\.${DIGIT_B9}(${DIGIT_B9})*"
	val BASE10_REG_EXP = "[+-]?[bB]10\\.${DIGIT_B10}(${DIGIT_B10})*"
	val BASE11_REG_EXP = "[+-]?[bB]11\\.${DIGIT_B11}(${DIGIT_B11})*"
	val BASE12_REG_EXP = "[+-]?[bB]12\\.${DIGIT_B12}(${DIGIT_B12})*"
	val BASE13_REG_EXP = "[+-]?[bB]13\\.${DIGIT_B13}(${DIGIT_B13})*"
	val BASE14_REG_EXP = "[+-]?[bB]14\\.${DIGIT_B14}(${DIGIT_B14})*"
	val BASE15_REG_EXP = "[+-]?[bB]15\\.${DIGIT_B15}(${DIGIT_B15})*"
	val BASE16_REG_EXP = "[+-]?([bB]16|[hH])\\.${DIGIT_B16}(${DIGIT_B16})*"
	val BASE17_REG_EXP = "[+-]?[bB]17\\.${DIGIT_B17}(${DIGIT_B17})*"
	val BASE18_REG_EXP = "[+-]?[bB]18\\.${DIGIT_B18}(${DIGIT_B18})*"
	val BASE19_REG_EXP = "[+-]?[bB]19\\.${DIGIT_B19}(${DIGIT_B19})*"
	val BASE20_REG_EXP = "[+-]?[bB]20\\.${DIGIT_B20}(${DIGIT_B20})*"
	val BASE21_REG_EXP = "[+-]?[bB]21\\.${DIGIT_B21}(${DIGIT_B21})*"
	val BASE22_REG_EXP = "[+-]?[bB]22\\.${DIGIT_B22}(${DIGIT_B22})*"
	val BASE23_REG_EXP = "[+-]?[bB]23\\.${DIGIT_B23}(${DIGIT_B23})*"
	val BASE24_REG_EXP = "[+-]?[bB]24\\.${DIGIT_B24}(${DIGIT_B24})*"
	val BASE25_REG_EXP = "[+-]?[bB]25\\.${DIGIT_B25}(${DIGIT_B25})*"
	val BASE26_REG_EXP = "[+-]?[bB]26\\.${DIGIT_B26}(${DIGIT_B26})*"
	val BASE27_REG_EXP = "[+-]?[bB]27\\.${DIGIT_B27}(${DIGIT_B27})*"
	val BASE28_REG_EXP = "[+-]?[bB]28\\.${DIGIT_B28}(${DIGIT_B28})*"
	val BASE29_REG_EXP = "[+-]?[bB]29\\.${DIGIT_B29}(${DIGIT_B29})*"
	val BASE30_REG_EXP = "[+-]?[bB]30\\.${DIGIT_B30}(${DIGIT_B30})*"
	val BASE31_REG_EXP = "[+-]?[bB]31\\.${DIGIT_B31}(${DIGIT_B31})*"
	val BASE32_REG_EXP = "[+-]?[bB]32\\.${DIGIT_B32}(${DIGIT_B32})*"
	val BASE33_REG_EXP = "[+-]?[bB]33\\.${DIGIT_B33}(${DIGIT_B33})*"
	val BASE34_REG_EXP = "[+-]?[bB]34\\.${DIGIT_B34}(${DIGIT_B34})*"
	val BASE35_REG_EXP = "[+-]?[bB]35\\.${DIGIT_B35}(${DIGIT_B35})*"
	val BASE36_REG_EXP = "[+-]?[bB]36\\.${DIGIT_B36}(${DIGIT_B36})*"
	val nameOnlyTokenRegExp = "([a-zA-Z_])+([a-zA-Z0-9_])*"
	val nameTokenRegExp = "(\\s)*${nameOnlyTokenRegExp}(\\s)*"
	val paramsTokenRegeExp = "(\\s)*\\((${nameTokenRegExp},(\\s)*)*${nameTokenRegExp}\\)(\\s)*"
	val constArgDefStrRegExp = "${nameTokenRegExp}=(\\s)*(.)+(\\s)*"
	val functionDefStrRegExp = "${nameTokenRegExp}${paramsTokenRegeExp}=(\\s)*(.)+(\\s)*"
	val function1ArgDefStrRegExp = "${nameTokenRegExp}(\\s)*\\(${nameTokenRegExp}(\\s)*\\)(\\s)*=(\\s)*(.)+(\\s)*"

	// ParserSymbol - token type id.
	val TYPE_ID = 20
	val TYPE_DESC = "Parser Symbol"

	// ParserSymbol - tokens id.
	val LEFT_PARENTHESES_ID = 1
	val RIGHT_PARENTHESES_ID = 2
	val COMMA_ID = 3
	val NUMBER_ID = 1
	val NUMBER_TYPE_ID = 0

	// ParserSymbol - tokens key words.
	val LEFT_PARENTHESES_STR = "("
	val RIGHT_PARENTHESES_STR = ")"
	val COMMA_STR = ","
	val SEMI_STR = ";"
	val NUMBER_STR = "_num_"

	// ParserSymbol - syntax.
	val LEFT_PARENTHESES_SYN = "( ... )"
	val RIGHT_PARENTHESES_SYN = "( ... )"
	val COMMA_SYN = "(a1, ... ,an)"
	val SEMI_SYN = "(a1; ... ;an)"
	val NUMBER_SYN = "1, -2, 001, +001.2e-10, b1.111, b2.1001, b3.12021, b16.af12, ..."

	// ParserSymbol - tokens description.
	val LEFT_PARENTHESES_DESC = "Left parentheses"
	val RIGHT_PARENTHESES_DESC = "Right parentheses"
	val COMMA_DESC = "Comma (function parameters)"
	val SEMI_DESC = "Semicolon (function parameters)"
	val NUMBER_DESC = "Decimal number"
	val NUMBER_REG_DESC = "Regullar expression for decimal numbers"

	// ParserSymbol - since.
	val LEFT_PARENTHESES_SINCE = MathParser.NAMEv10
	val RIGHT_PARENTHESES_SINCE = MathParser.NAMEv10
	val COMMA_SINCE = MathParser.NAMEv10
	val SEMI_SINCE = MathParser.NAMEv10
	val NUMBER_SINCE = MathParser.NAMEv10

}
