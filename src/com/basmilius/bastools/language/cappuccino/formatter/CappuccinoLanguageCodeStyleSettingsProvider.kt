package com.basmilius.bastools.language.cappuccino.formatter

import com.basmilius.bastools.language.cappuccino.CappuccinoLanguage
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider

/**
 * Class CappuccinoLanguageCodeStyleSettingsProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.formatter
 */
class CappuccinoLanguageCodeStyleSettingsProvider: LanguageCodeStyleSettingsProvider()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType)
	{
		if (settingsType == SettingsType.WRAPPING_AND_BRACES_SETTINGS)
		{
			consumer.showStandardOptions("RIGHT_MARGIN")
			consumer.showStandardOptions("WRAP_ON_TYPING")
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getCodeSample(p0: SettingsType) = "<!DOCTYPE html>\n<html>\n<body>\n<div id=\"content\">{% block content %}{% endblock %}</div>\n<div id=\"footer\">\n\t{% block footer %}\n\t\t&copy; Copyright 2011 by <a\n\t\t\thref=\"http://domain.invalid/\">you</a>.\n\t{% endblock %}\n</div>\n<p>{{ textarea('comment') }}</p>\n</body>\n</html>"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getDefaultCommonSettings(): CommonCodeStyleSettings
	{
		val settings = CommonCodeStyleSettings(this.language)
		settings.initIndentOptions()

		return settings
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getIndentOptionsEditor() = SmartIndentOptionsEditor()

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getLanguage() = CappuccinoLanguage.Instance

}
