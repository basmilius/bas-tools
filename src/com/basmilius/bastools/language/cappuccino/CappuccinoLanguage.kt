package com.basmilius.bastools.language.cappuccino

import com.intellij.lang.Language
import com.intellij.psi.templateLanguages.TemplateLanguage

/**
 * Class CappuccinoLanguage
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino
 */
class CappuccinoLanguage: Language(LanguageId), TemplateLanguage
{

	/**
	 * Companion Object CappuccinoLanguage
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino
	 */
	companion object
	{

		val DefaultExtension = "cappy"
		val Description = "Cappuccino, the flexible, fast, and secure template language for PHP."
		val LanguageId = "Cappuccino"
		val LanguageName = "Cappuccino"
		val Instance = CappuccinoLanguage()

	}

}
