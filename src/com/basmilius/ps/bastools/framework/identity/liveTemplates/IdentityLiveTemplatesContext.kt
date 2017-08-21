package com.basmilius.ps.bastools.framework.identity.liveTemplates

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile

/**
 * Class IdentityLiveTemplatesContext
 *
 * @constructor
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity.liveTemplates
 */
class IdentityLiveTemplatesContext: TemplateContextType("BASTOOLS.IDENTITY_FRAMEWORK", "Identity Framework")
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius
	 */
	override fun isInContext(psi: PsiFile, offset: Int): Boolean
	{
		return psi.name.endsWith(".php")
	}

}
