package com.basmilius.bastools.core.util.iid

/**
 * Interface ExceptionRunnable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.iid
 * @since 1.0.0
 */
interface ExceptionRunnable: Runnable
{

	/**
	 * Runs the operation.
	 *
	 * @throws Exception
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	@Throws(Exception::class)
	override fun run()

}
