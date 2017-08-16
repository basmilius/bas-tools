package com.basmilius.ps.bastools.toolWindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import javax.swing.JComponent

/**
 * Class BaseToolWindowFactory
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.toolWindow
 */
abstract class BaseToolWindowFactory : ToolWindowFactory
{

	protected var project : Project? = null
	protected var toolWindow : ToolWindow? = null

	/**
	 * @inheritdoc
	 *
	 * @author Bas Milius
	 */
	override fun createToolWindowContent(project : Project, toolWindow : ToolWindow)
	{
		this.project = project
		this.toolWindow = toolWindow

		val component = this.createWindowContent()
		val content = this.getContentFactory().createContent(component, "", false)
		this.toolWindow!!.contentManager.addContent(content)
	}

	/**
	 * Creates the Window Content.
	 *
	 * @return JComponent
	 *
	 * @author Bas Milius
	 */
	protected abstract fun createWindowContent() : JComponent

	/**
	 * Gets the content factory.
	 *
	 * @return ContentFactory
	 *
	 * @author Bas Milius
	 */
	private fun getContentFactory() : ContentFactory = ContentFactory.SERVICE.getInstance()

}
