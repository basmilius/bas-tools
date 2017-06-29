package com.basmilius.ps.bastools.framework.all.completion

import com.basmilius.ps.bastools.framework.base.completion.BaseCompletionProvider
import com.basmilius.ps.bastools.resource.Icons
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
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.framework.all.completion
 */
class FunctionCallAllArgumentsCompletionProvider : BaseCompletionProvider()
{

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun addCompletions(parameters : CompletionParameters, context : ProcessingContext, results : CompletionResultSet)
	{
		val func = PsiTreeUtil.getParentOfType(parameters.originalPosition, Function::class.java)

		if (func === null)
			return

		val ps = func.parameters
		val pss = arrayOfNulls<String>(ps.size)

		for (i in ps.indices)
			pss[i] = "$" + ps[i].name

		val lookupElement = LookupElementBuilder.create(pss.joinToString(", ")).withBoldness(true).withIcon(Icons.Beats)
		val lookupElementPrio = PrioritizedLookupElement.withPriority(lookupElement, 99999999999.0)

		results.addElement(lookupElementPrio)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getPlace() : ElementPattern<out PsiElement>
	{
		return PlatformPatterns.psiElement().inside(PlatformPatterns.psiElement(PhpElementTypes.FUNCTION_CALL))
	}

}
