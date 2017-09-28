package com.basmilius.bastools.component

import com.basmilius.bastools.intention.ComputeConstantValueIntentionAction
import com.basmilius.bastools.ui.laf.BasToolsLaf
import com.intellij.codeInsight.intention.IntentionManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.JBColor
import com.intellij.util.PlatformUtils

import javax.swing.*

/**
 * Class BasToolsComponent
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.component
 */
class BasToolsComponent: ApplicationComponent
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun initComponent()
	{
		try
		{
			UIManager.setLookAndFeel(BasToolsLaf())
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
	 * @author Bas Milius
	 */
	override fun disposeComponent()
	{

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getComponentName() = "Bas Tools"

}
