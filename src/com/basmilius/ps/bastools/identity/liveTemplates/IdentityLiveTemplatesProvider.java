package com.basmilius.ps.bastools.identity.liveTemplates;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.Nullable;

public class IdentityLiveTemplatesProvider implements DefaultLiveTemplatesProvider
{

	@Override
	public String[] getDefaultLiveTemplateFiles ()
	{
		return new String[] { "liveTemplates/IdentityFramework" };
	}

	@Nullable
	@Override
	public String[] getHiddenLiveTemplateFiles ()
	{
		return new String[0];
	}

}
