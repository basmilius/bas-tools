package com.basmilius.bastools.framework.all.liveTemplates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

/**
 * Class AllLiveTemplatesProvider
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.framework.all.liveTemplates
 */
class AllLiveTemplatesProvider: DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDefaultLiveTemplateFiles() = arrayOf("liveTemplates/MissingPhpStuffFramework")

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun getHiddenLiveTemplateFiles(): Array<String?> = arrayOfNulls(0)

}
