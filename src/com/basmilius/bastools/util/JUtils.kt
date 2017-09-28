package com.basmilius.bastools.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager

import javax.swing.*

/**
 * Object JUtils
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.util
 */
object JUtils
{

	/**
	 * Gets the root component for the project.
	 *
	 * @param project Project.
	 *
	 * @return JComponent
	 *
	 * @author Bas Milius
	 */
	fun getRootComponent(project: Project?): JComponent?
	{
		if (project != null)
		{
			val frame = WindowManager.getInstance().getIdeFrame(project)

			if (frame != null)
			{
				return frame.component
			}
		}

		val frame = WindowManager.getInstance().findVisibleFrame()
		return frame?.rootPane
	}

}
