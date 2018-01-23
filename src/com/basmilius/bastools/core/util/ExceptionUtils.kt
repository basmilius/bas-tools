package com.basmilius.bastools.core.util

import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import java.util.concurrent.TimeUnit

/**
 * Object ExceptionUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.0.0
 */
object ExceptionUtils
{

	/**
	 * Runs a Runnable and ignores the exception.
	 *
	 * @param callback () -> Unit
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun executeIgnore(callback: () -> Unit)
	{
		ExceptionUtils.executeIgnore(0, callback)
	}

	/**
	 * Runs a Runnable and ignores the exception.
	 *
	 * @param delay Int
	 * @param callback () -> Unit
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun executeIgnore(delay: Int, callback: () -> Unit)
	{
		ProgressManager.getInstance().run(object: Task.Backgroundable(null, "Bas Tools Process")
		{

			override fun run(progressIndicator: ProgressIndicator)
			{
				try
				{
					if (delay > 0)
						TimeUnit.MILLISECONDS.sleep(delay.toLong())

					callback()
				}
				catch (e: Exception)
				{
					e.printStackTrace()
				}

			}

		})
	}

}
