package com.basmilius.ps.bastools.component.module.wizard

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import javax.swing.JComponent
import javax.swing.JLabel

class IdentityFrameworkModuleWizzardStep : ModuleWizardStep()
{

	override fun getComponent() : JComponent
	{
		return JLabel("Men At Work - Down Under.")
	}

	override fun updateDataModel()
	{
	}

}
