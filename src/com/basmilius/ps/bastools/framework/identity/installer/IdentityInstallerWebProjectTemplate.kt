package com.basmilius.ps.bastools.framework.identity.installer

import com.basmilius.ps.bastools.resource.Icons
import com.intellij.ide.util.projectWizard.WebProjectTemplate
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class IdentityInstallerWebProjectTemplate : WebProjectTemplate<IdentityInstallerSettings>()
{

	/**
	 * {@inheritdoc}
	 */
	override fun generateProject(project : Project, baseDir : VirtualFile, settings : IdentityInstallerSettings, module : Module)
	{
		// TODO: Actually generate the project.
	}

	/**
	 * {@inheritdoc}
	 */
	override fun getDescription() : String
	{
		return "An Identity Framework WordPress project."
	}

	/**
	 * {@inheritdoc}
	 */
	override fun getName() : String
	{
		return "Identity Framework"
	}

	/**
	 * {@inheritdoc}
	 */
	override fun getIcon() : Icon
	{
		return Icons.Rhombus
	}

}