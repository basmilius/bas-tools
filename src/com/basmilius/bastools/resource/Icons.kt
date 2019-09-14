/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

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
object Icons
{

	val Default: Icon? = null

	val BasTools = IconLoader.getIcon("/META-INF/pluginIcon.svg")
	val CappuccinoFile = IconLoader.getIcon("/icons/cappuccino_file.png")
	val ChevronDown = IconLoader.getIcon("/icons/chevron_down.png")
	val ChevronRight = IconLoader.getIcon("/icons/chevron_right.png")
	val Close = IconLoader.getIcon("/icons/close.png")
	val CloseHover = IconLoader.getIcon("/icons/close_hover.png")
	val Code = IconLoader.getIcon("/icons/code.png")
	val IdeeMedia = IconLoader.getIcon("/icons/ideemedia.png")
	val IdentityPlugin = IconLoader.getIcon("/icons/identity_plugin.png")
	val IdentityTheme = IconLoader.getIcon("/icons/identity_theme.png")
	val Poop = IconLoader.getIcon("/icons/poop.svg")

}
