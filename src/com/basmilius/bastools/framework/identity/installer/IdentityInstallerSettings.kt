package com.basmilius.bastools.framework.identity.installer

/**
 * Class IdentityInstallerSettings
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.identity.installer
 */
class IdentityInstallerSettings(private val version: IdentityInstallerVersion, private val phpInterpreter: String)
{

	/**
	 * Returns TRUE if it's a download.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun isDownload(): Boolean
	{
		return true
	}

	/**
	 * Gets the current version of the framework.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getVersion(): IdentityInstallerVersion
	{
		return this.version
	}

	/**
	 * Gets the PHP Interpreter.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getPhpInterpreter(): String
	{
		return this.phpInterpreter
	}

	/**
	 * Gets the existing path.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getExistingPath(): String
	{
		return ""
	}

}
