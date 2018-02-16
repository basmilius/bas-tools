/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util.strtotime

import java.util.*

/**
 * Interface Matcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.strtotime
 * @since 1.1.0
 */
interface Matcher
{

	/**
	 * Tries to convert the string value to a date value.
	 *
	 * @param input String
	 * @param refDateStr String
	 *
	 * @return Date?
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun tryConvert(input: String, refDateStr: String): Date?

}
