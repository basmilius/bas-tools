/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util.strtotime

import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.time.DateUtils
import java.util.*
import java.util.regex.Pattern

/**
 * Class DaysMatcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.strtotime
 * @since 1.1.0
 */
class DaysMatcher: Matcher
{

	private val days = Pattern.compile("[\\-+]?\\d+ days")

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun tryConvert(input: String, refDateStr: String): Date?
	{
		val calendar = Calendar.getInstance()

		if (!StringUtils.isEmpty(refDateStr))
		{
			try
			{
				calendar.time = DateUtils.parseDate(refDateStr, Array(1) { "yyyy-MM-dd" })
			}
			catch (e: Exception)
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
