/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.renderer

import com.basmilius.bastools.core.util.ExceptionUtils
import java.util.concurrent.ArrayBlockingQueue

typealias Callback = () -> Unit

/**
 * Class TaskQueueRunner
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.renderer
 * @since 1.4.0
 */
class TaskQueueRunner: Runnable
{

	private val queue = ArrayBlockingQueue<Callback>(1000)
	private var stop = false

	/**
	 * Adds an extra world spin.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun run(task: Callback) = this.queue.add(task)

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun run()
	{
		while (!this.stop)
		{
			ExceptionUtils.dontCare {
				this.queue.take()()
			}
		}
	}

	/**
	 * Stops the world from spinning.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun stop()
	{
		this.stop = true
	}

}
