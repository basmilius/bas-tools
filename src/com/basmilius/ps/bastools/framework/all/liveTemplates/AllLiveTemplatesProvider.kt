package com.basmilius.ps.bastools.framework.all.liveTemplates

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

class AllLiveTemplatesProvider : DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 */
	override fun getDefaultLiveTemplateFiles(): Array<String>
	{
		return arrayOf("liveTemplates/MissingPhpStuffFramework")
	}

	/**
	 * {@inheritDoc}
	 */
	override fun getHiddenLiveTemplateFiles(): Array<String?>
	{
		return arrayOfNulls(0)
	}

}
