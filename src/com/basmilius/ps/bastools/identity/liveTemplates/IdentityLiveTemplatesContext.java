package com.basmilius.ps.bastools.identity.liveTemplates;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class IdentityLiveTemplatesContext extends TemplateContextType
{

	protected IdentityLiveTemplatesContext ()
	{
		super("BASTOOLS.IDENTITY_FRAMEWORK", "Identity Framework");
	}

	@Override
	public boolean isInContext (@NotNull final PsiFile file, final int offset)
	{
		return file.getName().endsWith(".php");
	}

}
