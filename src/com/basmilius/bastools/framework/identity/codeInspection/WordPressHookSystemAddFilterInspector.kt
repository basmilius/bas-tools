package com.basmilius.bastools.framework.identity.codeInspection

import com.basmilius.bastools.framework.base.codeInspection.simple.SimpleReplaceFunctionWithMethodInspector
import com.basmilius.bastools.framework.identity.IdentityFramework
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * Class WordPressHookSystemAddFilterInspector
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity.codeInspection
 */
class WordPressHookSystemAddFilterInspector: SimpleReplaceFunctionWithMethodInspector("add_filter", "Identity::filter", "WordPressHookSystemAddFilterInspector")
{

	/**
	 * @inheritdoc
	 *
	 * @author Bas Milius <bas@mili.us>
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
