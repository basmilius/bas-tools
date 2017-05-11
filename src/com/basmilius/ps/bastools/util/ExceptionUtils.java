package com.basmilius.ps.bastools.util;

import com.basmilius.ps.bastools.util.iid.ExceptionRunnable;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class ExceptionUtils
{

	/**
	 * Runs a Runnable and ignores the exception.
	 *
	 * @param runnable Runnable to run.
	 */
	public static void executeIgnore (final ExceptionRunnable runnable)
	{
		ExceptionUtils.executeIgnore(runnable, 0);
	}

	/**
	 * Runs a Runnable and ignores the exception.
	 *
	 * @param runnable Runnable to run.
	 * @param delay    Delay time in milliseconds.
	 */
	public static void executeIgnore (final ExceptionRunnable runnable, final int delay)
	{
		ProgressManager.getInstance().run(new Task.Backgroundable(null, "Bas Tools Process")
		{

			@Override
			public void run (@NotNull final ProgressIndicator progressIndicator)
			{
				try
				{
					if (delay > 0)
					{
						TimeUnit.MILLISECONDS.sleep(delay);
					}

					runnable.run();
				}
				catch (Exception e)
				{
					if (ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp"))
					{
						e.printStackTrace();
					}
				}
			}

		});
	}

}
