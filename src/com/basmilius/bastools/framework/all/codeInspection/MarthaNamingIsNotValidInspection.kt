package com.basmilius.bastools.framework.all.codeInspection

import com.basmilius.bastools.framework.base.codeInspection.BaseInspection
import com.basmilius.bastools.framework.base.codeInspection.BaseQuickFix
import com.basmilius.bastools.framework.base.visitor.BaseElementVisitor
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.refactoring.rename.RenameUtil
import com.intellij.usageView.UsageInfo
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpNamedElement

/**
 * Class MarthaNamingIsNotValidInspection
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.codeInspection
 * @since 1.3.0
 */
class MarthaNamingIsNotValidInspection: BaseInspection("MarthaNamingIsNotValidInspection")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = MarthaNamingIsNotValidInspectionVisitor(problemsHolder, isOnTheFly)

	/**
	 * Inner Class MarthaNamingIsNotValidInspectionVisitor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.framework.all.codeInspection.MarthaNamingIsNotValidInspection
	 * @since 1.3.0
	 */
	inner class MarthaNamingIsNotValidInspectionVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): BaseElementVisitor(problemsHolder, isOnTheFly)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.3.0
		 */
		override fun visitPhpFunction(function: Function?)
		{
			if (function == null || function.name.isEmpty())
				return

			if (function.name[0].isUpperCase())
				problemsHolder.registerProblem(function.nameIdentifier!!, "Martha naming is not recommended for functions!", ProblemHighlightType.GENERIC_ERROR, MarthaNamingIsNotValidQuickFix())

			super.visitPhpFunction(function)
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.3.0
		 */
		override fun visitPhpMethod(method: Method?)
		{
			if (method == null || method.name.isEmpty())
				return

			if (method.name[0].isUpperCase())
				problemsHolder.registerProblem(method.nameIdentifier!!, "Martha naming is not recommended for class members!", ProblemHighlightType.GENERIC_ERROR, MarthaNamingIsNotValidQuickFix())

			super.visitPhpMethod(method)
		}

	}

	/**
	 * Inner Class MarthaNamingIsNotValidQuickFix
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.framework.all.codeInspection.MarthaNamingIsNotValidInspection
	 * @since 1.3.0
	 */
	inner class MarthaNamingIsNotValidQuickFix: BaseQuickFix("Replace with a proper naming scheme")
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.3.0
		 */
		override fun applyFix(project: Project, descriptor: ProblemDescriptor)
		{
			val element = descriptor.psiElement.parent ?: return
			val renames = mapOf<PsiElement, String>()

			if (element !is PhpNamedElement)
				return

			val newName = element.name[0].toLowerCase() + element.name.substring(1)
			val usages = RenameUtil.findUsages(element, newName, true, true, renames)

			RenameUtil.doRename(element, newName, usages, project, null)

			usages.forEach {
				RenameUtil.rename(it, newName)
			}
		}

	}

}
