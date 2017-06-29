package com.basmilius.ps.bastools.framework.identity.installer

import com.basmilius.ps.bastools.resource.Icons
import com.intellij.ide.util.projectWizard.WebProjectTemplate
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

/**
 * Class IdentityInstallerWebProjectTemplate
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity.installer
 */
class IdentityInstallerWebProjectTemplate : WebProjectTemplate<IdentityInstallerSettings>()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun generateProject(project : Project, baseDir : VirtualFile, settings : IdentityInstallerSettings, module : Module)
	{
		// TODO: Actually generate the project.
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getDescription() : String
	{
		return "An Identity Framework WordPress project."
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getName() : String
	{
		return "Identity Framework"
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getIcon() : Icon
	{
		return Icons.Rhombus
	}

}