package com.basmilius.bastools.util.strtotime

import java.util.*
import java.util.regex.Pattern

/**
 * Class YesterdayMatcher
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.util.strtotime
 */
class YesterdayMatcher: Matcher
{

	private val tomorrow = Pattern.compile("\\W*yesterday\\W*")

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun tryConvert(input: String, refDateStr: String): Date?
	{
		if (tomorrow.matcher(input).matches())
		{
			val calendar = Calendar.getInstance()
			calendar.add(Calendar.DAY_OF_YEAR, -1)
			return calendar.time
		}

		return null
	}

}
