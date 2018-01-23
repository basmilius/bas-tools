package com.basmilius.bastools.framework.identity.liveTemplates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

/**
 * Class IdentityLiveTemplatesProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity.liveTemplates
 * @since 1.0.0
 */
class IdentityLiveTemplatesProvider: DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getDefaultLiveTemplateFiles() = arrayOf("liveTemplates/IdentityFramework")

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getHiddenLiveTemplateFiles(): Array<String?> = arrayOfNulls(0)

}
