package com.basmilius.bastools.language.cappuccino

import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage

/**
 * Class CappuccinoColorsAndFontsPage
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoColorsAndFontsPage: ColorSettingsPage
{

	/**
	 * Companion Object CappuccinoColorsAndFontsPage
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.language.cappuccino
	 */
	companion object
	{

		val ATTRS = arrayOf(
				AttributesDescriptor("Bad Character", CappuccinoHighlighterData.BAD_CHARACTER),
				AttributesDescriptor("Brackets", CappuccinoHighlighterData.BRACKETS),
				AttributesDescriptor("Comment", CappuccinoHighlighterData.COMMENT),
				AttributesDescriptor("Identifier", CappuccinoHighlighterData.IDENTIFIER),
				AttributesDescriptor("Keyword", CappuccinoHighlighterData.KEYWORD),
				AttributesDescriptor("Number", CappuccinoHighlighterData.NUMBER),
				AttributesDescriptor("Operator", CappuccinoHighlighterData.OPERATION_SIGN),
				AttributesDescriptor("Cappuccino Code", CappuccinoHighlighterData.TEMPLATE),
				AttributesDescriptor("String", CappuccinoHighlighterData.STRING)
		)

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getAdditionalHighlightingTagToDescriptorMap() = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getAttributeDescriptors() = ATTRS

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getColorDescriptors(): Array<out ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDemoText() = "{# Comment #}\n" +
			"{% macro input(name, value, type, size) %}\n" +
			"    <input type=\"{{ type|default('text') }}\" name=\"{{ name }}\" value=\"{{ value|e }}\" size=\"{{ size|default(20) }}\" />\n" +
			"{% endmacro %}\n" +
			"{% if loop.index is not divisibleby(3) %}\n" +
			"    {% set foo = [1, {\"foo\": \"bar\"}] %}\n" +
			"{% endif %}"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDisplayName() = "Cappuccino"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getHighlighter() = CappuccinoHighlighter.CappuccinoFileHighlighter()

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getIcon() = null

}
