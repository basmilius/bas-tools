/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component

import com.basmilius.bastools.component.basSettings.BasSettingsCodeStyleScheme
import com.basmilius.bastools.core.util.ApplicationUtils
import com.basmilius.bastools.core.util.ExceptionUtils
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.editor.colors.impl.EditorColorsManagerImpl
import com.intellij.openapi.project.Project
import com.intellij.psi.codeStyle.CodeStyleSchemes

/**
 * Class DefaultProjectComponent
 *
 * @constructor
 * @param project Project
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component
 * @since 1.0.0
 */
class DefaultProjectComponent(private val project: Project): ProjectComponent
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun initComponent()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun disposeComponent()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getComponentName() = "Bas Tools"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun projectOpened()
	{
		this.applyColorSchemeSettings()
		this.applyCodeStyleSettings()
		this.applyWorkspacePerUser()

		ApplicationUtils.withComponent(BasToolsComponent::class) {
			it.withFrameworks {
				it.onProjectOpened(this.project)
			}
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun projectClosed()
	{
		this.unloadWorkspacePerUser()

		ApplicationUtils.withComponent(BasToolsComponent::class) {
			it.withFrameworks {
				it.onProjectClosed(this.project)
			}
		}
	}

	/**
	 * Applies Bas Settings color scheme.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun applyColorSchemeSettings()
	{
		EditorColorsManagerImpl.getInstance().globalScheme = EditorColorsManagerImpl.getInstance().getScheme("Bas Settings")
	}

	/**
	 * Applies Bas Settings code style.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun applyCodeStyleSettings()
	{
		try
		{
			val oldSettings = CodeStyleSchemes.getInstance().findPreferredScheme("Bas Settings")
			if (oldSettings.name === "Bas Settings" && !oldSettings.isDefault)
				CodeStyleSchemes.getInstance().deleteScheme(oldSettings)
		}
		catch (e: Exception)
		{
			e.printStackTrace()
		}

		val bsScheme = BasSettingsCodeStyleScheme()
		CodeStyleSchemes.getInstance().addScheme(bsScheme)
		CodeStyleSchemes.getInstance().currentScheme = bsScheme
		CodeStyleSchemes.getInstance().defaultScheme.codeStyleSettings.copyFrom(bsScheme.codeStyleSettings)
	}

	/**
	 * Applies workspace per user.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun applyWorkspacePerUser()
	{
		val workspace = this.project.workspaceFile ?: return

		val username = System.getProperty("user.name").toLowerCase()
		val workspacePerUserFilename = "workspace.$username.xml"
		val workspacePerUser = workspace.parent.findChild(workspacePerUserFilename)

		ExceptionUtils.dontCare {
			ApplicationUtils.runWriteAction {
				if (workspacePerUser != null && workspacePerUser.exists())
				{
					val contents = workspacePerUser.contentsToByteArray()
					val stream = workspace.getOutputStream(this)

					stream.write(contents)
					stream.close()

					workspacePerUser.delete(this)
				}

				workspace.rename(this, workspacePerUserFilename)
				workspace.copy(this, workspace.parent, "workspace.xml")
			}
		}
	}

	/**
	 * Unloads workspace per user.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	private fun unloadWorkspacePerUser()
	{
		val workspacePerUser = this.project.workspaceFile

		if (workspacePerUser === null)
			return

		val workspace = workspacePerUser.parent.findChild("workspace.xml")

		ExceptionUtils.dontCare {
			ApplicationUtils.runWriteAction {
				if (workspace != null && workspace.exists())
					workspace.delete(this)

				workspacePerUser.copy(this, workspacePerUser.parent, "workspace.xml")
			}
		}
	}

}
