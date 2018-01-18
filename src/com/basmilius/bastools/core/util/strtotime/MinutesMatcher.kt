package com.basmilius.bastools.core.util.strtotime

import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.time.DateUtils
import java.util.*
import java.util.regex.Pattern

/**
 * Class MinutesMatcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.strtotime
 */
class MinutesMatcher: Matcher
{

	private val minutes = Pattern.compile("[\\-+]?\\d+ minutes")

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun tryConvert(input: String, refDateStr: String): Date?
	{
		val calendar = Calendar.getInstance()

		if (!StringUtils.isEmpty(refDateStr))
		{
			try
			{
				calendar.time = DateUtils.parseDate(refDateStr, Array(1) { "yyyy-MM-dd'T'HH:mm" })
			}
			catch (e: Exception)
			{
			}
		}

		if (minutes.matcher(input).matches())
		{
			val m = input.split(" ")[0].toInt()
			calendar.timeInMillis = System.currentTimeMillis() + (m * 60 * 1000)
			return calendar.time
		}

		return null
	}

}
