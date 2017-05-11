package com.basmilius.ps.bastools.framework.identity.liveTemplates;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.Nullable;

public class IdentityLiveTemplatesProvider implements DefaultLiveTemplatesProvider
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getDefaultLiveTemplateFiles ()
	{
		return new String[] { "liveTemplates/IdentityFramework" };
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public String[] getHiddenLiveTemplateFiles ()
	{
		return new String[0];
	}

}
