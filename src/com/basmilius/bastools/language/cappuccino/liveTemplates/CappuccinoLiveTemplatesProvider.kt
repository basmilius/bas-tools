package com.basmilius.bastools.language.cappuccino.liveTemplates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

/**
 * Class CappuccinoLiveTemplatesProvider
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.language.cappuccino.liveTemplates
 */
class CappuccinoLiveTemplatesProvider: DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDefaultLiveTemplateFiles() = arrayOf("liveTemplates/Cappuccino")

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getHiddenLiveTemplateFiles(): Array<String?> = arrayOfNulls(0)

}
