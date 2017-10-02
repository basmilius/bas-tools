package com.basmilius.bastools.framework.all.codeInspection

import com.basmilius.bastools.framework.base.codeInspection.BaseInspection
import com.basmilius.bastools.framework.base.visitor.BaseElementVisitor
import com.basmilius.bastools.core.util.PossibleValuesDiscoveryUtils
import com.basmilius.bastools.core.util.TypesUtils
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.jetbrains.php.lang.PhpCallbackFunctionUtil
import com.jetbrains.php.lang.PhpCallbackReferenceBase
import com.jetbrains.php.lang.psi.elements.*

/**
 * Class CallableValidityInspector
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.framework.all.codeInspection
 */
class CallableValidityInspector: BaseInspection("CallableValidityInspector")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean) = CallableValidityInspectorVisitor(problemsHolder, isOnTheFly)

	/**
	 * Inner Class CallableValidityInspectorVisitor
	 *
	 * @author Bas Milius
	 * @package com.basmilius.bastools.framework.all.codeInspection.CallableValidityInspector
	 */
	inner class CallableValidityInspectorVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): BaseElementVisitor(problemsHolder, isOnTheFly)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius
		 */
		override fun visitPhpFunctionCall(reference: FunctionReference?)
		{
			if (reference == null)
				return

			val functionName = reference.name
			val parameters = reference.parameters

			for (parameter in parameters)
			{
			}

			if (parameters.size == 1 && functionName == "is_callable")
			{
				val processed = HashSet<PsiElement>()
				val values = PossibleValuesDiscoveryUtils.discover(parameters[0], processed)
				processed.clear()

				val callable: PsiElement?

				if (values.size == 1)
					callable = values.iterator().next()
				else
					return

				if (this.isTarget(callable))
				{
					val resolver = this.buildResolver(callable)

					if (resolver != null)
					{
						this.analyzeValidity(resolver.resolve(), parameters[0], callable)
					}
				}
			}
		}

		/**
		 * Builds the callable resolver.
		 *
		 * @return PsiReference?
		 *
		 * @author Bas Milius
		 */
		private fun buildResolver(callable: PsiElement): PsiReference?
		{
			var result: PhpCallbackReferenceBase? = null
			val callback = PhpCallbackFunctionUtil.createCallback(callable)

			if (callback is PhpCallbackFunctionUtil.PhpMemberCallbackInfoHolder)
			{
				val classReference = callback.classElement
				result = PhpCallbackReferenceBase.createMemberReference(classReference, callback.callbackElement, true)
			}

			return result
		}

		/**
		 * Returns true if the callable is a target.
		 *
		 * @return Boolean
		 *
		 * @author Bas Milius
		 */
		private fun isTarget(callable: PsiElement): Boolean
		{
			var result = callable is StringLiteralExpression

			if (!result && callable is ArrayCreationExpression)
				result = callable.children.size == 2

			return result
		}

		/**
		 * Analyzes the validity of an element.
		 *
		 * @author Bas Milius
		 */
		private fun analyzeValidity(element: PsiElement?, target: PsiElement, callable: PsiElement)
		{
			if (element !is Method)
				return

			if (!element.access.isPublic)
			{
				problemsHolder.registerProblem(target, "${element.name} should be public. This would result in a runtime error.", ProblemHighlightType.GENERIC_ERROR_OR_WARNING)
			}

			var needStatic = false
			if (callable is StringLiteralExpression && !element.isStatic)
			{
				needStatic = true
			}

			if (callable is ArrayCreationExpression && !element.isStatic)
			{
				val classCandidate = callable.children[0].firstChild

				if (classCandidate is PhpTypedElement)
				{
					val project = classCandidate.project

					for (type in classCandidate.type.global(project).filterUnknown().types)
					{
						val resolvedType = TypesUtils.getType(type)

						if (resolvedType == TypesUtils.strString || resolvedType == TypesUtils.strCallable)
						{
							needStatic = true
							break
						}
					}
				}

				if (!needStatic && classCandidate is ClassConstantReference)
				{
					val constantName = classCandidate.name
					needStatic = constantName != null && constantName == "class"
				}
			}

			if (needStatic)
			{
				problemsHolder.registerProblem(target, "${element.name} should be static. This would result in a runtime error.", ProblemHighlightType.GENERIC_ERROR_OR_WARNING)
			}
		}

	}

}
