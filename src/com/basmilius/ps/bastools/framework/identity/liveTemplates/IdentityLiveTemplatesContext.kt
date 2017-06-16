package com.basmilius.ps.bastools.framework.identity.liveTemplates

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile

/**
 * IdentityLiveTemplatesContext Constructor.
 */
class IdentityLiveTemplatesContext : TemplateContextType("BASTOOLS.IDENTITY_FRAMEWORK", "Identity Framework")
{

	/**
	 * {@inheritDoc}
	 */
	override fun isInContext(psi: PsiFile, offset: Int): Boolean
	{
		return psi.name.endsWith(".php")
	}

}
