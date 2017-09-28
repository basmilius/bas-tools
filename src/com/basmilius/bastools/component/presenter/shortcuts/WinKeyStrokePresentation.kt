package com.basmilius.bastools.component.presenter.shortcuts

import com.intellij.openapi.util.text.StringUtil
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.lang.reflect.Modifier

val inputEventMaskFieldNames by lazy {
	InputEvent::class.java.fields
			.filter {
				it.name.endsWith("_MASK") && !it.name.endsWith("_DOWN_MASK")
						&& !it.name.startsWith("BUTTON")
						&& Modifier.isStatic(it.modifiers) && it.get(null) is Int
			}
			.map { Pair(fieldNameToPresentableName(it.name.removeSuffix("_MASK")), it.get(null) as Int) }
}

fun getWinModifiersText(modifiers: Int) =
		inputEventMaskFieldNames
				.filter { modifiers and (it.second) != 0 }.joinToString("+") { it.first }

val keyEventFieldNames by lazy {
	KeyEvent::class.java.fields
			.filter { it.name.startsWith("VK_") && Modifier.isStatic(it.modifiers) && it.get(null) is Int }
			.map { Pair(fieldNameToPresentableName(it.name.removePrefix("VK_")), it.get(null) as Int) }
			.groupBy { it.second }
			.mapValues { it.value.first().first }
}

fun getWinKeyText(key: Int) =
		when (key)
		{
			KeyEvent.VK_BACK_SPACE -> "Backspace"
			KeyEvent.VK_MULTIPLY -> "NumPad *"
			KeyEvent.VK_ADD -> "NumPad +"
			KeyEvent.VK_SEPARATOR -> "NumPad ,"
			KeyEvent.VK_SUBTRACT -> "NumPad -"
			KeyEvent.VK_DECIMAL -> "NumPad ."
			KeyEvent.VK_DIVIDE -> "NumPad /"
			in keyEventFieldNames.keys -> keyEventFieldNames[key]
			else -> "Unknown key 0x${Integer.toHexString(key)}"
		}

fun fieldNameToPresentableName(name: String) = name.split('_').joinToString(" ") { StringUtil.capitalize(it.toLowerCase()) }
