package com.basmilius.bastools.framework.all.completion

import com.basmilius.bastools.framework.base.completion.BaseCompletionProvider
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import com.jetbrains.localization.LocaleElementTypes

/**
 * Class GetTextCompletionProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.completion
 */
class GetTextCompletionProvider: BaseCompletionProvider()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, results: CompletionResultSet)
	{
		results.addElement(LookupElementBuilder.create("List with strings from .pot-file (parent) or index source code for gettext strings."))
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun isAvailable(): Boolean = true

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getPlace(): ElementPattern<out PsiElement> = PlatformPatterns.psiElement().inside(PlatformPatterns.psiElement(LocaleElementTypes.STRING_LITERAL_EXPRESSION))

}
