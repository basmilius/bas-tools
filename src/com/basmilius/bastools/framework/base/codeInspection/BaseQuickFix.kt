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
 */
abstract class BaseQuickFix(private val fixName: String): LocalQuickFix
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getFamilyName(): String
	{
		return this.fixName
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getName(): String
	{
		return this.fixName
	}

}
