package com.basmilius.ps.bastools.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;

import javax.swing.*;

public final class JUtils
{

	/**
	 * Gets the root component for the project.
	 *
	 * @param project Project.
	 *
	 * @return JComponent
	 */
	public static JComponent getRootComponent (final Project project)
	{
		if (project != null)
		{
			final IdeFrame frame = WindowManager.getInstance().getIdeFrame(project);

			if (frame != null)
			{
				return frame.getComponent();
			}
		}

		final JFrame frame = WindowManager.getInstance().findVisibleFrame();
		return frame != null ? frame.getRootPane() : null;
	}

}
