package com.basmilius.ps.bastools.util

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.elements.*
import org.jetbrains.annotations.NotNull

object PossibleValuesDiscoveryUtils
{

	fun discover(@NotNull expression : PsiElement, @NotNull processed : HashSet<PsiElement>) : HashSet<PsiElement>
	{
		val innerExpression = ExpressionSemanticUtils.getExpressionThroughParenthesis(expression)

		val result = HashSet<PsiElement>()

		if (processed.contains(innerExpression))
			return result

		processed.add(innerExpression)

		if (innerExpression is TernaryExpression) // Case 1: Ternary, recursively check variants
		{
			handleTernary(innerExpression, result, processed)
		}
		else if (innerExpression is Variable) // Case 2: Parameter defaults, assignments
		{
			handleVariable(innerExpression, result, processed)
		}
		else if (innerExpression is FieldReference) // Case 3: Default value discovery
		{
			handleClassFieldReference(innerExpression, result, processed)
		}
		else if (innerExpression is ClassConstantReference) // Case 4: Constants value discovery
		{
			handleClassConstantReference(innerExpression, result)
		}
		else
		{
			// Default case: Add expression itself
			result.add(innerExpression)
		}

		return result
	}

	private fun handleVariable(@NotNull variable : Variable, @NotNull result : HashSet<PsiElement>, @NotNull processed : HashSet<PsiElement>)
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

	private fun handleClassConstantReference(@NotNull reference : ClassConstantReference, @NotNull result : HashSet<PsiElement>)
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

	private fun handleClassFieldReference(@NotNull reference : FieldReference, @NotNull result : HashSet<PsiElement>, @NotNull processed : HashSet<PsiElement>)
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

	private fun handleTernary(@NotNull ternary : TernaryExpression, @NotNull result : HashSet<PsiElement>, @NotNull processed : HashSet<PsiElement>)
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
