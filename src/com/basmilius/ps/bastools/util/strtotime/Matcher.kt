package com.basmilius.ps.bastools.util.strtotime

import java.util.*

interface Matcher
{

	fun tryConvert (input : String, refDateStr : String) : Date?

}
