/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

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
