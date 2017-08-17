package com.basmilius.ps.bastools.util

import org.jetbrains.annotations.NotNull

/**
 * Object TypesUtils
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.util
 */
@Suppress("MemberVisibilityCanPrivate")
object TypesUtils
{

	val strArray = "array"
	val strIterable = "iterable"
	val strString = "string"
	val strBoolean = "bool"
	val strInteger = "int"
	val strFloat = "float"
	val strNull = "null"
	val strVoid = "void"
	val strMixed = "mixed"
	val strCallable = "callable"
	val strResource = "resource"
	val strStatic = "static"
	val strSelf = "self"
	val strObject = "object"
//	val strEmptySet = "ø"

//	val strResolvingAbortedOnPsiLevel = "\\aborted-on-psi-level"
//	val strClassNotResolved = "\\class-not-resolved"

	private var mapTypes : MutableMap<String, String>? = null

	private fun getTypesMap() : Map<String, String>
	{
		if (mapTypes == null)
		{
			mapTypes = HashMap()

			mapTypes!!.put(strArray, strArray)
			mapTypes!!.put("\\array", strArray)

			mapTypes!!.put(strIterable, strIterable)
			mapTypes!!.put("\\iterable", strIterable)

			mapTypes!!.put(strString, strString)
			mapTypes!!.put("\\string", strString)

			mapTypes!!.put(strBoolean, strBoolean)
			mapTypes!!.put("\\bool", strBoolean)
			mapTypes!!.put("boolean", strBoolean)
			mapTypes!!.put("\\boolean", strBoolean)
			mapTypes!!.put("false", strBoolean)
			mapTypes!!.put("\\false", strBoolean)
			mapTypes!!.put("true", strBoolean)
			mapTypes!!.put("\\true", strBoolean)

			mapTypes!!.put(strInteger, strInteger)
			mapTypes!!.put("\\int", strInteger)
			mapTypes!!.put("integer", strInteger)
			mapTypes!!.put("\\integer", strInteger)

			mapTypes!!.put(strFloat, strFloat)
			mapTypes!!.put("\\float", strFloat)

			mapTypes!!.put(strNull, strNull)
			mapTypes!!.put("\\null", strNull)

			mapTypes!!.put(strVoid, strVoid)
			mapTypes!!.put("\\void", strVoid)

			mapTypes!!.put(strMixed, strMixed)
			mapTypes!!.put("\\mixed", strMixed)

			mapTypes!!.put(strCallable, strCallable)
			mapTypes!!.put("\\callable", strCallable)
			mapTypes!!.put("\\closure", strCallable)

			mapTypes!!.put(strResource, strResource)
			mapTypes!!.put("\\resource", strResource)

			mapTypes!!.put(strStatic, strStatic)
			mapTypes!!.put("\\static", strStatic)
			mapTypes!!.put("\$this", strStatic)

			mapTypes!!.put(strSelf, strSelf)
			mapTypes!!.put("\\self", strSelf)

			mapTypes!!.put(strObject, strObject)
			mapTypes!!.put("\\object", strObject)
		}

		return mapTypes as MutableMap<String, String>
	}

	fun getType(@NotNull givenType : String) : String
	{
		if (givenType.contains("[]"))
			return strArray

		val resolvedType = getTypesMap()[givenType.toLowerCase()]

		if (resolvedType != null)
			return resolvedType

		return givenType
	}

}
