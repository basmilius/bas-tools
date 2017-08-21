package com.basmilius.ps.bastools.framework.identity.codeInspection

import com.basmilius.ps.bastools.framework.base.codeInspection.simple.SimpleReplaceFunctionWithMethodInspector
import com.basmilius.ps.bastools.framework.identity.IdentityFramework
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * Class WordPressHookSystemApplyFiltersInspector
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity.codeInspection
 */
class WordPressHookSystemApplyFiltersInspector: SimpleReplaceFunctionWithMethodInspector("apply_filters", "Identity::filterApply", "WordPressHookSystemApplyFiltersInspector")
{

	/**
	 * @inheritdoc
	 *
	 * @author Bas Milius
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
