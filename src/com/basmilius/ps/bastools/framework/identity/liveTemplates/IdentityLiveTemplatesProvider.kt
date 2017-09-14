package com.basmilius.ps.bastools.framework.identity.liveTemplates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider
import com.intellij.util.PlatformUtils

/**
 * Class IdentityLiveTemplatesProvider
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity.liveTemplates
 */
class IdentityLiveTemplatesProvider: DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDefaultLiveTemplateFiles(): Array<String>
	{
		if (PlatformUtils.isPhpStorm())
			return arrayOf("liveTemplates/IdentityFramework")

		return arrayOf()
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
