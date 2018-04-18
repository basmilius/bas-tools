/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util

import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import java.util.concurrent.TimeUnit

typealias Callback = () -> Unit

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
	 * We don't care what happends.
	 *
	 * @param func Closure
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	inline fun dontCare(func: Callback)
	{
		try
		{
			func()
		}
		catch (err: Exception)
		{
		}
	}

	/**
	 * Runs a Runnable and ignores the exception.
	 *
	 * @param func Closure
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun executeIgnore(func: Callback)
	{
		ExceptionUtils.executeIgnore(0, func)
	}

	/**
	 * Runs a Runnable and ignores the exception.
	 *
	 * @param delay Int
	 * @param func Closure
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	private fun executeIgnore(delay: Int, func: Callback)
	{
		ProgressManager.getInstance().run(object: Task.Backgroundable(null, "Bas Tools Process")
		{

			override fun run(progressIndicator: ProgressIndicator)
			{
				try
				{
					if (delay > 0)
						TimeUnit.MILLISECONDS.sleep(delay.toLong())

					func()
				}
				catch (e: Exception)
				{
					e.printStackTrace()
				}

			}

		})
	}

}
