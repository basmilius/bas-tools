package com.basmilius.ps.bastools.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager

import javax.swing.*

object JUtils
{

	/**
	 * Gets the root component for the project.
	 *
	 * @param project Project.
	 *
	 * @return JComponent
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
