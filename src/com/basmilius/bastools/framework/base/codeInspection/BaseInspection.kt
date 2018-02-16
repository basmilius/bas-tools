/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.base.codeInspection

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.jetbrains.php.lang.inspections.PhpInspection

/**
 * Class BaseInspection
 *
 * @constructor
 * @param shortName String
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.base.codeInspection
 * @since 1.1.0
 */
abstract class BaseInspection(shortName: String): PhpInspection()
{

	/**
	 * Gets or Sets the short name for this inspection.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private val inspectionShortName: String = shortName

	/**
	 * @inheritdoc
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
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
	 * @since 1.1.0
	 */
	open fun isValidFile(project: Project, file: PsiFile): Boolean
	{
		return true
	}

}
