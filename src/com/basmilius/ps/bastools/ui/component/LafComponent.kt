package com.basmilius.ps.bastools.ui.component

import com.basmilius.ps.bastools.ui.painter.ButtonPainter
import com.intellij.ide.ui.LafManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.ui.components.JBPanel
import javax.swing.UIManager

class LafComponent(lafManager : LafManager) : JBPanel<JBPanel<*>>(), ApplicationComponent
{

	init
	{
		lafManager.addLafManagerListener { this.installBasToolsComponents() }
	}

	override fun initComponent()
	{
		this.installBasToolsComponents()
	}

	override fun getComponentName() : String
	{
		return this.javaClass.name
	}

	private fun installBasToolsComponents()
	{
		this.replaceButtonUI()
	}

	private fun replaceButtonUI ()
	{
		UIManager.put("ButtonUI", ButtonComponent::class.qualifiedName)
		UIManager.getDefaults().put(ButtonComponent::class.qualifiedName, ButtonComponent::class.java)

		UIManager.put("Button.border", ButtonPainter())

		println("Did do replaceButtonUI")
	}

}
