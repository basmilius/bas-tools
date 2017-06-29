package com.basmilius.ps.bastools.util

import com.basmilius.ps.bastools.util.iid.ExceptionRunnable
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task

import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit

/**
 * Object ExceptionUtils
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.util
 */
object ExceptionUtils
{

	/**
	 * Runs a Runnable and ignores the exception.
	 *
	 * @param runnable Runnable to run.
	 *
	 * @author Bas Milius
	 */
	fun executeIgnore(runnable : Runnable)
	{
		ExceptionUtils.executeIgnore(runnable, 0)
	}

	/**
	 * Runs a Runnable and ignores the exception.
	 *
	 * @param runnable Runnable to run.
	 * @param delay    Delay time in milliseconds.
	 *
	 * @author Bas Milius
	 */
	fun executeIgnore(runnable : Runnable, delay : Int)
	{
		if (runnable is ExceptionRunnable)
		{
			ProgressManager.getInstance().run(object : Task.Backgroundable(null, "Bas Tools Process")
			{

				override fun run(progressIndicator : ProgressIndicator)
				{
					try
					{
						if (delay > 0)
						{
							TimeUnit.MILLISECONDS.sleep(delay.toLong())
						}

						runnable.run()
					}
					catch (e : Exception)
					{
						if (ManagementFactory.getRuntimeMXBean().inputArguments.toString().contains("jdwp"))
						{
							e.printStackTrace()
						}
					}

				}

			})
		}
		else
		{
			ProgressManager.getInstance().run(object : Task.Backgroundable(null, "Bas Tools Process")
			{

				override fun run(progressIndicator : ProgressIndicator)
				{
					if (delay > 0)
					{
						TimeUnit.MILLISECONDS.sleep(delay.toLong())
					}

					runnable.run()
				}

			})
		}
	}

}
