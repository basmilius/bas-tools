package com.basmilius.ps.bastools.util

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.elements.*
import org.jetbrains.annotations.NotNull

/**
 * Object PossibleValuesDiscoveryUtils
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.util
 */
object PossibleValuesDiscoveryUtils
{

	/**
	 * Discovers an expression.
	 *
	 * @param expression PsiElement
	 * @param processed HashSet<PsiElement>
	 *
	 * @return HashSet<PsiElement>
	 *
	 * @author Bas Milius
	 */
	fun discover(@NotNull expression: PsiElement, @NotNull processed: HashSet<PsiElement>): HashSet<PsiElement>
	{
		val innerExpression = ExpressionSemanticUtils.getExpressionThroughParenthesis(expression)

		val result = HashSet<PsiElement>()

		if (processed.contains(innerExpression))
			return result

		processed.add(innerExpression)

		when (innerExpression)
		{
			is TernaryExpression -> handleTernary(innerExpression, result, processed)
			is Variable -> handleVariable(innerExpression, result, processed)
			is FieldReference -> handleClassFieldReference(innerExpression, result, processed)
			is ClassConstantReference -> handleClassConstantReference(innerExpression, result)
			else -> result.add(innerExpression)
		}

		return result
	}

	/**
	 * Handles a variable.
	 *
	 * @param variable Variable
	 * @param result HashSet<PsiElement>
	 * @param processed HashSet<PsiElement>
	 *
	 * @author Bas Milius
	 */
	private fun handleVariable(@NotNull variable: Variable, @NotNull result: HashSet<PsiElement>, @NotNull processed: HashSet<PsiElement>)
	{
		val variableName = variable.name

		if (variableName.isEmpty())
			return

		val callable = ExpressionSemanticUtils.getScope(variable) ?: return

		for (parameter in callable.parameters)
		{
			val defaultValue = parameter.defaultValue

			if (defaultValue != null && parameter.name == variableName)
			{
				result.add(defaultValue)
				break
			}
		}

		val body = ExpressionSemanticUtils.getGroupStatement(callable)
		for (expression in PsiTreeUtil.findChildrenOfType(body, AssignmentExpression::class.java))
		{
			if (expression is SelfAssignmentExpression)
				continue

			val container = expression.variable
			val storedValue = expression.value

			if (storedValue != null && container is Variable)
			{
				val discoveredWrites = discover(storedValue, processed)

				if (discoveredWrites.isNotEmpty())
				{
					result.addAll(discoveredWrites)
					discoveredWrites.clear()
				}
			}
		}
	}

	/**
	 * Handles a class constant reference.
	 *
	 * @param reference ClassConstantReference
	 * @param result HashSet<PsiElement>
	 *
	 * @author Bas Milius
	 */
	private fun handleClassConstantReference(@NotNull reference: ClassConstantReference, @NotNull result: HashSet<PsiElement>)
	{
		val constantName = reference.name

		if (constantName == null || constantName.isEmpty())
			return

		val resolvedReference = reference.resolve() ?: return

		if (resolvedReference is Field)
		{
			val defaultValue = resolvedReference.defaultValue

			if (defaultValue != null)
			{
				result.add(defaultValue)
			}
		}
	}

	/**
	 * Handles a field reference.
	 *
	 * @param reference FieldReference
	 * @param result HashSet<PsiElement>
	 * @param processed HashSet<PsiElement>
	 *
	 * @author Bas Milius
	 */
	private fun handleClassFieldReference(@NotNull reference: FieldReference, @NotNull result: HashSet<PsiElement>, @NotNull processed: HashSet<PsiElement>)
	{
		val fieldName = reference.name

		if (fieldName == null || fieldName.isEmpty())
			return

		val resolvedReference = reference.resolve() ?: return

		if (resolvedReference is Field)
		{
			val defaultValue = resolvedReference.defaultValue

			if (defaultValue != null && !defaultValue.text.endsWith(fieldName))
			{
				result.add(defaultValue)
			}
		}

		val callable = ExpressionSemanticUtils.getScope(reference)
		if (callable != null)
		{
			val body = ExpressionSemanticUtils.getGroupStatement(callable)
			for (expression in PsiTreeUtil.findChildrenOfType(body, AssignmentExpression::class.java))
			{
				if (expression is SelfAssignmentExpression)
					continue

				val container = expression.variable
				val storedValue = expression.value

				if (storedValue != null && container is Variable)
				{
					val discoveredWrites = discover(storedValue, processed)

					if (discoveredWrites.isNotEmpty())
					{
						result.addAll(discoveredWrites)
						discoveredWrites.clear()
					}
				}
			}
		}
	}

	/**
	 * Handles a ternary expression.
	 *
	 * @param ternary TernaryExpression
	 * @param result HashSet<PsiElement>
	 * @param processed HashSet<PsiElement>
	 *
	 * @author Bas Milius
	 */
	private fun handleTernary(@NotNull ternary: TernaryExpression, @NotNull result: HashSet<PsiElement>, @NotNull processed: HashSet<PsiElement>)
	{
		val trueVariant = ternary.trueVariant
		val falseVariant = ternary.falseVariant

		if (trueVariant == null || falseVariant == null)
			return

		val trueVariants = discover(trueVariant, processed)
		val falseVariants = discover(falseVariant, processed)

		if (trueVariants.isNotEmpty())
		{
			result.addAll(trueVariants)
			trueVariants.clear()
		}

		if (falseVariants.isNotEmpty())
		{
			result.addAll(falseVariants)
			falseVariants.clear()
		}
	}

}
