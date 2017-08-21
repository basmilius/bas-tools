package com.basmilius.ps.bastools.framework.all.liveTemplates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

/**
 * Class AllLiveTemplatesProvider
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.all.liveTemplates
 */
class AllLiveTemplatesProvider: DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDefaultLiveTemplateFiles(): Array<String>
	{
		return arrayOf("liveTemplates/MissingPhpStuffFramework")
	}

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun getHiddenLiveTemplateFiles(): Array<String?>
	{
		return arrayOfNulls(0)
	}

}
