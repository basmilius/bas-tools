package com.basmilius.ps.bastools.util.iid

interface ExceptionRunnable : Runnable
{

	@Throws(Exception::class)
	override fun run()

}
