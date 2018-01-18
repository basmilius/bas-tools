package com.basmilius.bastools.framework.base.codeInspection

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.jetbrains.php.lang.inspections.PhpInspection

/**
 * Class BaseInspection
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.base.codeInspection
 */
abstract class BaseInspection(shortName: String): PhpInspection()
{

	/**
	 * Gets or Sets the short name for this inspection.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	private val inspectionShortName: String = shortName

	/**
	 * @inheritdoc
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getShortName(): String
	{
		return this.inspectionShortName
	}

	/**
	 * Checks if the file is qualified for this inspection.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	open fun isValidFile(project: Project, file: PsiFile): Boolean
	{
		return true
	}

}
