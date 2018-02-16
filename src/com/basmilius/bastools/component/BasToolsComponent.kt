/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component

import com.basmilius.bastools.intention.ComputeConstantValueIntentionAction
import com.basmilius.bastools.ui.laf.BasToolsLaf
import com.intellij.codeInsight.intention.IntentionManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.JBColor
import com.intellij.util.PlatformUtils
import javax.swing.UIManager

/**
 * Class BasToolsComponent
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component
 * @since 1.1.0
 */
class BasToolsComponent: ApplicationComponent
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun initComponent()
	{
		try
		{
			val laf = BasToolsLaf()
			laf.patchUI()

			UIManager.setLookAndFeel(laf)

			JBColor.setDark(true)
			IconLoader.setUseDarkIcons(true)
		}
		catch (e: Exception)
		{
			e.printStackTrace()
		}

		if (PlatformUtils.isPhpStorm())
		{
			IntentionManager.getInstance().addAction(ComputeConstantValueIntentionAction())
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun disposeComponent()
	{

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun getComponentName() = "Bas Tools"

}
