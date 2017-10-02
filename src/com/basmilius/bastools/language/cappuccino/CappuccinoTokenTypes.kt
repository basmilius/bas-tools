package com.basmilius.bastools.language.cappuccino

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

/**
 * Interface CappuccinoTokenTypes
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
interface CappuccinoTokenTypes
{

	/**
	 * Companion Object CappuccinoTokenTypes
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino
	 */
	companion object
	{

		val BAD_CHARACTER = CappuccinoElementType("BAD_CHARACTER")
		val COMMENT = CappuccinoElementType("COMMENT")
		val WHITE_SPACE = CappuccinoElementType("WHITE_SPACE")
		val PRINT_BLOCK_START = CappuccinoElementType("PRINT_BLOCK_START")
		val PRINT_BLOCK_END = CappuccinoElementType("PRINT_BLOCK_END")
		val STATEMENT_BLOCK_START = CappuccinoElementType("STATEMENT_BLOCK_START")
		val STATEMENT_BLOCK_END = CappuccinoElementType("STATEMENT_BLOCK_END")
		val BITWISE_OR = CappuccinoElementType("BITWISE_OR")
		val BITWISE_XOR = CappuccinoElementType("BITWISE_XOR")
		val BITWISE_AND = CappuccinoElementType("BITWISE_AND")
		val OR = CappuccinoElementType("OR")
		val AND = CappuccinoElementType("AND")
		val NOT = CappuccinoElementType("NOT")
		val IN = CappuccinoElementType("IN")
		val IS = CappuccinoElementType("IS")
		val NOT_IN = CappuccinoElementType("NOT_IN")
		val EQ = CappuccinoElementType("EQ")
		val EQ_EQ = CappuccinoElementType("EQ_EQ")
		val NOT_EQ = CappuccinoElementType("NOT_EQ")
		val LT = CappuccinoElementType("LT")
		val GT = CappuccinoElementType("GT")
		val LE = CappuccinoElementType("LE")
		val GE = CappuccinoElementType("GE")
		val PLUS = CappuccinoElementType("PLUS")
		val MINUS = CappuccinoElementType("MINUS")
		val CONCAT = CappuccinoElementType("CONCAT")
		val MUL = CappuccinoElementType("MUL")
		val DIV = CappuccinoElementType("DIV")
		val MOD = CappuccinoElementType("MOD")
		val DIV_DIV = CappuccinoElementType("DIV_DIV")
		val RANGE = CappuccinoElementType("RANGE")
		val POW = CappuccinoElementType("POW")
		val AS_KEYWORD = CappuccinoElementType("AS_KEYWORD")
		val IMPORT_KEYWORD = CappuccinoElementType("IMPORT_KEYWORD")
		val MATCHES = CappuccinoElementType("MATCHES")
		val STARTS_WITH = CappuccinoElementType("STARTS_WITH")
		val ENDS_WITH = CappuccinoElementType("ENDS_WITH")
		val TAG_NAME = CappuccinoElementType("TAG_NAME")
		val IDENTIFIER = CappuccinoElementType("IDENTIFIER")
		val RESERVED_ID = CappuccinoElementType("RESERVED_ID")
		val NUMBER = CappuccinoElementType("NUMBER")
		val BOOLEAN = CappuccinoElementType("BOOLEAN")
		val NONE = CappuccinoElementType("NONE")
		val LBRACE_SQ = CappuccinoElementType("LBRACE_SQ")
		val RBRACE_SQ = CappuccinoElementType("RBRACE_SQ")
		val COLON = CappuccinoElementType("COLON")
		val COMMA = CappuccinoElementType("COMMA")
		val LBRACE = CappuccinoElementType("LBRACE")
		val RBRACE = CappuccinoElementType("RBRACE")
		val LBRACE_CURL = CappuccinoElementType("LBRACE_CURL")
		val RBRACE_CURL = CappuccinoElementType("RBRACE_CURL")
		val FILTER = CappuccinoElementType("FILTER")
		val DOT = CappuccinoElementType("DOT")
		val QUESTION = CappuccinoElementType("QUESTION")
		val COALESCE = CappuccinoElementType("COALESCE")
		val IF_KEYWORD = CappuccinoElementType("IF_KEYWORD")
		val DOUBLE_QUOTE = CappuccinoElementType("DOUBLE_QUOTE")
		val STRING_TEXT = CappuccinoElementType("STRING_TEXT")
		val SINGLE_QUOTE = CappuccinoElementType("SINGLE_QUOTE")
		val TEMPLATE_HTML_TEXT = CappuccinoElementType("CAPPUCCINO_TEMPLATE_HTML_TEXT")
		val OUTER_ELEMENT_TYPE = CappuccinoElementType("CAPPUCCINO_FRAGMENT")
		val VERBATIM_CONTENT = CappuccinoElementType("VERBATIM_CONTENT")
		val STRING_LITERALS = TokenSet.create(SINGLE_QUOTE, DOUBLE_QUOTE, STRING_TEXT)
		val KEYWORDS = TokenSet.create(BITWISE_OR, BITWISE_XOR, BITWISE_AND, MATCHES, STARTS_WITH, ENDS_WITH, RESERVED_ID, BOOLEAN, NONE, IN, IS, NOT, NOT_IN, TAG_NAME, AS_KEYWORD, IMPORT_KEYWORD, IF_KEYWORD)
		val OPERATORS = TokenSet.create(COALESCE, EQ_EQ, NOT_EQ, LT, GT, LE, GE, PLUS, MINUS, CONCAT, MUL, DIV, MOD, DIV_DIV, RANGE, POW, AND, OR, DOT, QUESTION, COMMA, COLON, FILTER, EQ)
		val PARENTHS = TokenSet.create(LBRACE, RBRACE, LBRACE_SQ, RBRACE_SQ, PRINT_BLOCK_START, PRINT_BLOCK_END, STATEMENT_BLOCK_START, STATEMENT_BLOCK_END, LBRACE_CURL, RBRACE_CURL)

	}

}
