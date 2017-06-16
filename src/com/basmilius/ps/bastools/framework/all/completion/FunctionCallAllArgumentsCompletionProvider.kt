package com.basmilius.ps.bastools.framework.all.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.lookup.LookupValueWithPriority
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.elements.Function

class FunctionCallAllArgumentsCompletionProvider : CompletionProvider<CompletionParameters>()
{

	/**
	 * {@inheritdoc}
	 */
	override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, results: CompletionResultSet)
	{
		val func = PsiTreeUtil.getParentOfType(parameters.originalPosition, Function::class.java)

		if (func != null)
		{
			val ps = func.parameters
			val pss = arrayOfNulls<String>(ps.size)

			for (i in ps.indices)
			{
				pss[i] = "$" + ps[i].name
			}

			results.addElement(PrioritizedLookupElement.withPriority(LookupElementBuilder.create(pss.joinToString(", ")), LookupValueWithPriority.HIGHER.toDouble()))
		}
	}

	companion object
	{

		var PLACE: ElementPattern<*> = PlatformPatterns.psiElement().inside(PlatformPatterns.psiElement(PhpElementTypes.FUNCTION_CALL))
	}

}
