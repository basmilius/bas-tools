package com.basmilius.ps.bastools.framework.identity.installer

/**
 * Class IdentityInstallerVersion
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity.installer
 */
class IdentityInstallerVersion
{

	private var version : String? = null
	private var presentableName : String? = null
	private var url : String? = null

	/**
	 * Class IdentityInstallerVersion
	 *
	 * @param version String
	 *
	 * @author Bas Milius
	 */
	constructor(version : String)
	{
		this.version = version
		this.presentableName = version
	}

	/**
	 * Class IdentityInstallerVersion
	 *
	 * @param version String
	 * @param presentableName String
	 * @param url String
	 *
	 * @author Bas Milius
	 */
	constructor(version : String, presentableName : String, url : String)
	{
		this.version = version
		this.presentableName = presentableName
		this.url = url
	}

	/**
	 * Gets the presentable name.
	 *
	 * @author Bas Milius
	 *
	 * @return String?
	 */
	fun getPresentableName() : String?
	{
		return presentableName
	}

	/**
	 * Gets the version string.
	 *
	 * @author Bas Milius
	 *
	 * @return String?
	 */
	fun getVersion() : String?
	{
		return version
	}

	/**
	 * Gets the URL.
	 *
	 * @author Bas Milius
	 *
	 * @return String?
	 */
	fun getUrl() : String?
	{
		return url
	}

}