package com.basmilius.bastools.core.util

/**
 * Object ApplicationUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.1.0
 */
object ApplicationUtils
{

	/**
	 * Deferred run.
	 *
	 * @param timeout Long
	 * @param func Closure
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	fun deferRun(timeout: Long, func: () -> Unit)
	{
		val thread = Thread({
			Thread.sleep(timeout)

			func()
		})
		thread.start()
	}

}