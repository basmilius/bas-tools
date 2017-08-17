package com.basmilius.ps.bastools.framework.base.codeInspection

import com.intellij.codeInspection.LocalQuickFix

/**
 * Class BaseQuickFix
 *
 * @constructor
 * @param fixName String
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.base.codeInspection
 */
abstract class BaseQuickFix(private val fixName : String) : LocalQuickFix
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getFamilyName() : String
	{
		return this.fixName
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getName() : String
	{
		return this.fixName
	}

}
