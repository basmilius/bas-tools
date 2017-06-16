package com.basmilius.ps.bastools.core.config

import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SearchableConfigurable
import org.jetbrains.annotations.Nls

import javax.swing.*

class BasToolsConfigurable : SearchableConfigurable
{

	private var gui: BasToolsConfigurableGUI? = null

	@Nls
	override fun getDisplayName(): String
	{
		return "Bas Tools Preferences"
	}

	override fun getHelpTopic(): String
	{
		return "preference.BasTools"
	}

	override fun getId(): String
	{
		return "preference.BasTools"
	}

	override fun enableSearch(searchQuery: String?): Runnable?
	{
		return null
	}

	override fun createComponent(): JComponent?
	{
		this.gui = BasToolsConfigurableGUI()
		return this.gui!!.rootPanel
	}

	override fun isModified(): Boolean
	{
		return false
	}

	@Throws(ConfigurationException::class)
	override fun apply()
	{
	}

	override fun reset()
	{
	}

	override fun disposeUIResources()
	{
		this.gui = null
	}

}
