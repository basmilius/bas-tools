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
