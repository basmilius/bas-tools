package com.basmilius.ps.bastools.util.strtotime

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils
{

	private val matchers = LinkedList<Matcher>()

	init
	{
		matchers.add(NowMatcher())
		matchers.add(TomorrowMatcher())
		matchers.add(YesterdayMatcher())
		matchers.add(DaysMatcher())
		matchers.add(WeeksMatcher())
		matchers.add(MinutesMatcher())

		matchers.add(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("yyyy MM dd HH:mm:ss")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd HH:mm:ss")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("dd/mm/YYYY HH:mm:ss")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("dd-mm-YYYY HH:mm:ss")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("d-m-YYYY HH:mm:ss")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("yyyy MM dd HH:mm")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd HH:mm")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("dd/mm/YYYY HH:mm")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("dd-mm-YYYY HH:mm")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("yyyy MM dd")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("dd/mm/YYYY")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("dd-mm-YYYY")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("d-m-YYYY")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("d-m-YYYY HH:mm:ss")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy")))
		matchers.add(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ")))
	}

	fun registerMatcher(matcher : Matcher)
	{
		matchers.add(0, matcher)
	}

	fun convert(input : String, refDateStr : String = "") : Date?
	{
		return matchers
				.map { it.tryConvert(input, refDateStr) }
				.firstOrNull { it != null }
	}

}

fun strtotime(str : String) : Long?
{
	val date = DateTimeUtils.convert(str) ?: return null

	return date.time / 1000L
}
