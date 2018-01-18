package com.basmilius.bastools.core.util.strtotime

import java.util.*
import java.util.regex.Pattern

/**
 * Class NowWatcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.strtotime
 */
class NowMatcher: Matcher
{

	private val now = Pattern.compile("\\W*now\\W*")
	private val today = Pattern.compile("\\W*today\\W*")

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun tryConvert(input: String, refDateStr: String): Date?
	{
		if (now.matcher(input).matches() || today.matcher(input).matches())
			return Date()

		return null
	}

}
