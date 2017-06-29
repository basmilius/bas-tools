package com.basmilius.ps.bastools.framework.identity.installer

import com.basmilius.ps.bastools.resource.Icons
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.platform.ProjectTemplate
import com.intellij.platform.ProjectTemplatesFactory
import javax.swing.Icon

class IdentityInstallerTemplatesFactory : ProjectTemplatesFactory()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getGroups() : Array<String>
	{
		return Array(1, { _ -> "PHP" })
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getGroupIcon(p0 : String?) : Icon
	{
		return Icons.Rhombus
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun createTemplates(s : String?, w : WizardContext?) : Array<ProjectTemplate>
	{
		return arrayOf(IdentityInstallerWebProjectTemplate())
	}

}
