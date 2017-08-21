package com.basmilius.ps.bastools.util.strtotime

import java.util.*
import java.util.regex.Pattern

/**
 * Class TomorrowMatcher
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.util.strtotime
 */
class TomorrowMatcher: Matcher
{

	private val tomorrow = Pattern.compile("\\W*tomorrow\\W*")

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
			calendar.add(Calendar.DAY_OF_YEAR, 1)
			return calendar.time
		}

		return null
	}

}
