package com.basmilius.bastools.core.util.strtotime

import java.util.*
import java.util.regex.Pattern

/**
 * Class TomorrowMatcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.strtotime
 * @since 1.1.0
 */
class TomorrowMatcher: Matcher
{

	private val tomorrow = Pattern.compile("\\W*tomorrow\\W*")

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun tryConvert(input: String, refDateStr: String): Date?
	{
		if (tomorrow.matcher(input).matches())
		{
			val calendar = Calendar.getInstance()
			calendar.add(Calendar.DAY_OF_YEAR, 1)
			return calendar.time
		}

		return null
	}

}