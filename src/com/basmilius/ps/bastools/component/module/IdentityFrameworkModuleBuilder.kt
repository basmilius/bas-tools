package com.basmilius.ps.bastools.component.module

import com.basmilius.ps.bastools.component.module.wizard.IdentityFrameworkModuleWizzardStep
import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel

class IdentityFrameworkModuleBuilder : ModuleBuilder()
{

	override fun getModuleType() : ModuleType<*>
	{
		return IdentityFrameworkModule.getInstance()
	}

	override fun setupRootModel(model : ModifiableRootModel?)
	{
	}

	override fun getCustomOptionsStep(context : WizardContext?, parentDisposable : Disposable?) : ModuleWizardStep?
	{
		return IdentityFrameworkModuleWizzardStep()
	}
}
