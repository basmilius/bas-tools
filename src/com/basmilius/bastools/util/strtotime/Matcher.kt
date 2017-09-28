package com.basmilius.bastools.util.strtotime

import java.util.*

/**
 * Interface Matcher
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.util.strtotime
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
	 * @author Bas Milius
	 */
	fun tryConvert(input: String, refDateStr: String): Date?

}
