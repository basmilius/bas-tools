package com.basmilius.bastools.core.util.iid

/**
 * Interface ExceptionRunnable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.iid
 */
interface ExceptionRunnable: Runnable
{

	/**
	 * Runs the operation.
	 *
	 * @throws Exception
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	@Throws(Exception::class)
	override fun run()

}
