package com.basmilius.ps.bastools.framework.all.liveTemplates;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;

public class AllLiveTemplatesProvider implements DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String[] getDefaultLiveTemplateFiles ()
	{
		return new String[]{"liveTemplates/MissingPhpStuffFramework"};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String[] getHiddenLiveTemplateFiles ()
	{
		return new String[0];
	}

}
