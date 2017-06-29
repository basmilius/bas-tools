package com.basmilius.ps.bastools.component

import com.basmilius.ps.bastools.ui.laf.BasToolsLaf
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.JBColor

import javax.swing.*

/**
 * Class BasToolsComponent
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.component
 */
class BasToolsComponent : ApplicationComponent
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
		catch (e : Exception)
		{
			e.printStackTrace()
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
	override fun getComponentName() : String
	{
		return "Bas Tools"
	}

}
