package com.basmilius.ps.bastools.framework.identity.liveTemplates;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;

public class IdentityLiveTemplatesProvider implements DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String[] getDefaultLiveTemplateFiles ()
	{
		return new String[] { "liveTemplates/IdentityFramework" };
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