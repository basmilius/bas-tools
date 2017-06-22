package com.basmilius.ps.bastools.component.module

import com.basmilius.ps.bastools.resource.Icons
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import javax.swing.Icon

class IdentityFrameworkModule : ModuleType<IdentityFrameworkModuleBuilder>(ModuleTypeId)
{

	companion object
	{

		val ModuleTypeId = "BM_IDENTITY_FRAMEWORK_MODULE_TYPE"

		fun getInstance() : IdentityFrameworkModule
		{
			return ModuleTypeManager.getInstance().findByID(ModuleTypeId) as IdentityFrameworkModule
		}

	}

	override fun getNodeIcon(b : Boolean) : Icon
	{
		return Icons.Rhombus
	}

	override fun getBigIcon() : Icon
	{
		return Icons.Rhombus
	}

	override fun createModuleBuilder() : IdentityFrameworkModuleBuilder
	{
		return IdentityFrameworkModuleBuilder()
	}

	override fun getName() : String
	{
		return "Identity Framework"
	}

	override fun getDescription() : String
	{
		return "Creates a new project using Identity Framework by IdeeMedia. It's WordPress, but better."
	}

}
