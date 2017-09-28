package com.basmilius.bastools.language.cappuccino

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey

/**
 * Object CappuccinoHighlighterData
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
object CappuccinoHighlighterData
{

	val BAD_CHARACTER_ID = "CAPPUCCINO_BAD_CHARACTER"
	val COMMENT_ID = "CAPPUCCINO_COMMENT"
	val KEYWORD_ID = "CAPPUCCINO_KEYWORD"
	val NUMBER_ID = "CAPPUCCINO_NUMBER"
	val STRING_ID = "CAPPUCCINO_STRING"
	val OPERATION_SIGN_ID = "CAPPUCCINO_OPERATION_SIGN"
	val BRACKETS_ID = "CAPPUCCINO_BRACKETS"
	val IDENTIFIER_ID = "CAPPUCCINO_IDENTIFIER"
	val TEMPLATE_ID = "CAPPUCCINO_TEMPLATE"

	val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(BAD_CHARACTER_ID, HighlighterColors.BAD_CHARACTER)
	val COMMENT = TextAttributesKey.createTextAttributesKey(COMMENT_ID, DefaultLanguageHighlighterColors.LINE_COMMENT)
	val KEYWORD = TextAttributesKey.createTextAttributesKey(KEYWORD_ID, DefaultLanguageHighlighterColors.KEYWORD)
	val NUMBER = TextAttributesKey.createTextAttributesKey(NUMBER_ID, DefaultLanguageHighlighterColors.NUMBER)
	val STRING = TextAttributesKey.createTextAttributesKey(STRING_ID, DefaultLanguageHighlighterColors.STRING)
	val OPERATION_SIGN = TextAttributesKey.createTextAttributesKey(OPERATION_SIGN_ID, DefaultLanguageHighlighterColors.OPERATION_SIGN)
	val BRACKETS = TextAttributesKey.createTextAttributesKey(BRACKETS_ID, DefaultLanguageHighlighterColors.BRACKETS)
	val IDENTIFIER = TextAttributesKey.createTextAttributesKey(IDENTIFIER_ID, DefaultLanguageHighlighterColors.IDENTIFIER)
	val TEMPLATE = TextAttributesKey.createTextAttributesKey(TEMPLATE_ID, DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR)

}
