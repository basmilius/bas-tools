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

		val BAD_CHARACTER: IElementType = CappuccinoElementType("BAD_CHARACTER")
		val COMMENT: IElementType = CappuccinoElementType("COMMENT")
		val WHITE_SPACE: IElementType = CappuccinoElementType("WHITE_SPACE")
		val PRINT_BLOCK_START: IElementType = CappuccinoElementType("PRINT_BLOCK_START")
		val PRINT_BLOCK_END: IElementType = CappuccinoElementType("PRINT_BLOCK_END")
		val STATEMENT_BLOCK_START: IElementType = CappuccinoElementType("STATEMENT_BLOCK_START")
		val STATEMENT_BLOCK_END: IElementType = CappuccinoElementType("STATEMENT_BLOCK_END")
		val OR: IElementType = CappuccinoElementType("OR")
		val AND: IElementType = CappuccinoElementType("AND")
		val NOT: IElementType = CappuccinoElementType("NOT")
		val IN: IElementType = CappuccinoElementType("IN")
		val IS: IElementType = CappuccinoElementType("IS")
		val NOT_IN: IElementType = CappuccinoElementType("NOT_IN")
		val EQ: IElementType = CappuccinoElementType("EQ")
		val EQ_EQ: IElementType = CappuccinoElementType("EQ_EQ")
		val NOT_EQ: IElementType = CappuccinoElementType("NOT_EQ")
		val LT: IElementType = CappuccinoElementType("LT")
		val GT: IElementType = CappuccinoElementType("GT")
		val LE: IElementType = CappuccinoElementType("LE")
		val GE: IElementType = CappuccinoElementType("GE")
		val PLUS: IElementType = CappuccinoElementType("PLUS")
		val MINUS: IElementType = CappuccinoElementType("MINUS")
		val CONCAT: IElementType = CappuccinoElementType("CONCAT")
		val MUL: IElementType = CappuccinoElementType("MUL")
		val DIV: IElementType = CappuccinoElementType("DIV")
		val MOD: IElementType = CappuccinoElementType("MOD")
		val DIV_DIV: IElementType = CappuccinoElementType("DIV_DIV")
		val RANGE: IElementType = CappuccinoElementType("RANGE")
		val POW: IElementType = CappuccinoElementType("POW")
		val AS_KEYWORD: IElementType = CappuccinoElementType("AS_KEYWORD")
		val IMPORT_KEYWORD: IElementType = CappuccinoElementType("IMPORT_KEYWORD")
		val TAG_NAME: IElementType = CappuccinoElementType("TAG_NAME")
		val IDENTIFIER: IElementType = CappuccinoElementType("IDENTIFIER")
		val RESERVED_ID: IElementType = CappuccinoElementType("RESERVED_ID")
		val NUMBER: IElementType = CappuccinoElementType("NUMBER")
		val BOOLEAN: IElementType = CappuccinoElementType("BOOLEAN")
		val NONE: IElementType = CappuccinoElementType("NONE")
		val LBRACE_SQ: IElementType = CappuccinoElementType("LBRACE_SQ")
		val RBRACE_SQ: IElementType = CappuccinoElementType("RBRACE_SQ")
		val COLON: IElementType = CappuccinoElementType("COLON")
		val COMMA: IElementType = CappuccinoElementType("COMMA")
		val LBRACE: IElementType = CappuccinoElementType("LBRACE")
		val RBRACE: IElementType = CappuccinoElementType("RBRACE")
		val LBRACE_CURL: IElementType = CappuccinoElementType("LBRACE_CURL")
		val RBRACE_CURL: IElementType = CappuccinoElementType("RBRACE_CURL")
		val FILTER: IElementType = CappuccinoElementType("FILTER")
		val DOT: IElementType = CappuccinoElementType("DOT")
		val QUESTION: IElementType = CappuccinoElementType("QUESTION")
		val IF_KEYWORD: IElementType = CappuccinoElementType("IF_KEYWORD")
		val DOUBLE_QUOTE: IElementType = CappuccinoElementType("DOUBLE_QUOTE")
		val STRING_TEXT: IElementType = CappuccinoElementType("STRING_TEXT")
		val SINGLE_QUOTE: IElementType = CappuccinoElementType("SINGLE_QUOTE")
		val TEMPLATE_HTML_TEXT: IElementType = CappuccinoElementType("CAPPUCCINO_TEMPLATE_HTML_TEXT")
		val OUTER_ELEMENT_TYPE: IElementType = CappuccinoElementType("CAPPUCCINO_FRAGMENT")
		val VERBATIM_CONTENT: IElementType = CappuccinoElementType("VERBATIM_CONTENT")
		val STRING_LITERALS = TokenSet.create(SINGLE_QUOTE, DOUBLE_QUOTE, STRING_TEXT)
		val KEYWORDS = TokenSet.create(RESERVED_ID, BOOLEAN, NONE, IN, IS, NOT, AND, OR, NOT_IN, TAG_NAME, AS_KEYWORD, IMPORT_KEYWORD, IF_KEYWORD)
		val OPERATORS = TokenSet.create(EQ_EQ, NOT_EQ, LT, GT, LE, GE, PLUS, MINUS, CONCAT, MUL, DIV, MOD, DIV_DIV, RANGE, POW, DOT, QUESTION, COMMA, COLON, FILTER, EQ)
		val PARENTHS = TokenSet.create(LBRACE, RBRACE, LBRACE_SQ, RBRACE_SQ, PRINT_BLOCK_START, PRINT_BLOCK_END, STATEMENT_BLOCK_START, STATEMENT_BLOCK_END, LBRACE_CURL, RBRACE_CURL)

	}

}
