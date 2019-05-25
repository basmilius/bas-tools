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

typealias Callback = () -> Unit

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
inline fun processDontCare(crossinline func: Callback)
{
	ProgressManager.getInstance().run(object: Task.Backgroundable(null, "Bas Tools Process")
	{

		override fun run(progressIndicator: ProgressIndicator)
		{
			try
			{
				func()
			}
			catch (e: Exception)
			{
				e.printStackTrace()
			}

		}

	})
}
