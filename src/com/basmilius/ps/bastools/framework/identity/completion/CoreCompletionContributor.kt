package com.basmilius.ps.bastools.framework.identity.completion

import com.intellij.codeInsight.completion.*
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.elements.Method

class CoreCompletionContributor : CompletionContributor()
{
	/**
	 * CoreCompletionContributor Constructor.
	 */
	init
	{
		this.extend(CompletionType.BASIC, PlatformPatterns.psiElement().inside(PlatformPatterns.psiElement(PhpElementTypes.METHOD_REFERENCE)), PluginCallProvider())
	}

	internal inner class PluginCallProvider : CompletionProvider<CompletionParameters>()
	{

		override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, results: CompletionResultSet)
		{
			val func = PsiTreeUtil.getContextOfType(parameters.originalPosition, Method::class.java) ?: return

		}

	}

}
