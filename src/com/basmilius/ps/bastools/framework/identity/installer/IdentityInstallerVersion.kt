package com.basmilius.ps.bastools.framework.identity.installer

class IdentityInstallerVersion
{

	private var version : String? = null
	private var presentableName : String? = null
	private var url : String? = null

	constructor(version : String)
	{
		this.version = version
		this.presentableName = version
	}

	constructor(version : String, presentableName : String, url : String)
	{
		this.version = version
		this.presentableName = presentableName
		this.url = url
	}

	fun getPresentableName() : String?
	{
		return presentableName
	}

	fun getVersion() : String?
	{
		return version
	}

	fun getUrl() : String?
	{
		return url
	}

}
