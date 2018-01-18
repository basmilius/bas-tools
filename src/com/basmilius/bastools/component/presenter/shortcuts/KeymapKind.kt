package com.basmilius.bastools.component.presenter.shortcuts

import com.intellij.util.PlatformUtils

/**
 * Enum KeymapKind
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.presenter.shortcuts
 */
enum class KeymapKind(val displayName: String, val defaultKeymapName: String)
{

	WIN("Win/Linux", "\$default"),
	MAC("Mac", "Mac OS X 10.5+");

	/**
	 * Gets the alternative keymap.
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	fun getAlternativeKind() = when (this)
	{
		WIN -> MAC
		MAC -> if (PlatformUtils.isAppCode()) null else WIN
	}

}
