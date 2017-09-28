package com.basmilius.bastools.component

import com.basmilius.bastools.component.basSettings.BasSettingsCodeStyleScheme
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.project.Project
import com.intellij.psi.codeStyle.CodeStyleSchemes
import com.intellij.psi.codeStyle.CodeStyleSettingsManager
import com.intellij.util.PlatformUtils
import java.io.IOException

/**
 * Class DefaultProjectComponent
 *
 * @param project Project
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.component
 */
class DefaultProjectComponent(private val project: Project): ProjectComponent
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
	override fun getComponentName() = "Bas Tools - Default Project"

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun projectOpened()
	{
		val workspace = this.project.workspaceFile

		if (PlatformUtils.isPhpStorm())
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
            CodeStyleSettingsManager.getInstance().setTemporarySettings(bsScheme.codeStyleSettings)
        }

		if (workspace == null)
			return

		val username = System.getProperty("user.name").toLowerCase()
		val workspacePerUserFilename = "workspace.$username.xml"
		val workspacePerUser = workspace.parent.findChild(workspacePerUserFilename)

		try
		{
			ApplicationManager.getApplication().runWriteAction {
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
		catch (e: IOException)
		{
			e.printStackTrace()
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun projectClosed()
	{
		val workspacePerUser = this.project.workspaceFile

		if (workspacePerUser === null)
			return

		val workspace = workspacePerUser.parent.findChild("workspace.xml")

		try
		{
			ApplicationManager.getApplication().runWriteAction {
				if (workspace != null && workspace.exists())
					workspace.delete(this)

				workspacePerUser.copy(this, workspacePerUser.parent, "workspace.xml")
			}
		}
		catch (e: IOException)
		{
			e.printStackTrace()
		}
	}

}
