package com.basmilius.ps.bastools.component

import com.basmilius.ps.bastools.ui.laf.BasToolsLaf
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.JBColor

import javax.swing.*

class BasToolsComponent : ApplicationComponent
{

	/**
	 * {@inheritdoc}
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

	}

	/**
	 * {@inheritdoc}
	 */
	override fun disposeComponent()
	{

	}

	/**
	 * {@inheritdoc}
	 */
	override fun getComponentName(): String
	{
		return "Bas Tools"
	}

}
