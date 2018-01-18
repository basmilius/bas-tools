package com.basmilius.bastools.framework.identity.liveTemplates

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile

/**
 * Class IdentityLiveTemplatesContext
 *
 * @constructor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity.liveTemplates
 */
class IdentityLiveTemplatesContext: TemplateContextType("BASTOOLS.IDENTITY_FRAMEWORK", "Identity Framework")
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isInContext(psi: PsiFile, offset: Int) = psi.name.endsWith(".php")

}
