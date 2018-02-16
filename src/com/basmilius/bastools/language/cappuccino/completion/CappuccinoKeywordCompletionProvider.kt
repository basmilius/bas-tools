/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.language.cappuccino.completion

import com.basmilius.bastools.framework.base.completion.BaseCompletionProvider
import com.basmilius.bastools.language.cappuccino.CappuccinoTokenTypes
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoCompositeElement
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.util.PlatformUtils
import com.intellij.util.ProcessingContext

/**
 * Class CappuccinoKeywordCompletionProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.completion
 */
class CappuccinoKeywordCompletionProvider: BaseCompletionProvider()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, results: CompletionResultSet)
	{
		val current = parameters.position.originalElement

		if (current.node.elementType == CappuccinoTokenTypes.TAG_NAME)
		{
			CappuccinoCompletionContributor.addTagNameLookups(results)
		}
		else
		{
			var previous = current.prevSibling

			if (previous != null && previous is PsiWhiteSpace)
				previous = previous.prevSibling

			if (previous != null && previous.node.elementType == CappuccinoTokenTypes.FILTER)
				CappuccinoCompletionContributor.addBuildInFilterLookups(results)
		}
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isAvailable() = PlatformUtils.isPhpStorm()

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getPlace(): ElementPattern<out PsiElement> = PlatformPatterns.psiElement().withParent(CappuccinoCompositeElement::class.java)
}
