/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.presenter.shortcuts

import com.intellij.util.PlatformUtils

/**
 * Enum KeymapKind
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.presenter.shortcuts
 * @since 1.1.0
 */
enum class KeymapKind(val displayName: String, val defaultKeymapName: String)
{

	WIN("Win/Linux", "\$default"),
	MAC("Mac", "Mac OS X 10.5+");

	/**
	 * Gets the alternative keymap.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun getAlternativeKind() = when (this)
	{
		WIN -> MAC
		MAC -> if (PlatformUtils.isAppCode()) null else WIN
	}

}
