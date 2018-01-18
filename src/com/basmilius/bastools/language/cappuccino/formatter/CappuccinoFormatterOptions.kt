package com.basmilius.bastools.language.cappuccino.formatter

import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CustomCodeStyleSettings

/**
 * Class CappuccinoFormatterOptions
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.formatter
 */
class CappuccinoFormatterOptions(container: CodeStyleSettings): CustomCodeStyleSettings("Cappuccino", container)
{

	val SPACES_INSIDE_DELIMITERS = true
	val SPACES_INSIDE_VARIABLE_DELIMITERS = true

}
