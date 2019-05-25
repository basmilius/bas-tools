/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util.strtotime

import com.basmilius.bastools.core.util.dontCare
import java.text.SimpleDateFormat
import java.util.*

/**
 * Object DateTimeUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.strtotime
 * @since 1.1.0
 */
object DateTimeUtils
{

	private val matchers = LinkedList<Matcher>()

	/**
	 * DateTimeUtils Constructor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	init
	{
		registerMatcher(NowMatcher())
		registerMatcher(TomorrowMatcher())
		registerMatcher(YesterdayMatcher())
		registerMatcher(DaysMatcher())
		registerMatcher(WeeksMatcher())
		registerMatcher(MinutesMatcher())

		registerMatcher(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("yyyy MM dd HH:mm:ss")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd HH:mm:ss")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("dd/MM/YYYY HH:mm:ss")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("dd/MM/YYYY")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("dd-MM-YYYY HH:mm:ss")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("dd-MM-YYYY")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("d-m-YYYY HH:mm:ss")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("yyyy MM dd HH:mm")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd HH:mm")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("dd/MM/YYYY HH:mm")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("dd-MM-YYYY HH:mm")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("yyyy MM dd")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("dd/MM/YYYY")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("dd-MM-YYYY")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("d-m-YYYY")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("d-m-YYYY HH:mm:ss")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy")))
		registerMatcher(DateFormatMatcher(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ")))
	}

	/**
	 * Registers a matcher.
	 *
	 * @param matcher Matcher
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun registerMatcher(matcher: Matcher)
	{
		matchers.add(0, matcher)
	}

	/**
	 * Converts a string to date.
	 *
	 * @param input String
	 * @param refDateStr String
	 *
	 * @return Date?
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun convert(input: String, refDateStr: String = ""): Date?
	{
		return matchers
				.map { it.tryConvert(input, refDateStr) }
				.firstOrNull { it != null }
	}

}

/**
 * Converts a string to Unix Timestamp.
 *
 * @param str String
 *
 * @return Long?
 *
 * @author Bas Milius <bas@mili.us>
 * @since 1.1.0
 */
fun strtotime(str: String): Long?
{
	dontCare {
		val date = DateTimeUtils.convert(str) ?: return null

		return date.time / 1000L
	}

	return null
}
