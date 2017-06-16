package com.basmilius.ps.bastools.framework.identity.liveTemplates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

class IdentityLiveTemplatesProvider : DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 */
	override fun getDefaultLiveTemplateFiles(): Array<String>
	{
		return arrayOf("liveTemplates/IdentityFramework")
	}

	/**
	 * {@inheritDoc}
	 */
	override fun getHiddenLiveTemplateFiles(): Array<String?>
	{
		return arrayOfNulls(0)
	}

}
