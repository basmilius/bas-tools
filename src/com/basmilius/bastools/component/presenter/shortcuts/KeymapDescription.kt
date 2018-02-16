/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.presenter.shortcuts

import com.intellij.openapi.keymap.KeymapManager

/**
 * Class KeymapDescription
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.presenter.shortcuts
 * @since 1.1.0
 */
class KeymapDescription(val name: String = "", val displayText: String = "")
{

	/**
	 * Gets the keymap kind.
	 *
	 * @return KeymapKind
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun getKind() = if (name.contains("Mac OS")) KeymapKind.MAC else KeymapKind.WIN

	/**
	 * Gets the keymap.
	 *
	 * @return Keymap
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun getKeymap() = KeymapManager.getInstance().getKeymap(name)

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun equals(other: Any?): Boolean
	{
		return other is KeymapDescription && other.name == this.name && other.displayText == this.displayText
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun hashCode(): Int
	{
		return this.name.hashCode() + 31 * this.displayText.hashCode()
	}

}
