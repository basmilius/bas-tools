package com.basmilius.ps.bastools.util.strtotime

import java.text.DateFormat
import java.text.ParseException
import java.util.*

class DateFormatMatcher(private val dateFormat : DateFormat) : Matcher
{

	override fun tryConvert(input : String, refDateStr : String) : Date?
	{
		return try
		{
			this.dateFormat.parse(input)
		}
		catch (e : ParseException)
		{
			null
		}
	}

}
