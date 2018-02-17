/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.all

import com.basmilius.bastools.framework.base.AbstractFramework
import com.basmilius.bastools.intention.ComputeConstantValueIntentionAction
import com.intellij.codeInsight.intention.IntentionManager
import com.intellij.openapi.project.Project
import com.intellij.util.PlatformUtils

/**
 * Class AllFramework
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all
 * @since 1.4.0
 */
class AllFramework: AbstractFramework("General Stuff")
{

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun onLoad()
	{
		if (PlatformUtils.isPhpStorm())
		{
			// TODO(Bas): Figure out if we can port this to an universal PSI thing.
			IntentionManager.getInstance().addAction(ComputeConstantValueIntentionAction())
		}
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun onUnload()
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun onProjectClosed(project: Project)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun onProjectOpened(project: Project)
	{
	}

}
