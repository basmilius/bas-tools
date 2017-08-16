package com.basmilius.ps.bastools.util.strtotime

import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.time.DateUtils
import java.util.*
import java.util.regex.Pattern

class DaysMatcher : Matcher
{

	private val days = Pattern.compile("[\\-+]?\\d+ days")

	override fun tryConvert(input : String, refDateStr : String) : Date?
	{
		val calendar = Calendar.getInstance()

		if (!StringUtils.isEmpty(refDateStr))
		{
			try
			{
				calendar.time = DateUtils.parseDate(refDateStr, Array(1) { "yyyy-MM-dd" })
			}
			catch (e : Exception)
			{
			}
		}

		if (days.matcher(input).matches())
		{
			val d = input.split(" ")[0].toInt()
			calendar.add(Calendar.DAY_OF_YEAR, d)
			return calendar.time
		}

		return null
	}

}
