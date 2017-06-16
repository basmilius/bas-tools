package com.basmilius.ps.bastools.framework.all.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement

class AllCompletionContributor : CompletionContributor()
{

	init
	{
		@Suppress("UNCHECKED_CAST")
		this.extend(CompletionType.BASIC, FunctionCallAllArgumentsCompletionProvider.PLACE as ElementPattern<out PsiElement>, FunctionCallAllArgumentsCompletionProvider())
	}

}
