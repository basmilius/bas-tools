package com.basmilius.bastools.core.util

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.Function

/**
 * Object PhpUtils
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.core.util
 */
object PhpUtils
{

	/**
	 * Gets the canonical function/method name.
	 *
	 * @param func PsiElement
	 *
	 * @return String?
	 *
	 * @author Bas Milius
	 */
	fun getCanonicalFunctionName(func: PsiElement): String?
	{
		if (func.reference is MethodReference)
			return getMethodName(func)

		if (func.reference is FunctionReference)
			return getFunctionName(func)

		if (func is NewExpression)
			return getClassConstructName(func)

		return null
	}

	/**
	 * Gets the index of a parameter.
	 *
	 * @param param PsiElement?
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getParameterIndex(param: PsiElement?): Int
	{
		var index = 0
		var element = param

		while (element != null && element.prevSibling != null)
		{
			val elementClass = element.prevSibling.javaClass.simpleName

			if (elementClass == "LeafPsiElement")
				index++

			element = element.prevSibling
		}

		return index
	}

	/**
	 * Gets the name of a class constructor.
	 *
	 * @param func PsiElement
	 *
	 * @return String?
	 *
	 * @author Bas Milius
	 */
	private fun getClassConstructName(func: PsiElement): String?
	{
		val children = func.children

		if (children.isEmpty())
			return null

		val reference = children[0].reference as? ClassReference ?: return null
		val method = reference.resolve() as? Method ?: return null

		if (method.name != "__construct")
			return null

		val clazz = method.containingClass ?: return null
		val className = clazz.name

		return "$className::__construct"
	}

	/**
	 * Gets the name of a function.
	 *
	 * @param func PsiElement
	 *
	 * @return String?
	 *
	 * @author Bas Milius
	 */
	private fun getFunctionName(func: PsiElement): String?
	{
		if (func.reference == null)
			return null

		val function = func.reference?.resolve() as? Function ?: return null

		return function.name
	}

	/**
	 * Gets the name of a method.
	 *
	 * @param func PsiElement
	 *
	 * @return String?
	 *
	 * @author Bas Milius
	 */
	private fun getMethodName(func: PsiElement): String?
	{
		if (func.reference == null)
			return null

		val method = func.reference?.resolve() as? Method ?: return null
		val clazz = method.containingClass ?: return null

		val className = clazz.name
		val methodName = method.name

		return "$className::$methodName"
	}

}
