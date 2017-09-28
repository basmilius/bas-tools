package com.basmilius.bastools.resource

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * Interface Icons
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.resource
 */
interface Icons
{

	/**
	 * Companion Object for Icons
	 *
	 * @author Bas Milius
	 */
	companion object
	{

		val Default: Icon? = null

		val BasTools = IconLoader.getIcon("/icons/bas_tools.png")
		val CoffeeBean = IconLoader.getIcon("/icons/coffee_bean.png")
		val IdeeMedia = IconLoader.getIcon("/icons/ideemedia.png")
		val IdentityPlugin = IconLoader.getIcon("/icons/identity_plugin.png")
		val IdentityTheme = IconLoader.getIcon("/icons/identity_theme.png")
	}

}
