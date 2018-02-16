/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.base.codeInspection

import com.intellij.codeInspection.LocalQuickFix

/**
 * Class BaseQuickFix
 *
 * @constructor
 * @param fixName String
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.base.codeInspection
 * @since 1.1.0
 */
abstract class BaseQuickFix(private val fixName: String): LocalQuickFix
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun getFamilyName(): String
	{
		return this.fixName
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun getName(): String
	{
		return this.fixName
	}

}
