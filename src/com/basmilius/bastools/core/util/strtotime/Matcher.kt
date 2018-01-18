package com.basmilius.bastools.core.util.strtotime

import java.util.*

/**
 * Interface Matcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.strtotime
 */
interface Matcher
{

	/**
	 * Tries to convert the string value to a date value.
	 *
	 * @param input String
	 * @param refDateStr String
	 *
	 * @return Date?s
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun tryConvert(input: String, refDateStr: String): Date?

}
