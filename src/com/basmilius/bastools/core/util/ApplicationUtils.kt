package com.basmilius.bastools.core.util

/**
 * Object ApplicationUtils
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.core.util
 */
object ApplicationUtils
{

	/**
	 * Deferred run.
	 *
	 * @param timeout Long
	 * @param func Closure
	 *
	 * @author Bas Milius
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
