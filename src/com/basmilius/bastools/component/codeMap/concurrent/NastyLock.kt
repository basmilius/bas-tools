/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap.concurrent

/**
 * Class NastyLock
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap.concurrent
 * @since 1.4.0
 */
class NastyLock
{

	var locked = false
		private set

	var nasty = false
		private set

	fun acquire(): Boolean
	{
		synchronized(this) {
			if (this.locked)
			{
				this.nasty = true
				return false
			}

			this.locked = true
			return true
		}
	}

	fun release()
	{
		synchronized(this) {
			this.locked = false
		}
	}

	fun clean()
	{
		synchronized(this) {
			this.nasty = false
		}
	}

}
