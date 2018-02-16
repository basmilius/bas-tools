/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

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
