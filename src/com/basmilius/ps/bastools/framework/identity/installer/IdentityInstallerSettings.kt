package com.basmilius.ps.bastools.framework.identity.installer

/**
 * Class IdentityInstallerSettings
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.identity.installer
 */
class IdentityInstallerSettings(private val version: IdentityInstallerVersion, private val phpInterpreter: String)
{

	/**
	 * Returns TRUE if it's a download.
	 *
	 * @author Bas Milius
	 */
	fun isDownload(): Boolean
	{
		return true
	}

	/**
	 * Gets the current version of the framework.
	 *
	 * @author Bas Milius
	 */
	fun getVersion(): IdentityInstallerVersion
	{
		return this.version
	}

	/**
	 * Gets the PHP Interpreter.
	 *
	 * @author Bas Milius
	 */
	fun getPhpInterpreter(): String
	{
		return this.phpInterpreter
	}

	/**
	 * Gets the existing path.
	 *
	 * @author Bas Milius
	 */
	fun getExistingPath(): String
	{
		return ""
	}

}
