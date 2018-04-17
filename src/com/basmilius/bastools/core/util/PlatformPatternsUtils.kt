/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util

import com.intellij.patterns.PlatformPatterns
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement
import kotlin.reflect.KClass

/**
 * Object PlatformPatternsUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.4.0
 */
object PlatformPatternsUtils
{

	/**
	 * Gets a psi element pattern.
	 *
	 * @param clazz KClass<T>
	 *
	 * @return PsiElementPattern.Capture<T>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: PsiElement> psiElement(clazz: KClass<T>): PsiElementPattern.Capture<T>
	{
		return PlatformPatterns.psiElement(clazz.java)
	}

}