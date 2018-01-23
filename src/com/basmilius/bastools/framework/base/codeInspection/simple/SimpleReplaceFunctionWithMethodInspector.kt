package com.basmilius.bastools.framework.base.codeInspection.simple

import com.basmilius.bastools.framework.base.codeInspection.BaseInspection
import com.basmilius.bastools.framework.base.codeInspection.BaseQuickFix
import com.basmilius.bastools.framework.base.visitor.BaseElementVisitor
import com.basmilius.bastools.framework.identity.IdentityFramework
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.FunctionReference

/**
 * Class SimpleReplaceFunctionWithMethodInspector
 *
 * @constructor
 * @param functionName String
 * @param methodCall String
 * @param shortName String
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.base.codeInspection.simple
 * @since 1.1.0
 */
abstract class SimpleReplaceFunctionWithMethodInspector(val functionName: String, val methodCall: String, shortName: String): BaseInspection(shortName)
{

	/**
	 * Builds the visitor.
	 *
	 * @return BaseElementVisitor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor
	{
		return SimpleReplaceFunctionWithMethodInspectorVisitor(problemsHolder, isOnTheFly)
	}

	/**
	 * Inner Class SimpleReplaceFunctionWithMethodInspectorVisitor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.framework.base.codeInspection.simple.SimpleReplaceFunctionWithMethodInspector
	 * @since 1.1.0
	 */
	inner class SimpleReplaceFunctionWithMethodInspectorVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): BaseElementVisitor(problemsHolder, isOnTheFly)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		override fun visitPhpFunctionCall(reference: FunctionReference?)
		{
			if (reference == null)
				return

			if (!IdentityFramework.isIdentityFrameworkProject(reference.project))
				return

			val functionName = reference.name

			if (functionName != this@SimpleReplaceFunctionWithMethodInspector.functionName)
				return

			if (!this@SimpleReplaceFunctionWithMethodInspector.isValidFile(reference.project, reference.containingFile))
				return

			this.problemsHolder.registerProblem(reference.element, String.format("Function '%s' could be replaced with '%s'.", functionName, this@SimpleReplaceFunctionWithMethodInspector.methodCall), SimpleReplaceFunctionWithMethodInspectorQuickFix())
		}

	}

	/**
	 * Inner Class SimpleReplaceFunctionWithMethodInspectorQuickFix
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.framework.base.codeInspection.simple.SimpleReplaceFunctionWithMethodInspector
	 * @since 1.1.0
	 */
	inner class SimpleReplaceFunctionWithMethodInspectorQuickFix: BaseQuickFix("Replace with '${this@SimpleReplaceFunctionWithMethodInspector.methodCall}'")
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		override fun applyFix(project: Project, descriptor: ProblemDescriptor)
		{
			val psi = descriptor.psiElement as? FunctionReference ?: return

			val parameters = psi.parameterList!!
			val call = PhpPsiElementFactory.createMethodReference(project, "${this@SimpleReplaceFunctionWithMethodInspector.methodCall}();")

			var isFirst = true

			for (parameter in parameters.parameters)
			{
				if (!isFirst)
				{
					call.parameterList?.add(PhpPsiElementFactory.createComma(project))
				}

				call.parameterList?.add(parameter)

				isFirst = false
			}

			psi.replace(call)
		}

	}

}