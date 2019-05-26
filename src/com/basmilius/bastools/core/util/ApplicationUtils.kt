/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import kotlin.reflect.KClass

fun <T: BaseComponent> getApplicationComponent(clazz: KClass<T>): T? = ApplicationManager.getApplication().getComponent(clazz.java)

fun <T: BaseComponent> withApplicationComponent(clazz: KClass<T>, fn: (T) -> Unit)
{
	val component = getApplicationComponent(clazz) ?: return
	fn(component)
}

fun invokeLater(fn: () -> Unit) = ApplicationManager.getApplication().invokeLater(fn)
