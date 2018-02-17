/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.presenter.shortcuts

import com.basmilius.bastools.core.util.ApplicationUtils
import com.intellij.openapi.keymap.KeymapManager
import com.intellij.openapi.util.SystemInfo

private val winKeymap = KeymapManager.getInstance().getKeymap(KeymapKind.WIN.defaultKeymapName)
private val macKeymap = KeymapManager.getInstance().getKeymap(KeymapKind.MAC.defaultKeymapName)

/**
 * Gets the current OS KeymapKind
 *
 * @return KeymapKind
 *
 * @author Bas Milius <bas@mili.us>
 * @since 1.1.0
 */
fun getCurrentOSKind() = when
{
	SystemInfo.isMac -> KeymapKind.MAC
	else -> KeymapKind.WIN
}

/**
 * Gets the keymap instance.
 *
 * @return Keymap
 *
 * @author Bas Milius <bas@mili.us>
 * @since 1.1.0
 */
fun KeymapKind.getKeymap() = when (this)
{
	KeymapKind.WIN -> winKeymap
	KeymapKind.MAC -> macKeymap
}

/**
 * Gets the default keymap.
 *
 * @return KeymapDescription
 *
 * @author Bas Milius <bas@mili.us>
 * @since 1.1.0
 */
fun getDefaultMainKeymap() = KeymapDescription(getCurrentOSKind().defaultKeymapName, "")

/**
 * Gets the default alternative keymap.
 *
 * @return KeymapDescription
 *
 * @author Bas Milius <bas@mili.us>
 * @since 1.1.0
 */
fun getDefaultAlternativeKeymap() = getCurrentOSKind().getAlternativeKind()?.let { KeymapDescription(it.defaultKeymapName, "for ${it.displayName}") }

/**
 * Gets the shortcut presenter component instance.
 *
 * @return ShortcutPresenter
 *
 * @author Bas Milius <bas@mili.us>
 * @since 1.1.0
 */
fun getShortcutPresenter(): ShortcutPresenter? = ApplicationUtils.getComponent(ShortcutPresenter::class)
