/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util.strtotime

import java.util.*
import java.util.regex.Pattern

/**
 * Class YesterdayMatcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.strtotime
 * @since 1.1.0
 */
class YesterdayMatcher: Matcher
{

	private val tomorrow = Pattern.compile("\\W*yesterday\\W*")

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
			calendar.add(Calendar.DAY_OF_YEAR, -1)
			return calendar.time
		}

		return null
	}

}
