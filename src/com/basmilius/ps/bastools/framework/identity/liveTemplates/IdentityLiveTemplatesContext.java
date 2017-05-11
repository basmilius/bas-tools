package com.basmilius.ps.bastools.framework.identity.liveTemplates;

import com.basmilius.ps.bastools.framework.identity.IdentityFramework;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class IdentityLiveTemplatesContext extends TemplateContextType
{

	/**
	 * IdentityLiveTemplatesContext Constructor.
	 */
	public IdentityLiveTemplatesContext ()
	{
		super("BASTOOLS.IDENTITY_FRAMEWORK", "Identity Framework");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInContext (@NotNull final PsiFile psi, final int offset)
	{
		return psi.getName().endsWith(".php") && IdentityFramework.isIdentityFrameworkProject(psi.getProject());
	}

}
