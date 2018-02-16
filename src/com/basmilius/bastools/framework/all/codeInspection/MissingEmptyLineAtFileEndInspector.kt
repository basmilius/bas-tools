/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.all.codeInspection

import com.basmilius.bastools.framework.base.codeInspection.BaseInspection
import com.basmilius.bastools.framework.base.codeInspection.BaseQuickFix
import com.basmilius.bastools.framework.base.visitor.BaseElementVisitor
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiParserFacade

/**
 * Class MissingEmptyLineAtFileEndInspector
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.codeInspection
 * @since 1.3.1
 */
class MissingEmptyLineAtFileEndInspector: BaseInspection("MissingEmptyLineAtFileEndInspector")
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.1
	 */
	override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean) = MissingEmptyLineAtFileEndVisitor(problemsHolder, isOnTheFly)

	/**
	 * Inner Class MissingEmptyLineAtFileEndVisitor
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.framework.all.codeInspection.MissingEmptyLineAtFileEndInspector
	 * @since 1.3.1
	 */
	inner class MissingEmptyLineAtFileEndVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): BaseElementVisitor(problemsHolder, isOnTheFly)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.3.1
		 */
		override fun visitFile(file: PsiFile)
		{
			if (file.text.endsWith('\n'))
				return

			problemsHolder.registerProblem(file.lastChild, "Missing empty line!", ProblemHighlightType.GENERIC_ERROR_OR_WARNING, MissingEmptyLineAtFileEndQuickFix())
		}

	}

	/**
	 * Inner Class MissingEmptyLineAtFileEndQuickFix
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.framework.all.codeInspection.MissingEmptyLineAtFileEndInspector
	 * @since 1.3.1
	 */
	inner class MissingEmptyLineAtFileEndQuickFix: BaseQuickFix("Add empty line")
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.3.1
		 */
		override fun applyFix(project: Project, descriptor: ProblemDescriptor)
		{
			val element = descriptor.psiElement

			element.parent.addAfter(PsiParserFacade.SERVICE.getInstance(project).createWhiteSpaceFromText("\n"), element)
		}

	}

}
