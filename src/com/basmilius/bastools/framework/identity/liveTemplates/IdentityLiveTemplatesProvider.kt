package com.basmilius.bastools.framework.identity.liveTemplates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

/**
 * Class IdentityLiveTemplatesProvider
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.framework.identity.liveTemplates
 */
class IdentityLiveTemplatesProvider: DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDefaultLiveTemplateFiles() = arrayOf("liveTemplates/IdentityFramework")

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun getHiddenLiveTemplateFiles(): Array<String?> = arrayOfNulls(0)

}
