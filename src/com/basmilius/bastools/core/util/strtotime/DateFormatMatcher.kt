package com.basmilius.bastools.core.util.strtotime

import java.text.DateFormat
import java.text.ParseException
import java.util.*

/**
 * Class DateFormatMatcher
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util.strtotime
 */
class DateFormatMatcher(private val dateFormat: DateFormat): Matcher
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun tryConvert(input: String, refDateStr: String): Date?
	{
		return try
		{
			this.dateFormat.parse(input)
		}
		catch (e: ParseException)
		{
			null
		}
	}

}
