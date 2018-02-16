/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.framework.all.completion.confidence

import com.intellij.codeInsight.completion.CompletionConfidence
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.util.ThreeState
import com.jetbrains.php.lang.psi.PhpFile
import com.jetbrains.php.lang.psi.elements.ParameterList
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression

/**
 * Class PhpParameterStringCompletionConfidence
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.completion.confidence
 * @since 1.2.0
 */
class PhpParameterStringCompletionConfidence: CompletionConfidence()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.2.0
	 */
	override fun shouldSkipAutopopup(contextElement: PsiElement, psiFile: PsiFile, offset: Int): ThreeState
	{
		if (psiFile !is PhpFile)
			return ThreeState.UNSURE

		val context = contextElement.context as? StringLiteralExpression ?: return ThreeState.UNSURE
		val stringContext = context.context

		if (stringContext is ParameterList)
			return ThreeState.NO

		return ThreeState.UNSURE
	}

}
