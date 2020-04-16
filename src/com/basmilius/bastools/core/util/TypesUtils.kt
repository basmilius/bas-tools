/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.core.util

import org.jetbrains.annotations.NotNull

/**
 * Object TypesUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.1.0
 */
@Suppress("MemberVisibilityCanPrivate")
object TypesUtils
{

	private const val strArray = "array"
	private const val strIterable = "iterable"
	private const val strString = "string"
	private const val strBoolean = "bool"
	private const val strInteger = "int"
	private const val strFloat = "float"
	private const val strNull = "null"
	private const val strVoid = "void"
	private const val strMixed = "mixed"
	private const val strCallable = "callable"
	private const val strResource = "resource"
	private const val strStatic = "static"
	private const val strSelf = "self"
	private const val strObject = "object"
//	val strEmptySet = "ø"

//	val strResolvingAbortedOnPsiLevel = "\\aborted-on-psi-level"
//	val strClassNotResolved = "\\class-not-resolved"

	private var mapTypes: MutableMap<String, String>? = null

	private fun getTypesMap(): Map<String, String>
	{
		if (mapTypes == null)
		{
			mapTypes = mutableMapOf()

			mapTypes!![strArray] = strArray
			mapTypes!!["\\array"] = strArray

			mapTypes!![strIterable] = strIterable
			mapTypes!!["\\iterable"] = strIterable

			mapTypes!![strString] = strString
			mapTypes!!["\\string"] = strString

			mapTypes!![strBoolean] = strBoolean
			mapTypes!!["\\bool"] = strBoolean
			mapTypes!!["boolean"] = strBoolean
			mapTypes!!["\\boolean"] = strBoolean
			mapTypes!!["false"] = strBoolean
			mapTypes!!["\\false"] = strBoolean
			mapTypes!!["true"] = strBoolean
			mapTypes!!["\\true"] = strBoolean

			mapTypes!![strInteger] = strInteger
			mapTypes!!["\\int"] = strInteger
			mapTypes!!["integer"] = strInteger
			mapTypes!!["\\integer"] = strInteger

			mapTypes!![strFloat] = strFloat
			mapTypes!!["\\float"] = strFloat

			mapTypes!![strNull] = strNull
			mapTypes!!["\\null"] = strNull

			mapTypes!![strVoid] = strVoid
			mapTypes!!["\\void"] = strVoid

			mapTypes!![strMixed] = strMixed
			mapTypes!!["\\mixed"] = strMixed

			mapTypes!![strCallable] = strCallable
			mapTypes!!["\\callable"] = strCallable
			mapTypes!!["\\closure"] = strCallable

			mapTypes!![strResource] = strResource
			mapTypes!!["\\resource"] = strResource

			mapTypes!![strStatic] = strStatic
			mapTypes!!["\\static"] = strStatic
			mapTypes!!["\$this"] = strStatic

			mapTypes!![strSelf] = strSelf
			mapTypes!!["\\self"] = strSelf

			mapTypes!![strObject] = strObject
			mapTypes!!["\\object"] = strObject
		}

		return mapTypes as MutableMap<String, String>
	}

	/**
	 * Gets a type.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun getType(@NotNull givenType: String): String
	{
		if (givenType.contains("[]"))
			return strArray

		val resolvedType = getTypesMap()[givenType.toLowerCase()]

		if (resolvedType != null)
			return resolvedType

		return givenType
	}

}
