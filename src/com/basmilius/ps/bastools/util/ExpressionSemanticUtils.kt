package com.basmilius.ps.bastools.util

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocType
import com.jetbrains.php.lang.psi.PhpFile
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.Function
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * Object ExpressionSemanticUtils
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.util
 */
object ExpressionSemanticUtils
{

	/**
	 * Checks if an If statement has alternative branches.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius
	 */
	fun hasAlternativeBranches(ifStatement: If): Boolean
	{
		return (ifStatement.elseBranch != null || ifStatement.elseIfBranches.isNotEmpty())
	}

	/**
	 * Gets the return value expression.
	 *
	 * @return PhpExpression?
	 *
	 * @todo Double check if OpenAPI doesn't have a method for this.
	 *
	 * @author Bas Milius
	 */
	fun getReturnValue(@NotNull returnValue: PhpReturn): PhpExpression?
	{
		for (child in returnValue.children)
			if (child is PhpExpression)
				return child

		return null
	}

	/**
	 * Counts the number of expressions in a group.
	 *
	 * @return Int
	 *
	 * @author Bas Milius
	 */
	fun getCountExpressionsInGroup(@NotNull groupStatement: GroupStatement): Int
	{
		return groupStatement.children.count { it is PhpPsiElement && it !is PhpDocType && it !is PhpDocComment }
	}

	/**
	 * Gets the last statement.
	 *
	 * @return PsiElement?
	 *
	 * @author Bas Milius
	 */
	fun getLastStatement(@NotNull groupStatement: GroupStatement): PsiElement?
	{
		var lastChild = groupStatement.lastChild

		while (lastChild != null)
		{
			if (lastChild is PhpPsiElement && lastChild !is PhpDocComment)
				return lastChild

			lastChild = lastChild.prevSibling
		}

		return null
	}

	/**
	 * Gets a group statement.
	 *
	 * @return GroupStatement?
	 *
	 * @author Bas Milius
	 */
	fun getGroupStatement(@NotNull expression: PsiElement): GroupStatement?
	{
		for (child in expression.children)
		{
			if (child !is GroupStatement)
				continue

			return child
		}

		return null
	}

	/**
	 * Gets the expression within parenthesis
	 *
	 * @return PsiElement
	 *
	 * @author Bas Milius
	 */
	fun getExpressionThroughParenthesis(@Nullable expression: PsiElement): PsiElement
	{
		if (expression !is ParenthesizedExpression)
			return expression

		var innerExpression = expression.argument

		while (innerExpression is ParenthesizedExpression)
			innerExpression = innerExpression.argument

		return innerExpression
	}

	/**
	 * Gets the scope of a psi element.
	 *
	 * @return Function?
	 *
	 * @author Bas Milius
	 */
	fun getScope(@NotNull expression: PsiElement): Function?
	{
		var parent = expression.parent

		while (parent != null && parent !is PhpFile)
		{
			if (parent is Function)
				return parent

			parent = parent.parent
		}

		return null
	}

}
