package com.basmilius.ps.bastools.framework.identity.installer

class IdentityInstallerSettings(private val version : IdentityInstallerVersion, private val phpInterpreter : String)
{

	fun isDownload() : Boolean
	{
		return true
	}

	fun getVersion() : IdentityInstallerVersion
	{
		return version
	}

	fun getPhpInterpreter() : String
	{
		return phpInterpreter
	}

	fun getExistingPath() : String
	{
		return ""
	}

}
