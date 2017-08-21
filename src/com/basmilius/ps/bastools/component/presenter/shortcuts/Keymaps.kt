package com.basmilius.ps.bastools.component.presenter.shortcuts

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.keymap.KeymapManager
import com.intellij.openapi.util.SystemInfo

private val winKeymap = KeymapManager.getInstance().getKeymap(KeymapKind.WIN.defaultKeymapName)
private val macKeymap = KeymapManager.getInstance().getKeymap(KeymapKind.MAC.defaultKeymapName)

/**
 * Gets the current OS KeymapKind
 *
 * @return KeymapKind
 *
 * @author Bas Milius
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
 * @author Bas Milius
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
 * @author Bas Milius
 */
fun getDefaultMainKeymap() = KeymapDescription(getCurrentOSKind().defaultKeymapName, "")

/**
 * Gets the default alternative keymap.
 *
 * @return KeymapDescription
 *
 * @author Bas Milius
 */
fun getDefaultAlternativeKeymap() = getCurrentOSKind().getAlternativeKind()?.let { KeymapDescription(it.defaultKeymapName, "for ${it.displayName}") }

/**
 * Gets the shortcut presenter component instance.
 *
 * @return ShortcutPresenter
 *
 * @author Bas Milius
 */
fun getShortcutPresenter(): ShortcutPresenter = ApplicationManager.getApplication().getComponent(ShortcutPresenter::class.java)
