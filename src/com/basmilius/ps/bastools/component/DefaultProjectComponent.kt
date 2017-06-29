package com.basmilius.ps.bastools.component

import com.basmilius.ps.bastools.component.basSettings.BasSettingsCodeStyleScheme
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.psi.codeStyle.CodeStyleSchemes
import com.intellij.psi.codeStyle.CodeStyleSettingsManager
import java.io.IOException

/**
 * Class DefaultProjectComponent
 *
 * @param project Project
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.component
 */
class DefaultProjectComponent(private val project : Project) : ProjectComponent
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun initComponent()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun disposeComponent()
	{
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getComponentName() : String
	{
		return "Bas Tools - Default Project"
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun projectOpened()
	{
		val workspace = this.project.workspaceFile

		val bsScheme = BasSettingsCodeStyleScheme()
		CodeStyleSchemes.getInstance().addScheme(bsScheme)
		CodeStyleSchemes.getInstance().currentScheme = bsScheme
		CodeStyleSettingsManager.getInstance().setTemporarySettings(bsScheme.codeStyleSettings)

		if (workspace == null)
			return

		val username = System.getProperty("user.name").toLowerCase()
		val workspacePerUserFilename = "workspace.$username.xml"
		val workspacePerUser = workspace.parent.findChild(workspacePerUserFilename)

		ApplicationManager.getApplication().runWriteAction {
			try
			{
				if (workspacePerUser != null && workspacePerUser.exists())
				{
					val workspacePerUserContents = workspacePerUser.contentsToByteArray()
					val workspacePerUserStream = workspace.getOutputStream(this)

					workspacePerUserStream.write(workspacePerUserContents)
					workspacePerUserStream.close()

					workspacePerUser.delete(this)
				}

				workspace.rename(this, workspacePerUserFilename)
				workspace.copy(this, workspace.parent, "workspace.xml")
			}
			catch (e : IOException)
			{
				e.printStackTrace()
			}
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun projectClosed()
	{
	}

}
