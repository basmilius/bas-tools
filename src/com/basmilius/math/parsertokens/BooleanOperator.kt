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
 * Object BooleanOperator
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.math.parsertokens
 */
object BooleanOperator
{

	// BooleanOperator - token type id.
	val TYPE_ID = 2
	val TYPE_DESC = "Boolean Operator"

	// BooleanOperator - tokens id.
	val AND_ID = 1
	val NAND_ID = 2
	val OR_ID = 3
	val NOR_ID = 4
	val XOR_ID = 5
	val IMP_ID = 6
	val CIMP_ID = 7
	val NIMP_ID = 8
	val CNIMP_ID = 9
	val EQV_ID = 10
	val NEG_ID = 11

	// BooleanOperator - tokens key words.
	val NEG_STR = "~"
	val AND_STR = "&"
	val AND1_STR = "&&"
	val AND2_STR = "/\\"
	val NAND_STR = NEG_STR + AND_STR
	val NAND1_STR = NEG_STR + AND1_STR
	val NAND2_STR = NEG_STR + AND2_STR
	val OR_STR = "|"
	val OR1_STR = "||"
	val OR2_STR = "\\/"
	val NOR_STR = NEG_STR + OR_STR
	val NOR1_STR = NEG_STR + OR1_STR
	val NOR2_STR = NEG_STR + OR2_STR
	val XOR_STR = "(+)"
	val IMP_STR = "-->"
	val CIMP_STR = "<--"
	val NIMP_STR = "-/>"
	val CNIMP_STR = "</-"
	val EQV_STR = "<->"

	// BooleanOperator - syntax.
	val NEG_SYN = "~p"
	val AND_SYN = "p & q"
	val AND1_SYN = "p && q"
	val AND2_SYN = "p /\\ q"
	val NAND_SYN = "p ${NEG_STR}${AND_STR} q"
	val NAND1_SYN = "p ${NEG_STR}${AND1_STR} q"
	val NAND2_SYN = "p ${NEG_STR}${AND2_STR} q"
	val OR_SYN = "p | q"
	val OR1_SYN = "p || q"
	val OR2_SYN = "p \\/ q"
	val NOR_SYN = "p ${NEG_STR}${OR_STR} q"
	val NOR1_SYN = "p ${NEG_STR}${OR1_STR} q"
	val NOR2_SYN = "p ${NEG_STR}${OR2_STR} q"
	val XOR_SYN = "p (+) q"
	val IMP_SYN = "p --> q"
	val CIMP_SYN = "p <-- q"
	val NIMP_SYN = "p  -/> q"
	val CNIMP_SYN = "p </- q"
	val EQV_SYN = "p <-> q"

	// BooleanOperator - tokens description.
	val AND_DESC = "Logical conjunction (AND)"
	val OR_DESC = "Logical disjunction (OR)"
	val NEG_DESC = "Negation"
	val NAND_DESC = "NAND - Sheffer stroke"
	val NOR_DESC = "Logical NOR"
	val XOR_DESC = "Exclusive or (XOR)"
	val IMP_DESC = "Implication (IMP)"
	val CIMP_DESC = "Converse implication (CIMP)"
	val NIMP_DESC = "Material nonimplication (NIMP)"
	val CNIMP_DESC = "Converse nonimplication (CNIMP)"
	val EQV_DESC = "Logical biconditional (EQV)"

	// BooleanOperator - since.
	val AND_SINCE = MathParser.NAMEv10
	val OR_SINCE = MathParser.NAMEv10
	val NEG_SINCE = MathParser.NAMEv10
	val NAND_SINCE = MathParser.NAMEv10
	val NOR_SINCE = MathParser.NAMEv10
	val XOR_SINCE = MathParser.NAMEv10
	val IMP_SINCE = MathParser.NAMEv10
	val CIMP_SINCE = MathParser.NAMEv10
	val NIMP_SINCE = MathParser.NAMEv10
	val CNIMP_SINCE = MathParser.NAMEv10
	val EQV_SINCE = MathParser.NAMEv10

}
