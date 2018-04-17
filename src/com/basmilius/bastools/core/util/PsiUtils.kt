/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import kotlin.reflect.KClass

/**
 * Object PsiUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.4.0
 */
object PsiUtils
{

	/**
	 * Finds a child of {@see element} of type {@see clazz}.
	 *
	 * @param element PsiElement
	 * @param clazz KClass<T>
	 *
	 * @return Collection<T>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: PsiElement> findChildOfType(element: PsiElement?, clazz: KClass<T>): T?
	{
		return PsiTreeUtil.findChildOfType(element, clazz.java)
	}

	/**
	 * Finds children of {@see element} of type {@see clazz}.
	 *
	 * @param element PsiElement
	 * @param clazz KClass<T>
	 *
	 * @return Collection<T>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: PsiElement> findChildrenOfType(element: PsiElement?, clazz: KClass<T>): Collection<T>
	{
		return PsiTreeUtil.findChildrenOfType(element, clazz.java)
	}

	/**
	 * Finds an element of {@see clazz} at {@see offset}.
	 *
	 * @param file PsiFile
	 * @param offset Int
	 * @param clazz KClass<T>
	 * @param strictStart Boolean
	 *
	 * @return T?
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: PsiElement> findElementOfClassAtOffset(file: PsiFile, offset: Int, clazz: KClass<T>, strictStart: Boolean): T?
	{
		return PsiTreeUtil.findElementOfClassAtOffset(file, offset, clazz.java, strictStart)
	}

	/**
	 * Gets children of {@see element} of {@see clazz}.
	 *
	 * @param element PsiElement
	 * @param clazz KClass<T>
	 *
	 * @return Array<T>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: PsiElement> getChildrenOfType(element: PsiElement?, clazz: KClass<T>): Array<out T>?
	{
		return PsiTreeUtil.getChildrenOfType(element, clazz.java)
	}

	/**
	 * Gets the context of a type.
	 *
	 * @param element PsiElement
	 * @param classes KClass<T>
	 *
	 * @return T?
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: PsiElement> getContextOfType(element: PsiElement?, vararg classes: KClass<T>): T?
	{
		val javaClasses = classes
				.map { it.java }
				.toTypedArray()

		return PsiTreeUtil.getContextOfType(element, *javaClasses)
	}

	/**
	 * Gets a parent of {@see element} of type {@see clazz}.
	 *
	 * @param element PsiElement
	 * @param clazz KClass<T>
	 *
	 * @return T
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: PsiElement> getParentOfType(element: PsiElement?, clazz: KClass<T>): T?
	{
		return PsiTreeUtil.getParentOfType(element, clazz.java)
	}

	/**
	 * Gets a previous sibling of {@see element} of type {@see clazz}.
	 *
	 * @param element PsiElement
	 * @param clazz KClass<T>
	 *
	 * @return T
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	fun <T: PsiElement> getPrevSiblingOfType(element: PsiElement?, clazz: KClass<T>): T?
	{
		return PsiTreeUtil.getPrevSiblingOfType(element, clazz.java)
	}

}
