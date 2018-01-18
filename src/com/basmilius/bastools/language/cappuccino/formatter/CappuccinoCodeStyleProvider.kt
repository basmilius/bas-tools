package com.basmilius.bastools.language.cappuccino.formatter

import com.basmilius.bastools.language.cappuccino.CappuccinoLanguage
import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider

/**
 * Class CappuccinoCodeStyleProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.formatter
 */
class CappuccinoCodeStyleProvider: CodeStyleSettingsProvider()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun createCustomSettings(settings: CodeStyleSettings) = CappuccinoFormatterOptions(settings)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun createSettingsPage(settings: CodeStyleSettings, originalSettings: CodeStyleSettings) = MyConfigurable(settings, originalSettings)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getLanguage() = CappuccinoLanguage.Instance

	/**
	 * Class MyConfigurable
	 *
	 * @constructor
	 * @param settings CodeStyleSettings
	 * @param cloneSettings CodeStyleSettings
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino.formatter.CappuccinoCodeStyleProvider
	 */
	inner class MyConfigurable(settings: CodeStyleSettings, cloneSettings: CodeStyleSettings): CodeStyleAbstractConfigurable(settings, cloneSettings, "Cappuccino")
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel
		{
			return object: TabbedLanguageCodeStylePanel(CappuccinoLanguage.Instance, this.currentSettings, settings)
			{

				/**
				 * {@inheritdoc}
				 *
				 * @author Bas Milius <bas@mili.us>
				 */
				override fun initTabs(settings: CodeStyleSettings)
				{
					this.addIndentOptionsTab(settings)
					// TODO(Bas): Add CappuccinoFormatterOptionsPanel and figure out what the UI is.
				}

			}
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		override fun getHelpTopic() = "reference.settingsdialog.codestyle.cappuccino"

	}

}
