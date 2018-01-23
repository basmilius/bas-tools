package com.basmilius.bastools.framework.all.completion

import com.basmilius.bastools.framework.base.completion.BaseCompletionProvider
import com.basmilius.bastools.resource.Icons
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.elements.Function

/**
 * Class FunctionCallAllArgumentsCompletionProvider
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.framework.all.completion
 * @since 1.0.0
 */
class FunctionCallAllArgumentsCompletionProvider: BaseCompletionProvider()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, results: CompletionResultSet)
	{
		val func = PsiTreeUtil.getParentOfType(parameters.originalPosition, Function::class.java)

		if (func === null)
			return

		val ps = func.parameters
		val pss = arrayOfNulls<String>(ps.size)

		for (i in ps.indices)
			pss[i] = "$" + ps[i].name

		val lookupElement = LookupElementBuilder.create(pss.joinToString(", ")).withBoldness(true).withIcon(Icons.BasTools)
		val lookupElementPrio = PrioritizedLookupElement.withPriority(lookupElement, 99999999999.0)

		results.addElement(lookupElementPrio)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun getPlace(): ElementPattern<out PsiElement> = PlatformPatterns.psiElement().inside(PlatformPatterns.psiElement(PhpElementTypes.FUNCTION_CALL))

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun isAvailable() = false // Disabled. Need to fix this first.

}
