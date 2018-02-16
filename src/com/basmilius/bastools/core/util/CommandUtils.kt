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
import com.intellij.openapi.project.Project
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*

/**
 * Object CommandUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.0.0
 */
object CommandUtils
{

	/**
	 * Runs a command.
	 *
	 * @param project Project that the command should be executed on.
	 * @param command Command that should be executed.
	 * @param handler Handler.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	@Synchronized
	fun run(project: Project?, command: String, handler: ICommandResultHandler)
	{
		if (project == null)
			return

		ProgressManager.getInstance().run(object: Task.Backgroundable(project, "Executing command '$command'")
		{

			override fun run(progress: ProgressIndicator)
			{
				progress.isIndeterminate = true

				val runtime = Runtime.getRuntime()

				try
				{
					val process = runtime.exec(command, null, File(project.basePath!!))
					val `in` = BufferedInputStream(process.inputStream)
					val reader = BufferedReader(InputStreamReader(`in`))

					var line = reader.readLine()
					val lines = ArrayList<String>()

					while (line != null)
					{
						lines.add(line)
						line = reader.readLine()
					}

					handler.onCommandResult(lines.toTypedArray())

					process.waitFor()
					reader.close()
					`in`.close()
				}
				catch (e: Exception)
				{
					e.printStackTrace()
				}

			}

		})
	}

	/**
	 * Interface ICommandResultHandler
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	interface ICommandResultHandler
	{

		/**
		 * Invoked when a command is executed.
		 *
		 * @param lines Array<String>
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.0.0
		 */
		fun onCommandResult(lines: Array<String>)

	}

}
