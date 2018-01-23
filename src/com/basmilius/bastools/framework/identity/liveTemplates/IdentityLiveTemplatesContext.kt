package com.basmilius.bastools.framework.identity.liveTemplates

import com.basmilius.bastools.framework.identity.IdentityFramework
import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiFile

/**
 * Class IdentityLiveTemplatesContext
 *
 * @constructor
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity.liveTemplates
 * @since 1.0.0
 */
class IdentityLiveTemplatesContext: TemplateContextType("BASTOOLS.IDENTITY_FRAMEWORK", "Identity Framework")
{

	/**
	 * {@inheritDoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun isInContext(psi: PsiFile, offset: Int) = IdentityFramework.isIdentityFrameworkProject(psi.project) && psi.name.endsWith(".php")

}
