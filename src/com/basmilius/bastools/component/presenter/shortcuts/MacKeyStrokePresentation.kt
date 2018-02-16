/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.presenter.shortcuts

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.keymap.MacKeymapUtil
import java.awt.Font
import java.awt.GraphicsEnvironment

private val LOG = Logger.getInstance("shortcutPresenter.MacKeyStrokePresentation")

val macKeyStrokesFont by lazy {
	val font = GraphicsEnvironment.getLocalGraphicsEnvironment().allFonts.minBy { getNonDisplayableMacSymbols(it).size }
	if (font != null)
	{
		val macSymbols = getNonDisplayableMacSymbols(font)
		if (macSymbols.isNotEmpty())
		{
			LOG.warn("The following symbols from Mac shortcuts aren't supported in selected font: ${macSymbols.joinToString { it.first }}")
		}
	}
	font
}

private fun getNonDisplayableMacSymbols(font: Font) =
		MacKeymapUtil::class.java.declaredFields
				.filter { it.type == String::class.java && it.name != "APPLE" }
				.map { Pair(it.name, it.get(null) as String) }
				.filter { font.canDisplayUpTo(it.second) != -1 }
