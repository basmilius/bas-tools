package com.basmilius.bastools.framework.all.liveTemplates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

/**
 * Class AllLiveTemplatesProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.liveTemplates
 * @since 1.1.0
 */
class AllLiveTemplatesProvider: DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun getDefaultLiveTemplateFiles() = arrayOf("liveTemplates/MissingPhpStuffFramework")

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun getHiddenLiveTemplateFiles(): Array<String?> = arrayOfNulls(0)

}
