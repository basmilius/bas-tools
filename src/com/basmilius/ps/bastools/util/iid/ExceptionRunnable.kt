package com.basmilius.ps.bastools.util.iid

/**
 * Interface ExceptionRunnable
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.util.iid
 */
interface ExceptionRunnable : Runnable
{

	/**
	 * Runs the operation.
	 *
	 * @throws Exception
	 *
	 * @author Bas Milius
	 */
	@Throws(Exception::class)
	override fun run()

}