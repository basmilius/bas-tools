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
import com.basmilius.bastools.language.cappuccino.elements.CappuccinoBlockTag
import com.basmilius.bastools.language.cappuccino.util.CappuccinoLookupUtil
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.Condition
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.util.PlatformUtils
import com.intellij.util.ProcessingContext

/**
 * Class CappuccinoBlockCompletionProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.language.cappuccino.completion
 */
class CappuccinoBlockCompletionProvider: BaseCompletionProvider()
{

	/**
	 * Class CappuccinoBlockCompletionProvider
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.language.cappuccino.completion
	 */
	companion object
	{

		/**
		 * Collects block names from Cappuccino-files.
		 *
		 * @param results CompletionResultSet
		 * @param completionElement PsiElement
		 *
		 * @author Bas Milius <bas@mili.us>
		 */
		fun collectBlockNames(results: CompletionResultSet, completionElement: PsiElement)
		{
			if (!completionElement.isValid)
				return

			val file = completionElement.containingFile
			val finder = object: CappuccinoLookupUtil.ElementFinder(Condition { element -> element is CappuccinoBlockTag })
			{

				/**
				 * {@inheritdoc}
				 *
				 * @author Bas Milius <bas@mili.us>
				 */
				override fun handleMatch(element: PsiElement?): Boolean
				{
					if (element == null || element !is CappuccinoBlockTag)
						return false

					if (element.name != null && element.name!!.isNotEmpty())
						results.addElement(LookupElementBuilder.create(element.name!!))

					return false
				}

			}

			CappuccinoLookupUtil.lookupExtends(file, finder)
		}

	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, results: CompletionResultSet)
	{
		val current = parameters.position.originalElement

		if (current.node.elementType != CappuccinoTokenTypes.IDENTIFIER)
			return

		var previous = current.prevSibling ?: return

		if (previous is PsiWhiteSpace)
			previous = previous.prevSibling ?: return

		if (previous.node.elementType == CappuccinoTokenTypes.TAG_NAME && previous.text == "block")
		{
			var blockNamePrefix = results.prefixMatcher.prefix
			val spacePosition = blockNamePrefix.lastIndexOf(32.toChar())

			blockNamePrefix = if (spacePosition > 0) blockNamePrefix.substring(spacePosition + 1) else ""

			collectBlockNames(results.withPrefixMatcher(blockNamePrefix), current)
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
	override fun getPlace(): ElementPattern<out PsiElement> = PlatformPatterns.psiElement().withParent(CappuccinoBlockTag::class.java)

}
