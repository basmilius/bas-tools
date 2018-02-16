/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.identity.codeInspection

import com.basmilius.bastools.framework.base.codeInspection.simple.SimpleReplaceFunctionWithMethodInspector
import com.basmilius.bastools.framework.identity.IdentityFramework
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * Class WordPressHookSystemAddActionInspector
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity.codeInspection
 * @since 1.1.0
 */
class WordPressHookSystemAddActionInspector: SimpleReplaceFunctionWithMethodInspector("add_action", "Identity::action", "WordPressHookSystemAddActionInspector")
{

	/**
	 * @inheritdoc
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun isValidFile(project: Project, file: PsiFile): Boolean
	{
		// Check plugin dirs, but skip the main plugin file.
		if (IdentityFramework.isChildOf(project, file, "/wp-content/plugins/idty-"))
			if (file.containingDirectory.name == file.name)
				return false

		// Check if we're in themes.
		if (!IdentityFramework.isChildOf(project, file, "/wp-content/themes"))
			return false

		if (file.name.endsWith("Identity.php"))
			return false

		return true
	}

}
