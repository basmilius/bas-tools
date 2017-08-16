package com.basmilius.ps.bastools.util.strtotime

import java.util.*
import java.util.regex.Pattern

class NowMatcher : Matcher
{

	private val now = Pattern.compile("\\W*now\\W*")
	private val today = Pattern.compile("\\W*today\\W*")

	override fun tryConvert(input : String, refDateStr : String) : Date?
	{
		if (now.matcher(input).matches() || today.matcher(input).matches())
			return Date()

		return null
	}

}
