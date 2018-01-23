package com.basmilius.bastools.resource

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * Interface Icons
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.resource
 * @since 1.0.0
 */
interface Icons
{

	/**
	 * Companion Object for Icons
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	companion object
	{

		val Default: Icon? = null

		val BasTools = IconLoader.getIcon("/icons/bas_tools.png")
		val CappuccinoFile = IconLoader.getIcon("/icons/cappuccino_file.png")
		val IdeeMedia = IconLoader.getIcon("/icons/ideemedia.png")
		val IdentityPlugin = IconLoader.getIcon("/icons/identity_plugin.png")
		val IdentityTheme = IconLoader.getIcon("/icons/identity_theme.png")
	}

}
